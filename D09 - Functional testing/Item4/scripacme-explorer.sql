
start transaction;
create database `Acme-Explorer`;
use `Acme-Explorer`; 
create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577'; 
create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F'; 
grant select, insert, update, delete on `Acme-Explorer`.* to 'acme-user'@'%'; 
grant select, insert, update, delete, create, drop, references, index, alter, create temporary tables, lock tables, create view, create routine, alter routine, execute, trigger, show view on `Acme-Explorer`.* to 'acme-manager'@'%'; 

-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: acme-explorer
-- ------------------------------------------------------
-- Server version	5.5.29

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
-- Table structure for table `actor_messagefolder`
--

DROP TABLE IF EXISTS `actor_messagefolder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_messagefolder` (
  `Actor_id` int(11) NOT NULL,
  `messagesFolders_id` int(11) NOT NULL,
  UNIQUE KEY `UK_9r38jrnmcit3asym390tk6fyb` (`messagesFolders_id`),
  CONSTRAINT `FK_9r38jrnmcit3asym390tk6fyb` FOREIGN KEY (`messagesFolders_id`) REFERENCES `messagefolder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_messagefolder`
--

LOCK TABLES `actor_messagefolder` WRITE;
/*!40000 ALTER TABLE `actor_messagefolder` DISABLE KEYS */;
INSERT INTO `actor_messagefolder` VALUES (5445,5467),(5445,5468),(5445,5469),(5445,5470),(5445,5471),(5597,5472),(5597,5473),(5597,5474),(5597,5475),(5597,5476),(5598,5477),(5598,5478),(5598,5479),(5598,5480),(5598,5481),(5599,5482),(5599,5483),(5599,5484),(5599,5485),(5599,5486),(5600,5487),(5600,5488),(5600,5489),(5600,5490),(5600,5491),(5604,5492),(5604,5493),(5604,5494),(5604,5495),(5604,5496),(5605,5497),(5605,5498),(5605,5499),(5605,5500),(5605,5501),(5606,5502),(5606,5503),(5606,5504),(5606,5505),(5606,5506),(5607,5507),(5607,5508),(5607,5509),(5607,5510),(5607,5511),(5608,5512),(5608,5513),(5608,5514),(5608,5515),(5608,5516),(5630,5517),(5630,5518),(5630,5519),(5630,5520),(5630,5521),(5631,5522),(5631,5523),(5631,5524),(5631,5525),(5631,5526),(5632,5527),(5632,5528),(5632,5529),(5632,5530),(5632,5531),(5633,5532),(5633,5533),(5633,5534),(5633,5535),(5633,5536),(5634,5537),(5634,5538),(5634,5539),(5634,5540),(5634,5541),(5635,5542),(5635,5543),(5635,5544),(5635,5545),(5635,5546),(5636,5547),(5636,5548),(5636,5549),(5636,5550),(5636,5551),(5637,5552),(5637,5553),(5637,5554),(5637,5555),(5637,5556),(5638,5557),(5638,5558),(5638,5559),(5638,5560),(5638,5561),(5639,5562),(5639,5563),(5639,5564),(5639,5565),(5639,5566),(5640,5567),(5640,5568),(5640,5569),(5640,5570),(5640,5571);
/*!40000 ALTER TABLE `actor_messagefolder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actor_socialidentity`
--

DROP TABLE IF EXISTS `actor_socialidentity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_socialidentity` (
  `Actor_id` int(11) NOT NULL,
  `socialIdentities_id` int(11) NOT NULL,
  UNIQUE KEY `UK_fvjtbijvsijias3suehxiq0jm` (`socialIdentities_id`),
  CONSTRAINT `FK_fvjtbijvsijias3suehxiq0jm` FOREIGN KEY (`socialIdentities_id`) REFERENCES `socialidentity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_socialidentity`
--

LOCK TABLES `actor_socialidentity` WRITE;
/*!40000 ALTER TABLE `actor_socialidentity` DISABLE KEYS */;
INSERT INTO `actor_socialidentity` VALUES (5597,5578),(5598,5579),(5599,5580),(5600,5581);
/*!40000 ALTER TABLE `actor_socialidentity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_idt4b4u259p6vs4pyr9lax4eg` (`userAccount_id`),
  CONSTRAINT `FK_idt4b4u259p6vs4pyr9lax4eg` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (5445,0,'Administrator address','Administrator1@administrator.com','Admin','+34662657322','Administrator1 Surnames','\0',5446),(5597,0,'Administrator address','Administrator2@administrator.com','Administrator2','+34662657322','Administrator2 Surnames','\0',5447),(5598,0,'Administrator address','Administrator3@administrator.com','Administrator3','+34662657323','Administrator3 Surnames','\0',5448),(5599,0,'Administrator address','Administrator4@administrator.com','Administrator4','+34662657324','Administrator4 Surnames','\0',5449),(5600,0,'Administrator address','Administrator5@administrator.com','Administrator5','+34662657325','Administrator5 Surnames','\0',5450);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `applicationfor`
--

DROP TABLE IF EXISTS `applicationfor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `applicationfor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `brandName` varchar(255) DEFAULT NULL,
  `cvv` int(11) NOT NULL,
  `expirationMonth` varchar(255) DEFAULT NULL,
  `expirationYear` varchar(255) DEFAULT NULL,
  `holderName` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `reasonWhy` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `explorer_id` int(11) NOT NULL,
  `manager_id` int(11) DEFAULT NULL,
  `trip_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_qkn29o630r4wdvtaugkns82g3` (`explorer_id`),
  KEY `FK_ep3lhiylkqxx6w8v5n9jiacf6` (`manager_id`),
  KEY `FK_djyjfgplhq5lreh4yipstoch1` (`trip_id`),
  CONSTRAINT `FK_djyjfgplhq5lreh4yipstoch1` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`),
  CONSTRAINT `FK_ep3lhiylkqxx6w8v5n9jiacf6` FOREIGN KEY (`manager_id`) REFERENCES `manager` (`id`),
  CONSTRAINT `FK_qkn29o630r4wdvtaugkns82g3` FOREIGN KEY (`explorer_id`) REFERENCES `explorer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `applicationfor`
--

LOCK TABLES `applicationfor` WRITE;
/*!40000 ALTER TABLE `applicationfor` DISABLE KEYS */;
INSERT INTO `applicationfor` VALUES (5655,0,'La caixa',102,'03','20','Jose Angel Dominguez Espinaco','4388576018410707','2015-10-21 10:10:00','','ACCEPTED',5635,5630,5646),(5656,0,'Santander',103,'05','21','Daniel Lozano','4388576018410707','2017-12-11 00:10:00','','ACCEPTED',5636,5631,5646),(5657,0,'Santander',655,'06','17','Maria Ruiz','4388576018410707','2017-10-21 01:10:00','','ACCEPTED',5637,5632,5647),(5658,0,'Bankia',876,'04','16','Laura vera','4388576018410707','2017-10-21 02:10:00','','ACCEPTED',5637,5633,5648),(5659,0,'La caixa',724,'05','18','Joaquin rodriguez','4388576018410707','2017-10-21 03:10:00','','CANCELLED',5639,5634,5648),(5660,0,'La caixa',724,'05','18','María rodriguez','4388576018410707','2017-10-21 03:10:00','','PENDING',5640,NULL,5648),(5661,0,'La caixa',724,'05','18','Joaquin rodriguez','4388576018410707','2017-10-27 03:10:00','','ACCEPTED',5639,5630,5650);
/*!40000 ALTER TABLE `applicationfor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `applicationfor_comments`
--

DROP TABLE IF EXISTS `applicationfor_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `applicationfor_comments` (
  `ApplicationFor_id` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  KEY `FK_n30okwyuwhsnvclw83sjncnlt` (`ApplicationFor_id`),
  CONSTRAINT `FK_n30okwyuwhsnvclw83sjncnlt` FOREIGN KEY (`ApplicationFor_id`) REFERENCES `applicationfor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `applicationfor_comments`
--

LOCK TABLES `applicationfor_comments` WRITE;
/*!40000 ALTER TABLE `applicationfor_comments` DISABLE KEYS */;
INSERT INTO `applicationfor_comments` VALUES (5655,'comment1 ApplicaciontFor1'),(5655,'comment2 ApplicaciontFor1'),(5655,'comment3 ApplicaciontFor1'),(5656,'comment1 ApplicaciontFor2'),(5656,'comment2 ApplicaciontFor2'),(5656,'comment3 ApplicaciontFor2'),(5657,'comment1 ApplicaciontFor3'),(5657,'comment2 ApplicaciontFor3'),(5657,'comment3 ApplicaciontFor3'),(5658,'comment1 ApplicaciontFor4'),(5658,'comment2 ApplicaciontFor4'),(5658,'comment3 ApplicaciontFor4'),(5659,'comment1 ApplicaciontFor5'),(5659,'comment2 ApplicaciontFor5'),(5659,'comment3 ApplicaciontFor5'),(5660,'comment1 ApplicaciontFor5'),(5660,'comment2 ApplicaciontFor5'),(5660,'comment3 ApplicaciontFor5');
/*!40000 ALTER TABLE `applicationfor_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `fatherCategory_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_8dbpkngc2chtdg1xbd67fu6s0` (`fatherCategory_id`),
  CONSTRAINT `FK_8dbpkngc2chtdg1xbd67fu6s0` FOREIGN KEY (`fatherCategory_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (5619,1,'Lake',5625),(5620,1,'Savana',5626),(5621,1,'River',5627),(5622,1,'River',5625),(5623,1,'Mountain',5627),(5624,1,'Mountain',5626),(5625,1,'Water',5628),(5626,1,'Ground',5628),(5627,1,'Climbing',5629),(5628,1,'Safari',5629),(5629,0,'CATEGORY',NULL);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_trip`
--

DROP TABLE IF EXISTS `category_trip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category_trip` (
  `Category_id` int(11) NOT NULL,
  `trips_id` int(11) NOT NULL,
  KEY `FK_jpmhd61n1frm86vucckf760uh` (`trips_id`),
  KEY `FK_mvh6bvklcktdyn2rpho7d0cp0` (`Category_id`),
  CONSTRAINT `FK_mvh6bvklcktdyn2rpho7d0cp0` FOREIGN KEY (`Category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FK_jpmhd61n1frm86vucckf760uh` FOREIGN KEY (`trips_id`) REFERENCES `trip` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_trip`
--

LOCK TABLES `category_trip` WRITE;
/*!40000 ALTER TABLE `category_trip` DISABLE KEYS */;
/*!40000 ALTER TABLE `category_trip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configurationsystem`
--

DROP TABLE IF EXISTS `configurationsystem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configurationsystem` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `VAT` double NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `cacheMaxTime` int(11) NOT NULL,
  `defaultPhone` varchar(255) DEFAULT NULL,
  `maxNumberFinder` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configurationsystem`
--

LOCK TABLES `configurationsystem` WRITE;
/*!40000 ALTER TABLE `configurationsystem` DISABLE KEYS */;
INSERT INTO `configurationsystem` VALUES (5444,0,0.21,'http://creek-tours.com/wp-content/uploads/Kenya-Tanzania-Family-Safari-banner.jpg',1,'+34',10);
/*!40000 ALTER TABLE `configurationsystem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configurationsystem_category`
--

DROP TABLE IF EXISTS `configurationsystem_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configurationsystem_category` (
  `ConfigurationSystem_id` int(11) NOT NULL,
  `defaultCategories_id` int(11) NOT NULL,
  UNIQUE KEY `UK_pmon6y1retrhnobrj15emstb` (`defaultCategories_id`),
  KEY `FK_q1lbwcfv2ac1wq7bqjp1w89av` (`ConfigurationSystem_id`),
  CONSTRAINT `FK_q1lbwcfv2ac1wq7bqjp1w89av` FOREIGN KEY (`ConfigurationSystem_id`) REFERENCES `configurationsystem` (`id`),
  CONSTRAINT `FK_pmon6y1retrhnobrj15emstb` FOREIGN KEY (`defaultCategories_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configurationsystem_category`
--

LOCK TABLES `configurationsystem_category` WRITE;
/*!40000 ALTER TABLE `configurationsystem_category` DISABLE KEYS */;
INSERT INTO `configurationsystem_category` VALUES (5444,5619),(5444,5620),(5444,5621),(5444,5622),(5444,5623),(5444,5624),(5444,5625),(5444,5626),(5444,5627),(5444,5628),(5444,5629);
/*!40000 ALTER TABLE `configurationsystem_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configurationsystem_spamwords`
--

DROP TABLE IF EXISTS `configurationsystem_spamwords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configurationsystem_spamwords` (
  `ConfigurationSystem_id` int(11) NOT NULL,
  `spamWords` varchar(255) DEFAULT NULL,
  KEY `FK_ndaj1y1wr90fuqw567bbgckpt` (`ConfigurationSystem_id`),
  CONSTRAINT `FK_ndaj1y1wr90fuqw567bbgckpt` FOREIGN KEY (`ConfigurationSystem_id`) REFERENCES `configurationsystem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configurationsystem_spamwords`
--

LOCK TABLES `configurationsystem_spamwords` WRITE;
/*!40000 ALTER TABLE `configurationsystem_spamwords` DISABLE KEYS */;
INSERT INTO `configurationsystem_spamwords` VALUES (5444,'viagra'),(5444,'cialis'),(5444,'sex'),(5444,'jes extender');
/*!40000 ALTER TABLE `configurationsystem_spamwords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configurationsystem_tag`
--

DROP TABLE IF EXISTS `configurationsystem_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configurationsystem_tag` (
  `ConfigurationSystem_id` int(11) NOT NULL,
  `defaultTags_id` int(11) NOT NULL,
  UNIQUE KEY `UK_q5qv6nnhwa8iv9rd4ew7xdnji` (`defaultTags_id`),
  KEY `FK_t57c5hdi2dbn7txplhgodfmc8` (`ConfigurationSystem_id`),
  CONSTRAINT `FK_t57c5hdi2dbn7txplhgodfmc8` FOREIGN KEY (`ConfigurationSystem_id`) REFERENCES `configurationsystem` (`id`),
  CONSTRAINT `FK_q5qv6nnhwa8iv9rd4ew7xdnji` FOREIGN KEY (`defaultTags_id`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configurationsystem_tag`
--

LOCK TABLES `configurationsystem_tag` WRITE;
/*!40000 ALTER TABLE `configurationsystem_tag` DISABLE KEYS */;
INSERT INTO `configurationsystem_tag` VALUES (5444,5601),(5444,5602),(5444,5603);
/*!40000 ALTER TABLE `configurationsystem_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contactemergency`
--

DROP TABLE IF EXISTS `contactemergency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contactemergency` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contactemergency`
--

LOCK TABLES `contactemergency` WRITE;
/*!40000 ALTER TABLE `contactemergency` DISABLE KEYS */;
INSERT INTO `contactemergency` VALUES (5609,0,'contact1@gmail.com','contact 1','+55888846'),(5610,0,'contact2@gmail.com','contact 2','+34888846'),(5611,0,'contact3@gmail.com','contact 3','+36855846'),(5612,0,'contact4@gmail.com','contact 4','+368857446'),(5613,0,'contact5@gmail.com','contact 5','6657446');
/*!40000 ALTER TABLE `contactemergency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `explorer`
--

DROP TABLE IF EXISTS `explorer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `explorer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_fjs8q8hlbsegiuxw4r2ko6uv6` (`userAccount_id`),
  CONSTRAINT `FK_fjs8q8hlbsegiuxw4r2ko6uv6` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `explorer`
--

LOCK TABLES `explorer` WRITE;
/*!40000 ALTER TABLE `explorer` DISABLE KEYS */;
INSERT INTO `explorer` VALUES (5635,0,'Address explorer 1','explorer1@mail.com','Explorer 1','+313(987)1121','surname Explorer 1','\0',5461),(5636,0,'Address explorer 2','explorer2@mail.com','Explorer 2','+313(987)1131','surname Explorer 2','\0',5462),(5637,0,'Address explorer 3','explorer3@mail.com','Explorer 3','+313(987)1141','surname Explorer 3','\0',5463),(5638,0,'Address explorer 4','explorer4@mail.com','Explorer 4','+313(987)1131','surname Explorer 4','\0',5464),(5639,0,'Address explorer 5','explorer5@mail.com','Explorer 5','+313(987)1131','surname Explorer 5','\0',5465),(5640,0,'Address explorer 6','explorer6@mail.com','Explorer 6','+313(987)1131','surname Explorer 6','\0',5466);
/*!40000 ALTER TABLE `explorer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `explorer_contactemergency`
--

DROP TABLE IF EXISTS `explorer_contactemergency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `explorer_contactemergency` (
  `Explorer_id` int(11) NOT NULL,
  `contactsEmergency_id` int(11) NOT NULL,
  KEY `FK_snjdqww8qtb98wuqwl09evr4e` (`contactsEmergency_id`),
  KEY `FK_5g88gq101v31gy45c492yy3gp` (`Explorer_id`),
  CONSTRAINT `FK_5g88gq101v31gy45c492yy3gp` FOREIGN KEY (`Explorer_id`) REFERENCES `explorer` (`id`),
  CONSTRAINT `FK_snjdqww8qtb98wuqwl09evr4e` FOREIGN KEY (`contactsEmergency_id`) REFERENCES `contactemergency` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `explorer_contactemergency`
--

LOCK TABLES `explorer_contactemergency` WRITE;
/*!40000 ALTER TABLE `explorer_contactemergency` DISABLE KEYS */;
INSERT INTO `explorer_contactemergency` VALUES (5635,5609),(5636,5610),(5637,5609),(5637,5610),(5637,5611),(5639,5613);
/*!40000 ALTER TABLE `explorer_contactemergency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('DomainEntity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `legaltext`
--

DROP TABLE IF EXISTS `legaltext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `legaltext` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `draftMode` bit(1) NOT NULL,
  `lawsNumber` int(11) NOT NULL,
  `moment` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `legaltext`
--

LOCK TABLES `legaltext` WRITE;
/*!40000 ALTER TABLE `legaltext` DISABLE KEYS */;
INSERT INTO `legaltext` VALUES (5614,0,'body 1','\0',1,'2017-10-29 23:59:00','title 1'),(5615,0,'body 2','\0',1,'2017-11-02 23:59:00','title 2'),(5616,0,'body 3','\0',1,'2017-11-12 23:59:00','title 3'),(5617,0,' LEGAL TEXT   2. Acceptance of these Conditions   The fact that you access this website implies that    you know, understand and accept the Terms of Use in the version existing    at the time of access.','\0',4,'2017-11-23 23:59:00','title english'),(5618,0,'AVISO LEGAL   2. Aceptación de estas Condiciones   El hecho de que acceda a este sitio Web implica que conoce, entiende y acepta las Condiciones de    en la versión existente en el momento del acceso.','\0',4,'2017-11-23 23:59:00','title español');
/*!40000 ALTER TABLE `legaltext` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `legaltext_trip`
--

DROP TABLE IF EXISTS `legaltext_trip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `legaltext_trip` (
  `LegalText_id` int(11) NOT NULL,
  `trips_id` int(11) NOT NULL,
  UNIQUE KEY `UK_nhe46krpx0p82b2bfg4rfivtl` (`trips_id`),
  KEY `FK_euperohyjlo853196aj98q7k0` (`LegalText_id`),
  CONSTRAINT `FK_euperohyjlo853196aj98q7k0` FOREIGN KEY (`LegalText_id`) REFERENCES `legaltext` (`id`),
  CONSTRAINT `FK_nhe46krpx0p82b2bfg4rfivtl` FOREIGN KEY (`trips_id`) REFERENCES `trip` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `legaltext_trip`
--

LOCK TABLES `legaltext_trip` WRITE;
/*!40000 ALTER TABLE `legaltext_trip` DISABLE KEYS */;
INSERT INTO `legaltext_trip` VALUES (5614,5646),(5615,5647),(5616,5648);
/*!40000 ALTER TABLE `legaltext_trip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_84bmmxlq61tiaoc7dy7kdcghh` (`userAccount_id`),
  CONSTRAINT `FK_84bmmxlq61tiaoc7dy7kdcghh` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager`
--

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
INSERT INTO `manager` VALUES (5630,0,'Address Manager 1','manager1@mail.com','Manager 1','+313(987)1111','surname Manager 1 sex','\0',5451),(5631,0,'Address Manager 2','manager2@mail.com','Manager 2','+313(987)1112','surname Manager 2','\0',5452),(5632,0,'Address Manager 3','manager3@mail.com','Manager 3','+313(987)1113','surname Manager 3','\0',5453),(5633,0,'Address Manager 4','manager4@mail.com','Manager 4','+313(987)1114','surname Manager 4','\0',5454),(5634,0,'Address Manager 5','manager5@mail.com','Manager 5','+313(987)1115','surname Manager 5','\0',5455);
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `messageFolder_id` int(11) NOT NULL,
  `recipient_id` int(11) NOT NULL,
  `sender_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_iq38mox9qghnx2rc8hpdtmros` (`messageFolder_id`),
  CONSTRAINT `FK_iq38mox9qghnx2rc8hpdtmros` FOREIGN KEY (`messageFolder_id`) REFERENCES `messagefolder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (5642,0,'body 1','2017-10-01 15:20:00','HIGH','subject 1',5472,5597,5445),(5643,0,'body 1','2017-10-01 15:20:00','HIGH','subject 1',5468,5597,5445),(5644,0,'body 2','2017-10-01 15:25:00','HIGH','subject 2',5547,5631,5630),(5645,0,'body 2','2017-10-01 15:25:00','HIGH','subject 2',5543,5631,5630);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messagefolder`
--

DROP TABLE IF EXISTS `messagefolder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messagefolder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `modifiable` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messagefolder`
--

LOCK TABLES `messagefolder` WRITE;
/*!40000 ALTER TABLE `messagefolder` DISABLE KEYS */;
INSERT INTO `messagefolder` VALUES (5467,0,'\0','In box'),(5468,0,'\0','Out box'),(5469,0,'\0','Notification box'),(5470,0,'\0','Trash box'),(5471,0,'\0','Spam box'),(5472,0,'\0','In box'),(5473,0,'\0','Out box'),(5474,0,'\0','Notification box'),(5475,0,'\0','Trash box'),(5476,0,'\0','Spam box'),(5477,0,'\0','In box'),(5478,0,'\0','Out box'),(5479,0,'\0','Notification box'),(5480,0,'\0','Trash box'),(5481,0,'\0','Spam box'),(5482,0,'\0','In box'),(5483,0,'\0','Out box'),(5484,0,'\0','Notification box'),(5485,0,'\0','Trash box'),(5486,0,'\0','Spam box'),(5487,0,'\0','In box'),(5488,0,'\0','Out box'),(5489,0,'\0','Notification box'),(5490,0,'\0','Trash box'),(5491,0,'\0','Spam box'),(5492,0,'\0','In box'),(5493,0,'\0','Out box'),(5494,0,'\0','Notification box'),(5495,0,'\0','Trash box'),(5496,0,'\0','Spam box'),(5497,0,'\0','In box'),(5498,0,'\0','Out box'),(5499,0,'\0','Notification box'),(5500,0,'\0','Trash box'),(5501,0,'\0','Spam box'),(5502,0,'\0','In box'),(5503,0,'\0','Out box'),(5504,0,'\0','Notification box'),(5505,0,'\0','Trash box'),(5506,0,'\0','Spam box'),(5507,0,'\0','In box'),(5508,0,'\0','Out box'),(5509,0,'\0','Notification box'),(5510,0,'\0','Trash box'),(5511,0,'\0','Spam box'),(5512,0,'\0','In box'),(5513,0,'\0','Out box'),(5514,0,'\0','Notification box'),(5515,0,'\0','Trash box'),(5516,0,'\0','Spam box'),(5517,0,'\0','In box'),(5518,0,'\0','Out box'),(5519,0,'\0','Notification box'),(5520,0,'\0','Trash box'),(5521,0,'\0','Spam box'),(5522,0,'\0','In box'),(5523,0,'\0','Out box'),(5524,0,'\0','Notification box'),(5525,0,'\0','Trash box'),(5526,0,'\0','Spam box'),(5527,0,'\0','In box'),(5528,0,'\0','Out box'),(5529,0,'\0','Notification box'),(5530,0,'\0','Trash box'),(5531,0,'\0','Spam box'),(5532,0,'\0','In box'),(5533,0,'\0','Out box'),(5534,0,'\0','Notification box'),(5535,0,'\0','Trash box'),(5536,0,'\0','Spam box'),(5537,0,'\0','In box'),(5538,0,'\0','Out box'),(5539,0,'\0','Notification box'),(5540,0,'\0','Trash box'),(5541,0,'\0','Spam box'),(5542,0,'\0','In box'),(5543,0,'\0','Out box'),(5544,0,'\0','Notification box'),(5545,0,'\0','Trash box'),(5546,0,'\0','Spam box'),(5547,0,'\0','In box'),(5548,0,'\0','Out box'),(5549,0,'\0','Notification box'),(5550,0,'\0','Trash box'),(5551,0,'\0','Spam box'),(5552,0,'\0','In box'),(5553,0,'\0','Out box'),(5554,0,'\0','Notification box'),(5555,0,'\0','Trash box'),(5556,0,'\0','Spam box'),(5557,0,'\0','In box'),(5558,0,'\0','Out box'),(5559,0,'\0','Notification box'),(5560,0,'\0','Trash box'),(5561,0,'\0','Spam box'),(5562,0,'\0','In box'),(5563,0,'\0','Out box'),(5564,0,'\0','Notification box'),(5565,0,'\0','Trash box'),(5566,0,'\0','Spam box'),(5567,0,'\0','In box'),(5568,0,'\0','Out box'),(5569,0,'\0','Notification box'),(5570,0,'\0','Trash box'),(5571,0,'\0','Spam box'),(5572,0,'','folder 1'),(5573,0,'','folder 2'),(5574,0,'','folder 3'),(5575,0,'','folder 4'),(5576,0,'','folder 4');
/*!40000 ALTER TABLE `messagefolder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ranger`
--

DROP TABLE IF EXISTS `ranger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ranger` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `suspicious` bit(1) NOT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_axw07q0mobub2mvr0xeh7al5f` (`userAccount_id`),
  CONSTRAINT `FK_axw07q0mobub2mvr0xeh7al5f` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ranger`
--

LOCK TABLES `ranger` WRITE;
/*!40000 ALTER TABLE `ranger` DISABLE KEYS */;
INSERT INTO `ranger` VALUES (5604,0,'C/ avenida del ranger','ranger1@acmeexplorer.com','ranger 1','+34617557203','ranger1 ranger1','\0',5456),(5605,0,'C/ avenida del ranger','ranger2@acmeexplorer.com','ranger 2','+34617557203','ranger2 ranger2','\0',5457),(5606,0,'C/ avenida del ranger','ranger3@acmeexplorer.com','ranger 3','+34617557203','ranger3 ranger3','\0',5458),(5607,0,'C/ avenida del ranger','ranger4@acmeexplorer.com','ranger 4','+34617557203','ranger1 ranger4','\0',5459),(5608,0,'C/ avenida del ranger','ranger5@acmeexplorer.com','ranger 5','+34617557203','ranger5 ranger5','\0',5460);
/*!40000 ALTER TABLE `ranger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socialidentity`
--

DROP TABLE IF EXISTS `socialidentity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `socialidentity` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `link` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nick` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socialidentity`
--

LOCK TABLES `socialidentity` WRITE;
/*!40000 ALTER TABLE `socialidentity` DISABLE KEYS */;
INSERT INTO `socialidentity` VALUES (5577,0,'http://www.sample.com','identityadmin 1','nickadmin1','http://www.sample.com'),(5578,0,'http://www.sample.com','identityadmin 2','nickadmin2','http://www.sample.com'),(5579,0,'http://www.sample.com','identityadmin 3','nickadmin3','http://www.sample.com'),(5580,0,'http://www.sample.com','identityadmin 4','nickadmin4','http://www.sample.com'),(5581,0,'http://www.sample.com','identityadmin 5','nickadmin5','http://www.sample.com'),(5582,0,'http://www.sample.com','identitymanager 1','nickmanager1','http://www.sample.com'),(5583,0,'http://www.sample.com','identitymanager 2','nickmanager2','http://www.sample.com'),(5584,0,'http://www.sample.com','identitymanager 3','nickmanager3','http://www.sample.com'),(5585,0,'http://www.sample.com','identitymanager 4','nickmanager4','http://www.sample.com'),(5586,0,'http://www.sample.com','identitymanager 5','nickmanager5','http://www.sample.com'),(5587,0,'http://www.sample.com','identityranger 1','nickranger1','http://www.sample.com'),(5588,0,'http://www.sample.com','identityranger 2','nickranger2','http://www.sample.com'),(5589,0,'http://www.sample.com','identityranger 3','nickranger3','http://www.sample.com'),(5590,0,'http://www.sample.com','identityranger 4','nickranger4','http://www.sample.com'),(5591,0,'http://www.sample.com','identityranger 5','nickranger5','http://www.sample.com'),(5592,0,'http://www.sample.com','identityexplorer 1','nickexplorer 1','http://www.sample.com'),(5593,0,'http://www.sample.com','identityexplorer 2','nickexplorer 2','http://www.sample.com'),(5594,0,'http://www.sample.com','identityexplorer 3','nickexplorer3','http://www.sample.com'),(5595,0,'http://www.sample.com','identityexplorer 4','nickexplorer 4','http://www.sample.com'),(5596,0,'http://www.sample.com','identityexplorer 5','nickexplorer 5','http://www.sample.com');
/*!40000 ALTER TABLE `socialidentity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stage`
--

DROP TABLE IF EXISTS `stage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stage` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `number` int(11) NOT NULL,
  `price` double NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `totalPrice` double NOT NULL,
  `trip_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_g8uuphy1i55oxqtlti7cftxk5` (`trip_id`),
  CONSTRAINT `FK_g8uuphy1i55oxqtlti7cftxk5` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stage`
--

LOCK TABLES `stage` WRITE;
/*!40000 ALTER TABLE `stage` DISABLE KEYS */;
INSERT INTO `stage` VALUES (5662,0,'description 1',1,7,'title 1',0,5646),(5663,0,'description 2',2,2,'title 2',0,5646),(5664,0,'description 3',3,3,'title 3',0,5648),(5665,0,'description 4',4,4,'title 4',0,5649),(5666,0,'description 5',5,5,'title 5',0,5650),(5667,0,'description 6',5,5,'title 6',0,5647);
/*!40000 ALTER TABLE `stage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (5601,0,'country'),(5602,0,'expertise'),(5603,0,'dangerousness');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickoff`
--

DROP TABLE IF EXISTS `tickoff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tickoff` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `displayedMoment` datetime DEFAULT NULL,
  `gauge` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `manager_id` int(11) NOT NULL,
  `trip_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_t7l49098ry34g0nb076whpv4i` (`number`),
  KEY `FK_1t3j0gym9a8xb1eytain2g1bn` (`manager_id`),
  KEY `FK_9y6c7orao2r5n160yl0ula92r` (`trip_id`),
  CONSTRAINT `FK_9y6c7orao2r5n160yl0ula92r` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`),
  CONSTRAINT `FK_1t3j0gym9a8xb1eytain2g1bn` FOREIGN KEY (`manager_id`) REFERENCES `manager` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickoff`
--

LOCK TABLES `tickoff` WRITE;
/*!40000 ALTER TABLE `tickoff` DISABLE KEYS */;
INSERT INTO `tickoff` VALUES (5651,0,'hola',NULL,'1','aa180202bb','hola1',5630,5647),(5652,0,'hola','2017-08-02 20:00:00','2','hj171024mm','text2',5630,5647),(5653,0,'hola3','2020-09-15 20:00:00','3','mm161024lj','text3',5631,5648),(5654,0,'hola4','2019-09-15 20:00:00','2','mm161124lj','text4',5631,5648);
/*!40000 ALTER TABLE `tickoff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trip`
--

DROP TABLE IF EXISTS `trip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trip` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `cancelled` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `finishDate` date DEFAULT NULL,
  `price` double NOT NULL,
  `publicationDate` datetime DEFAULT NULL,
  `reasonWhy` varchar(255) DEFAULT NULL,
  `requirementsExplorers` varchar(255) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `manager_id` int(11) NOT NULL,
  `ranger_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ugq799eb7dgmgmqjoxkjpx5k` (`ticker`),
  KEY `FK_bt26lls0o7ykesy9ye75887rk` (`manager_id`),
  KEY `FK_65bd0l9xa0l1x5qnjg2su9tig` (`ranger_id`),
  CONSTRAINT `FK_65bd0l9xa0l1x5qnjg2su9tig` FOREIGN KEY (`ranger_id`) REFERENCES `ranger` (`id`),
  CONSTRAINT `FK_bt26lls0o7ykesy9ye75887rk` FOREIGN KEY (`manager_id`) REFERENCES `manager` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip`
--

LOCK TABLES `trip` WRITE;
/*!40000 ALTER TABLE `trip` DISABLE KEYS */;
INSERT INTO `trip` VALUES (5646,0,'\0','description 1','2016-12-30',0,'2016-10-14 23:59:00',NULL,'requirementsExplorers 1','2016-11-14','170112-WWWW','title 1',5630,5604),(5647,0,'\0','description 2','2019-08-04',0,'2018-10-14 23:59:00',NULL,'requirementsExplorers 2','2018-11-22','160112-AAAA','title 2',5630,5605),(5648,0,'\0','description 3','2018-08-04',0,'2018-07-10 22:45:00',NULL,'requirementsExplorers 3','2018-07-11','150112-BBBB','title 3',5631,5606),(5649,0,'','description 4','2015-08-04',0,'2015-01-20 22:45:00','reasonWhy 4','requirementsExplorers 4','2015-02-21','150112-CCCC','title 4',5633,5607),(5650,0,'\0','description 5','2019-08-04',0,'2019-01-14 23:59:00',NULL,'requirementsExplorers 5','2019-02-21','150112-DDDD','title 5',5634,5608);
/*!40000 ALTER TABLE `trip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trip_stage`
--

DROP TABLE IF EXISTS `trip_stage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trip_stage` (
  `Trip_id` int(11) NOT NULL,
  `stages_id` int(11) NOT NULL,
  UNIQUE KEY `UK_pypsdnr343h93d9c7hpugcyyv` (`stages_id`),
  KEY `FK_stea9evs7jmvh3yf9i17pw6kn` (`Trip_id`),
  CONSTRAINT `FK_stea9evs7jmvh3yf9i17pw6kn` FOREIGN KEY (`Trip_id`) REFERENCES `trip` (`id`),
  CONSTRAINT `FK_pypsdnr343h93d9c7hpugcyyv` FOREIGN KEY (`stages_id`) REFERENCES `stage` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip_stage`
--

LOCK TABLES `trip_stage` WRITE;
/*!40000 ALTER TABLE `trip_stage` DISABLE KEYS */;
INSERT INTO `trip_stage` VALUES (5646,5662),(5646,5663),(5647,5667),(5648,5664),(5649,5665),(5650,5666);
/*!40000 ALTER TABLE `trip_stage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `activated` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_csivo9yqa08nrbkog71ycilh5` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES (5446,0,'','21232f297a57a5a743894a0e4a801fc3','admin'),(5447,0,'','82954495ff7e2a735ed2192c35b2cd00','administrator2'),(5448,0,'','0185e578be37caa9c276e0c8903e261a','administrator3'),(5449,0,'','3ce013337481d5af81e21bf6791a574e','administrator4'),(5450,0,'','f2ec5fdcc58bcb002270a11f6bb2a9b3','administrator5'),(5451,0,'','c240642ddef994358c96da82c0361a58','manager1'),(5452,0,'','8df5127cd164b5bc2d2b78410a7eea0c','manager2'),(5453,0,'','2d3a5db4a2a9717b43698520a8de57d0','manager3'),(5454,0,'','e1ec6fc941af3ba79a4ac5242dd39735','manager4'),(5455,0,'','029cb1d27c0b9c551703ccba2591c334','manager5'),(5456,0,'','0724ee360da2267273a1330a2e61232c','ranger1'),(5457,0,'','dc095ac91ed81b91d6415f5cb90a14f5','ranger2'),(5458,0,'','3463481ed1abde8365da6600c3edb985','ranger3'),(5459,0,'','76bbcf2c573db6b0c41e2388ed3ff3c0','ranger4'),(5460,0,'','07fb3d4fb1b4cc4d79e10ae2a14b44e9','ranger5'),(5461,0,'','03fc13f308051f4942d35c84592b8d5a','explorer1'),(5462,0,'','5a1b9d1b35a024241cdf419f645f7765','explorer2'),(5463,0,'','1dfcf0b9f4a3ce786b5340e554929eb4','explorer3'),(5464,0,'','a970e7ef3301a37ccfe7aab380fa9d4a','explorer4'),(5465,0,'','d8420d0725247c256c6ed45abf4911d3','explorer5'),(5466,0,'','f1ee5f1a48950b10aeaa40c20a7e2b45','explorer6');
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount_authorities`
--

DROP TABLE IF EXISTS `useraccount_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_b63ua47r0u1m7ccc9lte2ui4r` (`UserAccount_id`),
  CONSTRAINT `FK_b63ua47r0u1m7ccc9lte2ui4r` FOREIGN KEY (`UserAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount_authorities`
--

LOCK TABLES `useraccount_authorities` WRITE;
/*!40000 ALTER TABLE `useraccount_authorities` DISABLE KEYS */;
INSERT INTO `useraccount_authorities` VALUES (5446,'ADMINISTRATOR'),(5447,'ADMINISTRATOR'),(5448,'ADMINISTRATOR'),(5449,'ADMINISTRATOR'),(5450,'ADMINISTRATOR'),(5451,'MANAGER'),(5452,'MANAGER'),(5453,'MANAGER'),(5454,'MANAGER'),(5455,'MANAGER'),(5456,'RANGER'),(5457,'RANGER'),(5458,'RANGER'),(5459,'RANGER'),(5460,'RANGER'),(5461,'EXPLORER'),(5462,'EXPLORER'),(5463,'EXPLORER'),(5464,'EXPLORER'),(5465,'EXPLORER'),(5466,'EXPLORER');
/*!40000 ALTER TABLE `useraccount_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `value`
--

DROP TABLE IF EXISTS `value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `value` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `value` int(11) DEFAULT NULL,
  `tag_id` int(11) DEFAULT NULL,
  `trip_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_gshaqbur2cof541isdpqw7fhs` (`tag_id`),
  KEY `FK_i0yqkpsgylvwk2bp6nky4o4es` (`trip_id`),
  CONSTRAINT `FK_i0yqkpsgylvwk2bp6nky4o4es` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`),
  CONSTRAINT `FK_gshaqbur2cof541isdpqw7fhs` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `value`
--

LOCK TABLES `value` WRITE;
/*!40000 ALTER TABLE `value` DISABLE KEYS */;
INSERT INTO `value` VALUES (5641,1,1,5601,5646);
/*!40000 ALTER TABLE `value` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-23 10:00:18
commit;