CREATE TABLE user_roles (
    role_id SERIAL PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT
);
INSERT INTO user_roles (role_name, description) VALUES
('ROLE_ADMIN', 'Administrator with full access'),
('ROLE_CUSTOMER', 'Customer');

CREATE TABLE categories (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP
);

INSERT INTO categories (category_name, description, created_at) VALUES
('Electronics', 'Devices and gadgets like phones, laptops, and cameras.', NOW()),
('Fashion', 'Clothing, footwear, and accessories for men and women.', NOW()),
('Home & Kitchen', 'Appliances, furniture, and home improvement items.', NOW()),
('Books', 'Fiction, non-fiction, educational books, and more.', NOW()),
('Health & Beauty', 'Skincare, wellness products, and personal care items.', NOW()),
('Toys & Games', 'Toys, games, and puzzles for all ages.', NOW()),
('Sports & Outdoors', 'Gear and equipment for sports, fitness, and outdoor activities.', NOW()),
('Automotive', 'Car accessories, parts, and tools.', NOW()),
('Pet Supplies', 'Food, toys, and essentials for pets.', NOW()),
('Groceries', 'Everyday essentials including food, drinks, and household items.', NOW());

