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
('Non-stick Frying Pan', 'Durable pan, perfect for blocking bullets and everyday cooking.', 25.50, true, false, NOW(), NOW(), 180, 3),
('Atomic Habits', 'Book by James Clear on building good habits.', 17.99, true, true, NOW(), NOW(), 500, 4),
('Vitamin C Serum', 'Brightening serum for healthy skin.', 19.99, true, false, NOW(), NOW(), 300, 5);
INSERT INTO product_images (
    image_url, alt_text, display_order, created_at, product_id
) VALUES
('https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-14-plus_1_.png', 'Front view of iPhone 14', 1, NOW(), 1),
('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRTIN8l5A45JRRcn7WwsZN_7YfOPrDcmwkH_w&s', 'Back view of iPhone 14', 2, NOW(), 1),
('https://static.nike.com/a/images/t_PDP_936_v1/f_auto,q_auto:eco/e783e052-9360-4afb-adb8-c4e9c0f5db07/NIKE+AIR+MAX+NUAXIS.png', 'Side view of Nike Air Max', 1, NOW(), 2),
('https://bizweb.dktcdn.net/100/340/361/products/dh2987-002-19.jpg?v=1726735475647','Back view of Nike Air Max', 2, NOW(), 2),
('https://i.ebayimg.com/00/s/NTk4WDYxMQ==/z/hRAAAOSwaGBbhVpX/$_1.JPG?set_id=8800005007', 'Non-stick frying pan', 1, NOW(), 3),
('https://cdn.shopify.com/s/files/1/0194/2855/files/atomic-habits_600x600.jpg?v=1624825894', 'Cover of Atomic Habits book', 1, NOW(), 4),
('https://m.media-amazon.com/images/I/51h+qCXUaSL._SL1000_.jpg', 'Bottle of Vitamin C Serum', 1, NOW(), 5);
