

SET FOREIGN_KEY_CHECKS=0;



-- ----------------------------
-- Table structure for dq1401
-- ----------------------------
DROP TABLE IF EXISTS `dq1401`;
CREATE TABLE `dq1401` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `sex` varchar(20) NOT NULL,
  `description` longtext,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL,
  `CONTENT` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `CREATETIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2549 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `mid` int(11) NOT NULL,
  `content` text,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `rid` bigint(20) DEFAULT NULL,
  `replies_ORDER` int(11) DEFAULT NULL,
  `hid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgtf56ne67pnafdl081u47h8c3` (`uid`),
  KEY `FKl72fpucyqt3vg8sv3agwm6nbn` (`rid`),
  KEY `FK9en9up1t73wvm2fwhtymkff1a` (`hid`),
  CONSTRAINT `FK9en9up1t73wvm2fwhtymkff1a` FOREIGN KEY (`hid`) REFERENCES `message` (`ID`),
  CONSTRAINT `FKgtf56ne67pnafdl081u47h8c3` FOREIGN KEY (`uid`) REFERENCES `dq1401` (`id`),
  CONSTRAINT `FKl72fpucyqt3vg8sv3agwm6nbn` FOREIGN KEY (`rid`) REFERENCES `message` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `isscored` int(11) NOT NULL DEFAULT '0',
  `tzs_1` int(11) DEFAULT NULL,
  `tzs_2` int(11) DEFAULT NULL,
  `tzs_3` int(11) DEFAULT NULL,
  `tzs_4` int(11) DEFAULT NULL,
  `tzs_5` int(11) DEFAULT NULL,
  `bz_1` int(11) DEFAULT NULL,
  `bz_2` int(11) DEFAULT NULL,
  `bz_3` int(11) DEFAULT NULL,
  `bz_4` int(11) DEFAULT NULL,
  `bz_5` int(11) DEFAULT NULL,
  `dxwy_1` int(11) DEFAULT NULL,
  `dxwy_2` int(11) DEFAULT NULL,
  `dxwy_3` int(11) DEFAULT NULL,
  `dxwy_4` int(11) DEFAULT NULL,
  `dxwy_5` int(11) DEFAULT NULL,
  `xxwy_1` int(11) DEFAULT NULL,
  `xxwy_2` int(11) DEFAULT NULL,
  `xxwy_3` int(11) DEFAULT NULL,
  `xxwy_4` int(11) DEFAULT NULL,
  `xxwy_5` int(11) DEFAULT NULL,
  `wtwy_1` int(11) DEFAULT NULL,
  `wtwy_2` int(11) DEFAULT NULL,
  `wtwy_3` int(11) DEFAULT NULL,
  `wtwy_4` int(11) DEFAULT NULL,
  `wtwy_5` int(11) DEFAULT NULL,
  `xlwy_1` int(11) DEFAULT NULL,
  `xlwy_2` int(11) DEFAULT NULL,
  `xlwy_3` int(11) DEFAULT NULL,
  `xlwy_4` int(11) DEFAULT NULL,
  `xlwy_5` int(11) DEFAULT NULL,
  `shwy_1` int(11) DEFAULT NULL,
  `shwy_2` int(11) DEFAULT NULL,
  `shwy_3` int(11) DEFAULT NULL,
  `shwy_4` int(11) DEFAULT NULL,
  `shwy_5` int(11) DEFAULT NULL,
  `zzwy_1` int(11) DEFAULT NULL,
  `zzwy_2` int(11) DEFAULT NULL,
  `zzwy_3` int(11) DEFAULT NULL,
  `zzwy_4` int(11) DEFAULT NULL,
  `zzwy_5` int(11) DEFAULT NULL,
  `evaluate` text,
  PRIMARY KEY (`id`),
  KEY `FKgf2ktsuw60n5koi4eat5fu37c` (`uid`),
  CONSTRAINT `FKgf2ktsuw60n5koi4eat5fu37c` FOREIGN KEY (`uid`) REFERENCES `dq1401` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sh_account
-- ----------------------------
DROP TABLE IF EXISTS `sh_account`;
CREATE TABLE `sh_account` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(255) DEFAULT NULL,
  `BALANCE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sh_book
-- ----------------------------
DROP TABLE IF EXISTS `sh_book`;
CREATE TABLE `sh_book` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `BOOKNAME` varchar(255) DEFAULT NULL,
  `ISBN` varchar(255) DEFAULT NULL,
  `PRICE` int(11) DEFAULT NULL,
  `STOCK` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for ssh_department
-- ----------------------------
DROP TABLE IF EXISTS `ssh_department`;
CREATE TABLE `ssh_department` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DEPARTMENTNAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ssh_employee
-- ----------------------------
DROP TABLE IF EXISTS `ssh_employee`;
CREATE TABLE `ssh_employee` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `LASTNAME` varchar(255) DEFAULT NULL,
  `EMIAL` varchar(255) DEFAULT NULL,
  `BIRTH` datetime DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DEPARTMENT_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK43d3ep06g2cqvdx0wwqlqnoi1` (`DEPARTMENT_ID`),
  CONSTRAINT `FK43d3ep06g2cqvdx0wwqlqnoi1` FOREIGN KEY (`DEPARTMENT_ID`) REFERENCES `ssh_department` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for students
-- ----------------------------
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students` (
  `username` char(30) NOT NULL,
  `password` char(30) NOT NULL,
  `name` char(30) NOT NULL,
  `sex` char(10) NOT NULL DEFAULT 'man'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
