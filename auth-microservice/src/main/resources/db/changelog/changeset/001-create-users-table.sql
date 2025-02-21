--liquibase formatted sql

--changeset Anastasia Martova:create-users-table
CREATE TABLE users
(
    id         UUID PRIMARY KEY,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    is_locked  BOOLEAN NOT NULL
);

--changeset Anastasia Martova:create-user-roles-table
CREATE TABLE user_roles
(
    user_id UUID         NOT NULL,
    roles   VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

--changeset Anastasia Martova:add-index-to-user-roles
CREATE INDEX idx_user_roles_user_id ON user_roles (user_id);

--changeset Anastasia Martova:add-unique-index-to-users-email
CREATE UNIQUE INDEX uc_users_email ON users (email);