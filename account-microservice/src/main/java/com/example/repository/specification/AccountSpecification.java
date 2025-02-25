package com.example.repository.specification;

import com.example.dto.AccountFilterDto;
import com.example.entity.Account;
import com.example.security.SecurityUtils;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AccountSpecification {

    String BIRTH_DATE = "birthDate";

    static Specification<Account> withFilter(AccountFilterDto afd) {
        return Specification.where(
                byIds(afd.getIds()))
                .and(byFirstName(afd.getFirstName()))
                .and(byLastName(afd.getLastName()))
                .and(byCity(afd.getCity()))
                .and(byCountry(afd.getCountry()))
                .and(byAge(afd.getAgeFrom(), afd.getAgeTo()))
                .and(excludeCurrentAccount());
    }

    static Specification<Account> byIds(List<UUID> ids) {
        return (root, query, cb) -> {
            if (ids == null || ids.isEmpty()) {
                return null;
            }

            return root.get("id").in(ids);
        };
    }

    static Specification<Account> byFirstName(String firstName) {
        return (root, query, cb) -> {
            if (firstName == null || firstName.isEmpty()) {
                return null;
            }

            return cb.like(cb.lower(root.get("firstName")), "%" + firstName.trim().toLowerCase() + "%");
        };
    }

    static Specification<Account> byLastName(String lastName) {
        return (root, query, cb) -> {
            if (lastName == null || lastName.isEmpty()) {
                return null;
            }

            return cb.like(cb.lower(root.get("lastName")), "%" + lastName.trim().toLowerCase() + "%");
        };
    }

    static Specification<Account> byCity(String city) {
        return (root, query, cb) -> {
            if (city == null || city.isEmpty()) {
                return null;
            }

            return cb.like(cb.lower(root.get("city")), "%" + city.trim().toLowerCase() + "%");
        };
    }


    static Specification<Account> byCountry(String country) {
        return (root, query, cb) -> {
            if (country == null || country.isEmpty()) {
                return null;
            }

            return cb.like(cb.lower(root.get("country")), "%" + country.trim().toLowerCase() + "%");
        };
    }

    static Specification<Account> byAge(Integer ageFrom, Integer ageTo) {
        return (root, query, cb) -> {
            if (ageFrom == null && ageTo == null) {
                return null;
            }

            LocalDate currentDate = LocalDate.now();
            LocalDate birthDateFrom = ageFrom != null ? currentDate.minusYears(ageFrom) : null;
            LocalDate birthDateTo = ageTo != null ? currentDate.minusYears(ageTo) : null;

            if (birthDateFrom != null && birthDateTo != null) {
                return cb.between(root.get(BIRTH_DATE),
                        Timestamp.valueOf(birthDateTo.atStartOfDay()),
                        Timestamp.valueOf(birthDateFrom.atStartOfDay()));
            }

            if (birthDateFrom != null) {
                return cb.lessThanOrEqualTo(root.get(BIRTH_DATE),
                        Timestamp.valueOf(birthDateFrom.atStartOfDay()));
            }

            return cb.greaterThanOrEqualTo(root.get(BIRTH_DATE),
                    Timestamp.valueOf(birthDateTo.atStartOfDay()));
        };
    }

    static Specification<Account> excludeCurrentAccount() {
        UUID currentAccountId = SecurityUtils.getUUIDFromSecurityContext();
        return (root, query, cb) -> cb.notEqual(root.get("id"), currentAccountId);
    }

}