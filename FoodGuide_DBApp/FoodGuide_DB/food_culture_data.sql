-- If an 'id' is AUTOINCREMENT (every 'id' should be), no need to include 'id' in INSERT statement

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
	SELECT QUERIES FOR TESTING
-----------------------------------
*/
USE food_culture;
SELECT * FROM food_address;