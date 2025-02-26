-- liquibase formatted sql

-- changeset Anastasia Martova:add-online-status-column
ALTER TABLE accounts
    ADD COLUMN online BOOLEAN;  -- Allow NULL initially

-- changeset Anastasia Martova:populate-online-status
UPDATE accounts SET online = FALSE WHERE online IS NULL;

-- changeset Anastasia Martova:add-not-null-constraint-to-online-status
ALTER TABLE accounts
    ALTER COLUMN online SET NOT NULL;

-- changeset Anastasia Martova:add-last-online-time-column
ALTER TABLE accounts
    ADD COLUMN last_online_time TIMESTAMP;