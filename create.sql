-- MySQL dump 10.13  Distrib 5.7.37, for Win64 (x86_64)
--
-- Host: localhost    Database: diary
-- ------------------------------------------------------
-- Server version	5.7.37-log

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
-- Table structure for table `calendar_accounting`
--

DROP TABLE IF EXISTS `calendar_accounting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `calendar_accounting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `target_date` date NOT NULL COMMENT '目标日期',
  `price` decimal(10,2) NOT NULL,
  `category` varchar(20) DEFAULT NULL,
  `type` char(1) DEFAULT '1' COMMENT '1：支出，2：收入',
  `column1` varchar(100) DEFAULT NULL,
  `column2` varchar(100) DEFAULT NULL,
  `column3` varchar(100) DEFAULT NULL,
  `column4` varchar(100) DEFAULT NULL,
  `column5` varchar(100) DEFAULT NULL,
  `deleted` char(1) DEFAULT '0',
  `create_by` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calendar_accounting`
--

LOCK TABLES `calendar_accounting` WRITE;
/*!40000 ALTER TABLE `calendar_accounting` DISABLE KEYS */;
/*!40000 ALTER TABLE `calendar_accounting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calendar_base`
--

DROP TABLE IF EXISTS `calendar_base`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `calendar_base` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `target_date` date NOT NULL,
  `target_year` varchar(4) DEFAULT NULL,
  `target_month` varchar(2) DEFAULT NULL,
  `target_week` varchar(2) DEFAULT NULL,
  `mark` varchar(10) DEFAULT NULL,
  `column1` varchar(10) DEFAULT NULL,
  `column2` varchar(10) DEFAULT NULL,
  `column3` varchar(10) DEFAULT NULL,
  `column4` varchar(10) DEFAULT NULL,
  `deleted` char(1) DEFAULT '0',
  `create_by` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calendar_base`
--

LOCK TABLES `calendar_base` WRITE;
/*!40000 ALTER TABLE `calendar_base` DISABLE KEYS */;
/*!40000 ALTER TABLE `calendar_base` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calendar_health_record`
--

DROP TABLE IF EXISTS `calendar_health_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `calendar_health_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `target_date` date NOT NULL COMMENT '目标日期',
  `weight` decimal(5,1) DEFAULT NULL COMMENT '体重',
  `weight_diff` decimal(5,1) DEFAULT NULL COMMENT '体重变化',
  `breakfast` varchar(500) DEFAULT NULL COMMENT '早餐食谱',
  `lunch` varchar(500) DEFAULT NULL COMMENT '午餐食谱',
  `dinner` varchar(500) DEFAULT NULL COMMENT '晚餐食谱',
  `snack` varchar(500) DEFAULT NULL COMMENT '加餐',
  `calorie` smallint(6) DEFAULT NULL COMMENT '摄入热量',
  `exercise_detail` varchar(500) DEFAULT NULL COMMENT '运动详情',
  `column1` varchar(100) DEFAULT NULL,
  `column2` varchar(100) DEFAULT NULL,
  `column3` varchar(100) DEFAULT NULL,
  `column4` varchar(100) DEFAULT NULL,
  `column5` varchar(100) DEFAULT NULL,
  `deleted` char(1) DEFAULT '0',
  `create_by` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calendar_health_record`
--

LOCK TABLES `calendar_health_record` WRITE;
/*!40000 ALTER TABLE `calendar_health_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `calendar_health_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calendar_todo`
--

DROP TABLE IF EXISTS `calendar_todo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `calendar_todo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `calendar_id` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `priority` tinyint(4) DEFAULT NULL,
  `column1` varchar(100) DEFAULT NULL,
  `column2` varchar(100) DEFAULT NULL,
  `column3` varchar(100) DEFAULT NULL,
  `column4` varchar(100) DEFAULT NULL,
  `column5` varchar(100) DEFAULT NULL,
  `deleted` char(1) DEFAULT '0',
  `create_by` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calendar_todo`
--

LOCK TABLES `calendar_todo` WRITE;
/*!40000 ALTER TABLE `calendar_todo` DISABLE KEYS */;
/*!40000 ALTER TABLE `calendar_todo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_users`
--

DROP TABLE IF EXISTS `sys_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(20) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `openid` varchar(64) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `avatar` binary(1) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `column1` varchar(10) DEFAULT NULL,
  `column2` varchar(10) DEFAULT NULL,
  `column3` varchar(10) DEFAULT NULL,
  `column4` varchar(10) DEFAULT NULL,
  `deleted` char(1) NOT NULL DEFAULT '0',
  `create_by` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_users`
--

LOCK TABLES `sys_users` WRITE;
/*!40000 ALTER TABLE `sys_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'diary'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-12 21:24:26
