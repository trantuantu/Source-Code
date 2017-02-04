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
-- Table structure for table `san_bay`
--

DROP TABLE IF EXISTS `san_bay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `san_bay` (
  `Ma` char(3) NOT NULL,
  `MaVung` char(10) DEFAULT NULL,
  `TenDiaDanh` varchar(45) DEFAULT NULL,
  `TenSanBay` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Ma`),
  KEY `Airport_Area` (`MaVung`),
  CONSTRAINT `Airport_Area` FOREIGN KEY (`MaVung`) REFERENCES `khu_vuc` (`Ma`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `san_bay`
--

LOCK TABLES `san_bay` WRITE;
/*!40000 ALTER TABLE `san_bay` DISABLE KEYS */;
INSERT INTO `san_bay` VALUES ('CDG','3','Paris','Sân bay quốc tế Paris'),('CXR','1','Cam Ranh','Sân bay quốc tế Cam Ranh'),('DAD','1','Đà Nẵng','Sân bay quốc tế Đà Nẵng'),('HAN','1','Hà Nội','Sân bay quốc tế Nội Bài'),('HPH','1','Hải Phòng','Sân bay quốc tế Cát Bi'),('ICN','1','Seoul','Sân bay quốc tế Incheon'),('LAX','2','Los Angeles','Sân bay quốc tế Los Angeles'),('NRT','1','Tokyo','Sân bay quốc tế Narita'),('SGN','1','Hồ Chí Minh','Sân bay quốc tế Tân Sơn Nhất'),('SIN','1','Singapore','Sân bay quốc tế Changi'),('SYD','5','Sydney','Sân bay quốc tế Sydney');
/*!40000 ALTER TABLE `san_bay` ENABLE KEYS */;
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
