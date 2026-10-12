-- ==========================================================
-- FoodieBite - MySQL Database Schema & Initial Seed Data
-- Import this file in phpMyAdmin or MySQL Workbench if needed
-- ==========================================================

CREATE DATABASE IF NOT EXISTS `foodiedb` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `foodiedb`;

-- 1. Categories Table
DROP TABLE IF EXISTS `order_items`;
DROP TABLE IF EXISTS `orders`;
DROP TABLE IF EXISTS `food_items`;
DROP TABLE IF EXISTS `categories`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `categories` (
    `id` VARCHAR(50) NOT NULL,
    `name` VARCHAR(100) NOT NULL,
    `icon` VARCHAR(50) DEFAULT 'fa-utensils',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `categories` (`id`, `name`, `icon`) VALUES
('all', 'All Items', 'fa-utensils'),
('pizza', 'Pizza', 'fa-pizza-slice'),
('burgers', 'Burgers', 'fa-hamburger'),
('pasta', 'Pasta', 'fa-bowl-food'),
('asian', 'Asian Dishes', 'fa-bowl-rice'),
('desserts', 'Desserts', 'fa-cake-candles'),
('beverages', 'Beverages', 'fa-mug-hot');

-- 2. Food Items Table
CREATE TABLE `food_items` (
    `id` VARCHAR(50) NOT NULL,
    `name` VARCHAR(150) NOT NULL,
    `category` VARCHAR(50) NOT NULL,
    `price` DECIMAL(10,2) NOT NULL,
    `rating` DECIMAL(3,1) DEFAULT 4.5,
    `prep_time` VARCHAR(50) DEFAULT '20 mins',
    `dietary` VARCHAR(20) DEFAULT 'veg',
    `badge` VARCHAR(50) DEFAULT NULL,
    `description` TEXT,
    `image` VARCHAR(500) DEFAULT NULL,
    `available` BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `food_items` (`id`, `name`, `category`, `price`, `rating`, `prep_time`, `dietary`, `badge`, `description`, `image`, `available`) VALUES
('f-1', 'Margherita Gourmet Pizza', 'pizza', 12.99, 4.8, '20-25 mins', 'veg', 'Best Seller', 'Stone-baked artisan pizza topped with fresh San Marzano tomato sauce, buffalo mozzarella, and fragrant garden basil leaves.', 'https://images.unsplash.com/photo-1604382355076-af4b0eb60143?w=800&auto=format&fit=crop&q=80', TRUE),
('f-2', 'Pepperoni Supreme Pizza', 'pizza', 15.49, 4.9, '20-25 mins', 'non-veg', 'Popular', 'Loaded with double layers of crispy Italian pepperoni, melted mozzarella cheese, oregano, and zesty marinara sauce.', 'https://images.unsplash.com/photo-1628840042765-356cda07504e?w=800&auto=format&fit=crop&q=80', TRUE),
('f-3', 'Smoked BBQ Bacon Burger', 'burgers', 11.99, 4.7, '15-20 mins', 'non-veg', 'Chef Special', 'Juicy flame-grilled beef patty, crispy bacon strips, smoked barbecue sauce, caramelized onions, and aged cheddar on a toasted brioche bun.', 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800&auto=format&fit=crop&q=80', TRUE),
('f-4', 'Crispy Paneer & Veggie Burger', 'burgers', 9.49, 4.6, '15-20 mins', 'veg', 'Vegetarian', 'Golden spiced paneer cutlet layered with crunchy iceberg lettuce, ripe tomatoes, spicy garlic mayo, and pickles.', 'https://images.unsplash.com/photo-1550547660-d9450f859349?w=800&auto=format&fit=crop&q=80', TRUE),
('f-5', 'Creamy Truffle Alfredo Fettuccine', 'pasta', 13.99, 4.8, '18-22 mins', 'veg', 'Trending', 'Silky fettuccine ribbons tossed in a rich parmesan and truffle garlic cream sauce, garnished with fresh parsley and crushed black pepper.', 'https://images.unsplash.com/photo-1645112411341-6c4fd023714a?w=800&auto=format&fit=crop&q=80', TRUE),
('f-6', 'Spicy Grilled Chicken Arrabiata', 'pasta', 14.79, 4.7, '20-25 mins', 'non-veg', 'Spicy', 'Al dente penne pasta in a fiery garlic chili tomato reduction with tender marinated grilled chicken breast chunks.', 'https://images.unsplash.com/photo-1621996346565-e3d5d6281691?w=800&auto=format&fit=crop&q=80', TRUE),
('f-7', 'Japanese Crispy Chicken Katsu Curry', 'asian', 16.50, 4.9, '22-26 mins', 'non-veg', 'Chef Special', 'Panko-crusted chicken fillet fried to golden perfection, served with sweet-savory aromatic Japanese curry sauce and steamed sushi rice.', 'https://images.unsplash.com/photo-1604908176997-125f25cc6f3d?w=800&auto=format&fit=crop&q=80', TRUE),
('f-8', 'Pan-Seared Veggie Gyoza Dumplings', 'asian', 8.99, 4.6, '12-15 mins', 'veg', 'Vegetarian', 'Handmade Japanese dumplings stuffed with minced cabbage, shiitake mushrooms, and scallions, served with tangy ginger soy dip.', 'https://images.unsplash.com/photo-1541696432-82c6da8ce7bf?w=800&auto=format&fit=crop&q=80', TRUE),
('f-9', 'Warm Belgian Chocolate Lava Cake', 'desserts', 6.99, 4.9, '10-12 mins', 'veg', 'Sweet Tooth', 'Decadent dark chocolate sponge cake with a warm, gooey molten fudge center, served with powdered sugar.', 'https://images.unsplash.com/photo-1606313564200-e75d5e30476c?w=800&auto=format&fit=crop&q=80', TRUE),
('f-10', 'Classic New York Strawberry Cheesecake', 'desserts', 7.49, 4.8, '8-10 mins', 'veg', 'Popular', 'Velvety smooth baked cream cheese on a crunchy graham cracker crust, topped with fresh strawberry glaze.', 'https://images.unsplash.com/photo-1533134242443-d4fd215305ad?w=800&auto=format&fit=crop&q=80', TRUE),
('f-11', 'Alphonso Mango & Mint Sparkler', 'beverages', 4.49, 4.8, '5-8 mins', 'veg', 'Refreshing', 'Fresh seasonal mango pulp blended with crushed mint leaves, lime juice, and chilled sparkling soda.', 'https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?w=800&auto=format&fit=crop&q=80', TRUE),
('f-12', 'Iced Vanilla Caramel Macchiato', 'beverages', 4.99, 4.7, '5-8 mins', 'veg', 'Energy Boost', 'Double shot of rich espresso layered with cold vanilla-infused milk, ice cubes, and a buttery caramel drizzle.', 'https://images.unsplash.com/photo-1517701550927-30cf4ba1dba5?w=800&auto=format&fit=crop&q=80', TRUE);

-- 3. Users Table
CREATE TABLE `users` (
    `id` VARCHAR(50) NOT NULL,
    `name` VARCHAR(100) NOT NULL,
    `email` VARCHAR(100) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `role` VARCHAR(20) DEFAULT 'customer',
    `phone` VARCHAR(30) DEFAULT NULL,
    `address` VARCHAR(255) DEFAULT NULL,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `users` (`id`, `name`, `email`, `password`, `role`, `phone`, `address`, `created_at`) VALUES
('u-admin', 'Admin Manager', 'admin@foodie.com', 'admin123', 'admin', '+1 (555) 019-2834', '100 Restaurant Ave, Springfield', NOW()),
('u-demo', 'Alex Johnson', 'customer@foodie.com', 'user123', 'customer', '+1 (555) 345-6789', '742 Evergreen Terrace, Springfield', NOW());

-- 4. Orders Table
CREATE TABLE `orders` (
    `id` VARCHAR(50) NOT NULL,
    `user_id` VARCHAR(50) DEFAULT 'guest',
    `customer_name` VARCHAR(100) NOT NULL,
    `customer_email` VARCHAR(100) DEFAULT NULL,
    `customer_phone` VARCHAR(30) NOT NULL,
    `delivery_address` VARCHAR(255) NOT NULL,
    `subtotal` DECIMAL(10,2) NOT NULL,
    `tax` DECIMAL(10,2) NOT NULL,
    `delivery_fee` DECIMAL(10,2) NOT NULL,
    `discount` DECIMAL(10,2) DEFAULT 0.00,
    `total` DECIMAL(10,2) NOT NULL,
    `coupon_code` VARCHAR(50) DEFAULT NULL,
    `payment_method` VARCHAR(50) DEFAULT 'Cash on Delivery',
    `payment_status` VARCHAR(30) DEFAULT 'Pending',
    `status` VARCHAR(50) DEFAULT 'Pending',
    `notes` TEXT,
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. Order Items Table
CREATE TABLE `order_items` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `order_id` VARCHAR(50) NOT NULL,
    `food_id` VARCHAR(50) NOT NULL,
    `name` VARCHAR(150) NOT NULL,
    `price` DECIMAL(10,2) NOT NULL,
    `quantity` INT NOT NULL DEFAULT 1,
    `image` VARCHAR(500) DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_order_items_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Sample Seed Orders
INSERT INTO `orders` (`id`, `user_id`, `customer_name`, `customer_email`, `customer_phone`, `delivery_address`, `subtotal`, `tax`, `delivery_fee`, `discount`, `total`, `payment_method`, `payment_status`, `status`, `notes`, `created_at`) VALUES
('ORD-1001', 'u-demo', 'Alex Johnson', 'customer@foodie.com', '+1 (555) 345-6789', '742 Evergreen Terrace, Springfield, IL 62704', 30.47, 1.52, 0.00, 5.00, 26.99, 'Credit / Debit Card', 'Paid', 'Delivered', 'Please leave at front door.', NOW() - INTERVAL 2 HOUR),
('ORD-1002', 'u-demo', 'Alex Johnson', 'customer@foodie.com', '+1 (555) 345-6789', '742 Evergreen Terrace, Springfield, IL 62704', 18.98, 0.95, 2.99, 0.00, 22.92, 'Cash on Delivery', 'Pending', 'Preparing', 'Extra ketchup packets please.', NOW() - INTERVAL 15 MINUTE);

INSERT INTO `order_items` (`order_id`, `food_id`, `name`, `price`, `quantity`, `image`) VALUES
('ORD-1001', 'f-1', 'Margherita Gourmet Pizza', 12.99, 2, 'https://images.unsplash.com/photo-1604382355076-af4b0eb60143?w=800&auto=format&fit=crop&q=80'),
('ORD-1001', 'f-11', 'Alphonso Mango & Mint Sparkler', 4.49, 1, 'https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?w=800&auto=format&fit=crop&q=80'),
('ORD-1002', 'f-3', 'Smoked BBQ Bacon Burger', 11.99, 1, 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800&auto=format&fit=crop&q=80'),
('ORD-1002', 'f-9', 'Warm Belgian Chocolate Lava Cake', 6.99, 1, 'https://images.unsplash.com/photo-1606313564200-e75d5e30476c?w=800&auto=format&fit=crop&q=80');
