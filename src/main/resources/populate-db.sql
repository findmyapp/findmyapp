
-- MySQL dump 10.13  Distrib 5.5.9, for Win32 (x86)
--
-- Host: 10.110.60.94    Database: findmydb
-- ------------------------------------------------------
-- Server version	5.1.57-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `APPSTORE_APPLICATION`
--

DROP TABLE IF EXISTS `APPSTORE_APPLICATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `APPSTORE_APPLICATION` (
  `appstore_application_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `platform` enum('1','2') DEFAULT NULL,
  `market_uri` varchar(70) DEFAULT NULL,
  `market_identifier` varchar(45) DEFAULT NULL,
  `appstore_developer_id` int(11) DEFAULT NULL,
  `facebook_secret` varchar(45) DEFAULT NULL,
  `developer_token` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`appstore_application_id`),
  KEY `appstore_developer_id_fk` (`appstore_developer_id`),
  CONSTRAINT `appstore_developer_id_fk` 
	FOREIGN KEY (`appstore_developer_id`) 
	REFERENCES `APPSTORE_DEVELOPER` (`appstore_developer_id`) 
	ON DELETE NO ACTION 
	ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `APPSTORE_APPLICATION`
--

LOCK TABLES `APPSTORE_APPLICATION` WRITE;
/*!40000 ALTER TABLE `APPSTORE_APPLICATION` DISABLE KEYS */;
INSERT INTO `APPSTORE_APPLICATION` VALUES 
	(1,'MyStar','2',NULL,'no.jg.jinfo',NULL,NULL,NULL),
	(2,'Battleheart','2',NULL,'com.KelliNoda.Battleheart',NULL,NULL,NULL),
	(3,'iOS-Test','1',NULL,NULL,NULL,NULL,NULL),
	(4,'MyTest','1',NULL,NULL,NULL,NULL,NULL),
	(5,'Meteor Blitz','2',NULL,'com.alleylabs.MeteorBlitz',NULL,NULL,NULL);
/*!40000 ALTER TABLE `APPSTORE_APPLICATION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `APPSTORE_DEVELOPER`
--

DROP TABLE IF EXISTS `APPSTORE_DEVELOPER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `APPSTORE_DEVELOPER` (
  `appstore_developer_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(15) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `salt` varchar(20) DEFAULT NULL,
  `fullname` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `developer_token` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`appstore_developer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `APPSTORE_DEVELOPER`
--

LOCK TABLES `APPSTORE_DEVELOPER` WRITE;
/*!40000 ALTER TABLE `APPSTORE_DEVELOPER` DISABLE KEYS */;
/*!40000 ALTER TABLE `APPSTORE_DEVELOPER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `POSITION_ACCESSPOINT`
--

DROP TABLE IF EXISTS `POSITION_ACCESSPOINT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `POSITION_ACCESSPOINT` (
  `bssid` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`bssid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `POSITION_ACCESSPOINT`
--

LOCK TABLES `POSITION_ACCESSPOINT` WRITE;
/*!40000 ALTER TABLE `POSITION_ACCESSPOINT` DISABLE KEYS */;
INSERT INTO `POSITION_ACCESSPOINT` VALUES ('edgar'),
										('lyche'),
										('storsalen'),
										('strossa');
/*!40000 ALTER TABLE `POSITION_ACCESSPOINT` ENABLE KEYS */;
UNLOCK TABLES;


-- -----------------------------------------------------
-- Table POSITION_LOCATION`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `POSITION_LOCATION` (
  `position_location_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(20) NULL DEFAULT NULL ,
  PRIMARY KEY (`position_location_id`) )
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = latin1;


--
-- Table structure for table `POSITION_LOCATION_FACT`
--

DROP TABLE IF EXISTS `POSITION_LOCATION_FACT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE  TABLE `POSITION_LOCATION_FACT` (
  `location_fact_id` INT NOT NULL AUTO_INCREMENT ,
  `position_location_id` INT NULL ,
  `text` VARCHAR(255) NULL ,
  PRIMARY KEY (`location_fact_id`) ,
  INDEX `position_location_id_fk` (`position_location_id` ASC) ,
  CONSTRAINT `position_location_id_fk`
    FOREIGN KEY (`position_location_id` )
    REFERENCES `POSITION_LOCATION` (`position_location_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Dumping data for table `POSITION_LOCATION_FACT`
--

LOCK TABLES `POSITION_LOCATION_FACT` WRITE;
/*!40000 ALTER TABLE `POSITION_LOCATION_FACT` DISABLE KEYS */;
/*!40000 ALTER TABLE `POSITION_LOCATION_FACT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `POSITION_SAMPLE`
--

DROP TABLE IF EXISTS `POSITION_SAMPLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `POSITION_SAMPLE` (
  `position_sample_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `position_location_id` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`position_sample_id`) ,
  INDEX `position_location_id_fk2` (`position_location_id` ASC) ,
  CONSTRAINT `position_location_id_fk2`
    FOREIGN KEY (`position_location_id` )
    REFERENCES `POSITION_LOCATION` (`position_location_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `POSITION_SAMPLE`
--

LOCK TABLES `POSITION_SAMPLE` WRITE;
/*!40000 ALTER TABLE `POSITION_SAMPLE` DISABLE KEYS */;
INSERT INTO `POSITION_SAMPLE` VALUES 
	(73,1),
	(76,1),
	(74,2),
	(75,3),
	(77,3);
/*!40000 ALTER TABLE `POSITION_SAMPLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `POSITION_SIGNAL`
--

DROP TABLE IF EXISTS `POSITION_SIGNAL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `POSITION_SIGNAL` (
  `position_signal_id` int(11) NOT NULL AUTO_INCREMENT,
  `position_accesspoint_bssid` varchar(255) DEFAULT NULL,
  `signal_strength` int(11) DEFAULT NULL,
  `position_sample_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`position_signal_id`),
  KEY `position_sample_fk` (`position_sample_id`),
  KEY `position_accesspoint_fk` (`position_accesspoint_bssid`),
  CONSTRAINT `position_sample_fk` 
	FOREIGN KEY (`position_sample_id`) 
	REFERENCES `POSITION_SAMPLE` (`position_sample_id`) 
	ON DELETE NO ACTION 
	ON UPDATE NO ACTION,
  CONSTRAINT `position_accesspoint_fk` 
	FOREIGN KEY (`position_accesspoint_bssid`) 
	REFERENCES `POSITION_ACCESSPOINT` (`bssid`) 
	ON DELETE NO ACTION 
	ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1194 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `POSITION_SIGNAL`
--

LOCK TABLES `POSITION_SIGNAL` WRITE;
/*!40000 ALTER TABLE `POSITION_SIGNAL` DISABLE KEYS */;
INSERT INTO `POSITION_SIGNAL` VALUES 
	(1189,'strossa',-20,73),
	(1190,'storsalen',-70,73),
	(1191,'edgar',-90,73),
	(1192,'strossa',-90,77),
	(1193,'edgar',-20,77);
/*!40000 ALTER TABLE `POSITION_SIGNAL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `POSITION_USER_POSITION`
--

DROP TABLE IF EXISTS `POSITION_USER_POSITION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `POSITION_USER_POSITION` (
  `user_id` INT NOT NULL ,
  `position_location_id` INT NULL ,
  `registered_time` TIMESTAMP NULL ,
  PRIMARY KEY (`user_id`) ,
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id` )
    REFERENCES `USER` (`user_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `POSITION_USER_POSITION`
--

LOCK TABLES `POSITION_USER_POSITION` WRITE;
/*!40000 ALTER TABLE `POSITION_USER_POSITION` DISABLE KEYS */;
INSERT INTO `POSITION_USER_POSITION` VALUES (1,1,'2011-07-04 15:33:27');
/*!40000 ALTER TABLE `POSITION_USER_POSITION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SENSOR_HUMIDITY`
--

DROP TABLE IF EXISTS `SENSOR_HUMIDITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SENSOR_HUMIDITY` (
  `sensor_humidity_id` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(20) DEFAULT NULL,
  `value` float DEFAULT NULL,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`sensor_humidity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SENSOR_HUMIDITY`
--

LOCK TABLES `SENSOR_HUMIDITY` WRITE;
/*!40000 ALTER TABLE `SENSOR_HUMIDITY` DISABLE KEYS */;
INSERT INTO `SENSOR_HUMIDITY` VALUES 
	(1,'strossa',3,NULL),
	(2,'strossa',2,NULL),
	(3,'strossa',100,NULL),
	(4,'storsalen',1,NULL),
	(5,'Strossa',59.65,NULL),
	(6,'Strossa',59.92,NULL),
	(7,'Strossa',60.56,'2011-07-01 07:33:14'),
	(8,'Strossa',60.28,'2011-07-01 07:33:21'),
	(9,'Strossa',60.07,'2011-07-01 07:33:37'),
	(10,'Strossa',12.32,'2011-07-01 07:33:41'),
	(11,'Strossa',12.32,'2011-07-01 07:33:49'),
	(12,'Strossa',60.56,'2011-07-01 07:33:53'),
	(13,'Strossa',60.22,'2011-07-01 07:34:09'),
	(14,'Strossa',60.19,'2011-07-01 07:34:31'),
	(15,'Strossa',60.19,'2011-07-01 07:34:53'),
	(16,'Strossa',59.58,'2011-07-01 07:35:57'),
	(17,'Strossa',59.43,'2011-07-01 07:36:10'),
	(18,'Strossa',59.98,'2011-07-01 07:36:24'),
	(19,'Strossa',59.89,'2011-07-01 07:36:41'),
	(20,'Strossa',60.28,'2011-07-01 07:36:55'),
	(21,'Strossa',59.74,'2011-07-01 07:37:08'),
	(22,'Strossa',59.61,'2011-07-01 07:37:21'),
	(23,'Strossa',59.61,'2011-07-01 07:37:33'),
	(24,'Strossa',59.49,'2011-07-01 07:37:46'),
	(25,'Strossa',59.13,'2011-07-01 07:38:06'),
	(26,'Strossa',59.22,'2011-07-01 07:38:26'),
	(27,'Strossa',59.4,'2011-07-01 07:38:46'),
	(28,'Strossa',59.71,'2011-07-01 07:39:06'),
	(29,'Strossa',58.94,'2011-07-01 07:39:26'),
	(30,'Strossa',58.79,'2011-07-01 07:39:46'),
	(31,'Strossa',58.67,'2011-07-01 07:40:06'),
	(32,'Strossa',59.58,'2011-07-01 07:53:58'),
	(33,'Strossa',59.61,'2011-07-01 07:58:23'),
	(34,'Strossa',59.28,'2011-07-01 07:58:53'),
	(35,'Strossa',59.37,'2011-07-01 07:59:03'),
	(36,'Strossa',59.28,'2011-07-01 07:59:21'),
	(37,'Strossa',59.43,'2011-07-01 08:01:27'),
	(38,'Strossa',60.31,'2011-07-01 08:01:34'),
	(39,'Strossa',60.13,'2011-07-01 08:01:47'),
	(40,'Strossa',59.52,'2011-07-01 08:01:59'),
	(41,'Strossa',59.31,'2011-07-01 08:02:48'),
	(42,'Strossa',59.25,'2011-07-01 08:02:56'),
	(43,'Strossa',59.1,'2011-07-01 08:03:09'),
	(44,'Strossa',58.97,'2011-07-01 08:03:29'),
	(45,'Strossa',59.46,'2011-07-01 08:03:47'),
	(46,'Strossa',59.22,'2011-07-01 08:04:48'),
	(47,'Strossa',59.1,'2011-07-01 08:04:56'),
	(48,'Strossa',59,'2011-07-01 08:05:45'),
	(49,'Strossa',59,'2011-07-01 08:05:52'),
	(50,'Strossa',58.79,'2011-07-01 08:06:11'),
	(51,'Strossa',58.97,'2011-07-01 08:12:21'),
	(52,'Strossa',58.82,'2011-07-01 08:12:28'),
	(53,'Strossa',58.79,'2011-07-01 08:12:50'),
	(54,'Strossa',58.79,'2011-07-01 08:13:03'),
	(55,'Strossa',58.79,'2011-07-01 08:13:16'),
	(56,'Strossa',58.76,'2011-07-01 08:13:36'),
	(57,'Strossa',58.79,'2011-07-01 08:13:55'),
	(58,'Strossa',58.76,'2011-07-01 08:14:15'),
	(59,'Strossa',58.97,'2011-07-01 08:14:36'),
	(60,'Strossa',59.77,'2011-07-01 08:14:57'),
	(61,'Strossa',59.28,'2011-07-01 08:15:17'),
	(62,'Strossa',59.1,'2011-07-01 08:15:37'),
	(63,'Strossa',58.79,'2011-07-01 08:15:58'),
	(64,'Strossa',59.19,'2011-07-01 08:38:29'),
	(65,'Strossa',59.16,'2011-07-01 08:38:36'),
	(66,'Strossa',59.04,'2011-07-01 08:38:49'),
	(67,'Strossa',58.97,'2011-07-01 08:39:05'),
	(68,'Strossa',58.94,'2011-07-01 08:39:17'),
	(69,'Strossa',59.04,'2011-07-01 08:39:32'),
	(70,'Strossa',59,'2011-07-01 08:39:52'),
	(71,'Strossa',58.76,'2011-07-01 08:40:12'),
	(72,'Strossa',59.19,'2011-07-01 08:40:56'),
	(73,'Strossa',59.8,'2011-07-01 08:41:04'),
	(74,'Strossa',59.74,'2011-07-01 08:41:17'),
	(75,'Strossa',59.65,'2011-07-01 08:41:30'),
	(76,'Strossa',59.19,'2011-07-01 08:41:46'),
	(77,'Strossa',59,'2011-07-01 08:42:01'),
	(78,'Strossa',58.82,'2011-07-01 08:42:21'),
	(79,'Strossa',58.76,'2011-07-01 08:42:41'),
	(80,'Strossa',58.67,'2011-07-01 08:43:11'),
	(81,'Strossa',58.85,'2011-07-01 08:43:31'),
	(82,'Strossa',58.67,'2011-07-01 08:43:51'),
	(83,'Strossa',58.76,'2011-07-01 08:44:11'),
	(84,'Strossa',58.61,'2011-07-01 08:44:31'),
	(85,'Strossa',58.88,'2011-07-01 08:44:48'),
	(86,'Strossa',58.82,'2011-07-01 08:45:08'),
	(87,'Strossa',59.07,'2011-07-01 08:45:28'),
	(88,'Strossa',58.67,'2011-07-01 08:45:47'),
	(89,'Strossa',58.61,'2011-07-01 08:46:08'),
	(90,'Strossa',58.67,'2011-07-01 08:46:37'),
	(91,'Strossa',58.7,'2011-07-01 08:46:57'),
	(92,'Strossa',58.61,'2011-07-01 08:47:17'),
	(93,'Strossa',58.61,'2011-07-01 08:47:37'),
	(94,'Strossa',58.64,'2011-07-01 08:47:57'),
	(95,'Strossa',58.58,'2011-07-01 08:48:16'),
	(96,'Strossa',58.7,'2011-07-01 08:48:36'),
	(97,'Strossa',58.7,'2011-07-01 08:48:56'),
	(98,'Strossa',58.61,'2011-07-01 08:49:16'),
	(99,'Strossa',58.79,'2011-07-01 08:49:35'),
	(100,'Strossa',58.82,'2011-07-01 08:49:55'),
	(101,'Strossa',58.85,'2011-07-01 08:50:13'),
	(102,'Strossa',58.82,'2011-07-01 08:50:32'),
	(103,'Strossa',58.76,'2011-07-01 08:50:52'),
	(104,'Strossa',58.36,'2011-07-01 08:56:14'),
	(105,'Strossa',58.02,'2011-07-01 08:56:23'),
	(106,'Strossa',57.99,'2011-07-01 08:56:35'),
	(107,'Strossa',59.46,'2011-07-01 08:56:49'),
	(108,'Strossa',59.55,'2011-07-01 10:43:25'),
	(109,'Strossa',59.19,'2011-07-01 10:43:36'),
	(110,'Strossa',58.76,'2011-07-01 10:43:49'),
	(111,'flytoget',3,'2011-07-04 13:55:49'),
	(112,'flytoget',98,'2011-07-04 14:10:55'),
	(113,'flytoget',45,'2011-07-04 14:11:51');
/*!40000 ALTER TABLE `SENSOR_HUMIDITY` ENABLE KEYS */;
UNLOCK TABLES;
--
-- Table structure for table `SENSOR_NOISE`
--

DROP TABLE IF EXISTS `SENSOR_NOISE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SENSOR_NOISE` (
  `sensor_noise_id` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(45) DEFAULT NULL,
  `decibel` float DEFAULT NULL,
  `raw_average` int(11) DEFAULT NULL,
  `raw_max` int(11) DEFAULT NULL,
  `raw_min` int(11) DEFAULT NULL,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`sensor_noise_id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SENSOR_NOISE`
--

LOCK TABLES `SENSOR_NOISE` WRITE;
/*!40000 ALTER TABLE `SENSOR_NOISE` DISABLE KEYS */;
INSERT INTO `SENSOR_NOISE` VALUES 
	(1,'strossa',111.111,44,99,3,NULL),
	(2,'strossa',111.111,44,99,3,NULL),
	(3,'storsalen',111.111,1,2,3,NULL),
	(4,'strossa',111.111,33,4,3,NULL),
	(5,'strossa',111.111,33,4,3,NULL),
	(6,'strossa',111.111,33,4,4,NULL),
	(7,'strossa',111.111,33,4,4,NULL),
	(8,'strossa',111.111,33,4,4,NULL),
	(9,'strossa',111.111,33,4,4,NULL),
	(10,'strossa',111.111,33,4,4,NULL),
	(11,'strossa',111.111,33,4,4,NULL),
	(12,'strossa',111.111,33,4,4,NULL),
	(13,'Strossa',111.111,338,369,320,NULL),
	(14,'Strossa',111.111,346,458,254,NULL),
	(15,'Strossa',111.111,337,361,320,NULL),
	(16,'Strossa',111.111,347,380,304,NULL),
	(17,'Strossa',111.111,352,391,317,'2011-07-01 07:33:28'),
	(18,'Strossa',111.111,336,363,317,'2011-07-01 07:33:42'),
	(19,'Strossa',111.111,330,388,257,'2011-07-01 07:33:58'),
	(20,'Strossa',111.111,336,353,318,'2011-07-01 07:34:15'),
	(21,'Strossa',111.111,341,411,295,'2011-07-01 07:34:37'),
	(22,'Strossa',111.111,347,391,302,'2011-07-01 07:34:59'),
	(23,'Strossa',111.111,341,364,315,'2011-07-01 07:37:11'),
	(24,'Strossa',111.111,340,360,329,'2011-07-01 07:37:24'),
	(25,'Strossa',111.111,349,392,323,'2011-07-01 07:37:37'),
	(26,'Strossa',111.111,337,351,327,'2011-07-01 07:37:50'),
	(27,'Strossa',111.111,339,368,320,'2011-07-01 07:38:12'),
	(28,'Strossa',111.111,339,381,313,'2011-07-01 07:38:32'),
	(29,'Strossa',111.111,341,361,318,'2011-07-01 07:38:52'),
	(30,'Strossa',111.111,344,368,323,'2011-07-01 07:39:12'),
	(31,'Strossa',111.111,359,457,308,'2011-07-01 07:39:32'),
	(32,'Strossa',111.111,346,372,310,'2011-07-01 07:39:52'),
	(33,'Strossa',111.111,336,359,313,'2011-07-01 07:59:10'),
	(34,'Strossa',111.111,339,354,328,'2011-07-01 07:59:27'),
	(35,'Strossa',111.111,341,366,326,'2011-07-01 08:01:37'),
	(36,'Strossa',111.111,339,371,320,'2011-07-01 08:01:50'),
	(37,'Strossa',111.111,342,361,328,'2011-07-01 08:03:00'),
	(38,'Strossa',111.111,353,402,306,'2011-07-01 08:03:13'),
	(39,'Strossa',111.111,337,353,323,'2011-07-01 08:03:36'),
	(40,'Strossa',111.111,338,361,316,'2011-07-01 08:03:53'),
	(41,'Strossa',111.111,349,420,317,'2011-07-01 08:05:00'),
	(42,'Strossa',111.111,344,365,329,'2011-07-01 08:05:57'),
	(43,'Strossa',111.111,337,381,297,'2011-07-01 08:12:34'),
	(44,'Strossa',111.111,338,355,326,'2011-07-01 08:12:53'),
	(45,'Strossa',111.111,336,348,320,'2011-07-01 08:13:06'),
	(46,'Strossa',111.111,341,352,323,'2011-07-01 08:13:22'),
	(47,'Strossa',111.111,339,357,320,'2011-07-01 08:13:42'),
	(48,'Strossa',111.111,339,357,310,'2011-07-01 08:14:01'),
	(49,'Strossa',111.111,339,367,313,'2011-07-01 08:14:22'),
	(50,'Strossa',111.111,348,363,330,'2011-07-01 08:14:43'),
	(51,'Strossa',111.111,343,360,320,'2011-07-01 08:15:03'),
	(52,'Strossa',111.111,338,350,320,'2011-07-01 08:15:23'),
	(53,'Strossa',111.111,341,369,320,'2011-07-01 08:15:43'),
	(54,'Strossa',111.111,339,363,318,'2011-07-01 08:16:04'),
	(55,'Strossa',111.111,339,374,298,'2011-07-01 08:38:40'),
	(56,'Strossa',111.111,338,352,322,'2011-07-01 08:38:52'),
	(57,'Strossa',111.111,339,357,328,'2011-07-01 08:39:08'),
	(58,'Strossa',111.111,341,357,330,'2011-07-01 08:39:21'),
	(59,'Strossa',111.111,338,367,315,'2011-07-01 08:39:38'),
	(60,'Strossa',111.111,335,378,307,'2011-07-01 08:39:58'),
	(61,'Strossa',111.111,340,367,308,'2011-07-01 08:41:07'),
	(62,'Strossa',111.111,339,348,332,'2011-07-01 08:41:21'),
	(63,'Strossa',111.111,339,370,312,'2011-07-01 08:41:36'),
	(64,'Strossa',111.111,341,384,278,'2011-07-01 08:41:49'),
	(65,'Strossa',111.111,340,361,308,'2011-07-01 08:42:07'),
	(66,'Strossa',111.111,352,461,260,'2011-07-01 08:42:27'),
	(67,'Strossa',111.111,340,389,309,'2011-07-01 08:42:57'),
	(68,'Strossa',111.111,344,380,323,'2011-07-01 08:43:17'),
	(69,'Strossa',111.111,339,367,274,'2011-07-01 08:43:37'),
	(70,'Strossa',111.111,342,363,326,'2011-07-01 08:43:57'),
	(71,'Strossa',111.111,338,359,324,'2011-07-01 08:44:17'),
	(72,'Strossa',111.111,344,363,323,'2011-07-01 08:44:34'),
	(73,'Strossa',111.111,338,353,326,'2011-07-01 08:44:54'),
	(74,'Strossa',111.111,341,385,304,'2011-07-01 08:45:14'),
	(75,'Strossa',111.111,339,365,315,'2011-07-01 08:45:34'),
	(76,'Strossa',111.111,337,356,322,'2011-07-01 08:45:54'),
	(77,'Strossa',111.111,344,374,321,'2011-07-01 08:46:13'),
	(78,'Strossa',111.111,339,368,287,'2011-07-01 08:46:43'),
	(79,'Strossa',111.111,343,362,328,'2011-07-01 08:47:03'),
	(80,'Strossa',111.111,339,352,316,'2011-07-01 08:47:23'),
	(81,'Strossa',111.111,341,354,323,'2011-07-01 08:47:43'),
	(82,'Strossa',111.111,343,375,324,'2011-07-01 08:48:03'),
	(83,'Strossa',111.111,339,370,308,'2011-07-01 08:48:22'),
	(84,'Strossa',111.111,342,384,313,'2011-07-01 08:48:42'),
	(85,'Strossa',111.111,338,353,327,'2011-07-01 08:49:02'),
	(86,'Strossa',111.111,343,372,313,'2011-07-01 08:49:22'),
	(87,'Strossa',111.111,339,375,279,'2011-07-01 08:49:41'),
	(88,'Strossa',111.111,339,350,325,'2011-07-01 08:49:59'),
	(89,'Strossa',111.111,340,355,330,'2011-07-01 08:50:19'),
	(90,'Strossa',111.111,341,355,321,'2011-07-01 08:50:38'),
	(91,'Strossa',111.111,342,393,291,'2011-07-01 08:56:26'),
	(92,'Strossa',111.111,357,491,238,'2011-07-01 08:56:39'),
	(93,'Strossa',111.111,340,400,294,'2011-07-01 08:56:52'),
	(94,'Strossa',111.111,341,359,320,'2011-07-01 10:43:40'),
	(95,'Strossa',111.111,355,435,261,'2011-07-01 10:43:56'),
	(96,'flytoget',-8.15631,400,600,100,'2011-07-04 08:22:53'),
	(97,'flytoget',0,1023,600,100,'2011-07-04 08:23:19'),
	(98,'flytoget',20.1975,1023,600,100,'2011-07-04 11:44:49'),
	(99,'flytoget',-12.0412,25,30,10,'2011-07-04 11:45:26'),
	(100,'flytoget',12.0412,400,600,10,'2011-07-04 11:45:48'),
	(101,'flytoget',6.0206,200,600,10,'2011-07-04 11:45:57'),
	(102,'flytoget',0,100,600,10,'2011-07-04 11:46:03'),
	(103,'flytoget',12.0412,400,680,300,'2011-07-04 13:38:56'),
	(104,'flytoget',12.0412,400,680,300,'2011-07-04 13:39:17'),
	(105,'flytoget',32.0412,400,680,300,'2011-07-04 13:39:38');
/*!40000 ALTER TABLE `SENSOR_NOISE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SENSOR_TEMPERATURE`
--

DROP TABLE IF EXISTS `SENSOR_TEMPERATURE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SENSOR_TEMPERATURE` (
  `sensor_temperature_id` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(20) DEFAULT NULL,
  `value` float DEFAULT NULL,
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`sensor_temperature_id`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SENSOR_TEMPERATURE`
--

LOCK TABLES `SENSOR_TEMPERATURE` WRITE;
/*!40000 ALTER TABLE `SENSOR_TEMPERATURE` DISABLE KEYS */;
INSERT INTO `SENSOR_TEMPERATURE` VALUES 
	(1,'Strossa',25.42,'2011-06-29 18:32:10'),
	(2,'Strossa',25.42,'2011-06-29 18:34:18'),
	(3,'Strossa',25.42,'2011-06-29 18:36:04'),
	(4,'Strossa',25.42,'2011-06-29 19:19:54'),
	(5,'Borte',9.23423,'0000-00-00 00:00:00'),
	(6,'Borte',9.23423,'2011-06-29 19:22:12'),
	(7,'Strossa',25.42,'2011-06-29 19:22:25'),
	(8,'storo',40,'2011-06-29 20:08:10'),
	(9,'Strodssa',25.42,'2011-06-29 20:13:37'),
	(10,'storo',40,'2011-06-29 20:20:30'),
	(11,'stofro',40,'2011-06-29 20:20:43'),
	(12,'stofro',40,'2011-06-29 20:24:52'),
	(13,'strossa',40,'2011-06-29 20:25:28'),
	(14,'strossa',43,'2011-06-29 20:33:46'),
	(15,'storsalen',19,'2011-06-29 20:33:58'),
	(16,'strossa',899,'2011-06-30 12:43:37'),
	(17,'strossa',899,'2011-06-30 12:44:47'),
	(18,'storsalen',19,'2011-06-30 15:13:59'),
	(19,'strossa',3,'2011-06-30 15:49:26'),
	(20,'strossa',4,'2011-06-30 15:49:34'),
	(21,'accenture_fornebu',3,'2011-06-30 15:50:14'),
	(22,'Accenture_fornebu',89,'2011-06-30 15:50:23'),
	(23,'storsalen',1,'2011-06-30 15:57:01'),
	(24,'storo',3,'2011-06-30 15:58:30'),
	(25,'xkxkxkereru',2,'2011-06-30 15:59:25'),
	(26,'stroassa',4,'2011-07-01 06:36:34'),
	(27,'strossa',55,'2011-07-01 06:37:29'),
	(28,'Strossa',24.41,'2011-07-01 07:22:03'),
	(29,'Strossa',24.32,'2011-07-01 07:33:18'),
	(30,'Strossa',24.37,'2011-07-01 07:33:34'),
	(31,'Strossa',24.34,'2011-07-01 07:33:49'),
	(32,'Strossa',24.38,'2011-07-01 07:34:05'),
	(33,'Strossa',24.4,'2011-07-01 07:34:24'),
	(34,'Strossa',24.42,'2011-07-01 07:34:47'),
	(35,'Strossa',24.54,'2011-07-01 07:36:00'),
	(36,'Strossa',24.54,'2011-07-01 07:36:28'),
	(37,'Strossa',24.55,'2011-07-01 07:37:04'),
	(38,'Strossa',24.52,'2011-07-01 07:37:17'),
	(39,'Strossa',24.55,'2011-07-01 07:37:30'),
	(40,'Strossa',24.57,'2011-07-01 07:37:43'),
	(41,'Strossa',24.57,'2011-07-01 07:38:01'),
	(42,'Strossa',24.59,'2011-07-01 07:38:20'),
	(43,'Strossa',24.58,'2011-07-01 07:38:40'),
	(44,'Strossa',24.61,'2011-07-01 07:39:00'),
	(45,'Strossa',24.62,'2011-07-01 07:39:20'),
	(46,'Strossa',24.61,'2011-07-01 07:39:40'),
	(47,'Strossa',24.56,'2011-07-01 07:40:00'),
	(48,'Strossa',24.38,'2011-07-01 07:54:01'),
	(49,'Strossa',24.52,'2011-07-01 07:58:59'),
	(50,'Strossa',24.53,'2011-07-01 07:59:17'),
	(51,'Strossa',24.66,'2011-07-01 08:01:30'),
	(52,'Strossa',24.64,'2011-07-01 08:01:43'),
	(53,'Strossa',24.63,'2011-07-01 08:01:56'),
	(54,'Strossa',24.6,'2011-07-01 08:02:52'),
	(55,'Strossa',24.59,'2011-07-01 08:03:06'),
	(56,'Strossa',24.6,'2011-07-01 08:03:22'),
	(57,'Strossa',24.61,'2011-07-01 08:03:43'),
	(58,'Strossa',24.58,'2011-07-01 08:04:00'),
	(59,'Strossa',24.63,'2011-07-01 08:04:52'),
	(60,'Strossa',24.61,'2011-07-01 08:05:06'),
	(61,'Strossa',24.56,'2011-07-01 08:05:49'),
	(62,'Strossa',24.56,'2011-07-01 08:06:06'),
	(63,'Strossa',24.7,'2011-07-01 08:12:24'),
	(64,'Strossa',24.72,'2011-07-01 08:12:46'),
	(65,'Strossa',24.71,'2011-07-01 08:12:59'),
	(66,'Strossa',24.69,'2011-07-01 08:13:12'),
	(67,'Strossa',24.67,'2011-07-01 08:13:30'),
	(68,'Strossa',24.62,'2011-07-01 08:13:50'),
	(69,'Strossa',24.63,'2011-07-01 08:14:09'),
	(70,'Strossa',24.59,'2011-07-01 08:14:30'),
	(71,'Strossa',24.6,'2011-07-01 08:14:52'),
	(72,'Strossa',24.64,'2011-07-01 08:15:11'),
	(73,'Strossa',24.66,'2011-07-01 08:15:31'),
	(74,'Strossa',24.68,'2011-07-01 08:15:51'),
	(75,'Strossa',24.62,'2011-07-01 08:38:33'),
	(76,'Strossa',24.66,'2011-07-01 08:38:45'),
	(77,'Strossa',24.7,'2011-07-01 08:39:01'),
	(78,'Strossa',24.7,'2011-07-01 08:39:14'),
	(79,'Strossa',24.7,'2011-07-01 08:39:27'),
	(80,'Strossa',24.73,'2011-07-01 08:39:46'),
	(81,'Strossa',24.7,'2011-07-01 08:40:06'),
	(82,'Strossa',24.74,'2011-07-01 08:41:00'),
	(83,'Strossa',24.76,'2011-07-01 08:41:14'),
	(84,'Strossa',24.77,'2011-07-01 08:41:27'),
	(85,'Strossa',24.77,'2011-07-01 08:41:42'),
	(86,'Strossa',24.78,'2011-07-01 08:41:55'),
	(87,'Strossa',24.78,'2011-07-01 08:42:15'),
	(88,'Strossa',24.82,'2011-07-01 08:42:35'),
	(89,'Strossa',24.81,'2011-07-01 08:43:05'),
	(90,'Strossa',24.81,'2011-07-01 08:43:25'),
	(91,'Strossa',24.8,'2011-07-01 08:43:45'),
	(92,'Strossa',24.8,'2011-07-01 08:44:05'),
	(93,'Strossa',24.79,'2011-07-01 08:44:25'),
	(94,'Strossa',24.83,'2011-07-01 08:44:42'),
	(95,'Strossa',24.84,'2011-07-01 08:45:02'),
	(96,'Strossa',24.84,'2011-07-01 08:45:22'),
	(97,'Strossa',24.84,'2011-07-01 08:45:42'),
	(98,'Strossa',24.85,'2011-07-01 08:46:02'),
	(99,'Strossa',24.82,'2011-07-01 08:46:30'),
	(100,'Strossa',24.82,'2011-07-01 08:46:51'),
	(101,'Strossa',24.83,'2011-07-01 08:47:11'),
	(102,'Strossa',24.8,'2011-07-01 08:47:31'),
	(103,'Strossa',24.79,'2011-07-01 08:47:51'),
	(104,'Strossa',24.78,'2011-07-01 08:48:11'),
	(105,'Strossa',24.78,'2011-07-01 08:48:30'),
	(106,'Strossa',24.78,'2011-07-01 08:48:50'),
	(107,'Strossa',24.77,'2011-07-01 08:49:10'),
	(108,'Strossa',24.74,'2011-07-01 08:49:30'),
	(109,'Strossa',24.74,'2011-07-01 08:49:49'),
	(110,'Strossa',24.73,'2011-07-01 08:50:07'),
	(111,'Strossa',24.72,'2011-07-01 08:50:27'),
	(112,'Strossa',24.72,'2011-07-01 08:50:46'),
	(113,'Strossa',25.1,'2011-07-01 08:56:19'),
	(114,'Strossa',25.2,'2011-07-01 08:56:32'),
	(115,'Strossa',25.29,'2011-07-01 08:56:45'),
	(116,'Strossa',25.36,'2011-07-01 08:56:58'),
	(117,'Strossa',25.08,'2011-07-01 10:43:31'),
	(118,'Strossa',25.06,'2011-07-01 10:43:46'),
	(119,'flytoget',5,'2011-07-04 13:54:48'),
	(120,'flytoget',5,'2011-07-04 13:54:53'),
	(121,'flytoget',5,'2011-07-04 13:55:02'),
	(122,'flytoget',199,'2011-07-04 14:01:51'),
	(123,'flytoget',5454,'2011-07-04 14:12:20'),
	(124,'strossa',454,'2011-07-04 14:36:26');
/*!40000 ALTER TABLE `SENSOR_TEMPERATURE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER`
--

DROP TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `facebook_id` int(11) DEFAULT NULL,
  `registered_date` timestamp NULL DEFAULT NULL,
  `last_logon` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER`
--

LOCK TABLES `USER` WRITE;
/*!40000 ALTER TABLE `USER` DISABLE KEYS */;
INSERT INTO `USER` VALUES 
	(1,772612305,'2011-07-01 10:11:48','2011-07-01 08:11:48'),
	(2,536801600,'2011-07-01 10:19:58','2011-07-01 08:19:58'),
	(3,602365620,'2011-07-01 10:21:33','2011-07-01 08:21:33'),
	(4,896670256,'2011-07-01 10:26:25','2011-07-01 08:26:25');
/*!40000 ALTER TABLE `USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FRIENDS`
--

CREATE TABLE IF NOT EXISTS `FRIENDS` (
  `user1_id` INT(11) NULL DEFAULT NULL ,
  `user2_id` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (user1_id, user2_id) ,
  CONSTRAINT `friend1_id_fk`
   FOREIGN KEY (`user1_id` )
   REFERENCES `USER` (`user_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Table structure for table `event_showing_real`
--

DROP TABLE IF EXISTS `event_showing_real`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_showing_real` (
  `id` int(11) NOT NULL DEFAULT '0',
  `showing_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `publish_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `place` varchar(30) DEFAULT NULL,
  `billig_id` int(11) DEFAULT NULL,
  `event_id` int(11) DEFAULT NULL,
  `netsale_from` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `netsale_to` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `free` enum('true','false') DEFAULT NULL,
  `canceled` enum('true','false') DEFAULT NULL,
  `entrance_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_showing_real`
--

LOCK TABLES `event_showing_real` WRITE;
/*!40000 ALTER TABLE `event_showing_real` DISABLE KEYS */;
INSERT INTO `event_showing_real` VALUES 
	(11,'2011-10-03 13:37:00','2011-10-01 11:37:00','Samfundet',0,1,'2011-10-01 11:37:00','2011-10-21 11:37:00','false','false',2222),
	(21,'2011-10-01 00:00:00','2011-10-07 00:00:00','Dodens dal',0,2,'2011-10-01 00:00:00','2011-10-07 00:00:00','false','false',2222),
	(22,'2011-10-10 18:00:00','2011-10-07 00:00:00','Rundt Dådens dal',0,5,'2011-10-01 00:00:00','2011-10-10 00:00:00','true','false',2222),
	(31,'2011-10-15 20:00:00','2011-10-01 11:37:00','Bodegaen',0,3,'2011-10-01 11:37:00','2011-10-21 11:37:00','false','false',2222),
	(32,'2011-10-15 17:00:00','2011-10-01 11:37:00','Dådens Dal',0,6,'2011-10-01 11:37:00','2011-10-21 11:37:00','false','false',2222);
/*!40000 ALTER TABLE `event_showing_real` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events_event`
--

DROP TABLE IF EXISTS `events_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `events_event` (
  `id` int(11) NOT NULL DEFAULT '0',
  `title` varchar(255) DEFAULT NULL,
  `lead` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `event_type` varchar(30) DEFAULT NULL,
  `image` varchar(100) DEFAULT NULL,
  `thumbnail` varchar(100) DEFAULT NULL,
  `hidden_from_listing` enum('true','false') DEFAULT NULL,
  `slug` varchar(50) DEFAULT NULL,
  `age_limit` smallint(6) DEFAULT NULL,
  `detail_photo_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events_event`
--

LOCK TABLES `events_event` WRITE;
/*!40000 ALTER TABLE `events_event` DISABLE KEYS */;
INSERT INTO `events_event` VALUES 
	(1,'Konsert 1','Lead 1','Dette er et arrangement!','Konsert','bilde1.jpg','thumb1.jpg','false','slug',23,2222),
	(2,'Oktoberfest','Lead 2','Dette blir fest!','fest!','bilde1.jpg','thumb1.jpg','false','slug',23,2222),
	(3,'90-tallsfest','På tide å ta frem klårne fra gotiden!','Dette blir fest! Trondheim fyller opp det meste de finner av rusk og rask og flytter dem ned i Bodegaen slik at de mange studentene fra Glåshaugen skal få pråve seg på dansegulvet :) Garantert masse dyrt ål og vill fest! Fellesnach på Sesam...','fest!','bilde1.jpg','http://localhost/thumb1.jpg','false','slug',23,2222),
	(4,'Velkomstkonsert','UKA 2011 smeller i gang med gratis velkomstkonsert!','Ser frem til en koselig kveld med venner og kjente på Samfundet. UKA er igang og alle er glade!','Konsert','bilde1.jpg','http://localhost/thumb2.jpg','false','slug',23,2222),
	(5,'Opplukk av fulle folk rundt teltet','Oktoberfest er ikke kjent for å våre et arrangement for de edrue, og også i år trengs opprydding.','Enten du rydder opp dge selv eller venner.. Eller kanskje du plukker opp en alt for full jente og hjeper henne hjem i trygghet. Still opp. De kommer til å bli mange som trenger hjelp :P','Konsert','bilde1.jpg','http://localhost/thumb2.jpg','false','slug',23,2222),
	(6,'Aqua','Noen skjånner det er på tide å gi seg, mens andre skjånner det ikke...','Aqua fikk tydeligvis ikke nok av Trondheim for 2 år siden og i år er de tilbake igjen. Hopp og sprett og tjo og hei! Dette blir morro!','Konsert','bilde1.jpg','http://localhost/thumb1.jpg','false','slug',23,2222);
/*!40000 ALTER TABLE `events_event` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-07-05 11:36:39
