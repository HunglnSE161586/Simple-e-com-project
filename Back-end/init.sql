CREATE TABLE user_roles (
    role_id SERIAL PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT
);
INSERT INTO user_roles (role_name, description) VALUES
('ROLE_ADMIN', 'Administrator with full access'),
('ROLE_CUSTOMER', 'Customer');
