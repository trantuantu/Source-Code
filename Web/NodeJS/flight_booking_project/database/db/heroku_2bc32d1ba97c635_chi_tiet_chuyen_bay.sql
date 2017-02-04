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
-- Table structure for table `chi_tiet_chuyen_bay`
--

DROP TABLE IF EXISTS `chi_tiet_chuyen_bay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chi_tiet_chuyen_bay` (
  `MaDatCho` char(10) NOT NULL,
  `MaChuyenBay` char(10) NOT NULL,
  `Ngay` varchar(20) NOT NULL,
  `Hang` char(1) DEFAULT NULL,
  `MucGia` char(1) DEFAULT NULL,
  `GiaBan` int(11) DEFAULT NULL,
  `MaHanhLy` char(30) DEFAULT NULL,
  `MaGhe` char(30) DEFAULT NULL,
  PRIMARY KEY (`MaDatCho`,`MaChuyenBay`,`Ngay`),
  KEY `FlightDetail_Flight` (`MaChuyenBay`,`Ngay`),
  KEY `FlightDetail_Luggage` (`MaHanhLy`),
  CONSTRAINT `FlightDetail_Booking` FOREIGN KEY (`MaDatCho`) REFERENCES `dat_ve` (`Ma`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FlightDetail_Flight` FOREIGN KEY (`MaChuyenBay`, `Ngay`) REFERENCES `chuyen_bay` (`Ma`, `Ngay`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FlightDetail_Luggage` FOREIGN KEY (`MaHanhLy`) REFERENCES `hanh_ly` (`MaHanhLy`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FlightDetail_TicketReview` FOREIGN KEY (`MaDatCho`) REFERENCES `ticket_review` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FlightDetail_Seat` FOREIGN KEY (`MaGhe`) REFERENCES `ghe` (`MaGhe`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chi_tiet_chuyen_bay`
--

LOCK TABLES `chi_tiet_chuyen_bay` WRITE;
/*!40000 ALTER TABLE `chi_tiet_chuyen_bay` DISABLE KEYS */;
INSERT INTO `chi_tiet_chuyen_bay` VALUES ('abcdef','VN224','01-02-2017','Y','E',2000000,'abcdefVN22401-02-2017','VN22401-02-2017'),('abcghi','VN224','01-02-2017','Y','E',10000000,'abcghiVN22401-02-2017','VN22401-02-2017');
/*!40000 ALTER TABLE `chi_tiet_chuyen_bay` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-16  2:13:43
