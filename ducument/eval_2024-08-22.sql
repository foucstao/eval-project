# ************************************************************
# Sequel Ace SQL dump
# 版本号： 20052
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# 主机: 127.0.0.1 (MySQL 8.1.0)
# 数据库: eval
# 生成时间: 2024-08-22 13:35:11 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE='NO_AUTO_VALUE_ON_ZERO', SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# 转储表 eval_index
# ------------------------------------------------------------

DROP TABLE IF EXISTS `eval_index`;

CREATE TABLE `eval_index` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pid` int DEFAULT NULL COMMENT 'parent indicator id',
  `pcode` varchar(100) DEFAULT NULL COMMENT 'parent indicator code',
  `code` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `node_type` varchar(45) DEFAULT NULL,
  `relation_type` varchar(100) DEFAULT NULL COMMENT 'index type: relation, entity',
  `is_leaf` tinyint DEFAULT NULL COMMENT '1: leaf index, 0: not leaf index',
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

LOCK TABLES `eval_index` WRITE;
/*!40000 ALTER TABLE `eval_index` DISABLE KEYS */;

INSERT INTO `eval_index` (`id`, `pid`, `pcode`, `code`, `name`, `node_type`, `relation_type`, `is_leaf`, `description`)
VALUES
	(1,0,'MacState','LifeTime','使用寿命','MACHINE','thing',1,NULL),
	(2,0,'MacState','numberOfFailure','故障次数','MACHINE',NULL,1,NULL),
	(3,0,'0','WorkerLevel','工人能力','WORKER','relation',1,NULL),
	(4,3,'WorkerLevel','Certificate','职称','WORKER',NULL,1,NULL),
	(5,3,'WorkerLevel','YearsOld','年龄','WORKER','thing',1,'PTRA'),
	(6,0,'0','MacState','机器状态','MACHINE','thing',1,''),
	(12,NULL,'','Seniority','工龄',NULL,'thing',1,'PTRA'),
	(13,NULL,'','AccRate','事故率',NULL,'thing',1,'PTRA'),
	(14,NULL,'','CwTime','日作业时间',NULL,'thing',1,'PTRA'),
	(15,NULL,'','Age','年龄',NULL,'thing',1,'PTRA'),
	(16,NULL,'','FailureRate','失误率',NULL,'thing',1,'PTRA'),
	(17,NULL,'','RepairTime','维修时间',NULL,'thing',1,'PTRA'),
	(18,NULL,'','EnduranceTime','持续时间',NULL,'thing',1,'PTRA'),
	(19,NULL,'','DownTime','停机时间',NULL,'thing',1,'PTRA'),
	(20,NULL,'','PassPdRate','产品合格率',NULL,'thing',1,'PTRA'),
	(21,NULL,'','EnergyCost','当前能耗',NULL,'thing',1,'PTRA'),
	(22,NULL,'','CurSpeed','当前速度',NULL,'thing',1,'PTRA'),
	(23,NULL,'','CarryTon','载重',NULL,'thing',1,'PTRA'),
	(24,NULL,'','CoTIme','磨合时间',NULL,'relation',1,'PTRA'),
	(25,NULL,'','Proficiency','熟练程度',NULL,'relation',1,'PTRA');

/*!40000 ALTER TABLE `eval_index` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 eval_index_model
# ------------------------------------------------------------

DROP TABLE IF EXISTS `eval_index_model`;

CREATE TABLE `eval_index_model` (
  `id` int NOT NULL AUTO_INCREMENT,
  `index_model` varchar(100) NOT NULL,
  `node_type` varchar(100) NOT NULL,
  `index_code` varchar(100) DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `membership` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

LOCK TABLES `eval_index_model` WRITE;
/*!40000 ALTER TABLE `eval_index_model` DISABLE KEYS */;

INSERT INTO `eval_index_model` (`id`, `index_model`, `node_type`, `index_code`, `weight`, `membership`)
VALUES
	(1,'I1','WORKER','Certificate',0.7999999999999999,'triangle'),
	(2,'I1','MACHINE','LifeTime',0.3460802947473044,'gauss'),
	(3,'I1','MACHINE','numberOfFailure',0.5059724603435276,'triangle'),
	(4,'I1','WORKER','YearsOld',0.19999999999999998,'triangle'),
	(5,'I1','MACHINE','MacState',0.14794724490916805,'triangle'),
	(21,'I2','MACHINE','numberOfFailure',0.6666666666666666,'triangle'),
	(22,'I2','MACHINE','speed',0.3333333333333333,'triangle'),
	(23,'I2','WORKER','WorkerLevel',0.19999999999999998,'triangle'),
	(24,'I2','WORKER','YearsOld',0.7999999999999999,'triangle'),
	(25,'I3','HumanWorker','YearsOld',NULL,'gauss'),
	(26,'I3','HumanWorker','Seniority',NULL,'gauss'),
	(27,'I3','HumanWorker','CwTime',NULL,'gauss'),
	(28,'I3','HumanWorker','AccRate',NULL,'gauss'),
	(29,'I3','RobticWorker','FailureRate',NULL,'gauss'),
	(30,'I3','RobticWorker','RepairTime',NULL,'gauss'),
	(31,'I3','RobticWorker','EnduranceTime',NULL,'gauss'),
	(32,'I3','RobticWorker','AccRate',NULL,'gauss'),
	(33,'I3','SpinningMachine','FailureRate',NULL,'gauss'),
	(34,'I3','SpinningMachine','DownTime',NULL,'gauss'),
	(35,'I3','SpinningMachine','AccRate',NULL,'gauss'),
	(36,'I3','SpinningMachine','PassPdRate',NULL,'gauss'),
	(37,'I3','TransVehicle','EnergyCost',NULL,'gauss'),
	(38,'I3','TransVehicle','CurSpeed',NULL,'gauss'),
	(39,'I3','TransVehicle','CarryTon',NULL,'gauss'),
	(40,'I3','TransVehicle','AccRate',NULL,'gauss'),
	(41,'I3','Interaction','CoTIme',NULL,'gauss'),
	(42,'I3','Interaction','Proficiency',NULL,'gauss');

/*!40000 ALTER TABLE `eval_index_model` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 eval_node
# ------------------------------------------------------------

DROP TABLE IF EXISTS `eval_node`;

CREATE TABLE `eval_node` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pid` int DEFAULT '0' COMMENT 'parent id',
  `code` varchar(100) NOT NULL,
  `pcode` varchar(100) DEFAULT NULL,
  `institution` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `source` varchar(100) DEFAULT NULL COMMENT 'source node id',
  `sink` varchar(100) DEFAULT NULL COMMENT 'target node id',
  `node_type` varchar(100) DEFAULT NULL,
  `is_leaf` tinyint DEFAULT NULL COMMENT '0:not leaf, 1: is leaf node',
  `relation_type` varchar(45) NOT NULL COMMENT 'two types:  relation, thing',
  `description` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `idx_institution` (`institution`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

LOCK TABLES `eval_node` WRITE;
/*!40000 ALTER TABLE `eval_node` DISABLE KEYS */;

INSERT INTO `eval_node` (`id`, `pid`, `code`, `pcode`, `institution`, `name`, `source`, `sink`, `node_type`, `is_leaf`, `relation_type`, `description`, `create_time`, `update_time`)
VALUES
	(1,0,'machine',NULL,'A','机器',NULL,NULL,'MACHINE',0,'thing','','2023-10-21 13:51:41','2024-04-28 23:26:05'),
	(2,0,'worker',NULL,'A','工人',NULL,NULL,'WORKER',0,'thing',NULL,'2023-10-21 13:51:41','2023-10-21 16:04:53'),
	(3,0,'workermachine',NULL,'A','工人-机器',NULL,NULL,'WORKER-MACHINE',0,'relation',NULL,'2023-10-21 13:51:41','2023-10-21 16:04:53'),
	(9,1,'mac1','machine','A','机器1',NULL,NULL,'MACHINE',1,'thing',NULL,'2023-10-21 13:51:41','2023-10-21 16:04:53'),
	(10,2,'worker1','worker','A','工人1',NULL,NULL,'WORKER',1,'thing',NULL,'2023-10-21 13:51:41','2023-10-21 16:04:53'),
	(12,0,'wokshop1',NULL,'A','生产车间1',NULL,NULL,'WORKSHOP',0,'thing','','2023-10-21 13:51:41','2024-01-21 09:23:13'),
	(29,0,'hw1','','B','工人1','','','HumanWorker',1,'thing','','2024-04-28 15:39:45','2024-04-28 23:24:19'),
	(30,0,'hw2','','B','工人2','','','HumanWorker',1,'thing','','2024-04-28 15:40:09','2024-04-28 15:45:50'),
	(31,0,'hw3','','B','工人3','','','HumanWorker',1,'thing','','2024-04-28 15:40:31','2024-04-28 15:46:59'),
	(32,0,'hw4','','B','工人4','','','HumanWorker',1,'thing','','2024-04-28 15:40:56','2024-04-28 15:47:06'),
	(33,0,'hw5','','B','工人5','','','HumanWorker',1,'thing','','2024-04-28 15:41:10','2024-04-28 15:47:15'),
	(34,0,'hw6','','B','工人6','','','HumanWorker',1,'thing','','2024-04-28 15:41:27','2024-04-28 15:47:24'),
	(35,0,'Rwk1','','B','机器人1','','','RobticWorker',1,'thing','','2024-04-28 21:29:04','2024-04-28 21:29:04'),
	(36,0,'Smac1','','B','纺纱机1','','','SpinningMachine',1,'thing','','2024-04-28 21:29:30','2024-04-28 21:30:17'),
	(37,0,'Smac2','','B','纺纱机2','','','SpinningMachine',1,'thing','','2024-04-28 21:29:58','2024-04-28 21:30:33'),
	(39,0,'Wmac1','','B','织造机1','','','SpinningMachine',1,'thing','','2024-04-28 21:38:22','2024-04-29 14:50:28'),
	(40,0,'Wmac2','','B','织造机2','','','SpinningMachine',1,'thing','','2024-04-28 21:39:03','2024-04-29 14:50:37'),
	(41,0,'PDmac1','','B','印染机1','','','SpinningMachine',1,'thing','','2024-04-28 21:39:36','2024-04-29 14:50:46'),
	(42,0,'Tv1','','B','运输车1','','','TransVehicle',1,'thing','','2024-04-28 21:41:54','2024-04-28 21:41:54'),
	(43,0,'Is1','','B','交互1','Hwk1','Tv1','Interaction',1,'relation','','2024-04-28 21:43:07','2024-04-28 21:43:07'),
	(44,0,'Is2','','B','交互2','Hwk2','Smac1','Interaction',1,'relation','','2024-04-28 21:43:35','2024-04-28 21:43:35'),
	(45,0,'Is3','','B','交互3','Hwk3','Smac2','Interaction',1,'relation','','2024-04-28 21:44:00','2024-04-28 21:44:00'),
	(46,0,'Is4','','B','交互4','Rmac1','Wmac1','Interaction',1,'relation','','2024-04-28 21:44:26','2024-04-28 21:44:26'),
	(47,0,'Is5','','B','交互5','Rmac1','Wmac2','Interaction',1,'relation','','2024-04-28 21:44:55','2024-04-28 23:22:54');

/*!40000 ALTER TABLE `eval_node` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 eval_node_evaluation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `eval_node_evaluation`;

CREATE TABLE `eval_node_evaluation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `eval_instance` varchar(45) DEFAULT NULL,
  `eval_obj_code` varchar(100) NOT NULL,
  `node_code` varchar(100) NOT NULL,
  `rating` varchar(50) DEFAULT NULL,
  `eval_value` double DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

LOCK TABLES `eval_node_evaluation` WRITE;
/*!40000 ALTER TABLE `eval_node_evaluation` DISABLE KEYS */;

INSERT INTO `eval_node_evaluation` (`id`, `eval_instance`, `eval_obj_code`, `node_code`, `rating`, `eval_value`, `description`, `update_time`, `create_time`)
VALUES
	(41,'evalworkshopi1001','workshop','mac1','middle',0.2805202083989188,NULL,'2024-07-16 11:41:21','2024-03-02 00:02:32'),
	(42,'evalworkshopi1001','workshop','worker1','low',0,NULL,'2024-07-16 11:41:21','2024-03-02 00:02:32'),
	(43,'evalworkshopi1001','workshop','mac1','low',0.0000000052707958790456274,NULL,'2024-07-16 11:41:21','2024-03-02 00:02:32'),
	(44,'evalworkshopi1001','workshop','worker1','high',0.08,NULL,'2024-07-16 11:41:21','2024-03-02 00:02:32'),
	(45,'evalworkshopi1001','workshop','mac1','high',0.6573143658792409,NULL,'2024-07-16 11:41:21','2024-03-02 00:02:32'),
	(46,'evalworkshopi1001','workshop','worker1','middle',0.8799999999999999,NULL,'2024-07-16 11:41:21','2024-03-02 00:02:32'),
	(47,'evalworkshopi2002','workshop','mac1','middle',0.6,NULL,'2024-03-02 00:18:20','2024-03-02 00:02:41'),
	(48,'evalworkshopi2002','workshop','worker1','low',0.09999999999999999,NULL,'2024-03-02 00:18:20','2024-03-02 00:02:41'),
	(49,'evalworkshopi2002','workshop','mac1','low',0.26666666666666666,NULL,'2024-03-02 00:18:20','2024-03-02 00:02:41'),
	(50,'evalworkshopi2002','workshop','worker1','high',0.32,NULL,'2024-03-02 00:18:20','2024-03-02 00:02:41'),
	(51,'evalworkshopi2002','workshop','mac1','high',0.16666666666666666,NULL,'2024-03-02 00:18:20','2024-03-02 00:02:41'),
	(52,'evalworkshopi2002','workshop','worker1','middle',0.32,NULL,'2024-03-02 00:18:20','2024-03-02 00:02:41');

/*!40000 ALTER TABLE `eval_node_evaluation` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 eval_node_index_evaluation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `eval_node_index_evaluation`;

CREATE TABLE `eval_node_index_evaluation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `eval_instance` varchar(45) DEFAULT NULL,
  `eval_obj_code` varchar(100) NOT NULL,
  `index_model` varchar(45) DEFAULT NULL,
  `node_code` varchar(100) NOT NULL,
  `index_code` varchar(100) DEFAULT NULL,
  `input_value` double DEFAULT NULL,
  `rating` varchar(50) DEFAULT NULL,
  `membership` varchar(45) DEFAULT NULL,
  `eval_value` double DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

LOCK TABLES `eval_node_index_evaluation` WRITE;
/*!40000 ALTER TABLE `eval_node_index_evaluation` DISABLE KEYS */;

INSERT INTO `eval_node_index_evaluation` (`id`, `eval_instance`, `eval_obj_code`, `index_model`, `node_code`, `index_code`, `input_value`, `rating`, `membership`, `eval_value`, `description`, `create_time`, `update_time`)
VALUES
	(182,'evalworkshopi1001','workshop','I1','mac1','MacState',10,'middle','triangle',0,NULL,'2024-03-02 00:02:32','2024-07-16 11:41:21'),
	(183,'evalworkshopi1001','workshop','I1','mac1','MacState',10,'low','triangle',0,NULL,'2024-03-02 00:02:32','2024-07-16 11:41:21'),
	(184,'evalworkshopi1001','workshop','I1','mac1','MacState',10,'high','triangle',1,NULL,'2024-03-02 00:02:32','2024-07-16 11:41:21'),
	(185,'evalworkshopi1001','workshop','I1','mac1','numberOfFailure',7,'middle','triangle',0.5,NULL,'2024-03-02 00:02:32','2024-07-16 11:41:21'),
	(186,'evalworkshopi1001','workshop','I1','mac1','numberOfFailure',7,'low','triangle',0,NULL,'2024-03-02 00:02:32','2024-07-16 11:41:21'),
	(187,'evalworkshopi1001','workshop','I1','mac1','numberOfFailure',7,'high','triangle',0.75,NULL,'2024-03-02 00:02:32','2024-07-16 11:41:21'),
	(188,'evalworkshopi1001','workshop','I1','mac1','LifeTime',15,'middle','gauss',0.0795595087182277,NULL,'2024-03-02 00:02:32','2024-07-16 11:41:21'),
	(189,'evalworkshopi1001','workshop','I1','mac1','LifeTime',15,'low','gauss',0.000000015229979744712642,NULL,'2024-03-02 00:02:32','2024-07-16 11:41:21'),
	(190,'evalworkshopi1001','workshop','I1','mac1','LifeTime',15,'high','gauss',0.37531109885139957,NULL,'2024-03-02 00:02:32','2024-07-16 11:41:21'),
	(191,'evalworkshopi1001','workshop','I1','worker1','YearsOld',37,'middle','triangle',0.4,NULL,'2024-03-02 00:02:32','2024-07-16 11:41:21'),
	(192,'evalworkshopi1001','workshop','I1','worker1','YearsOld',37,'low','triangle',0,NULL,'2024-03-02 00:02:32','2024-07-16 11:41:21'),
	(193,'evalworkshopi1001','workshop','I1','worker1','YearsOld',37,'high','triangle',0.4,NULL,'2024-03-02 00:02:32','2024-07-16 11:41:21'),
	(194,'evalworkshopi1001','workshop','I1','worker1','Certificate',3,'middle','triangle',1,NULL,'2024-03-02 00:02:32','2024-07-16 11:41:21'),
	(195,'evalworkshopi1001','workshop','I1','worker1','Certificate',3,'low','triangle',0,NULL,'2024-03-02 00:02:32','2024-07-16 11:41:21'),
	(196,'evalworkshopi1001','workshop','I1','worker1','Certificate',3,'high','triangle',0,NULL,'2024-03-02 00:02:32','2024-07-16 11:41:21'),
	(197,'evalworkshopi2002','workshop','I2','mac1','speed',34,'middle','triangle',0.8,NULL,'2024-03-02 00:02:41','2024-03-02 00:18:20'),
	(198,'evalworkshopi2002','workshop','I2','mac1','speed',34,'low','triangle',0.8,NULL,'2024-03-02 00:02:41','2024-03-02 00:18:20'),
	(199,'evalworkshopi2002','workshop','I2','mac1','speed',34,'high','triangle',0,NULL,'2024-03-02 00:02:41','2024-03-02 00:18:20'),
	(200,'evalworkshopi2002','workshop','I2','mac1','numberOfFailure',5,'middle','triangle',0.5,NULL,'2024-03-02 00:02:41','2024-03-02 00:18:20'),
	(201,'evalworkshopi2002','workshop','I2','mac1','numberOfFailure',5,'low','triangle',0,NULL,'2024-03-02 00:02:41','2024-03-02 00:18:20'),
	(202,'evalworkshopi2002','workshop','I2','mac1','numberOfFailure',5,'high','triangle',0.25,NULL,'2024-03-02 00:02:41','2024-03-02 00:18:20'),
	(203,'evalworkshopi2002','workshop','I2','worker1','YearsOld',37,'middle','triangle',0.4,NULL,'2024-03-02 00:02:41','2024-03-02 00:18:20'),
	(204,'evalworkshopi2002','workshop','I2','worker1','YearsOld',37,'low','triangle',0,NULL,'2024-03-02 00:02:41','2024-03-02 00:18:20'),
	(205,'evalworkshopi2002','workshop','I2','worker1','YearsOld',37,'high','triangle',0.4,NULL,'2024-03-02 00:02:41','2024-03-02 00:18:20'),
	(206,'evalworkshopi2002','workshop','I2','worker1','WorkerLevel',4.5,'middle','triangle',0,NULL,'2024-03-02 00:02:41','2024-03-02 00:18:20'),
	(207,'evalworkshopi2002','workshop','I2','worker1','WorkerLevel',4.5,'low','triangle',0.5,NULL,'2024-03-02 00:02:41','2024-03-02 00:18:20'),
	(208,'evalworkshopi2002','workshop','I2','worker1','WorkerLevel',4.5,'high','triangle',0,NULL,'2024-03-02 00:02:41','2024-03-02 00:18:20');

/*!40000 ALTER TABLE `eval_node_index_evaluation` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 eval_obj_evaluation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `eval_obj_evaluation`;

CREATE TABLE `eval_obj_evaluation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `eval_instance` varchar(45) DEFAULT NULL,
  `eval_obj_code` varchar(100) NOT NULL,
  `rating` varchar(50) DEFAULT NULL,
  `eval_value` double DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

LOCK TABLES `eval_obj_evaluation` WRITE;
/*!40000 ALTER TABLE `eval_obj_evaluation` DISABLE KEYS */;

INSERT INTO `eval_obj_evaluation` (`id`, `eval_instance`, `eval_obj_code`, `rating`, `eval_value`, `description`, `create_time`, `update_time`)
VALUES
	(19,'evalworkshopi1001','mac1','high',0.6573143658792409,NULL,'2024-03-02 00:02:32','2024-07-16 11:41:21'),
	(20,'evalworkshopi1001','worker1','middle',0.8799999999999999,NULL,'2024-03-02 00:02:32','2024-07-16 11:41:21'),
	(21,'evalworkshopi2002','mac1','middle',0.6,NULL,'2024-03-02 00:02:41','2024-03-02 00:18:20'),
	(22,'evalworkshopi2002','worker1','high',0.32,NULL,'2024-03-02 00:02:41','2024-03-02 00:18:20');

/*!40000 ALTER TABLE `eval_obj_evaluation` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 eval_object
# ------------------------------------------------------------

DROP TABLE IF EXISTS `eval_object`;

CREATE TABLE `eval_object` (
  `id` int NOT NULL AUTO_INCREMENT,
  `eval_obj` varchar(100) DEFAULT NULL,
  `eval_code` varchar(100) NOT NULL,
  `eval_method` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `institution` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `eval_code` (`eval_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

LOCK TABLES `eval_object` WRITE;
/*!40000 ALTER TABLE `eval_object` DISABLE KEYS */;

INSERT INTO `eval_object` (`id`, `eval_obj`, `eval_code`, `eval_method`, `description`, `create_time`, `update_time`, `institution`)
VALUES
	(1,'产品质量','product','模糊评判',NULL,NULL,NULL,'A单位'),
	(2,'xx生产车间','workshop','模糊评判',NULL,NULL,'2023-10-21 15:24:36','A单位'),
	(12,'xx生产车间','xxworkshop',NULL,NULL,'2023-10-21 10:38:15','2023-10-21 10:43:42','XCOPNATION'),
	(13,'','workshopx',NULL,NULL,'2023-10-21 15:34:38','2023-10-21 15:34:38','XCOPNATION');

/*!40000 ALTER TABLE `eval_object` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 eval_rating
# ------------------------------------------------------------

DROP TABLE IF EXISTS `eval_rating`;

CREATE TABLE `eval_rating` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `value` double DEFAULT NULL,
  `version` varchar(45) DEFAULT 'v1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

LOCK TABLES `eval_rating` WRITE;
/*!40000 ALTER TABLE `eval_rating` DISABLE KEYS */;

INSERT INTO `eval_rating` (`id`, `code`, `name`, `description`, `value`, `version`)
VALUES
	(1,'middle','中',NULL,70,'v1'),
	(2,'low','低',NULL,50,'v1'),
	(3,'high','高',NULL,100,'v1'),
	(5,'HighRisk','高风险','',100,'V2'),
	(6,'ReHighRIsk','相对高风险','',80,'V2'),
	(7,'MediumRisk','中风险','',60,'V2'),
	(8,'ReLowRisk','相对低风险','',40,'V2'),
	(9,'LowRisk','低风险','',20,'V2');

/*!40000 ALTER TABLE `eval_rating` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 evalobj_indexmodel
# ------------------------------------------------------------

DROP TABLE IF EXISTS `evalobj_indexmodel`;

CREATE TABLE `evalobj_indexmodel` (
  `id` int NOT NULL AUTO_INCREMENT,
  `eval_instance` varchar(45) DEFAULT NULL,
  `eval_obj_code` varchar(100) NOT NULL,
  `index_model` varchar(100) DEFAULT NULL,
  `institution` varchar(100) DEFAULT NULL,
  `importance_version` varchar(45) DEFAULT NULL COMMENT 'importance matrix version',
  `cluster_function` varchar(45) DEFAULT NULL,
  `rate_version` varchar(45) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `eval_instance_UNIQUE` (`eval_instance`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

LOCK TABLES `evalobj_indexmodel` WRITE;
/*!40000 ALTER TABLE `evalobj_indexmodel` DISABLE KEYS */;

INSERT INTO `evalobj_indexmodel` (`id`, `eval_instance`, `eval_obj_code`, `index_model`, `institution`, `importance_version`, `cluster_function`, `rate_version`, `create_time`, `update_time`)
VALUES
	(1,'evalworkshopi1001','workshop','I1','A','v1','max','v1',NULL,'2024-01-14 21:03:17'),
	(2,'evalworkshopi2002','workshop','I2','A','v2','max','v1',NULL,'2024-03-01 23:33:09');

/*!40000 ALTER TABLE `evalobj_indexmodel` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 evalobj_node_index_value
# ------------------------------------------------------------

DROP TABLE IF EXISTS `evalobj_node_index_value`;

CREATE TABLE `evalobj_node_index_value` (
  `id` int NOT NULL AUTO_INCREMENT,
  `eval_instance` varchar(45) DEFAULT NULL,
  `eval_obj_code` varchar(100) NOT NULL,
  `node_code` varchar(100) NOT NULL,
  `index_code` varchar(100) DEFAULT NULL,
  `input_value` double DEFAULT NULL,
  `pcode` varchar(100) DEFAULT NULL,
  `node_type` varchar(100) DEFAULT NULL,
  `relation_type` varchar(45) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

LOCK TABLES `evalobj_node_index_value` WRITE;
/*!40000 ALTER TABLE `evalobj_node_index_value` DISABLE KEYS */;

INSERT INTO `evalobj_node_index_value` (`id`, `eval_instance`, `eval_obj_code`, `node_code`, `index_code`, `input_value`, `pcode`, `node_type`, `relation_type`, `create_time`, `update_time`)
VALUES
	(35,'evalworkshopi1001','workshop','mac1','MacState',10,'machine','MACHINE','thing','2023-12-17 22:43:21','2024-03-02 00:38:20'),
	(36,'evalworkshopi1001','workshop','mac1','numberOfFailure',7,'machine','MACHINE','thing','2023-12-17 22:43:21','2023-12-18 22:26:29'),
	(37,'evalworkshopi1001','workshop','mac1','LifeTime',15,'machine','MACHINE','thing','2023-12-17 22:43:21','2023-12-18 22:26:34'),
	(38,'evalworkshopi1001','workshop','machine','MacState',NULL,NULL,'MACHINE','thing','2023-12-17 22:43:21','2023-12-17 22:43:21'),
	(39,'evalworkshopi1001','workshop','machine','numberOfFailure',NULL,NULL,'MACHINE','thing','2023-12-17 22:43:21','2023-12-17 22:43:21'),
	(40,'evalworkshopi1001','workshop','machine','LifeTime',NULL,NULL,'MACHINE','thing','2023-12-17 22:43:21','2023-12-17 22:43:21'),
	(41,'evalworkshopi1001','workshop','worker','YearsOld',NULL,NULL,'WORKER','thing','2023-12-17 22:43:21','2023-12-17 22:43:21'),
	(42,'evalworkshopi1001','workshop','worker','Certificate',NULL,NULL,'WORKER','thing','2023-12-17 22:43:21','2023-12-17 22:43:21'),
	(43,'evalworkshopi1001','workshop','worker1','YearsOld',37,'worker','WORKER','thing','2023-12-17 22:43:21','2023-12-18 22:26:37'),
	(44,'evalworkshopi1001','workshop','worker1','Certificate',3,'worker','WORKER','thing','2023-12-17 22:43:21','2024-01-13 00:12:14'),
	(55,'evalworkshopi3004','workshop','mac1','MacState',10,'machine','MACHINE','thing','2024-01-23 10:50:02','2024-01-23 10:50:24'),
	(56,'evalworkshopi3004','workshop','mac1','numberOfFailure',11,'machine','MACHINE','thing','2024-01-23 10:50:02','2024-01-23 10:50:29'),
	(57,'evalworkshopi3004','workshop','mac1','LifeTime',6,'machine','MACHINE','thing','2024-01-23 10:50:02','2024-01-23 10:50:34'),
	(58,'evalworkshopi3004','workshop','machine','MacState',NULL,NULL,'MACHINE','thing','2024-01-23 10:50:02','2024-01-23 10:50:02'),
	(59,'evalworkshopi3004','workshop','machine','numberOfFailure',NULL,NULL,'MACHINE','thing','2024-01-23 10:50:02','2024-01-23 10:50:02'),
	(60,'evalworkshopi3004','workshop','machine','LifeTime',NULL,NULL,'MACHINE','thing','2024-01-23 10:50:02','2024-01-23 10:50:02'),
	(61,'evalworkshopi3004','workshop','worker','YearsOld',NULL,NULL,'WORKER','thing','2024-01-23 10:50:02','2024-01-23 10:50:02'),
	(62,'evalworkshopi3004','workshop','worker','Certificate',NULL,NULL,'WORKER','thing','2024-01-23 10:50:02','2024-01-23 10:50:02'),
	(63,'evalworkshopi3004','workshop','worker1','YearsOld',8,'worker','WORKER','thing','2024-01-23 10:50:02','2024-01-23 10:50:37'),
	(64,'evalworkshopi3004','workshop','worker1','Certificate',3,'worker','WORKER','thing','2024-01-23 10:50:02','2024-01-23 10:50:42'),
	(65,'evalworkshopi2002','workshop','mac1','speed',34,'machine','MACHINE','thing','2024-03-01 23:33:17','2024-03-01 23:33:57'),
	(66,'evalworkshopi2002','workshop','mac1','numberOfFailure',5,'machine','MACHINE','thing','2024-03-01 23:33:17','2024-03-01 23:34:11'),
	(67,'evalworkshopi2002','workshop','machine','speed',NULL,NULL,'MACHINE','thing','2024-03-01 23:33:17','2024-03-01 23:33:17'),
	(68,'evalworkshopi2002','workshop','machine','numberOfFailure',NULL,NULL,'MACHINE','thing','2024-03-01 23:33:17','2024-03-01 23:33:17'),
	(69,'evalworkshopi2002','workshop','worker','YearsOld',NULL,NULL,'WORKER','thing','2024-03-01 23:33:17','2024-03-01 23:33:17'),
	(70,'evalworkshopi2002','workshop','worker','WorkerLevel',NULL,NULL,'WORKER','thing','2024-03-01 23:33:17','2024-03-01 23:33:17'),
	(71,'evalworkshopi2002','workshop','worker1','YearsOld',37,'worker','WORKER','thing','2024-03-01 23:33:17','2024-03-01 23:34:20'),
	(72,'evalworkshopi2002','workshop','worker1','WorkerLevel',4.5,'worker','WORKER','thing','2024-03-01 23:33:17','2024-03-01 23:34:25');

/*!40000 ALTER TABLE `evalobj_node_index_value` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 evalobj_node_link
# ------------------------------------------------------------

DROP TABLE IF EXISTS `evalobj_node_link`;

CREATE TABLE `evalobj_node_link` (
  `id` int NOT NULL AUTO_INCREMENT,
  `eval_obj_code` varchar(100) NOT NULL,
  `node_code` varchar(100) NOT NULL,
  `institution` varchar(100) DEFAULT NULL,
  `node_name` varchar(100) DEFAULT NULL,
  `node_type` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_institution` (`institution`,`node_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

LOCK TABLES `evalobj_node_link` WRITE;
/*!40000 ALTER TABLE `evalobj_node_link` DISABLE KEYS */;

INSERT INTO `evalobj_node_link` (`id`, `eval_obj_code`, `node_code`, `institution`, `node_name`, `node_type`, `create_time`, `update_time`)
VALUES
	(1,'workshop','worker1','A','工人1','WORKER','2023-10-21 13:42:28','2023-10-21 14:12:39'),
	(2,'workshop','mac1','A','机器1','MACHINE','2023-10-21 13:42:28','2023-10-21 14:12:39'),
	(3,'workshop','worker1mac2','A','工人1机器2','WORKER-MACHINE','2023-10-21 13:42:28','2023-10-21 16:04:54'),
	(4,'workshop','worker','A','工人','WORKER','2023-10-21 13:42:28','2023-10-21 14:12:39'),
	(5,'workshop','machine','A','机器','MACHINE','2023-10-21 13:42:28','2023-10-21 14:12:39'),
	(6,'workshop','workermachine','B','工人机器','WORKER-MACHINE','2023-10-21 13:42:28','2023-10-21 13:42:28');

/*!40000 ALTER TABLE `evalobj_node_link` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 index_importance
# ------------------------------------------------------------

DROP TABLE IF EXISTS `index_importance`;

CREATE TABLE `index_importance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `index_first` varchar(45) NOT NULL,
  `index_second` varchar(45) NOT NULL,
  `importance` double NOT NULL COMMENT 'first vs second importance',
  `version` varchar(45) NOT NULL DEFAULT 'v1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

LOCK TABLES `index_importance` WRITE;
/*!40000 ALTER TABLE `index_importance` DISABLE KEYS */;

INSERT INTO `index_importance` (`id`, `index_first`, `index_second`, `importance`, `version`)
VALUES
	(1,'MacState','LifeTime',0.5,'v1'),
	(2,'numberOfFailure','MacState',4,'v1'),
	(4,'Certificate','YearsOld',4,'v1'),
	(5,'LifeTime','MacState',2,'v1'),
	(6,'MacState','numberOfFailure',0.25,'v1'),
	(7,'numberOfFailure','LifeTime',1.25,'v1'),
	(8,'LifeTime','numberOfFailure',0.8,'v1'),
	(9,'MacState','MacState',1,'v1'),
	(10,'numberOfFailure','numberOfFailure',1,'v1'),
	(11,'LifeTime','LifeTime',1,'v1'),
	(12,'YearsOld','Certificate',0.25,'v1'),
	(16,'Certificate','Certificate',1,'v1'),
	(17,'speed','numberOfFailure',0.5,'v2'),
	(19,'YearsOld','YearsOld',1,'v1'),
	(20,'speed','speed',1,'v2'),
	(21,'WorkerLevel','WorkerLevel',1,'v2'),
	(22,'WorkerLevel','YearsOld',0.25,'v2'),
	(23,'numberOfFailure','numberOfFailure',1,'v2'),
	(24,'numberOfFailure','speed',2,'v2'),
	(25,'YearsOld','WorkerLevel',4,'v2'),
	(26,'YearsOld','YearsOld',1,'v2'),
	(27,'YearsOld','YearsOld',1,'V3'),
	(28,'YearsOld','Seniority',0.5,'V3'),
	(29,'YearsOld','CwTime',0.333,'V3'),
	(30,'YearsOld','AccRate',0.25,'V3'),
	(31,'Seniority','YearsOld',2,'V3'),
	(32,'Seniority','Seniority',1,'V3'),
	(33,'Seniority','CwTime',0.5,'V3'),
	(34,'Seniority','AccRate',1,'V3'),
	(35,'CwTime','YearsOld',3,'V3'),
	(36,'CwTime','Seniority',2,'V3'),
	(37,'CwTime','CwTime',1,'V3'),
	(38,'CwTime','AccRate',0.25,'V3'),
	(39,'AccRate','YearsOld',4,'V3'),
	(40,'AccRate','Seniority',1,'V3'),
	(41,'AccRate','CwTime',4,'V3'),
	(42,'AccRate','AccRate',1,'V3'),
	(43,'FailureRate','FailureRate',1,'V3'),
	(44,'FailureRate','RepairTime',0.5,'V3'),
	(45,'FailureRate','EnduranceTime',0.25,'V3'),
	(46,'FailureRate','AccRate',1,'V3');

/*!40000 ALTER TABLE `index_importance` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 index_membership
# ------------------------------------------------------------

DROP TABLE IF EXISTS `index_membership`;

CREATE TABLE `index_membership` (
  `id` int NOT NULL AUTO_INCREMENT,
  `index_code` varchar(100) NOT NULL,
  `rating_code` varchar(100) NOT NULL,
  `membership_function` varchar(100) NOT NULL COMMENT 'three types of function: gauss, triangle, trapezoid',
  `param1` double DEFAULT NULL COMMENT ' param1 refer to the first paramter of the membership function',
  `param2` double DEFAULT NULL,
  `param3` double DEFAULT NULL,
  `param4` double DEFAULT NULL,
  `param5` double DEFAULT NULL,
  `version` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

LOCK TABLES `index_membership` WRITE;
/*!40000 ALTER TABLE `index_membership` DISABLE KEYS */;

INSERT INTO `index_membership` (`id`, `index_code`, `rating_code`, `membership_function`, `param1`, `param2`, `param3`, `param4`, `param5`, `version`)
VALUES
	(1,'LifeTime','high','gauss',8,5,NULL,NULL,NULL,NULL),
	(2,'LifeTime','low','gauss',3,2,NULL,NULL,NULL,NULL),
	(3,'numberOfFailure','high','triangle',4,8,12,NULL,NULL,NULL),
	(4,'numberOfFailure','low','triangle',1,2,4,NULL,NULL,NULL),
	(5,'LifeTime','middle','gauss',6,4,NULL,NULL,NULL,NULL),
	(6,'numberOfFailure','middle','triangle',4,6,8,NULL,NULL,NULL),
	(9,'MacState','high','triangle',8,10,12,NULL,NULL,''),
	(10,'MacState','middle','triangle',6,8,10,NULL,NULL,''),
	(11,'MacState','low','triangle',4,6,8,NULL,NULL,''),
	(12,'YearsOld','high','triangle',35,40,60,NULL,NULL,''),
	(13,'YearsOld','middle','triangle',30,35,40,NULL,NULL,''),
	(14,'YearsOld','low','triangle',18,30,35,NULL,NULL,''),
	(15,'Certificate','high','triangle',3,5,7,NULL,NULL,''),
	(16,'Certificate','middle','triangle',2,3,5,NULL,NULL,''),
	(17,'Certificate','low','triangle',1,2,3,NULL,NULL,''),
	(22,'speed','high','triangle',35,40,60,NULL,NULL,''),
	(23,'speed','middle','triangle',30,35,40,NULL,NULL,''),
	(24,'speed','low','triangle',18,30,35,NULL,NULL,''),
	(25,'WorkerLevel','high','triangle',6,8,10,NULL,NULL,''),
	(26,'WorkerLevel','middle','triangle',5,6,7,NULL,NULL,''),
	(27,'WorkerLevel','low','triangle',4,5,6,NULL,NULL,'');

/*!40000 ALTER TABLE `index_membership` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 node_type
# ------------------------------------------------------------

DROP TABLE IF EXISTS `node_type`;

CREATE TABLE `node_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pid` int DEFAULT NULL,
  `code` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `relation_type` varchar(45) DEFAULT NULL COMMENT 'two types:  relation, thing',
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

LOCK TABLES `node_type` WRITE;
/*!40000 ALTER TABLE `node_type` DISABLE KEYS */;

INSERT INTO `node_type` (`id`, `pid`, `code`, `name`, `relation_type`, `description`)
VALUES
	(1,0,'MACHINE','机器','thing',NULL),
	(2,0,'WORKER','工人','thing',NULL),
	(3,0,'MATERIAL','物料','thing',NULL),
	(4,0,'ENVIRONMENT','环境','thing',NULL),
	(5,0,'PROCEDURE','规则程序','thing',NULL),
	(6,0,'WORKSHOP','车间','thing',NULL),
	(7,0,'WORKER-MACHINE','工人-机器','relation',NULL),
	(8,0,'MACHINE-MATERIAL','机器-物料','relation',NULL),
	(25,NULL,'MACHINE-EN','机器-环境','relation','dd'),
	(26,NULL,'RobticWorker','机器工人','thing',''),
	(27,NULL,'SpinningMachine','纺纱机','thing',''),
	(28,NULL,'WeavingMachine','织造机','thing',''),
	(29,NULL,'DyeingMachine','印染机','thing',''),
	(30,NULL,'HumanWorker','人类工人','thing',''),
	(31,NULL,'Interaction','交互','relation',''),
	(32,NULL,'TransVehicle','运输车','thing','');

/*!40000 ALTER TABLE `node_type` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 node_type_importance
# ------------------------------------------------------------

DROP TABLE IF EXISTS `node_type_importance`;

CREATE TABLE `node_type_importance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `index_first` varchar(45) NOT NULL,
  `index_second` varchar(45) NOT NULL,
  `importance` double NOT NULL COMMENT 'first vs second importance',
  `version` varchar(45) NOT NULL DEFAULT 'v1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

LOCK TABLES `node_type_importance` WRITE;
/*!40000 ALTER TABLE `node_type_importance` DISABLE KEYS */;

INSERT INTO `node_type_importance` (`id`, `index_first`, `index_second`, `importance`, `version`)
VALUES
	(1,'MACHINE','WORKER',0.4,'v1'),
	(2,'MACHINE','ENVIRONMENT',0.6,'v1');

/*!40000 ALTER TABLE `node_type_importance` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 nodetype_index_link
# ------------------------------------------------------------

DROP TABLE IF EXISTS `nodetype_index_link`;

CREATE TABLE `nodetype_index_link` (
  `id` int NOT NULL AUTO_INCREMENT,
  `node_type` varchar(100) NOT NULL,
  `index_code` varchar(100) DEFAULT NULL,
  `version` varchar(45) NOT NULL DEFAULT 'v1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

LOCK TABLES `nodetype_index_link` WRITE;
/*!40000 ALTER TABLE `nodetype_index_link` DISABLE KEYS */;

INSERT INTO `nodetype_index_link` (`id`, `node_type`, `index_code`, `version`)
VALUES
	(1,'MACHINE','LifeTime','v1'),
	(2,'MACHINE','numberOfFailure','v1'),
	(3,'WORKER','Certificate','v1'),
	(4,'WORKER','YearsOld','v1'),
	(5,'MACHINE','LifeTime','v2'),
	(6,'WORKER','Certificate','v2'),
	(7,'WORKER','YearsOld','v2');

/*!40000 ALTER TABLE `nodetype_index_link` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 obj_node_type_evaluation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `obj_node_type_evaluation`;

CREATE TABLE `obj_node_type_evaluation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `eval_instance` varchar(45) DEFAULT NULL,
  `eval_obj_code` varchar(100) NOT NULL,
  `node_type` varchar(100) NOT NULL,
  `rating` varchar(50) DEFAULT NULL,
  `eval_value` double DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

LOCK TABLES `obj_node_type_evaluation` WRITE;
/*!40000 ALTER TABLE `obj_node_type_evaluation` DISABLE KEYS */;

INSERT INTO `obj_node_type_evaluation` (`id`, `eval_instance`, `eval_obj_code`, `node_type`, `rating`, `eval_value`, `description`, `create_time`, `update_time`)
VALUES
	(1,'evalworkshopi1001','workshop','MACHINE','middle',1,NULL,'2023-10-21 17:27:06','2023-10-21 17:29:19'),
	(2,'evalworkshopi1001','workshop','MACHINE','low',0.32465246735834974,NULL,'2023-10-21 17:27:06','2023-10-21 17:29:19'),
	(3,'evalworkshopi1001','workshop','WORKER','high',0.88,NULL,'2023-10-21 17:27:06','2023-10-21 17:29:19'),
	(4,'evalworkshopi1001','workshop','MACHINE','high',0.9231163463866358,NULL,'2023-10-21 17:27:06','2023-10-21 17:29:19');

/*!40000 ALTER TABLE `obj_node_type_evaluation` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_dict_data
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_dict_data`;

CREATE TABLE `sys_dict_data` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `dict_sort` int DEFAULT NULL COMMENT '显示顺序',
  `dict_label` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '字典标签',
  `dict_value` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '字典键值',
  `dict_type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '字典类型',
  `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;

INSERT INTO `sys_dict_data` (`id`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `remark`, `create_time`)
VALUES
	(1,1,'男','0','user_sex','',NULL),
	(2,2,'女','1','user_sex','',NULL),
	(3,1,'sdf','fgfd','check_status','',NULL),
	(4,1,'video/mp4','0','sys_video','',NULL),
	(5,2,'m3u8','1','sys_video','',NULL);

/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_dict_type
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_dict_type`;

CREATE TABLE `sys_dict_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dict_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '字典名称',
  `dict_type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '字典类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type` DISABLE KEYS */;

INSERT INTO `sys_dict_type` (`id`, `dict_name`, `dict_type`, `create_time`, `remark`)
VALUES
	(1,'用户性别','user_sex','2021-07-28 17:58:16',''),
	(3,'开关状态','check_status','2021-07-29 09:52:36',''),
	(4,'视频类型','sys_video_type','2022-09-07 16:36:55','');

/*!40000 ALTER TABLE `sys_dict_type` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_menu
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `path` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '#' COMMENT '访问地址',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '菜单名称',
  `parent_id` bigint DEFAULT NULL COMMENT '父级菜单',
  `icon` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '菜单图标',
  `type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '权限标识',
  `perms` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '权限标识',
  `sort` int DEFAULT NULL COMMENT '显示顺序',
  `iframe` int DEFAULT NULL COMMENT '是否为内置页面',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='菜单列表';

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;

INSERT INTO `sys_menu` (`id`, `path`, `name`, `parent_id`, `icon`, `type`, `perms`, `sort`, `iframe`)
VALUES
	(3,'dd','系统管理',0,'Setting','0',NULL,2,0),
	(4,'system/Menus','菜单管理',3,'Menu','1',NULL,1,0),
	(5,'system/Roles','角色管理',3,'HelpFilled','1','sys:role',2,0),
	(33,'system/Users','用户管理',3,'UserFilled','1','system:user:list',3,0),
	(41,'base','基础管理',0,'Calendar','0','',4,0),
	(42,'blacklist','黑名单记录',41,'ios-aperture','0','base:black:list',1,0),
	(45,'system/DictType','字典管理',3,'DataAnalysis','1','sys:dict:list',4,0),
	(46,'blacklist','白名单记录',42,'ios-aperture','1','base:black:list',1,0),
	(52,'genCode','代码生成',3,'Menu','1','ss',1,0),
	(54,'personCenter','个人中心',0,'Stamp','1','sd',3,0),
	(56,'','新增',5,'Menu','2','sys:role:add',1,0),
	(61,'','测试目录',0,'FullScreen','0','',6,0),
	(63,'','编辑',5,'Menu','2','sys:role:edit',1,0),
	(64,'system/SysTest-ele','测试管理',61,'TakeawayBox','1','',1,0),
	(65,'','新增',64,'Menu','2','sysy',1,0),
	(66,'','删除不',64,'Menu','2','222',1,0),
	(67,'','sdf',46,'Menu','2','岁的法国',1,0),
	(68,'','是德国',46,'Menu','2','dfg',1,0),
	(69,'system/SysVideo','视频管理',61,'View','1','sss',1,0),
	(70,'','态势评估',0,'Menu','0','',1,0),
	(71,'eval/evalObjMgmt','评估对象管理',70,'Avatar','1','',6,0),
	(72,'eval/nodeDef','评估节点管理',70,'Aim','1','',3,0),
	(73,'eval/indexDef','评估指标管理',70,'Compass','1','',4,0),
	(75,'eval/commitLevelDef','评级评语设计',70,'CircleCheck','1','',5,0),
	(77,'eval/evalResult','模型评价输出',70,'CircleCheck','1',NULL,9,0),
	(78,'eval/nodeTypeDef','节点类型设计',70,'Menu','1','',2,0),
	(79,'eval/evalObjNode','评估对象节点',70,'Menu','1','',7,0),
	(80,'eval/indexMenbership','隶属度参数',70,'Menu','1','',5,0),
	(81,'eval/indexImportance','指标权重设置',70,'Menu','1','',4,0),
	(83,'eval/evalNodeIndex','评估节点指标',70,'Menu','1','',7,0),
	(85,'eval/configFileInput','上游系统接口',70,'Menu','1','',8,0),
	(86,'eval/indexModelSet','指标模型设计',70,'Menu','1','',4,0),
	(87,'eval/evalInstanceDef','评估实例管理',70,'Menu','1','',6,0);

/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_menu_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_menu_role`;

CREATE TABLE `sys_menu_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint DEFAULT NULL,
  `menu_ids` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `select_ids` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='菜单-权限关系表';

LOCK TABLES `sys_menu_role` WRITE;
/*!40000 ALTER TABLE `sys_menu_role` DISABLE KEYS */;

INSERT INTO `sys_menu_role` (`id`, `role_id`, `menu_ids`, `select_ids`)
VALUES
	(5,5,'3,4,5',NULL),
	(13,4,'1,4',NULL),
	(15,2,'56,65,70,71,72,73,75,76,77,5,3,64,61,0','56,65,70,71,72,73,75,76,77'),
	(22,3,'7,8,10,4,9',NULL),
	(23,6,'5,3','5'),
	(24,11,'4,5,7,3,6','4,5,7'),
	(25,11,'4,5,33,3','4,5,33'),
	(26,12,'4,5,33,3','4,5,33'),
	(27,13,'5,33,45,3','5,33,45'),
	(28,14,'4,5,33,45,3','4,5,33,45'),
	(29,15,'33,3','33'),
	(30,16,'45,50,3','45,50'),
	(31,17,'33,45,3','33,45'),
	(32,18,'33,50,3','33,50'),
	(40,38,'61,62,0','61,62'),
	(41,9,'61,64,65,66,69,70,71,72,73,75,76,77,41,42,46,67,68,0','61,64,65,66,69,70,71,72,73,75,76,77,41,42,46,67,68');

/*!40000 ALTER TABLE `sys_menu_role` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `role_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '角色名称',
  `role_key` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '角色权限字符串',
  `role_sort` int DEFAULT '0' COMMENT '显示顺序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='权限表';

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;

INSERT INTO `sys_role` (`id`, `role_name`, `role_key`, `role_sort`, `create_time`, `remark`)
VALUES
	(1,'超级管理员','ROLE_SUPER_ADMIN',1,'2021-07-22 09:18:20',NULL),
	(2,'人事专员','ROLE_PERSON',3,'2021-07-21 09:18:23',NULL),
	(3,'招聘主管','ROLE_recruiter',4,'2021-07-07 09:18:28',NULL),
	(4,'培训主管','ROLE_train',5,'2021-07-13 09:18:30',NULL),
	(5,'薪酬绩效主管','ROLE_performance',6,'2021-07-11 09:18:35',NULL),
	(9,'管理员','ADMIN',7,'2021-07-22 11:13:41','');

/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_test
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_test`;

CREATE TABLE `sys_test` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '名字',
  `age` int DEFAULT NULL COMMENT '年龄',
  `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='测试生成表';

LOCK TABLES `sys_test` WRITE;
/*!40000 ALTER TABLE `sys_test` DISABLE KEYS */;

INSERT INTO `sys_test` (`id`, `name`, `age`, `remark`)
VALUES
	(1,'asd',1,'士大夫');

/*!40000 ALTER TABLE `sys_test` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户名（手机号）',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '密码',
  `role` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;

INSERT INTO `sys_user` (`id`, `name`, `password`, `role`, `remark`, `create_time`)
VALUES
	(1,'admin','$2a$10$hGHXhwZvHa3cPSBcsw5l2ON.o70rw7r6kT7pZqxZa1dieMnVEl2HC','1,2','超级管理员','2021-07-21 16:25:07'),
	(6,'user','$2a$10$9js5P2t54NJtWieJ.3ZO1.8blkugIVqAsRVGFctIBeNvc45vx9EF.','2',NULL,'2021-07-09 16:25:10'),
	(25,'user2','$2a$10$tOa3NVUWoM1z5EFQl2i3vec4R.IDExsm7GTcVw3ikxc21QOpYfpsu','3','','2022-05-24 08:20:07'),
	(27,'tzk','$2a$10$oWOGsfBLP4eY1uzsfelYseib7uZ.UaUkDOom.d.KAJuiTmj7ugs9u','2','1','2023-10-05 09:36:59');

/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 sys_video
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sys_video`;

CREATE TABLE `sys_video` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '摄像头名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '播放地址',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '位置',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `type` int DEFAULT NULL COMMENT '视频类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

LOCK TABLES `sys_video` WRITE;
/*!40000 ALTER TABLE `sys_video` DISABLE KEYS */;

INSERT INTO `sys_video` (`id`, `name`, `url`, `address`, `remark`, `type`, `create_time`)
VALUES
	(1,'测试设备1','https://cdn.jsdelivr.net/gh/xdlumia/files/video-play/IronMan.mp4','公司测试','',NULL,'2022-09-07 00:00:00');

/*!40000 ALTER TABLE `sys_video` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
