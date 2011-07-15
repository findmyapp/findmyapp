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
  `description` varchar(255) DEFAULT NULL,
  `market_identifier` varchar(45) DEFAULT NULL,
  `appstore_developer_id` int(11) DEFAULT NULL,
  `category` enum('none','app_of_the_day') DEFAULT 'none',
  `publish_date` datetime DEFAULT NULL,
  `ranking` double DEFAULT NULL,
  `times_downloaded` int(11) DEFAULT NULL,
  `facebook_app_id` varchar(45) DEFAULT NULL,
  `thumb_image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`appstore_application_id`),
  KEY `appstore_developer_id_fk` (`appstore_developer_id`),
  CONSTRAINT `appstore_developer_id_fk` FOREIGN KEY (`appstore_developer_id`) REFERENCES `APPSTORE_DEVELOPER` (`appstore_developer_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `APPSTORE_APPLICATION`
--

LOCK TABLES `APPSTORE_APPLICATION` WRITE;
/*!40000 ALTER TABLE `APPSTORE_APPLICATION` DISABLE KEYS */;
INSERT INTO `APPSTORE_APPLICATION` VALUES (1,'MyStar','2',NULL,'no.jg.jinfo',NULL,'none',NULL,NULL,NULL,NULL,NULL),(2,'Battleheart','2',NULL,'com.KelliNoda.Battleheart',NULL,'none',NULL,NULL,NULL,NULL,NULL),(3,'iOS-Test','1',NULL,NULL,NULL,'none',NULL,NULL,NULL,NULL,NULL),(4,'MyTest','1',NULL,NULL,NULL,'none',NULL,NULL,NULL,NULL,NULL),(5,'Meteor Blitz','2',NULL,'com.alleylabs.MeteorBlitz',NULL,'app_of_the_day',NULL,NULL,NULL,NULL,NULL),(7,'Mi UKA','1',NULL,'identifier',1,'app_of_the_day','2011-07-05 11:45:23',4,423,NULL,NULL),(8,'Programus Ukaus','1',NULL,'identifier',2,'none','2011-07-05 11:46:41',2,21,NULL,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `APPSTORE_DEVELOPER`
--

LOCK TABLES `APPSTORE_DEVELOPER` WRITE;
/*!40000 ALTER TABLE `APPSTORE_DEVELOPER` DISABLE KEYS */;
INSERT INTO `APPSTORE_DEVELOPER` VALUES (1,'Torstein Barkve','mySecreTTT','saltIsSecure','Torstein Melhus Barkve','torstein@barkve.no','myDevToken'),(2,'ole','mySeTTT','saltIsSecure','Ole Christian Røed','ole@roed.no','myDevToken');
/*!40000 ALTER TABLE `APPSTORE_DEVELOPER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FRIENDS`
--

DROP TABLE IF EXISTS `FRIENDS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FRIENDS` (
  `user1_id` int(11) NOT NULL DEFAULT '0',
  `user2_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user1_id`,`user2_id`),
  CONSTRAINT `friend1_id_fk` FOREIGN KEY (`user1_id`) REFERENCES `USER` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FRIENDS`
--

LOCK TABLES `FRIENDS` WRITE;
/*!40000 ALTER TABLE `FRIENDS` DISABLE KEYS */;
INSERT INTO `FRIENDS` VALUES (1,2),(1,3),(1,4);
/*!40000 ALTER TABLE `FRIENDS` ENABLE KEYS */;
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
INSERT INTO `POSITION_ACCESSPOINT` VALUES ('edgar'),('lyche'),('storsalen'),('strossa');
/*!40000 ALTER TABLE `POSITION_ACCESSPOINT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `POSITION_LOCATION`
--

DROP TABLE IF EXISTS `POSITION_LOCATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `POSITION_LOCATION` (
  `position_location_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`position_location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `POSITION_LOCATION`
--

LOCK TABLES `POSITION_LOCATION` WRITE;
/*!40000 ALTER TABLE `POSITION_LOCATION` DISABLE KEYS */;
INSERT INTO `POSITION_LOCATION` VALUES (1,'strossa'),(2,'storsalen'),(3,'edgar'),(10,'bodegaen'),(11,'knaus'),(12,'selskapssiden');
/*!40000 ALTER TABLE `POSITION_LOCATION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `POSITION_LOCATION_FACT`
--

DROP TABLE IF EXISTS `POSITION_LOCATION_FACT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `POSITION_LOCATION_FACT` (
  `location_fact_id` int(11) NOT NULL AUTO_INCREMENT,
  `position_location_id` int(11) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`location_fact_id`),
  KEY `position_location_id_fk` (`position_location_id`),
  CONSTRAINT `position_location_id_fk` FOREIGN KEY (`position_location_id`) REFERENCES `POSITION_LOCATION` (`position_location_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `POSITION_LOCATION_FACT`
--

LOCK TABLES `POSITION_LOCATION_FACT` WRITE;
/*!40000 ALTER TABLE `POSITION_LOCATION_FACT` DISABLE KEYS */;
INSERT INTO `POSITION_LOCATION_FACT` VALUES (1,1,'En strosse er et hulrom i fjellet der har blitt utvinnet malm. Gruvedrift har det nok aldri vært under Samfundet, men lokalene gjestet i sin tid Pub-a-gogo – en pub mest kjent for sin toppløs servering og sine strippeshow.\n'),(2,2,'Storsalen er selve hjertet av Samfundet. Lokalet har gjestet alt fra Dalai Lama til Sex Pistols – og det meste i mellom. '),(3,2,'Til UKA i 1947 bygde man en dreiescene som fyller det meste av bakscenen, og går helt fram til sceneteppet. Denne ble bygget som en diplomoppgave, og er i dag den eldste i landet som fremdeles er i bruk.'),(4,3,'Edgar er oppkalt etter Edgar B. Schieldrop,  Samfundets første formann og mannen bak sitatet \"Høiskolen vil gjøre dere til studerende, vi, Samfundet, vil gjøre dere til studenter\". '),(5,10,'Bodegaen har siden siden utgravingen i 1930 vært en tumleplass for byens glade studenter. I den første tiden rommet lokalet en bodega i ordets rette forstand; en vinkjeller.'),(6,11,'Knaus er oppkalt etter maleren Knut Knaus som i sin tid stod for mye av den innvendige dekorasjonen på Samfundet. Du kan se noen av hans malerier i Ryttergangen, trappen mellom Rundhallen og Daglighallen.'),(7,12,'Selskapssiden har et gjennomført skandinavisk preg inspirert av hytteliv, mørk skog, jakt og fangst.');
/*!40000 ALTER TABLE `POSITION_LOCATION_FACT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `POSITION_SAMPLE`
--

DROP TABLE IF EXISTS `POSITION_SAMPLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `POSITION_SAMPLE` (
  `position_sample_id` int(11) NOT NULL AUTO_INCREMENT,
  `position_location_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`position_sample_id`),
  KEY `position_location_id_fk2` (`position_location_id`),
  CONSTRAINT `position_location_id_fk2` FOREIGN KEY (`position_location_id`) REFERENCES `POSITION_LOCATION` (`position_location_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `POSITION_SAMPLE`
--

LOCK TABLES `POSITION_SAMPLE` WRITE;
/*!40000 ALTER TABLE `POSITION_SAMPLE` DISABLE KEYS */;
INSERT INTO `POSITION_SAMPLE` VALUES (73,1),(76,1),(74,2),(75,3),(77,3);
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
  CONSTRAINT `position_sample_fk` FOREIGN KEY (`position_sample_id`) REFERENCES `POSITION_SAMPLE` (`position_sample_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `position_accesspoint_fk` FOREIGN KEY (`position_accesspoint_bssid`) REFERENCES `POSITION_ACCESSPOINT` (`bssid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1196 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `POSITION_SIGNAL`
--

LOCK TABLES `POSITION_SIGNAL` WRITE;
/*!40000 ALTER TABLE `POSITION_SIGNAL` DISABLE KEYS */;
INSERT INTO `POSITION_SIGNAL` VALUES (1189,'strossa',-20,73),(1190,'storsalen',-70,73),(1191,'edgar',-90,73),(1192,'strossa',-90,77),(1193,'edgar',-20,77),(1194,'storsalen',-30,74),(1195,'strossa',-60,74);
/*!40000 ALTER TABLE `POSITION_SIGNAL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `POSITION_USER_POSITION`
--

DROP TABLE IF EXISTS `POSITION_USER_POSITION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `POSITION_USER_POSITION` (
  `user_id` int(11) NOT NULL,
  `position_location_id` int(11) DEFAULT NULL,
  `registered_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `USER` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `POSITION_USER_POSITION`
--

LOCK TABLES `POSITION_USER_POSITION` WRITE;
/*!40000 ALTER TABLE `POSITION_USER_POSITION` DISABLE KEYS */;
INSERT INTO `POSITION_USER_POSITION` VALUES (1,1,'2011-07-04 15:33:27'),(2,1,'2011-07-04 15:35:27'),(3,1,'2011-07-04 15:37:27'),(4,2,'2011-07-04 15:37:27');
/*!40000 ALTER TABLE `POSITION_USER_POSITION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SENSOR_BEERTAP`
--

DROP TABLE IF EXISTS `SENSOR_BEERTAP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SENSOR_BEERTAP` (
  `sensor_beertap_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `position_location_id` int(11) DEFAULT NULL,
  `tapnr` int(11) DEFAULT NULL,
  `value` int(11) DEFAULT NULL,
  PRIMARY KEY (`sensor_beertap_id`),
  KEY `location_fk` (`position_location_id`),
  CONSTRAINT `location_fk` FOREIGN KEY (`position_location_id`) REFERENCES `POSITION_LOCATION` (`position_location_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SENSOR_BEERTAP`
--

LOCK TABLES `SENSOR_BEERTAP` WRITE;
/*!40000 ALTER TABLE `SENSOR_BEERTAP` DISABLE KEYS */;
INSERT INTO `SENSOR_BEERTAP` VALUES (6,'2011-07-05 18:22:38',1,443,55),(7,'2011-07-05 18:22:38',2,31,75),(8,'2011-07-05 18:22:38',11,56,115),(9,'2011-07-05 18:22:38',12,223,25),(10,'2011-07-05 18:22:38',3,75,37),(11,'2007-07-05 01:00:00',2,31,43),(12,'2007-07-05 02:00:00',2,31,15),(13,'2007-07-05 03:00:00',2,31,78),(14,'2007-07-05 04:00:00',2,31,18),(15,'2007-07-05 05:00:00',2,31,36),(16,'2007-07-05 06:00:00',2,31,11);
/*!40000 ALTER TABLE `SENSOR_BEERTAP` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SENSOR_HUMIDITY`
--

DROP TABLE IF EXISTS `SENSOR_HUMIDITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SENSOR_HUMIDITY` (
  `sensor_humidity_id` int(11) NOT NULL AUTO_INCREMENT,
  `position_location_id` int(11) DEFAULT NULL,
  `value` float DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`sensor_humidity_id`),
  KEY `humidity_location_fk` (`position_location_id`),
  CONSTRAINT `humidity_location_fk` FOREIGN KEY (`position_location_id`) REFERENCES `POSITION_LOCATION` (`position_location_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SENSOR_HUMIDITY`
--

LOCK TABLES `SENSOR_HUMIDITY` WRITE;
/*!40000 ALTER TABLE `SENSOR_HUMIDITY` DISABLE KEYS */;
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
  `position_location_id` int(11) DEFAULT NULL,
  `decibel` float DEFAULT NULL,
  `raw_average` int(11) DEFAULT NULL,
  `raw_max` int(11) DEFAULT NULL,
  `raw_min` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`sensor_noise_id`),
  KEY `noise_location_id` (`position_location_id`),
  CONSTRAINT `noise_location_id` FOREIGN KEY (`position_location_id`) REFERENCES `POSITION_LOCATION` (`position_location_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SENSOR_NOISE`
--

LOCK TABLES `SENSOR_NOISE` WRITE;
/*!40000 ALTER TABLE `SENSOR_NOISE` DISABLE KEYS */;
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
  `position_location_id` int(11) DEFAULT NULL,
  `value` float DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`sensor_temperature_id`),
  KEY `temperature_location_fk` (`position_location_id`),
  CONSTRAINT `temperature_location_fk` FOREIGN KEY (`position_location_id`) REFERENCES `POSITION_LOCATION` (`position_location_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SENSOR_TEMPERATURE`
--

LOCK TABLES `SENSOR_TEMPERATURE` WRITE;
/*!40000 ALTER TABLE `SENSOR_TEMPERATURE` DISABLE KEYS */;
INSERT INTO `SENSOR_TEMPERATURE` VALUES (127,1,22,'2011-07-06 12:30:11');
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
INSERT INTO `USER` VALUES (1,772612305,'2011-07-01 10:11:48','2011-07-01 08:11:48'),(2,536801600,'2011-07-01 10:19:58','2011-07-01 08:19:58'),(3,602365620,'2011-07-01 10:21:33','2011-07-01 08:21:33'),(4,896670256,'2011-07-01 10:26:25','2011-07-01 08:26:25');
/*!40000 ALTER TABLE `USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_showing_real`
--

DROP TABLE IF EXISTS `UKA_EVENTS`;

CREATE TABLE `UKA_EVENTS` (
  `id` int(11) NOT NULL DEFAULT '0',
  `showing_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `publish_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `place` varchar(30) DEFAULT NULL,
  `billig_id` int(11) DEFAULT NULL,
  `billig_name` varchar(255) DEFAULT NULL,
  `netsale_from` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `netsale_to` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `sale_from` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `sale_to` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `free` BOOLEAN DEFAULT FALSE,
  `available_for_purchase` BOOLEAN DEFAULT TRUE,
  `canceled` BOOLEAN DEFAULT FALSE,
  `entrance_id` int(11) DEFAULT NULL,
  `event_id` int(11) NOT NULL DEFAULT '0',
  `title` varchar(255) DEFAULT NULL,
  `lead` varchar(255) DEFAULT NULL,
  `text` text DEFAULT NULL,
  `event_type` varchar(30) DEFAULT NULL,
  `image` varchar(100) DEFAULT NULL,
  `thumbnail` varchar(100) DEFAULT NULL,
  `age_limit` smallint(6) DEFAULT NULL,
  `spotify_string` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `UKA_EVENTS` WRITE;

INSERT INTO `UKA_EVENTS` VALUES (
	11,
	'2011-10-03 13:37:00',
	'2011-10-01 11:37:00',
	'Samfundet',
	0,
	'Et eller annet',
	'2011-10-01 11:37:00',
	'2011-10-21 11:37:00',
	'2011-10-01 11:37:00',
	'2011-10-21 11:37:00',
	0,
	1,
	0,
	10,
	10,
	'Konsert 1',
	'Lead 1',
	'Dette er et arrangement!',
	'Konsert',
	'bilde1.jpg',
	'thumb1.jpg',
	23,
	'skjdhkjshakjhskdjhsadh');
UNLOCK TABLES;

LOCK TABLES `events_event` WRITE;
/*!40000 ALTER TABLE `events_event` DISABLE KEYS */;
INSERT INTO `events_event` VALUES (1,'Konsert 1','Lead 1','Dette er et arrangement!','Konsert','bilde1.jpg','thumb1.jpg','false','slug',23,2222),(2,'Oktoberfest','Lead 2','Dette blir fest!','fest!','bilde1.jpg','thumb1.jpg','false','slug',23,2222),(3,'90-tallsfest','På tide å ta frem klårne fra gotiden!','Dette blir fest! Trondheim fyller opp det meste de finner av rusk og rask og flytter dem ned i Bodegaen slik at de mange studentene fra Glåshaugen skal få pråve seg på dansegulvet :) Garantert masse dyrt ål og vill fest! Fellesnach på Sesam...','fest!','bilde1.jpg','http://localhost/thumb1.jpg','false','slug',23,2222),(4,'Velkomstkonsert','UKA 2011 smeller i gang med gratis velkomstkonsert!','Ser frem til en koselig kveld med venner og kjente på Samfundet. UKA er igang og alle er glade!','Konsert','bilde1.jpg','http://localhost/thumb2.jpg','false','slug',23,2222),(5,'Opplukk av fulle folk rundt teltet','Oktoberfest er ikke kjent for å våre et arrangement for de edrue, og også i år trengs opprydding.','Enten du rydder opp dge selv eller venner.. Eller kanskje du plukker opp en alt for full jente og hjeper henne hjem i trygghet. Still opp. De kommer til å bli mange som trenger hjelp :P','Konsert','bilde1.jpg','http://localhost/thumb2.jpg','false','slug',23,2222),(6,'Aqua','Noen skjånner det er på tide å gi seg, mens andre skjånner det ikke...','Aqua fikk tydeligvis ikke nok av Trondheim for 2 år siden og i år er de tilbake igjen. Hopp og sprett og tjo og hei! Dette blir morro!','Konsert','bilde1.jpg','http://localhost/thumb1.jpg','false','slug',23,2222);
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

-- Dump completed on 2011-07-06 13:24:55
