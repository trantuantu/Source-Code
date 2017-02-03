-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: strava
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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` char(40) NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `info` longtext,
  `created_at` varchar(45) DEFAULT NULL,
  `strava_id` varchar(45) DEFAULT NULL,
  `access_token` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('118456326163956718496367','Trần Tuấn Tú','\'{\"id\":18496367,\"username\":\"trn_tunt\",\"resource_state\":3,\"firstname\":\"Tr\\u1ea7n\",\"lastname\":\"Tu\\u1ea5n T\\u00fa\",\"city\":null,\"state\":null,\"country\":null,\"sex\":\"M\",\"premium\":false,\"created_at\":\"2016-11-15T03:12:31Z\",\"updated_at\":\"2016-11-17T11:11:41Z\",\"badge_type_id\":0,\"profile_medium\":\"https:\\/\\/graph.facebook.com\\/v2.2\\/1194567183972508\\/picture?height=256&width=256\",\"profile\":\"https:\\/\\/graph.facebook.com\\/v2.2\\/1194567183972508\\/picture?height=256&width=256\",\"friend\":null,\"follower\":null,\"follower_count\":0,\"friend_count\":3,\"mutual_friend_count\":0,\"athlete_type\":1,\"date_preference\":\"%m\\/%d\\/%Y\",\"measurement_preference\":\"feet\",\"clubs\":[],\"email\":\"trantuantu020995@gmail.com\",\"ftp\":null,\"weight\":null,\"bikes\":[],\"shoes\":[]}\'','2016-11-15T03:12:31Z','18496367','22d3386ad8ac2c1cd48747b548f55250c72c7d77'),('99827559031614518496639','Huy Do','\'{\"id\":18496639,\"username\":null,\"resource_state\":3,\"firstname\":\"Huy\",\"lastname\":\"Do\",\"city\":null,\"state\":null,\"country\":null,\"sex\":null,\"premium\":false,\"created_at\":\"2016-11-15T04:11:19Z\",\"updated_at\":\"2016-12-22T08:58:33Z\",\"badge_type_id\":0,\"profile_medium\":\"https:\\/\\/graph.facebook.com\\/v2.2\\/984498925027145\\/picture?height=256&width=256\",\"profile\":\"https:\\/\\/graph.facebook.com\\/v2.2\\/984498925027145\\/picture?height=256&width=256\",\"friend\":null,\"follower\":null,\"follower_count\":1,\"friend_count\":0,\"mutual_friend_count\":0,\"athlete_type\":1,\"date_preference\":\"%m\\/%d\\/%Y\",\"measurement_preference\":\"meters\",\"clubs\":[],\"email\":\"huydo16695@gmail.com\",\"ftp\":null,\"weight\":null,\"bikes\":[],\"shoes\":[]}\'','2016-11-15T04:11:19Z','18496639','7b815aa0435dca6c0661eb391941468271ae51af');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-27 21:55:14
