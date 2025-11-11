/*
NOTE:
1. If an 'id' has data type AUTOINCREMENT (every 'id' should be), no need to include 'id' in INSERT statement
2. !!AUTOINCREMENTS DO NOT GET RESET!! even if data gets deleted from a table. Refer to this site: https://www.geeksforgeeks.org/mysql/how-to-reset-auto-increment-in-mysql/
*/
USE food_culture;

/*
-----------------------------------
	food_event and origin tables
-----------------------------------
*/
ALTER TABLE `origin`
AUTO_INCREMENT = 1;

INSERT INTO `origin` (name)
VALUES
('Tagalog'),
('Kapampangan'),
('Cebuano'),
('Ilonggo'),
('Filipino-Chinese');

ALTER TABLE `food_event`
AUTO_INCREMENT = 1;

INSERT INTO  `food_event` (`food_event_name`, `description`)
VALUES
('Daily Fare', 'Common Food eaten on a day-to-day basis'),
('Katyusha', 'Missiles Galore'),
('Redo of Healer', 'Trully the healer of all time'),
('Boku no Pico', 'Cuz why not'),
('Ishuzoku Reviewer', 'Monster tag is very good');


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
	food TABLE dummy values
-----------------------------------
*/
ALTER TABLE `food`
auto_increment = 1;

INSERT INTO `food` (`food_name`, `food_event_id`, `origin_id`)
VALUES
('Itlog with Egg',
	(SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Daily Fare'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Tagalog')
    ),
('Shrek',
	(SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Redo of Healer'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Cebuano')
    ),
('Prinitong daga',
	(SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Katyusha'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Kapampangan')
    ),
('Gordon Ramsay',
	(SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Boku no Pico'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Ilonggo')
    ),
('Minimum Search Tree',
	(SELECT `food_event_id` FROM `food_event` WHERE `food_event_name` = 'Ishuzoku Reviewer'),
    (SELECT `origin_id` FROM `origin` WHERE `name` = 'Filipino-Chinese')
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
);

/*
-----------------------------------
	SELECT QUERIES FOR TESTING
-----------------------------------
*/
SELECT * FROM food_address;

DELETE FROM restaurant WHERE restaurant_id > 0;
SELECT * FROM restaurant;