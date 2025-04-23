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
    created_at TIMESTAMP,
    image TEXT
);


INSERT INTO categories (category_name, description, created_at, image) VALUES
('Electronics', 'Devices and gadgets like phones, laptops, and cameras.', NOW(), 'https://example.com/images/electronics.jpg'),
('Fashion', 'Clothing, footwear, and accessories for men and women.', NOW(), 'https://example.com/images/fashion.jpg'),
('Home & Kitchen', 'Appliances, furniture, and home improvement items.', NOW(), 'https://example.com/images/home_kitchen.jpg'),
('Books', 'Fiction, non-fiction, educational books, and more.', NOW(), 'https://example.com/images/books.jpg'),
('Health & Beauty', 'Skincare, wellness products, and personal care items.', NOW(), 'https://example.com/images/health_beauty.jpg'),
('Toys & Games', 'Toys, games, and puzzles for all ages.', NOW(), 'https://example.com/images/toys_games.jpg'),
('Sports & Outdoors', 'Gear and equipment for sports, fitness, and outdoor activities.', NOW(), 'https://example.com/images/sports_outdoors.jpg'),
('Automotive', 'Car accessories, parts, and tools.', NOW(), 'https://example.com/images/automotive.jpg'),
('Pet Supplies', 'Food, toys, and essentials for pets.', NOW(), 'https://example.com/images/pet_supplies.jpg'),
('Groceries', 'Everyday essentials including food, drinks, and household items.', NOW(), 'https://example.com/images/groceries.jpg');

CREATE TABLE products (
    product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    is_active BOOLEAN NOT NULL,
    is_featured BOOLEAN NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    stock BIGINT,
    category_id BIGINT
);
CREATE TABLE product_images (
    image_id SERIAL PRIMARY KEY,
    image_url TEXT NOT NULL,
    alt_text VARCHAR(255),
    display_order INTEGER,
    created_at TIMESTAMP,
    product_id BIGINT,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);
INSERT INTO products (
    product_name, description, price, is_active, is_featured,
    created_at, updated_at, stock, category_id
) VALUES
('iPhone 14', 'Latest Apple smartphone with A15 Bionic chip.', 999.99, true, true, NOW(), NOW(), 100, 1),
('Nike Air Max', 'Comfortable and stylish running shoes.', 129.99, true, false, NOW(), NOW(), 250, 2),
('Non-stick Frying Pan', 'Durable pan perfect for everyday cooking.', 25.50, true, false, NOW(), NOW(), 180, 3),
('Atomic Habits', 'Book by James Clear on building good habits.', 17.99, true, true, NOW(), NOW(), 500, 4),
('Vitamin C Serum', 'Brightening serum for healthy skin.', 19.99, true, false, NOW(), NOW(), 300, 5);
INSERT INTO product_images (
    image_url, alt_text, display_order, created_at, product_id
) VALUES
('https://example.com/images/iphone14-front.jpg', 'Front view of iPhone 14', 1, NOW(), 1),
('https://example.com/images/iphone14-back.jpg', 'Back view of iPhone 14', 2, NOW(), 1),
('https://example.com/images/nike-air-max-side.jpg', 'Side view of Nike Air Max', 1, NOW(), 2),
('https://example.com/images/nike-air-max-top.jpg', 'Top view of Nike Air Max', 2, NOW(), 2),
('https://example.com/images/frying-pan.jpg', 'Non-stick frying pan', 1, NOW(), 3),
('https://example.com/images/atomic-habits-cover.jpg', 'Cover of Atomic Habits book', 1, NOW(), 4),
('https://example.com/images/vitamin-c-serum.jpg', 'Bottle of Vitamin C Serum', 1, NOW(), 5);
