CREATE DATABASE  IF NOT EXISTS `eval` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `eval`;
-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: eval
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `eval_index`
--

DROP TABLE IF EXISTS `eval_index`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eval_index` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pid` int DEFAULT NULL COMMENT 'parent indicator id',
  `pcode` varchar(100) DEFAULT NULL COMMENT 'parent indicator code',
  `code` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `relation_type` varchar(100) DEFAULT NULL COMMENT 'index type: relation, entity',
  `is_leaf` tinyint DEFAULT NULL COMMENT '1: leaf index, 0: not leaf index',
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eval_index`
--

LOCK TABLES `eval_index` WRITE;
/*!40000 ALTER TABLE `eval_index` DISABLE KEYS */;
INSERT INTO `eval_index` VALUES (1,0,'MacState','LifeTime','使用寿命','entity',1,NULL),(2,0,'MacState','numberOfFailure','故障次数',NULL,1,NULL),(3,0,'0','WorkerLevel','工人能力','relation',1,NULL),(4,3,'WorkerLevel','Certificate','职称',NULL,1,NULL),(5,3,'WorkerLevel','YearsOld','年龄',NULL,1,NULL),(6,0,'0','MacState','机器状态','entity',1,NULL);
/*!40000 ALTER TABLE `eval_index` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eval_node`
--

DROP TABLE IF EXISTS `eval_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eval_node` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pid` int DEFAULT NULL COMMENT 'parent id',
  `code` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `source` varchar(100) DEFAULT NULL COMMENT 'source node id',
  `sink` varchar(100) DEFAULT NULL COMMENT 'target node id',
  `node_type` varchar(100) DEFAULT NULL,
  `is_leaf` tinyint DEFAULT NULL COMMENT '0:not leaf, 1: is leaf node',
  `relation_type` varchar(45) NOT NULL COMMENT 'two types:  relation, thing',
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eval_node`
--

LOCK TABLES `eval_node` WRITE;
/*!40000 ALTER TABLE `eval_node` DISABLE KEYS */;
INSERT INTO `eval_node` VALUES (1,0,'machine','机器',NULL,NULL,'MACHINE',0,'thing',NULL),(2,0,'worker','工人',NULL,NULL,'WORKER',0,'thing',NULL),(3,0,'workermachine','工人-机器',NULL,NULL,'WORKER-MACHINE',0,'relation',NULL),(6,3,'worker2mac1','工人2-机器1','worker2','mac1','WORKER-MACHINE',1,'relation',NULL),(7,3,'worker1mac2','工人1-机器2','worker1','mac2','WORKER-MACHINE',1,'relation',NULL),(8,0,'env1mac1','车间1环境-机器1','env1','mac1','ENVIRONMENT-MACHINE',1,'relation',NULL),(9,1,'mac1','机器1',NULL,NULL,'MACHINE',1,'thing',NULL),(10,2,'worker1','工人1',NULL,NULL,'WORKER',1,'thing',NULL);
/*!40000 ALTER TABLE `eval_node` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eval_node_index`
--

DROP TABLE IF EXISTS `eval_node_index`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eval_node_index` (
  `id` int NOT NULL AUTO_INCREMENT,
  `node_code` varchar(100) NOT NULL,
  `node_name` varchar(100) DEFAULT NULL,
  `index_code` varchar(100) DEFAULT NULL,
  `index_name` varchar(100) DEFAULT NULL,
  `input_value` double DEFAULT NULL,
  `eval_obj` varchar(100) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `eval_value` double DEFAULT NULL,
  `first_aff` double DEFAULT NULL COMMENT 'the first affiliation rate',
  `second_aff` double DEFAULT NULL COMMENT 'the second affiliation rate',
  `third_aff` double DEFAULT NULL,
  `fourth_aff` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `entity_code` (`node_code`,`index_code`,`eval_obj`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eval_node_index`
--

LOCK TABLES `eval_node_index` WRITE;
/*!40000 ALTER TABLE `eval_node_index` DISABLE KEYS */;
INSERT INTO `eval_node_index` VALUES (1,'worker1','工人1','Certificate','职称',NULL,'workshop',NULL,NULL,NULL,NULL,NULL,NULL),(2,'mac1','机器1','MacState','机器状态',NULL,'workshop',NULL,NULL,NULL,NULL,NULL,NULL),(3,'mac1','机器1','LifeTime','使用寿命',NULL,'workshop',NULL,NULL,NULL,NULL,NULL,NULL),(4,'worker1mac2','工人1机器2','CheckTimes','检查次数',NULL,'workshop',NULL,NULL,NULL,NULL,NULL,NULL),(5,'worker','工人','Certificate',NULL,NULL,'workshop',NULL,NULL,NULL,NULL,NULL,NULL),(6,'machine','机器','Certificate',NULL,NULL,'workshop',NULL,NULL,NULL,NULL,NULL,NULL),(7,'workermachine','工人机器','Certificate',NULL,NULL,'workshop',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `eval_node_index` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eval_object`
--

DROP TABLE IF EXISTS `eval_object`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eval_object` (
  `id` int NOT NULL AUTO_INCREMENT,
  `eval_obj` varchar(100) DEFAULT NULL,
  `eval_code` varchar(100) NOT NULL,
  `eval_method` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `eval_code` (`eval_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eval_object`
--

LOCK TABLES `eval_object` WRITE;
/*!40000 ALTER TABLE `eval_object` DISABLE KEYS */;
INSERT INTO `eval_object` VALUES (1,'产品质量','product','模糊评判',NULL),(2,'生产车间','workshop','模糊评判',NULL);
/*!40000 ALTER TABLE `eval_object` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eval_rating`
--

DROP TABLE IF EXISTS `eval_rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eval_rating` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `value` double DEFAULT NULL,
  `version` varchar(45) DEFAULT 'v1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eval_rating`
--

LOCK TABLES `eval_rating` WRITE;
/*!40000 ALTER TABLE `eval_rating` DISABLE KEYS */;
INSERT INTO `eval_rating` VALUES (1,'mdium','中',NULL,70,'v1'),(2,'low','低',NULL,50,'v1'),(3,'high','高',NULL,100,'v1');
/*!40000 ALTER TABLE `eval_rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evalobj_node_link`
--

DROP TABLE IF EXISTS `evalobj_node_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evalobj_node_link` (
  `id` int NOT NULL AUTO_INCREMENT,
  `eval_obj_code` varchar(100) NOT NULL,
  `node_code` varchar(100) NOT NULL,
  `node_name` varchar(100) DEFAULT NULL,
  `node_type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evalobj_node_link`
--

LOCK TABLES `evalobj_node_link` WRITE;
/*!40000 ALTER TABLE `evalobj_node_link` DISABLE KEYS */;
INSERT INTO `evalobj_node_link` VALUES (1,'workshop','worker1','工人1',NULL),(2,'workshop','mac1','机器1',NULL),(3,'workshop','worker1mac2','工人1机器2',NULL),(4,'workshop','worker','工人',NULL),(5,'workshop','machine','机器',NULL),(6,'workshop','workermachine','工人机器',NULL);
/*!40000 ALTER TABLE `evalobj_node_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `index_importance`
--

DROP TABLE IF EXISTS `index_importance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `index_importance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `index_first` varchar(45) NOT NULL,
  `index_second` varchar(45) NOT NULL,
  `importance` double NOT NULL COMMENT 'first vs second importance',
  `version` varchar(45) NOT NULL DEFAULT 'v1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `index_importance`
--

LOCK TABLES `index_importance` WRITE;
/*!40000 ALTER TABLE `index_importance` DISABLE KEYS */;
INSERT INTO `index_importance` VALUES (1,'MacState','LifeTime',0.5,'v1'),(2,'numberOfFailure','MacState',0.6,'v1'),(4,'WorkCertificate','MacPerformance',0.25,'v1');
/*!40000 ALTER TABLE `index_importance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `index_membership`
--

DROP TABLE IF EXISTS `index_membership`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `index_membership` (
  `id` int NOT NULL AUTO_INCREMENT,
  `index_code` varchar(100) NOT NULL,
  `rating_code` varchar(100) NOT NULL,
  `affiliation` varchar(100) NOT NULL COMMENT 'three types of function: gauss, triangle, trapezoid',
  `param1` double DEFAULT NULL COMMENT ' param1 refer to the first paramter of the affiliation function',
  `param2` double DEFAULT NULL,
  `param3` double DEFAULT NULL,
  `param4` double DEFAULT NULL,
  `param5` double DEFAULT NULL,
  `version` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `index_membership`
--

LOCK TABLES `index_membership` WRITE;
/*!40000 ALTER TABLE `index_membership` DISABLE KEYS */;
INSERT INTO `index_membership` VALUES (1,'LifeTime','high','gauss',0,1,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `index_membership` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `node_index_link`
--

DROP TABLE IF EXISTS `node_index_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `node_index_link` (
  `id` int NOT NULL AUTO_INCREMENT,
  `node_type` varchar(100) NOT NULL,
  `node_type_name` varchar(100) DEFAULT NULL,
  `index_code` varchar(100) DEFAULT NULL,
  `index_name` varchar(100) DEFAULT NULL,
  `version` varchar(45) NOT NULL DEFAULT 'v1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `node_index_link`
--

LOCK TABLES `node_index_link` WRITE;
/*!40000 ALTER TABLE `node_index_link` DISABLE KEYS */;
INSERT INTO `node_index_link` VALUES (1,'MACHINE','MACHINE','LifeTime','使用寿命','v1'),(2,'MACHINE','MACHINE','numberOfFailure',NULL,'v1'),(3,'WORKER','WORKER','Certificate','职称','v1');
/*!40000 ALTER TABLE `node_index_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `node_type`
--

DROP TABLE IF EXISTS `node_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `node_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `relation_type` varchar(45) DEFAULT NULL COMMENT 'two types:  relation, thing',
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `node_type`
--

LOCK TABLES `node_type` WRITE;
/*!40000 ALTER TABLE `node_type` DISABLE KEYS */;
INSERT INTO `node_type` VALUES (1,'MACHINE','机器','thing',NULL),(2,'WORKER','工人','thing',NULL),(3,'MATERIAL','物料','thing',NULL),(4,'ENVIRONMENT','环境','thing',NULL),(5,'PROCEDURE','规则程序','thing',NULL),(6,'WORKSHOP','车间','thing',NULL),(7,'WORKER-MACHINE','工人-机器','relation',NULL),(8,'MACHINE-MATERIAL','机器-物料','relation',NULL);
/*!40000 ALTER TABLE `node_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `node_type_importance`
--

DROP TABLE IF EXISTS `node_type_importance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `node_type_importance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `index_first` varchar(45) NOT NULL,
  `index_second` varchar(45) NOT NULL,
  `importance` double NOT NULL COMMENT 'first vs second importance',
  `version` varchar(45) NOT NULL DEFAULT 'v1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `node_type_importance`
--

LOCK TABLES `node_type_importance` WRITE;
/*!40000 ALTER TABLE `node_type_importance` DISABLE KEYS */;
INSERT INTO `node_type_importance` VALUES (1,'MACHINE','WORKER',0.4,'v1'),(2,'MACHINE','ENVIRONMENT',0.6,'v1');
/*!40000 ALTER TABLE `node_type_importance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_data`
--

DROP TABLE IF EXISTS `sys_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_data` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `dict_sort` int DEFAULT NULL COMMENT '显示顺序',
  `dict_label` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字典标签',
  `dict_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字典键值',
  `dict_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字典类型',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_data`
--

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;
INSERT INTO `sys_dict_data` VALUES (1,1,'男','0','user_sex','',NULL),(2,2,'女','1','user_sex','',NULL),(3,1,'sdf','fgfd','check_status','',NULL),(4,1,'video/mp4','0','sys_video','',NULL),(5,2,'m3u8','1','sys_video','',NULL);
/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_type`
--

DROP TABLE IF EXISTS `sys_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dict_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字典名称',
  `dict_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字典类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_type`
--

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type` DISABLE KEYS */;
INSERT INTO `sys_dict_type` VALUES (1,'用户性别','user_sex','2021-07-28 17:58:16',''),(3,'开关状态','check_status','2021-07-29 09:52:36',''),(4,'视频类型','sys_video_type','2022-09-07 16:36:55','');
/*!40000 ALTER TABLE `sys_dict_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '#' COMMENT '访问地址',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单名称',
  `parent_id` bigint DEFAULT NULL COMMENT '父级菜单',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单图标',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限标识',
  `perms` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限标识',
  `sort` int DEFAULT NULL COMMENT '显示顺序',
  `iframe` int DEFAULT NULL COMMENT '是否为内置页面',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单列表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (3,'dd','系统管理',0,'Setting','0',NULL,1,0),(4,'system/Menus','菜单管理',3,'Menu','1',NULL,1,0),(5,'system/Roles','角色管理',3,'HelpFilled','1','sys:role',2,0),(33,'system/Users','用户管理',3,'UserFilled','1','system:user:list',3,0),(41,'base','基础管理',0,'Calendar','0','',2,0),(42,'blacklist','黑名单记录',41,'ios-aperture','0','base:black:list',1,0),(45,'system/DictType','字典管理',3,'DataAnalysis','1','sys:dict:list',4,0),(46,'blacklist','白名单记录',42,'ios-aperture','1','base:black:list',1,0),(52,'genCode','代码生成',3,'Menu','1','ss',1,0),(54,'personCenter','个人中心',0,'Stamp','1','sd',3,0),(56,'','新增',5,'Menu','2','sys:role:add',1,0),(61,'','测试目录',0,'FullScreen','0','',1,0),(63,'','编辑',5,'Menu','2','sys:role:edit',1,0),(64,'system/SysTest-ele','测试管理',61,'TakeawayBox','1','',1,0),(65,'','新增',64,'Menu','2','sysy',1,0),(66,'','删除不',64,'Menu','2','222',1,0),(67,'','sdf',46,'Menu','2','岁的法国',1,0),(68,'','是德国',46,'Menu','2','dfg',1,0),(69,'system/SysVideo','视频管理',61,'View','1','sss',1,0),(70,'','态势评估',0,'Menu','0','',1,0),(71,'404','评估对象管理',70,'Avatar','1','',1,0),(72,'404','评估节点管理',70,'Aim','1','',1,0),(73,'404','评估指标管理',70,'Compass','1','',1,0),(75,'404','评级评语设计',70,'CircleCheck','1','',2,0),(76,'404','对象指标建模',70,'Grid','1','',4,0),(77,'#','模型评价计算',70,'CircleCheck','1',NULL,5,0);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu_role`
--

DROP TABLE IF EXISTS `sys_menu_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint DEFAULT NULL,
  `menu_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `select_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单-权限关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu_role`
--

LOCK TABLES `sys_menu_role` WRITE;
/*!40000 ALTER TABLE `sys_menu_role` DISABLE KEYS */;
INSERT INTO `sys_menu_role` VALUES (5,5,'3,4,5',NULL),(13,4,'1,4',NULL),(15,2,'56,65,5,3,64,61','56,65'),(22,3,'7,8,10,4,9',NULL),(23,6,'5,3','5'),(24,11,'4,5,7,3,6','4,5,7'),(25,11,'4,5,33,3','4,5,33'),(26,12,'4,5,33,3','4,5,33'),(27,13,'5,33,45,3','5,33,45'),(28,14,'4,5,33,45,3','4,5,33,45'),(29,15,'33,3','33'),(30,16,'45,50,3','45,50'),(31,17,'33,45,3','33,45'),(32,18,'33,50,3','33,50'),(40,38,'61,62,0','61,62');
/*!40000 ALTER TABLE `sys_menu_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名称',
  `role_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色权限字符串',
  `role_sort` int DEFAULT '0' COMMENT '显示顺序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'超级管理员','ROLE_SUPER_ADMIN',1,'2021-07-22 09:18:20',NULL),(2,'人事专员','ROLE_PERSON',3,'2021-07-21 09:18:23',NULL),(3,'招聘主管','ROLE_recruiter',4,'2021-07-07 09:18:28',NULL),(4,'培训主管','ROLE_train',5,'2021-07-13 09:18:30',NULL),(5,'薪酬绩效主管','ROLE_performance',6,'2021-07-11 09:18:35',NULL),(9,'管理员','ADMIN',7,'2021-07-22 11:13:41','');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_test`
--

DROP TABLE IF EXISTS `sys_test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_test` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '名字',
  `age` int DEFAULT NULL COMMENT '年龄',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='测试生成表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_test`
--

LOCK TABLES `sys_test` WRITE;
/*!40000 ALTER TABLE `sys_test` DISABLE KEYS */;
INSERT INTO `sys_test` VALUES (1,'asd',1,'士大夫');
/*!40000 ALTER TABLE `sys_test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名（手机号）',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `role` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'admin','$2a$10$hGHXhwZvHa3cPSBcsw5l2ON.o70rw7r6kT7pZqxZa1dieMnVEl2HC','1,2','超级管理员','2021-07-21 16:25:07'),(6,'user','$2a$10$9js5P2t54NJtWieJ.3ZO1.8blkugIVqAsRVGFctIBeNvc45vx9EF.','2',NULL,'2021-07-09 16:25:10'),(25,'user2','$2a$10$tOa3NVUWoM1z5EFQl2i3vec4R.IDExsm7GTcVw3ikxc21QOpYfpsu','3','','2022-05-24 08:20:07');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_video`
--

DROP TABLE IF EXISTS `sys_video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_video` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '摄像头名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '播放地址',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '位置',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `type` int DEFAULT NULL COMMENT '视频类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_video`
--

LOCK TABLES `sys_video` WRITE;
/*!40000 ALTER TABLE `sys_video` DISABLE KEYS */;
INSERT INTO `sys_video` VALUES (1,'测试设备1','https://cdn.jsdelivr.net/gh/xdlumia/files/video-play/IronMan.mp4','公司测试','',NULL,'2022-09-07 00:00:00');
/*!40000 ALTER TABLE `sys_video` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-29  8:39:40
