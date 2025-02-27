package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID>, JpaSpecificationExecutor<Account> {

    boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.isOnline = true, a.lastOnlineTime = CURRENT_TIMESTAMP WHERE a.id = :accountId")
    void markAccountAsOnline(UUID accountId);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.isOnline = false WHERE a.isOnline = true AND a.lastOnlineTime < :cutoffTime")
    void updateOfflineStatus(LocalDateTime cutoffTime);

}