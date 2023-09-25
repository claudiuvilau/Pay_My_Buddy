CREATE DATABASE  IF NOT EXISTS `paymybuddy` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `paymybuddy`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: paymybuddy
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `collectionmoney`
--

DROP TABLE IF EXISTS `collectionmoney`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `collectionmoney` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `amount_percentage` decimal(5,3) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `start_date` (`start_date`),
  UNIQUE KEY `end_date` (`end_date`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `collectionmoney`
--

LOCK TABLES `collectionmoney` WRITE;
/*!40000 ALTER TABLE `collectionmoney` DISABLE KEYS */;
INSERT INTO `collectionmoney` VALUES (1,'2022-12-01','2099-12-31',0.005);
/*!40000 ALTER TABLE `collectionmoney` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `costsdetailstransactions`
--

DROP TABLE IF EXISTS `costsdetailstransactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `costsdetailstransactions` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `trans_id_trans` int unsigned NOT NULL,
  `amount` decimal(6,2) NOT NULL,
  `type_trans` int unsigned NOT NULL,
  `name_trans` int unsigned NOT NULL,
  `to_from_user` int unsigned NOT NULL,
  `description_id` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_number_trans_id_trans` (`trans_id_trans`),
  KEY `fk_type_trans_id_type_trans` (`type_trans`),
  KEY `fk_name_trans_id_trans` (`name_trans`),
  KEY `fk_to_from_user` (`to_from_user`),
  KEY `fk_description_id` (`description_id`),
  CONSTRAINT `fk_description_id` FOREIGN KEY (`description_id`) REFERENCES `descriptions` (`id_descriptions`),
  CONSTRAINT `fk_name_trans_id_trans` FOREIGN KEY (`name_trans`) REFERENCES `nametransactions` (`id_name_trans`),
  CONSTRAINT `fk_number_trans_id_trans` FOREIGN KEY (`trans_id_trans`) REFERENCES `transactions` (`id_trans`),
  CONSTRAINT `fk_to_from_user` FOREIGN KEY (`to_from_user`) REFERENCES `users` (`id_users`),
  CONSTRAINT `fk_type_trans_id_type_trans` FOREIGN KEY (`type_trans`) REFERENCES `typetransactions` (`id_type_trans`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `costsdetailstransactions`
--

LOCK TABLES `costsdetailstransactions` WRITE;
/*!40000 ALTER TABLE `costsdetailstransactions` DISABLE KEYS */;
INSERT INTO `costsdetailstransactions` VALUES (1,1,30.00,1,1,1,NULL),(2,1,10.00,2,3,3,1),(3,1,0.05,2,5,1,NULL),(4,2,10.00,1,4,1,1),(5,3,10.00,2,2,2,NULL),(6,4,50.00,1,1,3,NULL),(7,5,100.00,1,1,2,2),(8,6,10.00,2,2,2,3),(9,7,20.00,2,3,1,4),(10,7,0.10,2,5,2,5),(11,8,20.00,1,4,2,4),(12,9,10.00,2,3,2,4),(13,9,0.05,2,5,3,5),(14,10,10.00,1,4,3,4);
/*!40000 ALTER TABLE `costsdetailstransactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `descriptions`
--

DROP TABLE IF EXISTS `descriptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `descriptions` (
  `id_descriptions` int unsigned NOT NULL AUTO_INCREMENT,
  `description` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id_descriptions`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descriptions`
--

LOCK TABLES `descriptions` WRITE;
/*!40000 ALTER TABLE `descriptions` DISABLE KEYS */;
INSERT INTO `descriptions` VALUES (1,'remb. ciné'),(2,'versement'),(3,'transfert'),(4,'pour le repas de midi'),(5,'frais');
/*!40000 ALTER TABLE `descriptions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friends` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `users_id_users` int unsigned NOT NULL,
  `buddy` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_ind_users_id_users_buddy` (`users_id_users`,`buddy`),
  KEY `fk_buddy_Friends` (`buddy`),
  CONSTRAINT `fk_buddy_Friends` FOREIGN KEY (`buddy`) REFERENCES `users` (`id_users`),
  CONSTRAINT `fk_user_id_users_Friends` FOREIGN KEY (`users_id_users`) REFERENCES `users` (`id_users`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends`
--

LOCK TABLES `friends` WRITE;
/*!40000 ALTER TABLE `friends` DISABLE KEYS */;
INSERT INTO `friends` VALUES (1,1,2),(2,1,3),(4,2,1),(3,3,2);
/*!40000 ALTER TABLE `friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoices`
--

DROP TABLE IF EXISTS `invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoices` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `date_inv` datetime NOT NULL,
  `id_trans` int unsigned NOT NULL,
  `inv_number` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_id_trans_id_trans` (`id_trans`),
  CONSTRAINT `fk_id_trans_id_trans` FOREIGN KEY (`id_trans`) REFERENCES `transactions` (`id_trans`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices`
--

LOCK TABLES `invoices` WRITE;
/*!40000 ALTER TABLE `invoices` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nametransactions`
--

DROP TABLE IF EXISTS `nametransactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nametransactions` (
  `id_name_trans` int unsigned NOT NULL AUTO_INCREMENT,
  `name_trans` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `type_name_trans` int unsigned NOT NULL,
  PRIMARY KEY (`id_name_trans`),
  KEY `fk_type_name_trans` (`type_name_trans`),
  CONSTRAINT `fk_type_name_trans` FOREIGN KEY (`type_name_trans`) REFERENCES `typetransactions` (`id_type_trans`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nametransactions`
--

LOCK TABLES `nametransactions` WRITE;
/*!40000 ALTER TABLE `nametransactions` DISABLE KEYS */;
INSERT INTO `nametransactions` VALUES (1,'versement',1),(2,'transfert',2),(3,'envoi',2),(4,'encaissement',1),(5,'Frais',2),(6,'Intérêts',2);
/*!40000 ALTER TABLE `nametransactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id_roles` int unsigned NOT NULL AUTO_INCREMENT,
  `name_role` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id_roles`),
  UNIQUE KEY `ind_name_roles` (`name_role`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ADMIN'),(2,'USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `id_trans` int unsigned NOT NULL AUTO_INCREMENT,
  `date_trans` date NOT NULL,
  `user` int unsigned NOT NULL,
  `invoiced` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_trans`),
  KEY `fk_user_id_users_Transactions` (`user`),
  CONSTRAINT `fk_user_id_users_Transactions` FOREIGN KEY (`user`) REFERENCES `users` (`id_users`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,'2023-01-26',1,'0'),(2,'2023-01-26',3,'0'),(3,'2023-02-03',2,'0'),(4,'2023-02-05',3,'0'),(5,'2023-06-02',2,'0'),(6,'2023-06-02',2,'0'),(7,'2023-06-02',2,'0'),(8,'2023-06-02',1,'0'),(9,'2023-09-05',3,'0'),(10,'2023-09-05',2,'0');
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `typetransactions`
--

DROP TABLE IF EXISTS `typetransactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `typetransactions` (
  `id_type_trans` int unsigned NOT NULL AUTO_INCREMENT,
  `nom_type_trans` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id_type_trans`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `typetransactions`
--

LOCK TABLES `typetransactions` WRITE;
/*!40000 ALTER TABLE `typetransactions` DISABLE KEYS */;
INSERT INTO `typetransactions` VALUES (1,'CREDIT'),(2,'DEBIT');
/*!40000 ALTER TABLE `typetransactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id_users` int unsigned NOT NULL AUTO_INCREMENT,
  `id_email` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name_user` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `first_name` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL,
  `birth_date` date NOT NULL,
  `password` char(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role_id` int unsigned NOT NULL,
  PRIMARY KEY (`id_users`),
  UNIQUE KEY `ind_id_email` (`id_email`),
  UNIQUE KEY `ind_password` (`password`),
  KEY `fk_role_id` (`role_id`),
  CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id_roles`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'jack.dupont@yahoo.fr','DUPONT','Jack','1982-01-22','$2a$10$L17796jDsldbIAteOItmZONsqtcHYBBOO8YMiIZ8zQ8TpqkkI6YBm',2),(2,'mireille.benoit@hotmail.com','BENOIT','Mireille','1970-12-31','$2a$10$f0kDO5HMomT0zjdC7YwSF.gBnof2mNe94E6TGfCERPByrFHxiluUy',1),(3,'sebastien.martin@hotmail.fr','MARTIN','Sébastien','1977-09-19','$2a$10$IAzJLgE0gtud70R3L4cjiuTT8OmKZg7l.QKQxz3phUZWUu9BXfi1S',2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'paymybuddy'
--

--
-- Dumping routines for database 'paymybuddy'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-05 18:40:32
