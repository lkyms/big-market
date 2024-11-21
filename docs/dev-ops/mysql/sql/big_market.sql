# ************************************************************
# Sequel Ace SQL dump
# 版本号： 20077
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# 主机: localhost (MySQL 8.0.32)
# 数据库: big_market
# 生成时间: 2024-11-21 05:36:32 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE='NO_AUTO_VALUE_ON_ZERO', SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# 转储表 award
# ------------------------------------------------------------

DROP TABLE IF EXISTS `award`;

CREATE TABLE `award` (
                         `id` int unsigned NOT NULL AUTO_INCREMENT,
                         `award_id` int NOT NULL,
                         `award_key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `award_config` varchar(128) NOT NULL,
                         `award_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                         `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `award` WRITE;
/*!40000 ALTER TABLE `award` DISABLE KEYS */;

INSERT INTO `award` (`id`, `award_id`, `award_key`, `award_config`, `award_desc`, `create_time`, `update_time`)
VALUES
    (1,101,'user_credit_random','1,100','用户积分','2024-11-21 13:14:21','2024-11-21 13:14:21'),
    (2,102,'openai_use_count','5','增加使用次数','2024-11-21 13:14:56','2024-11-21 13:14:56'),
    (3,103,'openai_use_count','10','增加使用次数','2024-11-21 13:14:56','2024-11-21 13:15:12'),
    (4,104,'openai_use_count','20','增加使用次数','2024-11-21 13:14:56','2024-11-21 13:15:14'),
    (5,105,'openai_model','gpt-e','增加模型','2024-11-21 13:14:56','2024-11-21 13:16:42'),
    (6,106,'openai_model','dall-e-2','增加模型','2024-11-21 13:14:56','2024-11-21 13:16:46'),
    (7,107,'openai_model','dall-e-3','增加模型','2024-11-21 13:14:56','2024-11-21 13:17:41'),
    (8,108,'openai_use_count','100','增加使用次数','2024-11-21 13:14:56','2024-11-21 13:17:01'),
    (9,109,'openai_model','gpt-4,dall-e-2,dall-e-3','增加模型','2024-11-21 13:17:38','2024-11-21 13:17:38');

/*!40000 ALTER TABLE `award` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 strategy
# ------------------------------------------------------------

DROP TABLE IF EXISTS `strategy`;

CREATE TABLE `strategy` (
                            `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
                            `strategy_id` int NOT NULL COMMENT '抽奖策略id',
                            `strategy_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '抽奖策略描述',
                            `rule_models` varchar(256) DEFAULT NULL COMMENT '策略模型',
                            `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '抽奖时间',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `strategy` WRITE;
/*!40000 ALTER TABLE `strategy` DISABLE KEYS */;

INSERT INTO `strategy` (`id`, `strategy_id`, `strategy_desc`, `rule_models`, `create_time`, `update_time`)
VALUES
    (1,10001,'抽奖策略A',NULL,'2024-11-20 17:28:22','2024-11-20 17:28:22');

/*!40000 ALTER TABLE `strategy` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 strategy_award
# ------------------------------------------------------------

DROP TABLE IF EXISTS `strategy_award`;

CREATE TABLE `strategy_award` (
                                  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
                                  `strategy_id` int NOT NULL COMMENT '抽奖策略id',
                                  `award_id` int NOT NULL COMMENT '奖品id',
                                  `award_title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '抽奖标题',
                                  `award_subtitle` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '抽奖副标题',
                                  `award_count` int NOT NULL COMMENT '奖品总量',
                                  `award_count_surplus` int NOT NULL COMMENT '奖品剩余量',
                                  `award_rate` decimal(6,4) NOT NULL COMMENT '奖品中奖概率',
                                  `rule_models` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '规则模型，rule规则配置记录',
                                  `sort` int NOT NULL DEFAULT '0' COMMENT '排序',
                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `strategy_award` WRITE;
/*!40000 ALTER TABLE `strategy_award` DISABLE KEYS */;

INSERT INTO `strategy_award` (`id`, `strategy_id`, `award_id`, `award_title`, `award_subtitle`, `award_count`, `award_count_surplus`, `award_rate`, `rule_models`, `sort`, `create_time`, `update_time`)
VALUES
    (1,10001,101,'随机积分',NULL,80000,80000,80.0000,'rule_random,rule_luck_award',1,'2024-11-20 17:29:31','2024-11-21 13:09:39'),
    (2,10001,102,'5次使用',NULL,10000,10000,10.0000,'rule_luck_award',2,'2024-11-20 17:30:13','2024-11-21 13:09:54'),
    (3,10001,103,'10次使用',NULL,5000,5000,5.0000,'rule_luck_award',3,'2024-11-20 17:30:13','2024-11-21 13:09:54'),
    (4,10001,104,'20次使用',NULL,4000,4000,4.0000,'rule_luck_award',4,'2024-11-20 17:30:13','2024-11-21 13:09:55'),
    (5,10001,105,'增加gpt-4对话模型',NULL,600,600,0.6000,'rule_luck_award',5,'2024-11-20 17:30:13','2024-11-21 13:09:56'),
    (6,10001,106,'增加dall-e-2画图模型',NULL,200,200,0.2000,'rule_luck_award',6,'2024-11-20 17:30:13','2024-11-21 13:09:57'),
    (7,10001,107,'增加dall-e-3画图模型','抽奖1次后解锁',200,200,0.2000,'rule_lock,rule_luck_award',7,'2024-11-20 17:30:13','2024-11-21 13:10:18'),
    (8,10001,108,'增加100次使用','抽奖2次后解锁',199,199,0.1999,'rule_lock,rule_luck_award',8,'2024-11-20 17:30:13','2024-11-21 13:10:16'),
    (9,10001,109,'解锁全部模型','抽奖6次后解锁',1,1,0.0001,'rule_lock,rule_luck_award',9,'2024-11-20 17:30:13','2024-11-21 13:10:21');

/*!40000 ALTER TABLE `strategy_award` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 strategy_rule
# ------------------------------------------------------------

DROP TABLE IF EXISTS `strategy_rule`;

CREATE TABLE `strategy_rule` (
                                 `id` int unsigned NOT NULL AUTO_INCREMENT,
                                 `strategy_id` int NOT NULL COMMENT '策略id',
                                 `award_id` int DEFAULT NULL COMMENT '奖品id',
                                 `rule_type` int NOT NULL DEFAULT '0' COMMENT '抽奖规则类型【1-策略规则、2-奖品规则】',
                                 `rule_model` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '抽奖规则类型',
                                 `rule_value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '抽奖规则比值',
                                 `rule_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '抽奖规则描述',
                                 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `strategy_rule` WRITE;
/*!40000 ALTER TABLE `strategy_rule` DISABLE KEYS */;

INSERT INTO `strategy_rule` (`id`, `strategy_id`, `award_id`, `rule_type`, `rule_model`, `rule_value`, `rule_desc`, `create_time`, `update_time`)
VALUES
    (1,10001,101,2,'rule_random','1,1000','随机积分策略','2024-11-20 17:36:00','2024-11-20 17:36:00'),
    (2,10001,107,2,'rule_lock','1','抽奖1次后解锁','2024-11-20 17:36:00','2024-11-20 17:36:00'),
    (3,10001,108,2,'rule_lock','2','抽奖2次后解锁','2024-11-20 17:36:00','2024-11-20 17:36:00'),
    (4,10001,109,2,'rule_lock','6','抽奖6次后解锁','2024-11-20 17:36:00','2024-11-20 17:36:00'),
    (5,10001,107,2,'rule_luck_award','1,1000','随机积分兜底','2024-11-20 17:36:00','2024-11-20 17:36:00'),
    (6,10001,NULL,1,'rule_weight','6000:102,103,104,105,106,107,108,109','随机积分兜底','2024-11-20 17:36:00','2024-11-20 17:43:51'),
    (7,10001,NULL,1,'rule_backlist','1','黑名单用户，1积分兜底','2024-11-20 17:36:00','2024-11-20 17:43:51');

/*!40000 ALTER TABLE `strategy_rule` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
