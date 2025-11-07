-- NOTE: If an 'id' is AUTOINCREMENT (every 'id' should be), no need to include 'id' in INSERT statement
USE food_culture;

/*
-----------------------------------
	??? TABLE
-----------------------------------
*/


/*
-----------------------------------
	food_address TABLE
-----------------------------------
*/
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
INSERT INTO `food_culture`.`restaurant`(`food_address_id`,`restaurant_name`,`description`,`num_of_visits`,`total_rating`) VALUES
(1,'Winikko Cafe And Catering','Cafe specializing in Filipino Classics and catering services.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 1),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 1)),
(1,'Cafe Poblacion - A Mabini','All-day breakfast and traditional Filipino comfort food dishes.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 1),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 1)),
(2,'Mama Lou''s Italian Kitchen','Original location, famous for Italian dishes with Filipino market appeal.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 2),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 2)),
(2,'Bojangles Grill & Gym','Restaurant bar serving high-quality cuisine, including Filipino favorites like pancit and crispy chicken skin.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 2),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 2)),
(3,'Friends and Neighbors Restaurant','Classic carinderia serving home-cooked Filipino dishes like Lechon Kawali and Lumpiang Shanghai.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 3),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 3)),
(3,'Kanto Freestyle Breakfast','24/7 Filipino breakfast spot known for silog meals and gourmet tapsilog.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 3),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 3)),
(4,'Nanay''s Pancit Malabon','Iconic legacy spot specializing in the traditional Pancit Malabon.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 4),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 4)),
(4,'Master Garden Filipino Specialty Restaurant','Filipino restaurant serving traditional regional specialties.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 4),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 4)),
(5,'Café Mezzanine','Air-conditioned Chinese-Filipino eatery known for its charitable cause.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 5),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 5)),
(5,'New Po Heng Lumpia House','Traditional eatery famous for its fresh, savory Chinese-Filipino lumpia.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 5),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 5)),
(6,'Plana''s Pantry','Homey Filipino-Western comfort food, famous for its ribs and pesto rice.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 6),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 6)),
(6,'Tumba-Tumba Crispy Pata','(Made-up) Filipino specialty spot focusing on deep-fried pork knuckle.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 6),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 6)),
(7,'Lola Helen','Long-standing legacy Filipino eatery known for traditional Filipino cuisine.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 7),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 7)),
(7,'Luyong''s','Institution serving Filipino-Chinese comfort food, known for pancit.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 7),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 7)),
(8,'Un Cuenca','Spanish-Filipino fusion cuisine with a focus on elevated Filipino dishes.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 8),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 8)),
(8,'Romulo Café','Elevated Filipino fare, known for its creative dishes like chicken inasal sisig.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 8),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 8)),
(9,'Pia''s in Navotas','Filipino restaurant and bar known for large, festive boodle fights.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 9),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 9)),
(9,'Norma''s Pansit Luglog and Restaurant','Famous for traditional Pancit Luglog and seafood-based Filipino noodles.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 9),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 9)),
(10,'Serye','Restaurant specializing in traditional Filipino dishes and comfort food.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 10),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 10)),
(10,'Little Quiapo','A branch of a well-known institution serving Filipino comfort food and heritage dishes.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 10),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 10)),
(11,'Three Sisters Restaurant','An institution famous for its traditional Pancit and classic Filipino fare.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 11),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 11)),
(11,'Silantro Fil-Mex Cantina','Popular Kapitolyo staple offering Filipino-Mexican fusion cuisine (burritos and tacos).',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 11),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 11)),
(12,'Paezeta','Filipino restaurant serving localized dishes on Morcilla Street.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 12),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 12)),
(12,'Original Pares Mami House','Traditional house known for classic Filipino Pares (beef stew) and Mami (noodle soup).',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 12),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 12)),
(13,'Pino Restaurant','Neighborhood food stop for modernized Filipino-fusion cuisine.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 13),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 13)),
(13,'Rodic''s Diner','Filipino diner, famed for its Tapsilog and budget-friendly comfort food.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 13),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 13)),
(14,'The Local Table','(Made-up) Bistro specializing in elevated Filipino regional cuisine.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 14),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 14)),
(14,'Relish Cafe','Cozy modern bistro serving Filipino-inspired cross-continental dishes.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 14),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 14)),
(15,'Elias','A restaurant where you can fully enjoy the charm of Filipino cuisine (formerly Crisostomo).',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 15),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 15)),
(15,'Lore by Chef Tatung','Hidden gem restaurant known for locally-inspired, modern Filipino cuisine.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 15),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 15)),
(16,'Baliwag Lechon Manok at Liempo','Popular chain serving Filipino Lechon Manok and Liempo.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 16),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 16)),
(16,'Traditional Bibingka & Puto Bumbong Stall','Heritage food stall (Since 1960) specializing in classic Filipino rice cakes.',(SELECT IFNULL(SUM(T.food_transaction_id), 0) FROM food_culture.food_transaction T JOIN food_culture.food_rating R ON T.food_transaction_id = R.food_transaction_id WHERE R.restaurant_id = 16),(SELECT IFNULL(AVG(R.overall_rating), 0) FROM food_culture.food_rating R WHERE R.restaurant_id = 16));

/*
-----------------------------------
	SELECT QUERIES FOR TESTING
-----------------------------------
*/
SELECT * FROM food_address;
SELECT * FROM restaurant;