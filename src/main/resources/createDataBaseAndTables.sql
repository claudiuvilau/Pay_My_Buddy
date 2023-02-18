CREATE DATABASE IF NOT EXISTS payMyBuddy CHARACTER SET UTF8MB4 COLLATE=utf8mb4_0900_ai_ci;

USE payMyBuddy;

CREATE TABLE IF NOT EXISTS Users (
	id_users INT UNSIGNED NOT NULL AUTO_INCREMENT,
	id_email VARCHAR(50) NOT NULL,
	name_user VARCHAR(20) NOT NULL,
	first_name VARCHAR(40) NOT NULL,
	birth_date DATE NOT NULL,
	PRIMARY KEY (id_users),
	UNIQUE INDEX ind_id_email (id_email)
)
ENGINE=INNODB;

INSERT INTO Users (id_users, id_email, name_user, first_name, birth_date) VALUES 
	(1, "jack.dupont@yahoo.fr", "DUPONT", "Jack", "1982-01-22"),
	(2, "mireille.benoit@hotmail.com", "BENOIT", "Mireille", "1970-12-31"),
	(3, "sebastien.martin@hotmail.fr", "MARTIN", "Sébastien", "1977-09-19");

CREATE TABLE IF NOT EXISTS FriendsNetwork (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	users_id_users INT UNSIGNED NOT NULL,
	buddy INT UNSIGNED NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT fk_user_id_users_FriendsNetwork 
		FOREIGN KEY (users_id_users) 
		REFERENCES Users(id_users),
	CONSTRAINT fk_buddy_FriendsNetwork  
		FOREIGN KEY (buddy) 
		REFERENCES Users(id_users)
)
ENGINE=INNODB;

INSERT INTO FriendsNetwork (users_id_users, buddy) VALUES 
	(1, 2),
	(1, 3),
	(3, 2);

CREATE TABLE IF NOT EXISTS CollectionMoney (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	start_date DATE UNIQUE NOT NULL,
	end_date DATE UNIQUE NOT NULL,
	amount_percentage DECIMAL(4,2) NOT NULL,
	PRIMARY KEY (id)
)
ENGINE=INNODB;

INSERT INTO CollectionMoney (start_date, end_date, amount_percentage) VALUES 
	("2022-12-01", "2099-12-31", 0.5);

CREATE TABLE IF NOT EXISTS Transactions (
	id_trans INT UNSIGNED NOT NULL AUTO_INCREMENT,
	date_trans DATE NOT NULL,
	user INT UNSIGNED NOT NULL,
	invoiced CHAR(1) NOT NULL DEFAULT '0',
	PRIMARY KEY (id_trans),
	CONSTRAINT fk_user_id_users_Transactions 
		FOREIGN KEY (user) 
		REFERENCES Users(id_users)
)
ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS TypeTransactions (
	id_type_trans INT UNSIGNED NOT NULL AUTO_INCREMENT,
	type_trans VARCHAR(15) NOT NULL,
	PRIMARY KEY (id_type_trans)
)
ENGINE=INNODB;

INSERT INTO TypeTransactions (id_type_trans, type_trans) VALUES 
	(1, "CREDIT"),
	(2, "DEBIT");


CREATE TABLE IF NOT EXISTS NameTransactions (
	id_name_trans INT UNSIGNED NOT NULL AUTO_INCREMENT,
	name_trans VARCHAR(15) NOT NULL,
	PRIMARY KEY (id_name_trans)
)
ENGINE=INNODB;

INSERT INTO NameTransactions (id_name_trans, name_trans) VALUES 
	(1, "Verser"),
	(2, "Transférer"),
	(3, "Envoyer"),
	(4, "Recevoir"),
	(5, "Frais"),
	(6, "Intérêts");


CREATE TABLE IF NOT EXISTS CostsDetailsTransactions (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	trans_id_trans INT UNSIGNED NOT NULL, 
	amount DECIMAL(6,2) NOT NULL,
	type_trans INT UNSIGNED NOT NULL, 
	name_trans INT UNSIGNED NOT NULL, 
	PRIMARY KEY (id),
	CONSTRAINT fk_number_trans_id_trans 
		FOREIGN KEY (trans_id_trans) 
		REFERENCES Transactions(id_trans),
	CONSTRAINT fk_type_trans_id_type_trans 
		FOREIGN KEY (type_trans) 
		REFERENCES TypeTransactions(id_type_trans),	
	CONSTRAINT fk_name_trans_id_trans 
		FOREIGN KEY (name_trans) 
		REFERENCES NameTransactions(id_name_trans)
)
ENGINE=INNODB;

/*insert a transaction for 1 = add money and send to 3*/
/*insert a transaction for 2 = add money*/
/*DUPONT Jack have 30 and send 10 to MARTIN Sébastien*/
/*So collection money 0,5% per transaction*/
/*user 3 recover in your acount bank the amount recieved*/
INSERT INTO Transactions (id_trans, date_trans, user) VALUES 
	(1, "2023-01-26", 1),
	(2, "2023-01-26", 3),
	(3, "2023-02-03", 2),
	(4, "2023-2-05", 3);


INSERT INTO CostsDetailsTransactions (trans_id_trans, amount, type_trans, name_trans) VALUES 
	(1, 30, 1, 1),
	(1, 10, 2, 3),
	(1, 0.05, 2, 5),
	(2, 10, 1, 4),
	(3, 10, 2, 2);

CREATE TABLE IF NOT EXISTS Invoices (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	date_inv DATETIME NOT NULL,
	id_trans INT UNSIGNED NOT NULL,
	inv_number VARCHAR(20) NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT fk_id_trans_id_trans
		FOREIGN KEY (id_trans) 
		REFERENCES Transactions(id_trans)
)
ENGINE=INNODB;
