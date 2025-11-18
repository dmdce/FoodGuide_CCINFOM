DROP SCHEMA IF EXISTS food_culture;

CREATE SCHEMA IF NOT EXISTS food_culture;

USE food_culture;

CREATE TABLE
  `food_user` (
    `food_user_id` int unsigned NOT NULL AUTO_INCREMENT,
    `food_user_name` varchar(100) NOT NULL,
    `food_user_email` varchar(100) NOT NULL,
    PRIMARY KEY (`food_user_id`)
  );
  
CREATE TABLE
  `food_address` (
    `food_address_id` int unsigned NOT NULL AUTO_INCREMENT,
    `street` varchar(100) NOT NULL,
    `city` varchar(100) NOT NULL,
    PRIMARY KEY (`food_address_id`)
  );

CREATE TABLE
  `restaurant` (
    `restaurant_id` int unsigned NOT NULL AUTO_INCREMENT,
    `food_address_id` int unsigned NOT NULL,
    `restaurant_name` varchar(100) NOT NULL,
    `description` tinytext,
    `num_of_visits` int NOT NULL DEFAULT 0 COMMENT 'counts transactions',
    `total_rating` decimal(3, 2) NOT NULL DEFAULT 0.00 COMMENT 'average rating from food ratings',
    PRIMARY KEY (`restaurant_id`),
    KEY `restaurant_relation_1` (`food_address_id`),
    CONSTRAINT `restaurant_relation_1` FOREIGN KEY (`food_address_id`) REFERENCES `food_address` (`food_address_id`)
  );

CREATE TABLE
  `food_transaction` (
    `food_transaction_id` int unsigned NOT NULL AUTO_INCREMENT,
    `restaurant_name` varchar(100) NOT NULL,
    `promo` decimal(3, 2) NOT NULL COMMENT 'sale, discount, etc.',
    `final_price` decimal(14, 2) NOT NULL COMMENT 'initial_price calculated with promo',
    `initial_price` decimal(14, 2) NOT NULL COMMENT 'base price of food',
    `food_user_id` int unsigned NOT NULL,
    `transaction_date` timestamp NOT NULL,
    PRIMARY KEY (`food_transaction_id`),
    KEY `food_transaction_relation_1` (`food_user_id`),
    CONSTRAINT `food_transaction_relation_1` FOREIGN KEY (`food_user_id`) REFERENCES `food_user` (`food_user_id`)
  );

CREATE TABLE
  `food_rating` (
    `food_rating_id` int unsigned NOT NULL AUTO_INCREMENT,
    `food_transaction_id` int unsigned UNIQUE NOT NULL, -- Enforces one-to-one relationship
    `restaurant_id` int unsigned NOT NULL,
    `suggestion` tinytext COMMENT 'feedback from user',
    `quality` tinyint NOT NULL COMMENT 'food taste, presentation, and originality',
    `authenticity` tinyint NOT NULL COMMENT 'how close the food is to its event and/or origin',
    `overall_rating` float NOT NULL COMMENT 'mean of quality and authenticity',
    PRIMARY KEY (`food_rating_id`),
    KEY `food_rating_relation_1` (`food_transaction_id`),
    KEY `food_rating_relation_2` (`restaurant_id`),
    CONSTRAINT `food_rating_relation_1` FOREIGN KEY (`food_transaction_id`) REFERENCES `food_transaction` (`food_transaction_id`),
    CONSTRAINT `food_rating_relation_2` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`)
  );

CREATE TABLE
  `origin` (
    `origin_id` int unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL COMMENT 'name is a place/s',
    PRIMARY KEY (`origin_id`)
  );

CREATE TABLE
  `food_event` (
    `food_event_id` int unsigned NOT NULL AUTO_INCREMENT,
    `food_event_name` varchar(100) NOT NULL,
    `description` tinytext,
    PRIMARY KEY (`food_event_id`)
  );

CREATE TABLE
  `food` (
    `food_id` int unsigned NOT NULL AUTO_INCREMENT,
    `food_event_id` int unsigned NOT NULL,
    `origin_id` int unsigned NOT NULL,
    `food_name` varchar(100) NOT NULL,
    PRIMARY KEY (`food_id`),
    KEY `food_relation_1` (`food_event_id`),
    KEY `food_relation_2` (`origin_id`),
    CONSTRAINT `food_relation_1` FOREIGN KEY (`food_event_id`) REFERENCES `food_event` (`food_event_id`),
    CONSTRAINT `food_relation_2` FOREIGN KEY (`origin_id`) REFERENCES `origin` (`origin_id`)
  );

CREATE TABLE
  `food_menu` (
    `food_menu_id` int unsigned NOT NULL AUTO_INCREMENT,
    `food_id` int unsigned NOT NULL,
    `food_alias` varchar(100) NOT NULL,
    `price` decimal(14, 2) NOT NULL,
    `restaurant_id` int unsigned NOT NULL,
    PRIMARY KEY (`food_menu_id`),
    KEY `food_menu_relation_1` (`restaurant_id`),
    KEY `food_menu_relation_2` (`food_id`),
    CONSTRAINT `food_menu_relation_1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`),
    CONSTRAINT `food_menu_relation_2` FOREIGN KEY (`food_id`) REFERENCES `food` (`food_id`)
  );

CREATE TABLE
  `food_order` (
    `food_order_id` int unsigned NOT NULL AUTO_INCREMENT,
    `food_transaction_id` int unsigned NOT NULL,
    `food_menu_id` int unsigned NOT NULL,
    `quantity` int NOT NULL DEFAULT '1',
    `food_id` int unsigned NOT NULL,
    PRIMARY KEY (`food_order_id`),
    KEY `food_order_relation_1` (`food_transaction_id`),
    KEY `food_order_relation_2` (`food_menu_id`),
    KEY `food_order_relation_3` (`food_id`),
    CONSTRAINT `food_order_relation_1` FOREIGN KEY (`food_transaction_id`) REFERENCES `food_transaction` (`food_transaction_id`),
    CONSTRAINT `food_order_relation_2` FOREIGN KEY (`food_menu_id`) REFERENCES `food_menu` (`food_menu_id`),
    CONSTRAINT `food_order_relation_3` FOREIGN KEY (`food_id`) REFERENCES `food` (`food_id`)
  );

CREATE TABLE
    `food_promo` (
        `food_promo_id` int unsigned NOT NULL AUTO_INCREMENT,
        `restaurant_id` int unsigned NULL, -- sometimes null
        `promo_code` varchar(50) NOT NULL UNIQUE,
        `percentage_off` decimal(5, 4) NOT NULL,  -- 0.10 = 10% off
        `promo_description` varchar(100) NULL,
        PRIMARY KEY(`food_promo_id`),
        CONSTRAINT `restaurant_id` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`)
    );
