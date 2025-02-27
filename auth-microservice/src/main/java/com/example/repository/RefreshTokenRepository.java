package com.example.repository;

import com.example.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByData(String data);

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUserId(UUID id);

}