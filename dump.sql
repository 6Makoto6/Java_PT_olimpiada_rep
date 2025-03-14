-- MySQL dump 10.19  Distrib 10.3.39-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: Passenger_transportation
-- ------------------------------------------------------
-- Server version	10.3.39-MariaDB-0+deb10u2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `booking` (
  `id_booking` int(11) NOT NULL AUTO_INCREMENT,
  `id_trip` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_status` int(11) NOT NULL,
  PRIMARY KEY (`id_booking`),
  KEY `Booking_Trip_FK` (`id_trip`),
  KEY `Booking_User_FK` (`id_user`),
  KEY `Booking_Status_FK` (`id_status`),
  CONSTRAINT `Booking_Status_FK` FOREIGN KEY (`id_status`) REFERENCES `status` (`id_status`) ON DELETE CASCADE,
  CONSTRAINT `Booking_Trip_FK` FOREIGN KEY (`id_trip`) REFERENCES `trip` (`id_trip`) ON DELETE CASCADE,
  CONSTRAINT `Booking_User_FK` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (1,1,1,2),(2,2,2,1),(3,3,3,3),(4,4,4,4),(5,5,5,2),(6,6,2,2),(7,5,3,2),(10,1,1,1),(22,7,6,1),(23,8,7,2),(24,9,8,3),(25,10,9,4);
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `points`
--

DROP TABLE IF EXISTS `points`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `points` (
  `id_point` int(11) NOT NULL AUTO_INCREMENT,
  `name_point` varchar(100) DEFAULT NULL,
  `address_point` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_point`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `points`
--

LOCK TABLES `points` WRITE;
/*!40000 ALTER TABLE `points` DISABLE KEYS */;
INSERT INTO `points` VALUES (1,'Шереметьево','Москва, Химки, Международный аэропорт Шереметьево'),(2,'Домодедово','Москва, Домодедово, Международный аэропорт Домодедово'),(3,'Казанский вокзал','Москва, Комсомольская площадь, 2'),(4,'Ленинградский вокзал','Москва, Комсомольская площадь, 3'),(5,'Автовокзал Южные ворота','Москва, ул. Кировоградская, 1'),(6,'Пулково','Санкт-Петербург, Пулковское шоссе, 41'),(7,'Вокзал Екатеринбург-Пассажирский','Екатеринбург, ул. Вокзальная, 22'),(8,'Вокзал Новосибирск-Главный','Новосибирск, ул. Шамшурина, 43'),(9,'Сочи Адлер','Сочи, ул. Мира, 50'),(10,'Владивосток','Владивосток, ул. Русская, 37'),(11,'Внуково','Москва, ул. 1-я Рейсовая, 12'),(12,'Жуковский','Жуковский, ул. Гагарина, 1'),(13,'Кольцово','Екатеринбург, ул. Бахчиванджи, 1'),(14,'Толмачёво','Новосибирск, ул. Сибиряков-Гвардейцев, 1'),(15,'Платов','Ростов-на-Дону, ул. Аэропортовая, 1'),(16,'Кневичи','Владивосток, ул. Владимира Сайбеля, 41'),(17,'Уфа','Уфа, ул. Аэропортовая, 1'),(18,'Курумоч','Самара, ул. Аэропортовая, 1'),(19,'Красноярск (Емельяново)','Красноярск, ул. Аэропортовая, 1'),(20,'Храброво','Калининград, ул. Аэропортовая, 1'),(21,'Киевский вокзал','Москва, площадь Киевского Вокзала, 1'),(22,'Ярославский вокзал','Москва, Комсомольская площадь, 5'),(23,'Вокзал Санкт-Петербург-Главный','Санкт-Петербург, Невский проспект, 85'),(24,'Вокзал Нижний Новгород-Московский','Нижний Новгород, ул. Московская, 85А'),(25,'Вокзал Самара','Самара, ул. Льва Толстого, 1'),(26,'Вокзал Челябинск-Главный','Челябинск, ул. Свободы, 2'),(27,'Вокзал Казань-Пассажирская','Казань, ул. Привокзальная, 1'),(28,'Вокзал Ростов-Главный','Ростов-на-Дону, ул. Привокзальная, 1'),(29,'Вокзал Красноярск-Пассажирский','Красноярск, ул. 30 Июля, 1'),(30,'Вокзал Иркутск-Пассажирский','Иркутск, ул. Челнокова, 1'),(31,'Автовокзал Северные ворота','Москва, ул. Дмитровское шоссе, 100'),(32,'Автовокзал Восточные ворота','Москва, ул. Щёлковское шоссе, 75'),(33,'Автовокзал Западные ворота','Москва, ул. Можайское шоссе, 89'),(34,'Автовокзал Центральный','Санкт-Петербург, наб. Обводного канала, 36'),(35,'Автовокзал Екатеринбург','Екатеринбург, ул. 8 Марта, 213'),(36,'Автовокзал Новосибирск','Новосибирск, ул. Сибирская, 35'),(37,'Автовокзал Казань','Казань, ул. Девятаева, 15'),(38,'Автовокзал Нижний Новгород','Нижний Новгород, ул. Лядова, 2'),(39,'Автовокзал Самара','Самара, ул. Авроры, 207'),(40,'Автовокзал Ростов-на-Дону','Ростов-на-Дону, ул. Сиверса, 1'),(41,'Аэропорт Симферополь','Симферополь, ул. Киевская, 191'),(42,'Аэропорт Минеральные Воды','Минеральные Воды, ул. Аэропортовая, 1'),(43,'Аэропорт Сочи','Сочи, ул. Мира, 50'),(44,'Аэропорт Хабаровск','Хабаровск, ул. Аэропортовая, 1'),(45,'Аэропорт Омск','Омск, ул. Аэропортовая, 1'),(46,'Аэропорт Пермь','Пермь, ул. Аэропортовая, 1'),(47,'Аэропорт Ульяновск','Ульяновск, ул. Аэропортовая, 1'),(48,'Аэропорт Тюмень','Тюмень, ул. Аэропортовая, 1'),(49,'Аэропорт Иркутск','Иркутск, ул. Ширямова, 1'),(50,'Аэропорт Калининград','Калининград, ул. Аэропортовая, 1');
/*!40000 ALTER TABLE `points` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `routes`
--

DROP TABLE IF EXISTS `routes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `routes` (
  `id_route` int(11) NOT NULL AUTO_INCREMENT,
  `id_start_point` int(11) NOT NULL,
  `id_finish_point` int(11) NOT NULL,
  PRIMARY KEY (`id_route`),
  KEY `Routes_Points_FK` (`id_start_point`),
  KEY `Routes_Points_FK_1` (`id_finish_point`),
  CONSTRAINT `Routes_Points_FK` FOREIGN KEY (`id_start_point`) REFERENCES `points` (`id_point`) ON DELETE CASCADE,
  CONSTRAINT `Routes_Points_FK_1` FOREIGN KEY (`id_finish_point`) REFERENCES `points` (`id_point`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `routes`
--

LOCK TABLES `routes` WRITE;
/*!40000 ALTER TABLE `routes` DISABLE KEYS */;
INSERT INTO `routes` VALUES (1,1,6),(2,3,7),(3,4,8),(4,5,9),(5,2,10),(6,1,3),(7,3,2),(8,6,7),(9,7,4),(10,8,5),(11,1,7),(12,1,11),(13,2,12),(14,3,13),(15,4,14),(16,5,15),(17,6,16),(18,6,17),(19,6,18),(20,6,19),(21,6,20),(22,7,21),(23,7,22),(24,7,23),(25,7,24),(26,7,25),(27,8,26),(28,8,27),(29,8,28),(30,8,29),(31,8,30),(32,9,31),(33,9,32),(34,9,33),(35,9,34),(36,9,35),(37,10,36),(38,10,37),(39,10,38),(40,10,39),(41,10,40);
/*!40000 ALTER TABLE `routes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status` (
  `id_status` int(11) NOT NULL AUTO_INCREMENT,
  `name_status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_status`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (1,'Ожидает оплаты'),(2,'Оплачен'),(3,'Отменен'),(4,'Завершен');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transport`
--

DROP TABLE IF EXISTS `transport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transport` (
  `id_transport` int(11) NOT NULL AUTO_INCREMENT,
  `name_transport` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_transport`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transport`
--

LOCK TABLES `transport` WRITE;
/*!40000 ALTER TABLE `transport` DISABLE KEYS */;
INSERT INTO `transport` VALUES (1,'Авиа'),(2,'Ж/Д'),(3,'Автобус'),(4,'Микс');
/*!40000 ALTER TABLE `transport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trip`
--

DROP TABLE IF EXISTS `trip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trip` (
  `id_trip` int(11) NOT NULL AUTO_INCREMENT,
  `id_route` int(11) NOT NULL,
  `datetime` datetime NOT NULL,
  `id_transport` int(11) NOT NULL,
  `limit` int(11) DEFAULT NULL,
  `datetimeend` datetime NOT NULL,
  PRIMARY KEY (`id_trip`),
  KEY `Trip_Transport_FK` (`id_transport`),
  KEY `Trip_Routes_FK` (`id_route`),
  CONSTRAINT `Trip_Routes_FK` FOREIGN KEY (`id_route`) REFERENCES `routes` (`id_route`) ON DELETE CASCADE,
  CONSTRAINT `Trip_Transport_FK` FOREIGN KEY (`id_transport`) REFERENCES `transport` (`id_transport`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip`
--

LOCK TABLES `trip` WRITE;
/*!40000 ALTER TABLE `trip` DISABLE KEYS */;
INSERT INTO `trip` VALUES (1,1,'2025-05-01 08:00:00',1,150,'2025-05-01 10:00:00'),(2,2,'2025-05-02 19:30:00',2,75,'2025-05-02 21:30:00'),(3,3,'2025-12-03 15:45:00',3,25,'2025-12-03 17:45:00'),(4,4,'2025-12-04 09:15:00',3,25,'2025-12-04 09:17:00'),(5,5,'2023-12-05 18:00:00',1,175,'2023-12-05 20:00:00'),(6,6,'2025-05-03 15:30:00',2,5,'2025-05-03 17:30:00'),(7,8,'2025-05-05 12:00:00',2,150,'2025-05-05 16:00:00'),(8,11,'2025-12-12 17:30:00',2,250,'2025-12-12 19:30:00'),(9,1,'2025-05-01 08:00:00',1,150,'2025-05-01 10:00:00'),(10,2,'2025-05-02 19:30:00',2,75,'2025-05-02 21:30:00'),(11,3,'2025-12-03 15:45:00',3,25,'2025-12-03 17:45:00'),(12,4,'2025-12-04 09:15:00',3,25,'2025-12-04 09:17:00'),(13,5,'2023-12-05 18:00:00',1,175,'2023-12-05 20:00:00'),(14,6,'2025-05-03 15:30:00',2,5,'2025-05-03 17:30:00'),(15,8,'2025-05-05 12:00:00',2,150,'2025-05-05 16:00:00'),(16,11,'2025-12-12 17:30:00',2,250,'2025-12-12 19:30:00'),(17,12,'2025-06-01 10:00:00',1,100,'2025-06-01 12:00:00'),(18,13,'2025-06-02 14:00:00',3,50,'2025-06-02 16:00:00'),(19,14,'2025-06-03 09:00:00',2,200,'2025-06-03 11:00:00'),(20,15,'2025-06-04 18:00:00',1,150,'2025-06-04 20:00:00'),(21,16,'2025-06-05 12:30:00',3,30,'2025-06-05 14:30:00'),(22,17,'2025-06-06 08:45:00',2,75,'2025-06-06 10:45:00'),(23,18,'2025-06-07 17:00:00',1,120,'2025-06-07 19:00:00'),(24,19,'2025-06-08 11:15:00',3,40,'2025-06-08 13:15:00'),(25,20,'2025-06-09 16:30:00',2,90,'2025-06-09 18:30:00'),(26,21,'2025-06-10 10:00:00',1,200,'2025-06-10 12:00:00'),(27,22,'2025-06-11 14:45:00',3,60,'2025-06-11 16:45:00'),(28,23,'2025-06-12 09:30:00',2,100,'2025-06-12 11:30:00'),(29,24,'2025-06-13 18:15:00',1,150,'2025-06-13 20:15:00'),(30,25,'2025-06-14 12:00:00',3,25,'2025-06-14 14:00:00'),(31,26,'2025-06-15 08:00:00',2,80,'2025-06-15 10:00:00'),(32,27,'2025-06-16 17:30:00',1,180,'2025-06-16 19:30:00'),(33,28,'2025-06-17 11:45:00',3,35,'2025-06-17 13:45:00'),(34,29,'2025-06-18 16:00:00',2,95,'2025-06-18 18:00:00'),(35,30,'2025-06-19 10:30:00',1,210,'2025-06-19 12:30:00'),(36,31,'2025-06-20 14:15:00',3,45,'2025-06-20 16:15:00'),(37,32,'2025-06-21 09:00:00',2,110,'2025-06-21 11:00:00'),(38,33,'2025-06-22 17:45:00',1,160,'2025-06-22 19:45:00'),(39,34,'2025-06-23 12:30:00',3,55,'2025-06-23 14:30:00'),(40,35,'2025-06-24 08:15:00',2,85,'2025-06-24 10:15:00');
/*!40000 ALTER TABLE `trip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `passport` varchar(100) DEFAULT NULL,
  `tg_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Петр','+79512689535','1234 567890 ГУ МВД РОССИИ ПО МОСКВЕ','example1'),(2,'Иван','+79859638546','2345 678901 ГУ МВД РОССИИ ПО САНКТ-ПЕТЕРБУРГУ','example2'),(3,'Сидор','+79863486179','3456 789012 ГУ МВД РОССИИ ПО МОСКОВСКОЙ ОБЛАСТИ','example3'),(4,'Иван','+79161234567','4567 890123 ГУ МВД РОССИИ ПО НИЖЕГОРОДСКОЙ ОБЛАСТИ','example4'),(5,'Петр','+79162345678','5678 901234 ГУ МВД РОССИИ ПО ТАТАРСТАНУ','example5'),(6,'Мария','+79163456789','6789 012345 ГУ МВД РОССИИ ПО СВЕРДЛОВСКОЙ ОБЛАСТИ','example6'),(7,'Алексей','+79164567890','7890 123456 ГУ МВД РОССИИ ПО ЧЕЛЯБИНСКОЙ ОБЛАСТИ','example7'),(8,'Ольга','+79165678901','8901 234567 ГУ МВД РОССИИ ПО КРАСНОДАРСКОМУ КРАЮ','example8'),(9,'Маргарита',' 79863845987','9012 345678 ГУ МВД РОССИИ ПО РОСТОВСКОЙ ОБЛАСТИ','example9'),(10,'Petr','+79999999999','passport_test','example10'),(24,'Макото','393519644538','Паспорт','lDoterOtBogal');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'Passenger_transportation'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-14 12:27:59
