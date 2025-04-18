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
('Anime', 'Figures based on Japanese anime characters.', NOW()),
('Manga', 'Figures inspired by Japanese manga series.', NOW()),
('Video Games', 'Figures from popular video game franchises.', NOW()),
('Movies & TV', 'Figures from live-action and animated films/series.', NOW()),
('Superheroes', 'Characters from Marvel, DC, and other comic universes.', NOW()),
('Mecha', 'Robot and mechanical figures, e.g., Gundam.', NOW()),
('Fantasy', 'Fantasy-themed figures like elves, dragons, and knights.', NOW()),
('Horror', 'Figures based on horror movie monsters and characters.', NOW()),
('Chibi / SD', 'Super-deformed or chibi-style figures.', NOW()),
('Limited Edition', 'Rare or special release figures.', NOW());

