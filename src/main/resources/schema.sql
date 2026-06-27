-- Schema for the Starzplay payment configuration service.
-- Runs automatically on startup (spring.sql.init.mode=always).

CREATE TABLE IF NOT EXISTS payment_method (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    display_name  VARCHAR(255) NOT NULL,
    payment_type  VARCHAR(255) NOT NULL,
    country       VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS payment_plan (
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    net_amount         DOUBLE NOT NULL,
    tax_amount         DOUBLE NOT NULL,
    gross_amount       DOUBLE NOT NULL,
    currency           VARCHAR(255) NOT NULL,
    duration           VARCHAR(255) NOT NULL,
    payment_method_id  INT NOT NULL,
    CONSTRAINT fk_plan_method FOREIGN KEY (payment_method_id) REFERENCES payment_method (id)
);

CREATE TABLE IF NOT EXISTS users (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(255) NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS roles (
    id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    role  VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id  BIGINT NOT NULL,
    role_id  BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles (id)
);
