-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: heroku_2bc32d1ba97c635
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.16-MariaDB

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
-- Table structure for table `thong_tin_chuyen_bay`
--

DROP TABLE IF EXISTS `thong_tin_chuyen_bay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `thong_tin_chuyen_bay` (
  `Ma` char(10) NOT NULL,
  `NoiDi` char(3) DEFAULT NULL,
  `NoiDen` char(3) DEFAULT NULL,
  `QuaCanh` char(10) DEFAULT NULL,
  PRIMARY KEY (`Ma`),
  KEY `FlightInfo_Airport_1` (`NoiDi`),
  KEY `FlightInfo_Airport_2` (`NoiDen`),
  KEY `FlightInfo_FlightTransit` (`QuaCanh`),
  CONSTRAINT `FlightInfo_Airport_1` FOREIGN KEY (`NoiDi`) REFERENCES `san_bay` (`Ma`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FlightInfo_Airport_2` FOREIGN KEY (`NoiDen`) REFERENCES `san_bay` (`Ma`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FlightInfo_FlightTransit` FOREIGN KEY (`QuaCanh`) REFERENCES `qua_canh` (`MaQuaCanh`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thong_tin_chuyen_bay`
--

LOCK TABLES `thong_tin_chuyen_bay` WRITE;
/*!40000 ALTER TABLE `thong_tin_chuyen_bay` DISABLE KEYS */;
INSERT INTO `thong_tin_chuyen_bay` VALUES ('VN224','SGN','HAN',NULL),('VN225','CXR','SGN',NULL),('VN226','SGN','LAX','TVN226'),('VN227','SGN','SYD',NULL),('VN228','HAN','NRT','TVN228'),('VN229','HAN','SYD',NULL),('VN300','SGN','CXR',NULL),('VN301','HAN','SGN',NULL),('VN302','CDG','SGN',NULL),('VN303','CXR','HAN',NULL),('VN304','CXR','SGN',NULL),('VN305','DAD','HAN',NULL),('VN306','DAD','SGN',NULL),('VN307','HPH','SGN',NULL),('VN308','HPH','HAN',NULL),('VN309','ICN','SGN',NULL),('VN310','ICN','HAN',NULL),('VN311','LAX','SGN',NULL),('VN312','LAX','HAN',NULL),('VN313','NRT','SGN',NULL),('VN314','NRT','HAN',NULL),('VN315','SIN','SGN',NULL),('VN316','SIN','HAN',NULL),('VN317','SYD','SGN',NULL),('VN318','SYD','HAN',NULL),('VN319','SGN','DAD',NULL),('VN320','SGN','CDG',NULL),('VN321','SGN','SIN',NULL),('VN322','SGN','SYD',NULL),('VN323','HAN','SYD',NULL),('VN324','SGN','NRT',NULL),('VN325','SGN','HAN',NULL);
/*!40000 ALTER TABLE `thong_tin_chuyen_bay` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-16  2:13:45
