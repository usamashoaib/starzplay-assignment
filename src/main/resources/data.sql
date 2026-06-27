-- Seed data for the Starzplay payment configuration service.
-- Runs automatically on startup (spring.sql.init.mode=always).
-- INSERT IGNORE keeps startup idempotent across restarts.

-- Payment methods (mirrors the assignment sample response)
INSERT IGNORE INTO payment_method (id, name, display_name, payment_type, country) VALUES
    (1, 'credit card', 'credit card',  'CREDIT_CARD',    'US'),
    (2, 'alfa_lb',     'Alfa Lebanon', 'MOBILE_CARRIER', 'SA'),
    (3, 'voucher',     'Voucher',      'VOUCHER',        'AE');

-- Payment plans (voucher intentionally has none)
INSERT IGNORE INTO payment_plan (id, net_amount, tax_amount, gross_amount, currency, duration, payment_method_id) VALUES
    (1,  5.99, 0, 5.99, 'USD', 'Month', 1),
    (72, 5.99, 0, 5.99, 'USD', 'Month', 2),
    (54, 10,   0, 10,   'SAR', 'Week',  2);

-- Auth seed: username 'admin', password 'password'
-- (BCrypt cost-10 hash of "password")
INSERT IGNORE INTO users (id, username, password) VALUES
    (1, 'admin', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG');

INSERT IGNORE INTO roles (id, role) VALUES
    (1, 'ROLE_USER');

INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
    (1, 1);
