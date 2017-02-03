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
-- Table structure for table `user_data`
--

DROP TABLE IF EXISTS `user_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_data` (
  `user_id` char(40) NOT NULL,
  `json_stats` longtext,
  `json_clubs` longtext,
  `json_friends` longtext,
  `json_followers` longtext,
  `json_both_following` longtext,
  `json_kom` longtext,
  `json_starred_segments` longtext,
  `json_facebook_friends` longtext,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_data`
--

LOCK TABLES `user_data` WRITE;
/*!40000 ALTER TABLE `user_data` DISABLE KEYS */;
INSERT INTO `user_data` VALUES ('118456326163956718496367','\'{\"biggest_ride_distance\":3204.6,\"biggest_climb_elevation_gain\":null,\"recent_ride_totals\":{\"count\":0,\"distance\":0,\"moving_time\":0,\"elapsed_time\":0,\"elevation_gain\":0,\"achievement_count\":0},\"recent_run_totals\":{\"count\":0,\"distance\":0,\"moving_time\":0,\"elapsed_time\":0,\"elevation_gain\":0,\"achievement_count\":0},\"recent_swim_totals\":{\"count\":0,\"distance\":0,\"moving_time\":0,\"elapsed_time\":0,\"elevation_gain\":0,\"achievement_count\":0},\"ytd_ride_totals\":{\"count\":1,\"distance\":3205,\"moving_time\":2663,\"elapsed_time\":4473,\"elevation_gain\":0},\"ytd_run_totals\":{\"count\":1,\"distance\":3205,\"moving_time\":1159,\"elapsed_time\":4473,\"elevation_gain\":0},\"ytd_swim_totals\":{\"count\":0,\"distance\":0,\"moving_time\":0,\"elapsed_time\":0,\"elevation_gain\":0},\"all_ride_totals\":{\"count\":1,\"distance\":3205,\"moving_time\":2663,\"elapsed_time\":4473,\"elevation_gain\":0},\"all_run_totals\":{\"count\":1,\"distance\":3205,\"moving_time\":1159,\"elapsed_time\":4473,\"elevation_gain\":0},\"all_swim_totals\":{\"count\":0,\"distance\":0,\"moving_time\":0,\"elapsed_time\":0,\"elevation_gain\":0}}\'','\'[]\'','\'[{\"id\":18434079,\"username\":\"hdominh3\",\"resource_state\":2,\"firstname\":\"Huy\",\"lastname\":\"Do Minh (3)\",\"city\":null,\"state\":null,\"country\":null,\"sex\":\"M\",\"premium\":false,\"created_at\":\"2016-11-10T08:18:53Z\",\"updated_at\":\"2016-11-17T10:40:12Z\",\"badge_type_id\":0,\"profile_medium\":\"https:\\/\\/lh3.googleusercontent.com\\/-LQNzc5esXI8\\/AAAAAAAAAAI\\/AAAAAAAAABE\\/vXHVKI2NZI0\\/photo.jpg\",\"profile\":\"https:\\/\\/lh3.googleusercontent.com\\/-LQNzc5esXI8\\/AAAAAAAAAAI\\/AAAAAAAAABE\\/vXHVKI2NZI0\\/photo.jpg\",\"friend\":\"accepted\",\"follower\":null},{\"id\":839031,\"username\":\"alexander_rink\",\"resource_state\":2,\"firstname\":\"Alexander\",\"lastname\":\"Rink \\u24cb\",\"city\":\"Ottawa River, Canada\",\"state\":\"ON\",\"country\":\"Canada\",\"sex\":\"M\",\"premium\":true,\"created_at\":\"2012-07-28T04:36:55Z\",\"updated_at\":\"2016-10-15T01:23:00Z\",\"badge_type_id\":1,\"profile_medium\":\"https:\\/\\/dgalywyr863hv.cloudfront.net\\/pictures\\/athletes\\/839031\\/187817\\/1\\/medium.jpg\",\"profile\":\"https:\\/\\/dgalywyr863hv.cloudfront.net\\/pictures\\/athletes\\/839031\\/187817\\/1\\/large.jpg\",\"friend\":\"accepted\",\"follower\":null},{\"id\":18496639,\"username\":null,\"resource_state\":2,\"firstname\":\"Huy\",\"lastname\":\"Do\",\"city\":null,\"state\":null,\"country\":null,\"sex\":null,\"premium\":false,\"created_at\":\"2016-11-15T04:11:19Z\",\"updated_at\":\"2016-12-22T08:58:33Z\",\"badge_type_id\":0,\"profile_medium\":\"https:\\/\\/graph.facebook.com\\/v2.2\\/984498925027145\\/picture?height=256&width=256\",\"profile\":\"https:\\/\\/graph.facebook.com\\/v2.2\\/984498925027145\\/picture?height=256&width=256\",\"friend\":\"accepted\",\"follower\":null}]\'','\'[]\'','\'[{\"id\":839031,\"username\":\"alexander_rink\",\"resource_state\":2,\"firstname\":\"Alexander\",\"lastname\":\"Rink \\u24cb\",\"city\":\"Ottawa River, Canada\",\"state\":\"ON\",\"country\":\"Canada\",\"sex\":\"M\",\"premium\":true,\"created_at\":\"2012-07-28T04:36:55Z\",\"updated_at\":\"2016-10-15T01:23:00Z\",\"badge_type_id\":1,\"profile_medium\":\"https:\\/\\/dgalywyr863hv.cloudfront.net\\/pictures\\/athletes\\/839031\\/187817\\/1\\/medium.jpg\",\"profile\":\"https:\\/\\/dgalywyr863hv.cloudfront.net\\/pictures\\/athletes\\/839031\\/187817\\/1\\/large.jpg\",\"friend\":\"accepted\",\"follower\":null},{\"id\":18496639,\"username\":null,\"resource_state\":2,\"firstname\":\"Huy\",\"lastname\":\"Do\",\"city\":null,\"state\":null,\"country\":null,\"sex\":null,\"premium\":false,\"created_at\":\"2016-11-15T04:11:19Z\",\"updated_at\":\"2016-12-22T08:58:33Z\",\"badge_type_id\":0,\"profile_medium\":\"https:\\/\\/graph.facebook.com\\/v2.2\\/984498925027145\\/picture?height=256&width=256\",\"profile\":\"https:\\/\\/graph.facebook.com\\/v2.2\\/984498925027145\\/picture?height=256&width=256\",\"friend\":\"accepted\",\"follower\":null},{\"id\":18434079,\"username\":\"hdominh3\",\"resource_state\":2,\"firstname\":\"Huy\",\"lastname\":\"Do Minh (3)\",\"city\":null,\"state\":null,\"country\":null,\"sex\":\"M\",\"premium\":false,\"created_at\":\"2016-11-10T08:18:53Z\",\"updated_at\":\"2016-11-17T10:40:12Z\",\"badge_type_id\":0,\"profile_medium\":\"https:\\/\\/lh3.googleusercontent.com\\/-LQNzc5esXI8\\/AAAAAAAAAAI\\/AAAAAAAAABE\\/vXHVKI2NZI0\\/photo.jpg\",\"profile\":\"https:\\/\\/lh3.googleusercontent.com\\/-LQNzc5esXI8\\/AAAAAAAAAAI\\/AAAAAAAAABE\\/vXHVKI2NZI0\\/photo.jpg\",\"friend\":\"accepted\",\"follower\":null}]\'','\'[]\'','\'[]\'','\'{\"id\":\"1184563261639567\",\"friends\":[{\"name\":\"Huy Do\",\"id\":\"998275590316145\"},{\"name\":\"V\\u00f5 V\\u00e2n To\\u00e0n\",\"id\":\"686013798224769\"},{\"name\":\"\\u0110\\u1eb7ng V\\u0103n An\",\"id\":\"640964592743295\"}]}\''),('99827559031614518496639','\'{\"biggest_ride_distance\":null,\"biggest_climb_elevation_gain\":null,\"recent_ride_totals\":{\"count\":0,\"distance\":0,\"moving_time\":0,\"elapsed_time\":0,\"elevation_gain\":0,\"achievement_count\":0},\"recent_run_totals\":{\"count\":0,\"distance\":0,\"moving_time\":0,\"elapsed_time\":0,\"elevation_gain\":0,\"achievement_count\":0},\"recent_swim_totals\":{\"count\":0,\"distance\":0,\"moving_time\":0,\"elapsed_time\":0,\"elevation_gain\":0,\"achievement_count\":0},\"ytd_ride_totals\":{\"count\":0,\"distance\":0,\"moving_time\":0,\"elapsed_time\":0,\"elevation_gain\":0},\"ytd_run_totals\":{\"count\":1,\"distance\":3205,\"moving_time\":1159,\"elapsed_time\":4473,\"elevation_gain\":0},\"ytd_swim_totals\":{\"count\":0,\"distance\":0,\"moving_time\":0,\"elapsed_time\":0,\"elevation_gain\":0},\"all_ride_totals\":{\"count\":0,\"distance\":0,\"moving_time\":0,\"elapsed_time\":0,\"elevation_gain\":0},\"all_run_totals\":{\"count\":1,\"distance\":3205,\"moving_time\":1159,\"elapsed_time\":4473,\"elevation_gain\":0},\"all_swim_totals\":{\"count\":0,\"distance\":0,\"moving_time\":0,\"elapsed_time\":0,\"elevation_gain\":0}}\'','\'[]\'','\'[]\'','\'[{\"id\":18496367,\"username\":\"trn_tunt\",\"resource_state\":2,\"firstname\":\"Tr\\u1ea7n\",\"lastname\":\"Tu\\u1ea5n T\\u00fa\",\"city\":null,\"state\":null,\"country\":null,\"sex\":\"M\",\"premium\":false,\"created_at\":\"2016-11-15T03:12:31Z\",\"updated_at\":\"2016-11-17T11:11:41Z\",\"badge_type_id\":0,\"profile_medium\":\"https:\\/\\/graph.facebook.com\\/v2.2\\/1194567183972508\\/picture?height=256&width=256\",\"profile\":\"https:\\/\\/graph.facebook.com\\/v2.2\\/1194567183972508\\/picture?height=256&width=256\",\"friend\":null,\"follower\":\"accepted\"}]\'','\'[]\'','\'[]\'','\'[]\'','\'{\"id\":\"998275590316145\",\"friends\":[{\"name\":\"Tr\\u1ea7n Tu\\u1ea5n T\\u00fa\",\"id\":\"1184563261639567\"},{\"name\":\"V\\u00f5 V\\u00e2n To\\u00e0n\",\"id\":\"686013798224769\"},{\"name\":\"\\u0110\\u1eb7ng V\\u0103n An\",\"id\":\"640964592743295\"}]}\'');
/*!40000 ALTER TABLE `user_data` ENABLE KEYS */;
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
