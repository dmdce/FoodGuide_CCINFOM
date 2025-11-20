/*
NOTE:
1. If an 'id' has data type AUTOINCREMENT (every 'id' should be), no need to include 'id' in INSERT statement
2. !!AUTOINCREMENTS DO NOT GET RESET!! even if data gets deleted from a table. Refer to this site: https://www.geeksforgeeks.org/mysql/how-to-reset-auto-increment-in-mysql/
*/
USE food_culture;

/*
-----------------------------------------
	food_user TABLE
-----------------------------------------
*/
ALTER TABLE `food_user`
AUTO_INCREMENT = 1;

INSERT INTO `food_user` (`food_user_name`, `food_user_email`)
VALUES
('avram', 'avram_tiu@dlsu.edu.ph'),
('darryl', 'darryl_esguerra@dlsu.edu.ph'),
('selina', 'selina_chu@dlsu.edu.ph'),
('nykko', 'nykko_vanguardia@dlsu.edu.ph'),
('test1', 'test1@yahoo.com'),
('test2', 'test2@gmail.com'),
('test3', 'test3@dlsu.edu.ph'),
('customer1', 'customer1@dlsu.edu.ph'),
('customer2', 'customer2@deped.gov.ph'),
('customer3', 'customer3@gmail.com');

/*
-----------------------------------------
	food_event TABLE and origin TABLE
-----------------------------------------
*/
ALTER TABLE `origin`
AUTO_INCREMENT = 1;

INSERT INTO `origin` (`name`)
VALUES
('Tagalog'),
('Kapampangan'),
('Cebuano'),
('Ilonggo'),
('Filipino-Chinese'),
('Spanish'),
('Non-Filipino');

ALTER TABLE `food_event`
AUTO_INCREMENT = 1;

INSERT INTO  `food_event` (`food_event_name`, `description`)
VALUES
('Fiesta sa Nayon', 'A major town or village festival celebrating a patron saint or local harvest.'),
('Noche Buena', 'The traditional Christmas Eve feast and dinner.'),
('Pasko', 'The general season and celebrations for Christmas in the Philippines.'),
('Eid al-Fitr', 'The Islamic "Festival of Breaking the Fast" celebrated by Filipino Muslims.'),
('Semana Santa', 'Holy Week observances, typically involving meatless meals.'),
('Christmas', 'Jose Mari Chan is getting unfreezed'),
('Lunar New Year', 'Love me some mooncakes'),
('Valentines', 'Chuushite in Filipino'),
('Summer', 'Jose Mari Chan is getting unfreezed'),
('All Souls Day', 'Give Coca-cola and fried chicken to your beloved'),
('Everyday', 'Nothing unusual');


/*
-----------------------------------
	food_address TABLE
-----------------------------------
*/
ALTER TABLE food_address
AUTO_INCREMENT = 1;
INSERT INTO `food_culture`.`food_address` (`street`,`city`) VALUES
("A. Mabini Street","Caloocan"),
("Tropical Avenue","Las Piñas"),
("P. Burgos Street","Makati"),
("F. Sevilla Boulevard","Malabon"),
("Ongpin Street","Manila"),
("Domingo M. Guevara Street","Mandaluyong"),
("J. P. Rizal Street","Marikina"),
("Madrigal Avenue","Muntinlupa"),
("M. Naval Street","Navotas"),
("Aguirre Avenue","Parañaque"),
("East Capitol Drive","Pasig"),
("B. Morcilla Street","Pateros"),
("Maginhawa Street","Quezon City"),
("Wilson Street","San Juan"),
("Bonifacio High Street","Taguig"),
("Del Pilar Street","Valenzuela");

/*
-----------------------------------
	restaurant TABLE
-----------------------------------

NOTE: `num_of_visits` and `total_rating` are represented by aggregate statements 
which rely on the existence of `food_culture.food_transaction` and `food_culture.food_rating` tables
*/
ALTER TABLE restaurant
AUTO_INCREMENT = 1;

INSERT INTO `food_culture`.`restaurant`(`food_address_id`,`restaurant_name`,`description`,`num_of_visits`,`total_rating`) VALUES
(1,'Winikko Cafe And Catering','Cafe specializing in Filipino Classics and catering services.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 1),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 1)),
(1,'Cafe Poblacion - A Mabini','All-day breakfast and traditional Filipino comfort food dishes.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 2),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 2)),
(1,'Chachu Tapsilogan Sa Caloocan','Restaurant that specializes in serving Tapsilog and other Silog-like dishes.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 3),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 3)),
(2,'Mama Lou''s Italian Kitchen','Original location, famous for Italian dishes with Filipino market appeal.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 4),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 4)),
(2,'Bojangles Grill & Gym','Restaurant bar serving high-quality cuisine, including Filipino favorites like pancit and crispy chicken skin.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 5),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 5)),
(3,'Friends and Neighbors Restaurant','Classic carinderia serving home-cooked Filipino dishes like Lechon Kawali and Lumpiang Shanghai.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 6),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 6)),
(3,'Kanto Freestyle Breakfast','24/7 Filipino breakfast spot known for silog meals and gourmet tapsilog.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 7),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 7)),
(4,'Nanay''s Pancit Malabon','Iconic legacy spot specializing in the traditional Pancit Malabon.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 8),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 8)),
(4,'Master Garden Filipino Specialty Restaurant','Filipino restaurant serving traditional regional specialties.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 9),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 9)),
(5,'Café Mezzanine','Air-conditioned Chinese-Filipino eatery known for its charitable cause.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 10),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 10)),
(5,'New Po Heng Lumpia House','Traditional eatery famous for its fresh, savory Chinese-Filipino lumpia.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 11),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 11)),
(6,'Plana''s Pantry','Homey Filipino-Western comfort food, famous for its ribs and pesto rice.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 12),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 12)),
(6,'Tumba-Tumba Crispy Pata','(Made-up) Filipino specialty spot focusing on deep-fried pork knuckle.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 13),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 13)),
(7,'Lola Helen','Long-standing legacy Filipino eatery known for traditional Filipino cuisine.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 14),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 14)),
(7,'Luyong''s','Institution serving Filipino-Chinese comfort food, known for pancit.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 15),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 15)),
(8,'Un Cuenca','Spanish-Filipino fusion cuisine with a focus on elevated Filipino dishes.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 16),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 16)),
(8,'Romulo Café','Elevated Filipino fare, known for its creative dishes like chicken inasal sisig.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 17),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 17)),
(9,'Pia''s in Navotas','Filipino restaurant and bar known for large, festive boodle fights.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 18),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 18)),
(9,'Norma''s Pansit Luglog and Restaurant','Famous for traditional Pancit Luglog and seafood-based Filipino noodles.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 19),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 19)),
(10,'Serye','Restaurant specializing in traditional Filipino dishes and comfort food.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 20),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 20)),
(10,'Little Quiapo','A branch of a well-known institution serving Filipino comfort food and heritage dishes.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 21),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 21)),
(11,'Three Sisters Restaurant','An institution famous for its traditional Pancit and classic Filipino fare.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 22),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 22)),
(11,'Silantro Fil-Mex Cantina','Popular Kapitolyo staple offering Filipino-Mexican fusion cuisine (burritos and tacos).',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 23),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 23)),
(12,'Paezeta','Filipino restaurant serving localized dishes on Morcilla Street.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 24),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 24)),
(12,'Original Pares Mami House','Traditional house known for classic Filipino Pares (beef stew) and Mami (noodle soup).',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 25),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 25)),
(13,'Pino Restaurant','Neighborhood food stop for modernized Filipino-fusion cuisine.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 26),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 26)),
(13,'Rodic''s Diner','Filipino diner, famed for its Tapsilog and budget-friendly comfort food.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 27),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 27)),
(14,'The Local Table','(Made-up) Bistro specializing in elevated Filipino regional cuisine.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 28),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 28)),
(14,'Relish Cafe','Cozy modern bistro serving Filipino-inspired cross-continental dishes.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 29),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 29)),
(15,'Elias','A restaurant where you can fully enjoy the charm of Filipino cuisine (formerly Crisostomo).',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 30),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 30)),
(15,'Lore by Chef Tatung','Hidden gem restaurant known for locally-inspired, modern Filipino cuisine.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 31),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 31)),
(16,'Baliwag Lechon Manok at Liempo','Popular chain serving Filipino Lechon Manok and Liempo.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 32),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 32)),
(16,'Traditional Bibingka & Puto Bumbong Stall','Heritage food stall (Since 1960) specializing in classic Filipino rice cakes.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 33),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 33)),
(1,'Aling Banang Caloocan','Serves Filipino dishes in large quantities for family gatherings.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 34),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 34)),
(3,'Tíabuela Cocina Española','Serves authentic Spanish dishes with Spanish wines and cocktails.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 35),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 35));


/*
-----------------------------------
	food TABLE
-----------------------------------
*/
ALTER TABLE `food`
auto_increment = 1;

INSERT INTO `food` (`food_name`, `food_event_id`, `origin_id`)
VALUES
('Itlog with Egg',
	(SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Fiesta sa Nayon'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Tagalog')
    ),
('Shrek',
	(SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Rizal Day'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Cebuano')
    ),
('Prinitong daga',
	(SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Noche Buena'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Kapampangan')
    ),
('Gordon Ramsay',
	(SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Eid al-Fitr'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Ilonggo')
    ),
('Minimum Search Tree',
	(SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Semana Santa'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Filipino-Chinese')
    ),
('Schubligsilog',
	(SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Rizal Day'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Filipino-Chinese')
    ),
('Pares',
	(SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Everyday'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Tagalog')
	),
('Holiday Ham Breakfast',
	(SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Christmas'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Non-Filipino')
	),
('Lechong Paksiw',
	(SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Everyday'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Tagalog')
	),
('Birthday Noodles',
	(SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Lunar New Year'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Filipino-Chinese')
	),
('Adobo',
    (SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Semana Santa'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Tagalog')
    ),
('Pancit Malabon',
    (SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Everyday'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Tagalog')
    ),
('Sisig',
    (SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Everyday'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Kapampangan')
    ),
('Lechon',
    (SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Fiesta sa Nayon'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Cebuano')
    ),
('Bibingka',
    (SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Pasko'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Tagalog')
    ),
('Tapa',
    (SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Semana Santa'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Tagalog')
    ),
('Tapsilog',
    (SELECT `food_id` FROM `food` WHERE `food_name` = 'Tapa'), -- Re-using Tapa's food_id for its Silog dish
    (SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Semana Santa'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Tagalog')
    ),
('Buko Pandan',
    (SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Summer'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Tagalog')
    ),
('Lumpia Shanghai',
    (SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Everyday'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Filipino-Chinese')
    ),
('Dinuguan',
    (SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Everyday'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Tagalog')
    );

/*
-----------------------------------
	food_menu TABLE dummy values
-----------------------------------
*/
ALTER TABLE `food_menu`
auto_increment = 1;

INSERT INTO `food_menu` (`food_id`, `food_alias`, `price`, `restaurant_id`)
VALUES
(
	(SELECT `food_id` FROM `food` WHERE `food_name` = 'Itlog with Egg'),
	'Boiled egg in boiled egg sauce',
    10,
    (SELECT `restaurant_id` FROM `restaurant` WHERE `restaurant_name` = 'Winikko Cafe And Catering')
),
(
	(SELECT `food_id` FROM `food` WHERE `food_name` = 'Minimum Search Tree'),
	'Binary Search Tree',
    20,
    (SELECT `restaurant_id` FROM `restaurant` WHERE `restaurant_name` = 'Cafe Poblacion - A Mabini')
),
(
	(SELECT `food_id` FROM `food` WHERE `food_name` = 'Shrek'),
	'Rockstar',
    30,
    (SELECT `restaurant_id` FROM `restaurant` WHERE `restaurant_name` = 'Mama Lou''s Italian Kitchen')
),
(
	(SELECT `food_id` FROM `food` WHERE `food_name` = 'Prinitong daga'),
	'Micah Bell',
    40,
    (SELECT `restaurant_id` FROM `restaurant` WHERE `restaurant_name` = 'Bojangles Grill & Gym')
),
(
	(SELECT `food_id` FROM `food` WHERE `food_name` = 'Gordon Ramsay'),
	'Where is da lamb sauce',
    50,
    (SELECT `restaurant_id` FROM `restaurant` WHERE `restaurant_name` = 'Friends and Neighbors Restaurant')
),
(
	(SELECT `food_id` FROM `food` WHERE `food_name` = 'Schubligsilog'),
	'Schublig with egg and rice',
    100,
    (SELECT `restaurant_id` FROM `restaurant` WHERE `restaurant_name` = 'Friends and Neighbors Restaurant')
),
(
    (SELECT `food_id` FROM `food` WHERE `food_name` = 'Adobo' ORDER BY food_id DESC LIMIT 1),
    'Classic Chicken Adobo',
    250.00,
    (SELECT `restaurant_id` FROM `restaurant` WHERE `restaurant_name` = 'Winikko Cafe And Catering')
),
(
    (SELECT `food_id` FROM `food` WHERE `food_name` = 'Pancit Malabon' ORDER BY food_id DESC LIMIT 1),
    'Nanay''s Special Pancit',
    350.50,
    (SELECT `restaurant_id` FROM `restaurant` WHERE `restaurant_name` = 'Nanay''s Pancit Malabon')
),
(
    (SELECT `food_id` FROM `food` WHERE `food_name` = 'Sisig' ORDER BY food_id DESC LIMIT 1),
    'Sizzling Pork Sisig',
    280.00,
    (SELECT `restaurant_id` FROM `restaurant` WHERE `restaurant_name` = 'Kanto Freestyle Breakfast')
),
(
    (SELECT `food_id` FROM `food` WHERE `food_name` = 'Tapsilog' ORDER BY food_id DESC LIMIT 1),
    'Chachu''s Best Tapsilog',
    185.00,
    (SELECT `restaurant_id` FROM `restaurant` WHERE `restaurant_name` = 'Chachu Tapsilogan Sa Caloocan')
),
(
    (SELECT `food_id` FROM `food` WHERE `food_name` = 'Bibingka' ORDER BY food_id DESC LIMIT 1),
    'Traditional Bibingka Special',
    95.00,
    (SELECT `restaurant_id` FROM `restaurant` WHERE `restaurant_name` = 'Traditional Bibingka & Puto Bumbong Stall')
),
(
    (SELECT `food_id` FROM `food` WHERE `food_name` = 'Lumpia Shanghai' ORDER BY food_id DESC LIMIT 1),
    'Fresh Lumpia',
    120.00,
    (SELECT `restaurant_id` FROM `restaurant` WHERE `restaurant_name` = 'New Po Heng Lumpia House')
);

/*
-----------------------------------
	food_promo TABLE
-----------------------------------
*/
ALTER TABLE `food_promo`
AUTO_INCREMENT = 1;

INSERT INTO food_promo (promo_code, percentage_off, promo_description)
VALUES
('SAVE10', 0.10, '10% off your entire order'),
('SAVE20', 0.20, '20% off your entire order'),
('SAVE30', 0.30, '30% off your entire order'),
('SAVE40', 0.40, '40% off your entire order'),
('SAVE5', 0.05, '5% off your entire order'),
('SAVE15', 0.15, '15% off your entire order');

-- this one is restaurant specific
INSERT INTO food_promo (restaurant_id, promo_code, percentage_off, promo_description)
VALUES
(1, 'COFFEE15', 0.15, '15% off for Winikko Cafe And Catering'),
(3, 'SILOG20', 0.20, '10% off for Tapsilog dishes at Chachu Tapsilogan Sa Caloocan'),
(8, 'PANC33T', 0.33, '33% off for Nanay''s Pancit Malabon'),
(9, 'DELUXE10', 0.10, '10% off on exclusive dishes at Master Garden Filipino Specialty Restaurant');

/*
-----------------------------------
    food_transaction TABLE (Prerequisite for food_rating)
-----------------------------------
*/
ALTER TABLE `food_transaction`
auto_increment = 1;

INSERT INTO `food_transaction` (`restaurant_name`, `promo`, `initial_price`, `final_price`, `food_user_id`, `transaction_date`)
VALUES
('Chachu Tapsilogan Sa Caloocan', 0.20, 370.00, 296.00, 1, '2025-11-15 10:30:00'),
('Nanay''s Pancit Malabon', 0.33, 701.00, 469.67, 2, '2025-11-16 12:45:00'),
('Kanto Freestyle Breakfast', 0.00, 560.00, 560.00, 3, '2025-11-17 08:00:00'),
('Winikko Cafe And Catering', 0.10, 500.00, 450.00, 4, '2025-11-18 14:15:00'),
('Traditional Bibingka & Puto Bumbong Stall', 0.00, 190.00, 190.00, 5, '2025-11-19 19:30:00'),
('Master Garden Filipino Specialty Restaurant', (SELECT percentage_off FROM food_promo WHERE promo_code = 'DELUXE10'), 1200.00, 1080.00, 6, '2025-11-20 12:00:00'),
('Tumba-Tumba Crispy Pata', (SELECT percentage_off FROM food_promo WHERE promo_code = 'SAVE20'), 1500.00, 1200.00, 7, '2025-11-21 20:15:00'),
('Cafe Poblacion - A Mabini', (SELECT percentage_off FROM food_promo WHERE promo_code = 'SAVE5'), 350.00, 332.50, 8, '2025-11-22 07:45:00'),
('Elias', (SELECT percentage_off FROM food_promo WHERE promo_code = 'SAVE10'), 900.00, 810.00, 9, '2025-11-23 18:30:00'),
('Baliwag Lechon Manok at Liempo', 0.00, 750.00, 750.00, 10, '2025-11-24 13:00:00');

/*
-----------------------------------
    food_rating TABLE
-----------------------------------
*/
ALTER TABLE `food_rating`
auto_increment = 1;

INSERT INTO `food_rating` (`food_transaction_id`, `restaurant_id`, `suggestion`, `quality`, `authenticity`, `overall_rating`)
VALUES
(1, (SELECT restaurant_id FROM restaurant WHERE restaurant_name = 'Chachu Tapsilogan Sa Caloocan'), 'More garlic rice options needed.', 4, 5, 4.5),
(2, (SELECT restaurant_id FROM restaurant WHERE restaurant_name = 'Nanay''s Pancit Malabon'), 'The calamansi was too small.', 5, 5, 5.0),
(3, (SELECT restaurant_id FROM restaurant WHERE restaurant_name = 'Kanto Freestyle Breakfast'), 'Loved the presentation, very homey.', 4, 3, 3.5),
(4, (SELECT restaurant_id FROM restaurant WHERE restaurant_name = 'Winikko Cafe And Catering'), 'Great twist on a classic!', 5, 4, 4.5),
(5, (SELECT restaurant_id FROM restaurant WHERE restaurant_name = 'Traditional Bibingka & Puto Bumbong Stall'), 'Perfectly cooked, tasted like my lola made it.', 5, 5, 5.0),
(6,(SELECT restaurant_id FROM restaurant WHERE restaurant_name = 'Master Garden Filipino Specialty Restaurant'),'Excellent service but the ambiance was lacking.',5, 4, 4.5),
(7,(SELECT restaurant_id FROM restaurant WHERE restaurant_name = 'Tumba-Tumba Crispy Pata'),'The crispy pata lived up to its name! So crunchy.',5, 5, 5.0),
(8,(SELECT restaurant_id FROM restaurant WHERE restaurant_name = 'Cafe Poblacion - A Mabini'),'Good breakfast spot, wish the coffee was hotter.',3, 4, 3.5),
(9,(SELECT restaurant_id FROM restaurant WHERE restaurant_name = 'Elias'),'A truly elevated Filipino experience. Will be back!',5, 5, 5.0),
(10,(SELECT restaurant_id FROM restaurant WHERE restaurant_name = 'Baliwag Lechon Manok at Liempo'),'Reliable and tasty as always. Good value.',4, 4, 4.0);

/*
-----------------------------------
    food_order TABLE
-----------------------------------
*/
ALTER TABLE `food_order`
auto_increment = 1;

INSERT INTO `food_order` (`food_transaction_id`, `food_menu_id`, `quantity`, `food_id`)
VALUES
(1, (SELECT food_menu_id FROM food_menu WHERE food_alias = 'Chachu''s Best Tapsilog'), 2, (SELECT food_id FROM food WHERE food_name = 'Tapsilog' ORDER BY food_id DESC LIMIT 1)),
(2, (SELECT food_menu_id FROM food_menu WHERE food_alias = 'Nanay''s Special Pancit'), 1, (SELECT food_id FROM food WHERE food_name = 'Pancit Malabon' ORDER BY food_id DESC LIMIT 1)),
(3, (SELECT food_menu_id FROM food_menu WHERE food_alias = 'Sizzling Pork Sisig'), 2, (SELECT food_id FROM food WHERE food_name = 'Sisig' ORDER BY food_id DESC LIMIT 1)),
(4, (SELECT food_menu_id FROM food_menu WHERE food_alias = 'Classic Chicken Adobo'), 2, (SELECT food_id FROM food WHERE food_name = 'Adobo' ORDER BY food_id DESC LIMIT 1)),
(4, (SELECT food_menu_id FROM food_menu WHERE food_alias = 'Boiled egg in boiled egg sauce'), 1, (SELECT food_id FROM food WHERE food_name = 'Itlog with Egg' ORDER BY food_id ASC LIMIT 1)),
(5, (SELECT food_menu_id FROM food_menu WHERE food_alias = 'Traditional Bibingka Special'), 2, (SELECT food_id FROM food WHERE food_name = 'Bibingka' ORDER BY food_id DESC LIMIT 1)),
-- Transaction ID 6: Master Garden Filipino Specialty Restaurant
(6,(SELECT food_menu_id FROM food_menu WHERE food_alias = 'Classic Chicken Adobo'),3,(SELECT food_id FROM food WHERE food_name = 'Adobo' ORDER BY food_id DESC LIMIT 1)),
(6,(SELECT food_menu_id FROM food_menu WHERE food_alias = 'Binary Search Tree'),2,(SELECT food_id FROM food WHERE food_name = 'Minimum Search Tree' ORDER BY food_id DESC LIMIT 1)),
-- Transaction ID 7: Tumba-Tumba Crispy Pata
(7,(SELECT food_menu_id FROM food_menu WHERE food_alias = 'Boiled egg in boiled egg sauce'),5,(SELECT food_id FROM food WHERE food_name = 'Itlog with Egg' ORDER BY food_id ASC LIMIT 1)),
-- Transaction ID 8: Cafe Poblacion - A Mabini
(8,(SELECT food_menu_id FROM food_menu WHERE food_alias = 'Chachu''s Best Tapsilog'),1,(SELECT food_id FROM food WHERE food_name = 'Tapsilog' ORDER BY food_id DESC LIMIT 1)),
(8,(SELECT food_menu_id FROM food_menu WHERE food_alias = 'Boiled egg in boiled egg sauce'),1,(SELECT food_id FROM food WHERE food_name = 'Itlog with Egg' ORDER BY food_id ASC LIMIT 1)),
-- Transaction ID 9: Elias
(9,(SELECT food_menu_id FROM food_menu WHERE food_alias = 'Classic Chicken Adobo'),4,(SELECT food_id FROM food WHERE food_name = 'Adobo' ORDER BY food_id DESC LIMIT 1)),
(9,(SELECT food_menu_id FROM food_menu WHERE food_alias = 'Binary Search Tree'),1,(SELECT food_id FROM food WHERE food_name = 'Minimum Search Tree' ORDER BY food_id DESC LIMIT 1)),
-- Transaction ID 10: Baliwag Lechon Manok at Liempo
(10,(SELECT food_menu_id FROM food_menu WHERE food_alias = 'Classic Chicken Adobo'),2,(SELECT food_id FROM food WHERE food_name = 'Adobo' ORDER BY food_id DESC LIMIT 1)),
(10,(SELECT food_menu_id FROM food_menu WHERE food_alias = 'Chachu''s Best Tapsilog'),1,(SELECT food_id FROM food WHERE food_name = 'Tapsilog' ORDER BY food_id DESC LIMIT 1)),
(10,(SELECT food_menu_id FROM food_menu WHERE food_alias = 'Boiled egg in boiled egg sauce'),1,(SELECT food_id FROM food WHERE food_name = 'Itlog with Egg' ORDER BY food_id ASC LIMIT 1));


/*
-----------------------------------
    food_reservation TABLE
-----------------------------------
*/
ALTER TABLE `food_reservation`
auto_increment = 1;

INSERT INTO `food_reservation` (`restaurant_name`, `initial_price`, `food_user_id`, `reservation_date`)
VALUES
('New Po Heng Lumpia House', 240.00, 6, '2025-11-25 18:00:00'),
('Un Cuenca', 800.00, 7, '2025-12-10 19:30:00'),
('Original Pares Mami House', 500.00, 8, '2025-11-20 11:00:00'),
('Lore by Chef Tatung', 1500.00, 1, '2025-12-01 19:00:00'),
('Relish Cafe', 900.00, 2, '2025-12-05 18:30:00'),
('Plana''s Pantry', 650.00, 3, '2025-12-12 12:00:00'),
('Luyong''s', 400.00, 4, '2025-12-15 11:30:00'),
('Rodic''s Diner', 300.00, 5, '2025-12-18 09:00:00'),
('Three Sisters Restaurant', 1200.00, 6, '2025-12-24 20:00:00'),
('Winikko Cafe And Catering', 700.00, 7, '2025-12-28 14:00:00');

/*
-----------------------------------
    reservation_order TABLE
-----------------------------------
*/
ALTER TABLE `reservation_order`
auto_increment = 1;

INSERT INTO `reservation_order` (`food_reservation_id`, `food_menu_id`, `quantity`, `food_id`)
VALUES
(1, (SELECT food_menu_id FROM food_menu WHERE food_alias = 'Fresh Lumpia'), 2, (SELECT food_id FROM food WHERE food_name = 'Lumpia Shanghai' ORDER BY food_id DESC LIMIT 1)),
(2, (SELECT food_menu_id FROM food_menu WHERE food_alias = 'Classic Chicken Adobo'), 3, (SELECT food_id FROM food WHERE food_name = 'Adobo' ORDER BY food_id DESC LIMIT 1)),
(3, (SELECT food_menu_id FROM food_menu WHERE food_alias = 'Boiled egg in boiled egg sauce'), 5, (SELECT food_id FROM food WHERE food_name = 'Itlog with Egg' ORDER BY food_id ASC LIMIT 1)),
-- Reservation ID 4: Lore by Chef Tatung
(4,(SELECT food_menu_id FROM food_menu WHERE food_alias = 'Classic Chicken Adobo'),4,(SELECT food_id FROM food WHERE food_name = 'Adobo' ORDER BY food_id DESC LIMIT 1)),
-- Reservation ID 5: Relish Cafe
(5,(SELECT food_menu_id FROM food_menu WHERE food_alias = 'Classic Chicken Adobo'),2,(SELECT food_id FROM food WHERE food_name = 'Adobo' ORDER BY food_id DESC LIMIT 1)),
-- Reservation ID 6: Plana''s Pantry
(6,(SELECT food_menu_id FROM food_menu WHERE food_alias = 'Boiled egg in boiled egg sauce'),3,(SELECT food_id FROM food WHERE food_name = 'Itlog with Egg' ORDER BY food_id ASC LIMIT 1)),
-- Reservation ID 7: Luyong''s
(7,(SELECT food_menu_id FROM food_menu WHERE food_alias = 'Binary Search Tree'),1,(SELECT food_id FROM food WHERE food_name = 'Minimum Search Tree' ORDER BY food_id DESC LIMIT 1)),
-- Reservation ID 8: Rodic''s Diner
(8,(SELECT food_menu_id FROM food_menu WHERE food_alias = 'Chachu''s Best Tapsilog'),2,(SELECT food_id FROM food WHERE food_name = 'Tapsilog' ORDER BY food_id DESC LIMIT 1)),
-- Reservation ID 9: Three Sisters Restaurant
(9,(SELECT food_menu_id FROM food_menu WHERE food_alias = 'Classic Chicken Adobo'),5,(SELECT food_id FROM food WHERE food_name = 'Adobo' ORDER BY food_id DESC LIMIT 1)),
-- Reservation ID 10: Winikko Cafe And Catering
(10,(SELECT food_menu_id FROM food_menu WHERE food_alias = 'Boiled egg in boiled egg sauce'),4,(SELECT food_id FROM food WHERE food_name = 'Itlog with Egg' ORDER BY food_id ASC LIMIT 1));


/*
-----------------------------------
	SELECT QUERIES FOR TESTING
-----------------------------------
*/
-- Reset tables (WARNING: CAN DELETE DATA, VERY DANGEROUS)
DELETE FROM origin WHERE origin_id >= 6;
DELETE FROM food_event WHERE food_event_id >= 6;
DELETE FROM food WHERE food_id >= 6;
DELETE FROM food_menu WHERE food_menu_id >= 6;
DELETE FROM restaurant WHERE restaurant_id > 0;
DELETE FROM food_user WHERE food_user_id > 4;

-- Select tables
SELECT * FROM origin;
SELECT * FROM food_event;
SELECT * FROM food;
SELECT * FROM food_menu;
SELECT * FROM restaurant;
SELECT * FROM food_address;
SELECT * FROM food_transaction;


-- Other queries
SELECT DISTINCT r.*
FROM restaurant r
JOIN food_menu fm ON fm.restaurant_id = r.restaurant_id
JOIN food f ON f.food_id = fm.food_id
LEFT JOIN origin o ON f.origin_id = o.origin_id
LEFT JOIN food_event fe ON f.food_event_id = fe.food_event_id
WHERE o.name IN ('Tagalog', 'Filipino-Chinese', 'Ilonggo')
GROUP BY fm.food_menu_id;
