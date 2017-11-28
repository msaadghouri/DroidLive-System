-- MySQL dump 10.13  Distrib 5.7.12, for osx10.9 (x86_64)
--
-- Host: 127.0.0.1    Database: arkansas
-- ------------------------------------------------------
-- Server version	5.7.17

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
-- Table structure for table `AccountHolders`
--

DROP TABLE IF EXISTS `AccountHolders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AccountHolders` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(100) NOT NULL,
  `EmailID` varchar(100) NOT NULL,
  `KEYMD5` varchar(225) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UserName_UNIQUE` (`UserName`),
  UNIQUE KEY `KEY_UNIQUE` (`KEYMD5`),
  UNIQUE KEY `EmailID_UNIQUE` (`EmailID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BrowserTable`
--

DROP TABLE IF EXISTS `BrowserTable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BrowserTable` (
  `BrowserId` int(11) NOT NULL AUTO_INCREMENT,
  `UserRefId` varchar(225) NOT NULL,
  `URLName` longtext,
  `SearchedDate` datetime DEFAULT NULL,
  `DomainName` varchar(225) DEFAULT NULL,
  `SearchedText` varchar(225) DEFAULT NULL,
  `CreatedDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `TransactionID` int(20) DEFAULT NULL,
  PRIMARY KEY (`BrowserId`),
  KEY `browserRef` (`UserRefId`),
  KEY `browserTrans` (`TransactionID`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CallLogsTable`
--

DROP TABLE IF EXISTS `CallLogsTable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CallLogsTable` (
  `CallId` int(11) NOT NULL AUTO_INCREMENT,
  `UserRefId` varchar(225) NOT NULL,
  `CallLogID` int(11) DEFAULT NULL,
  `ContactName` varchar(225) DEFAULT NULL,
  `ContactNumber` varchar(45) DEFAULT NULL,
  `CallDuration` int(11) DEFAULT NULL,
  `CallDate` datetime DEFAULT NULL,
  `CallType` varchar(45) DEFAULT NULL,
  `CountryISO` varchar(45) DEFAULT NULL,
  `CreatedDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `TransactionID` int(20) DEFAULT NULL,
  PRIMARY KEY (`CallId`),
  KEY `call_userRef` (`UserRefId`),
  KEY `call_TRANSID` (`TransactionID`)
) ENGINE=InnoDB AUTO_INCREMENT=2153 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ContactsTable`
--

DROP TABLE IF EXISTS `ContactsTable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ContactsTable` (
  `ContID` int(11) NOT NULL AUTO_INCREMENT,
  `UserRefId` varchar(45) NOT NULL,
  `ContactID` int(11) DEFAULT NULL,
  `PhoneName` varchar(45) DEFAULT NULL,
  `PhoneNumber` varchar(225) DEFAULT NULL,
  `CreatedDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `TransactionID` int(20) DEFAULT NULL,
  PRIMARY KEY (`ContID`),
  KEY `cont_UserID` (`UserRefId`),
  KEY `cont_transID` (`TransactionID`)
) ENGINE=InnoDB AUTO_INCREMENT=1534 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DeviceAccount`
--

DROP TABLE IF EXISTS `DeviceAccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DeviceAccount` (
  `DeviceAccId` int(11) NOT NULL AUTO_INCREMENT,
  `UserRefId` varchar(225) NOT NULL,
  `AccountName` varchar(225) DEFAULT NULL,
  `AccountType` varchar(225) DEFAULT NULL,
  `CreatedDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `TransactionID` int(20) DEFAULT NULL,
  PRIMARY KEY (`DeviceAccId`),
  KEY `acc_userRef` (`UserRefId`),
  KEY `acc_TransID` (`TransactionID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DiscoveryTable`
--

DROP TABLE IF EXISTS `DiscoveryTable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DiscoveryTable` (
  `DiscoveryId` int(11) NOT NULL AUTO_INCREMENT,
  `UserRefId` varchar(225) NOT NULL,
  `System` mediumtext,
  `Node` mediumtext,
  `ReleaseForm` mediumtext,
  `Version` mediumtext,
  `Machine` mediumtext,
  `Kernel` mediumtext,
  `Fqdn` mediumtext,
  `InstallDate` mediumtext,
  `ClientName` longtext,
  `ClientVersion` varchar(45) DEFAULT NULL,
  `BuildTime` datetime DEFAULT NULL,
  `ClientDescription` longtext,
  `MacAddress` mediumtext,
  `Ipv4` mediumtext,
  `Ipv6` mediumtext,
  `CreatedDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `TransactionID` int(20) DEFAULT NULL,
  PRIMARY KEY (`DiscoveryId`),
  KEY `discovery_userRef_id_idx` (`UserRefId`),
  KEY `discovery_trans_id_idx` (`TransactionID`),
  CONSTRAINT `discovery_trans_id` FOREIGN KEY (`TransactionID`) REFERENCES `RequestTable` (`TransactionID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `discovery_userRef_id` FOREIGN KEY (`UserRefId`) REFERENCES `User_Table` (`UserRefId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `RequestTable`
--

DROP TABLE IF EXISTS `RequestTable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RequestTable` (
  `TransactionID` int(20) NOT NULL,
  `UserRefId` varchar(225) DEFAULT NULL,
  `FCMRegId` varchar(225) DEFAULT NULL,
  `FlowName` varchar(225) DEFAULT NULL,
  `FlowDate` datetime DEFAULT NULL,
  `RequestStatus` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`TransactionID`),
  KEY `userIDIndex` (`UserRefId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SMSTable`
--

DROP TABLE IF EXISTS `SMSTable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SMSTable` (
  `TableID` int(11) NOT NULL AUTO_INCREMENT,
  `UserRefId` varchar(45) NOT NULL,
  `SMSID` int(11) DEFAULT NULL,
  `SMSAddress` varchar(225) DEFAULT NULL,
  `SMSDate` datetime DEFAULT NULL,
  `SMSAction` varchar(45) DEFAULT NULL,
  `SMSBody` longtext,
  `CreatedDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `TransactionID` int(20) DEFAULT NULL,
  PRIMARY KEY (`TableID`),
  KEY `sms_UserID` (`UserRefId`),
  KEY `sms_transID` (`TransactionID`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `UsageStats`
--

DROP TABLE IF EXISTS `UsageStats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UsageStats` (
  `UsageStatsID` int(11) NOT NULL AUTO_INCREMENT,
  `UserRefId` varchar(225) NOT NULL,
  `UsageStatsData` longtext,
  `CreatedDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `TransactionID` int(20) DEFAULT NULL,
  PRIMARY KEY (`UsageStatsID`),
  KEY `stats_userRef` (`UserRefId`),
  KEY `stats_transID` (`TransactionID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `User_Table`
--

DROP TABLE IF EXISTS `User_Table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User_Table` (
  `UserRefId` varchar(225) NOT NULL,
  `UserName` mediumtext NOT NULL,
  `FullName` longtext,
  `FirstLogOn` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `LastLogOn` datetime DEFAULT NULL,
  `HomeDirectory` longtext,
  `FCMRegId` longtext NOT NULL,
  PRIMARY KEY (`UserRefId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-28 12:25:06
