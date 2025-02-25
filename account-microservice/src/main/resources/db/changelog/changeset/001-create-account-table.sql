-- liquibase formatted sql

-- changeset Anastasia Martova:create-accounts-table
CREATE TABLE accounts
(
    id               UUID PRIMARY KEY,
    email            VARCHAR(255) NOT NULL,
    first_name       VARCHAR(100) NOT NULL,
    last_name        VARCHAR(100) NOT NULL,
    phone            VARCHAR(255),
    city             VARCHAR(50),
    country          VARCHAR(50),
    birth_date       TIMESTAMP,
    about            VARCHAR(255),
    created_on       TIMESTAMP    NOT NULL,
    updated_on       TIMESTAMP    NOT NULL
);