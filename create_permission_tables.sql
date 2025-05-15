-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: webnoithat-webnoithat.j.aivencloud.com    Database: qlksjava
-- ------------------------------------------------------
-- Server version	8.0.35

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '7223d7f1-24d0-11f0-909e-1ac79ba5889f:1-277,
d009cb70-2c21-11f0-84f9-862ccfb06db6:1-534';

--
-- Table structure for table `check_in`
--

DROP TABLE IF EXISTS `check_in`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `check_in` (
                            `ma_checkin` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
                            `ma_phieu` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                            `ma_hop_dong` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
                            `ngay_checkin` datetime DEFAULT CURRENT_TIMESTAMP,
                            `nhan_vien` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
                            `ghi_chu` text COLLATE utf8mb4_general_ci,
                            `ma_nguon` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
                            `loai_nguon` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
                            PRIMARY KEY (`ma_checkin`),
                            KEY `ma_phieu` (`ma_phieu`),
                            CONSTRAINT `check_in_ibfk_1` FOREIGN KEY (`ma_phieu`) REFERENCES `phieu_dat_phong` (`ma_phieu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check_in`
--

LOCK TABLES `check_in` WRITE;
/*!40000 ALTER TABLE `check_in` DISABLE KEYS */;
INSERT INTO `check_in` VALUES ('CI123327',NULL,NULL,'2025-05-12 06:12:03','NV001','Check-in theo lịch','PDPE2F01','phieu'),('CI133957',NULL,NULL,'2025-05-12 22:35:33','NV001','Check-in theo lịch','PDP5D5EB','phieu'),('CI137181',NULL,NULL,'2025-05-15 13:38:57','NV001','Check-in theo lịch (hợp đồng)','HD4E195A60','hop_dong'),('CI205429','PDP8D779',NULL,'2025-05-11 23:50:05','NV001','Check-in theo lịch',NULL,NULL),('CI357333',NULL,NULL,'2025-05-12 05:42:37','NV001','Check-in theo lịch','PDP10A5D','phieu'),('CI395296',NULL,NULL,'2025-05-12 05:43:15','NV001','Check-in theo lịch (hợp đồng)','HD0EA30DB7','hop_dong'),('CI409536',NULL,NULL,'2025-05-12 16:16:49','NV001','Check-in theo lịch','PDP4434A','phieu'),('CI410782',NULL,NULL,'2025-05-12 05:43:30','NV001','Check-in theo lịch','PDPDC855','phieu'),('CI416974',NULL,NULL,'2025-05-12 05:43:36','NV001','Check-in theo lịch','PDP875F1','phieu'),('CI570020',NULL,NULL,'2025-05-14 16:56:10','NV001','Check-in theo lịch','PDP27BC4','phieu'),('CI578154',NULL,NULL,'2025-05-15 02:22:58','NV001','Check-in theo lịch','PDP111A2','phieu'),('CI598619',NULL,NULL,'2025-05-14 16:56:38','NV001','Check-in theo lịch','PDP46774','phieu'),('CI612233',NULL,NULL,'2025-05-15 02:23:32','NV001','Check-in theo lịch','PDP27B57','phieu'),('CI615428',NULL,NULL,'2025-05-14 16:56:55','NV001','Check-in theo lịch','PDP11C66','phieu'),('CI621664',NULL,NULL,'2025-05-15 02:23:41','NV001','Check-in theo lịch','PDP413F7','phieu'),('CI638543',NULL,NULL,'2025-05-12 16:20:38','NV001','Check-in theo lịch','PDP0B664','phieu'),('CI643390',NULL,NULL,'2025-05-14 16:57:23','NV001','Check-in theo lịch','PDP5DA4B','phieu'),('CI647433',NULL,NULL,'2025-05-12 06:04:07','NV001','Check-in theo lịch','PDPE49D9','phieu'),('CI651159',NULL,NULL,'2025-05-15 02:24:11','NV001','Check-in theo lịch','PDP41B16','phieu'),('CI656432',NULL,NULL,'2025-05-12 06:04:16','NV001','Check-in theo lịch','PDP1D4D7','phieu'),('CI664372',NULL,NULL,'2025-05-12 06:04:24','NV001','Check-in theo lịch','PDPF8FD7','phieu'),('CI670119',NULL,NULL,'2025-05-12 06:04:30','NV001','Check-in theo lịch','PDP68DB0','phieu'),('CI679842',NULL,NULL,'2025-05-15 02:24:39','NV001','Check-in theo lịch','PDP8D8BD','phieu'),('CI700585',NULL,NULL,'2025-05-15 02:25:00','NV001','Check-in theo lịch','PDPF0EE9','phieu'),('CI7113','PDP695F2',NULL,'2025-05-12 02:16:47','NV001','Check-in theo lịch',NULL,NULL),('CI896450',NULL,NULL,'2025-05-12 02:31:36','NV001','Check-in theo lịch (check-in sớm) (hợp đồng)','HD82822EB2','hop_dong'),('CI94206','PDP3C5AF',NULL,'2025-05-11 01:51:34','NV001','Check-in sớm',NULL,NULL),('CI974781',NULL,NULL,'2025-05-12 15:36:14','NV001','Check-in theo lịch','PDP8440B','phieu');
/*!40000 ALTER TABLE `check_in` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `check_out`
--

DROP TABLE IF EXISTS `check_out`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `check_out` (
                             `ma_checkout` varchar(10) NOT NULL,
                             `ma_phieu` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                             `ngay_checkout` datetime DEFAULT NULL,
                             `nhan_vien` varchar(10) DEFAULT NULL,
                             `ghi_chu` text,
                             `ma_hop_dong` varchar(20) DEFAULT NULL,
                             PRIMARY KEY (`ma_checkout`),
                             KEY `fk_checkout_phieu` (`ma_phieu`),
                             CONSTRAINT `fk_checkout_phieu` FOREIGN KEY (`ma_phieu`) REFERENCES `phieu_dat_phong` (`ma_phieu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check_out`
--

LOCK TABLES `check_out` WRITE;
/*!40000 ALTER TABLE `check_out` DISABLE KEYS */;
INSERT INTO `check_out` VALUES ('CO144727','PDP68DB0','2025-05-12 06:12:24','NV001','tr',NULL),('CO15780',NULL,'2025-05-12 02:33:35','NV001','KHÁCH GIẬN','HD82822EB2'),('CO158239','PDP3C5AF','2025-05-11 01:52:38','NV001','trả sớm do khách hàng có việc gấp',NULL),('CO171736','PDP8D779','2025-05-12 01:46:11','NV001','',NULL),('CO204726',NULL,'2025-05-15 13:40:04','NV001','Do khách dư tiền','HD4E195A60'),('CO23107','PDP4434A','2025-05-14 14:50:23','NV001','khách dỗi',NULL),('CO264634','PDP875F1','2025-05-12 05:57:44','NV001','fcgv',NULL),('CO314709','PDPF8FD7','2025-05-12 06:15:14','NV001','z',NULL),('CO322287','PDPE2F01','2025-05-14 15:45:22','NV001','dvs',NULL),('CO346365','PDP8440B','2025-05-14 14:55:46','NV001','do khách quên',NULL),('CO365517','PDP5DA4B','2025-05-14 19:06:05','NV001','cccccccccccccccccc',NULL),('CO381509','PDPDC855','2025-05-12 05:59:41','NV001','cg',NULL),('CO405489','PDPE49D9','2025-05-14 16:53:25','NV001','tests',NULL),('CO455381',NULL,'2025-05-12 05:44:15','NV001','bssnj','HD0EA30DB7'),('CO50381','PDP27BC4','2025-05-14 18:44:10','NV001','a',NULL),('CO619557','PDP27B57','2025-05-15 02:40:19','NV001','test2',NULL),('CO630444','PDP46774','2025-05-14 18:53:50','NV001','ác',NULL),('CO69039','PDP0B664','2025-05-12 23:57:49','NV001','khách khùng',NULL),('CO692873','PDP1D4D7','2025-05-12 06:04:52','NV001','hfcg',NULL),('CO698987','PDP11C66','2025-05-14 16:58:18','NV001','test1',NULL),('CO762483','PDP111A2','2025-05-15 02:26:02','NV001','test1',NULL),('CO789127','PDP10A5D','2025-05-12 05:49:49','NV001','xc',NULL),('CO850810','PDP41B16','2025-05-15 02:44:10','NV001','test3',NULL),('CO920093','PDP5D5EB','2025-05-14 15:05:20','NV001','sgds',NULL),('CO992355','PDP695F2','2025-05-12 02:33:12','NV001','DO KHÁCH BÚ BÓNG XONG RỒI',NULL);
/*!40000 ALTER TABLE `check_out` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chi_tiet_dich_vu`
--

DROP TABLE IF EXISTS `chi_tiet_dich_vu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chi_tiet_dich_vu` (
                                    `ma_phieu` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
                                    `ma_dv` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
                                    `so_luong` int DEFAULT '1',
                                    PRIMARY KEY (`ma_phieu`,`ma_dv`),
                                    KEY `idx_phieu` (`ma_phieu`),
                                    KEY `idx_dv` (`ma_dv`),
                                    CONSTRAINT `chi_tiet_dich_vu_ibfk_1` FOREIGN KEY (`ma_phieu`) REFERENCES `phieu_dat_phong` (`ma_phieu`),
                                    CONSTRAINT `chi_tiet_dich_vu_ibfk_2` FOREIGN KEY (`ma_dv`) REFERENCES `dich_vu` (`ma_dv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chi_tiet_dich_vu`
--

LOCK TABLES `chi_tiet_dich_vu` WRITE;
/*!40000 ALTER TABLE `chi_tiet_dich_vu` DISABLE KEYS */;
INSERT INTO `chi_tiet_dich_vu` VALUES ('PDP0B664','DV01',1),('PDP111A2','DV01',1),('PDP111A2','DV02',1),('PDP111A2','DV04',1),('PDP11C66','DV01',1),('PDP27B57','DV02',1),('PDP27B57','DV03',1),('PDP41B16','DV04',1),('PDP46774','DV01',1),('PDP5DA4B','DV01',1),('PDP5DA4B','DV03',1),('PDP8D8BD','DV01',1);
/*!40000 ALTER TABLE `chi_tiet_dich_vu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chi_tiet_dich_vu_hop_dong`
--

DROP TABLE IF EXISTS `chi_tiet_dich_vu_hop_dong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chi_tiet_dich_vu_hop_dong` (
                                             `ma_hop_dong` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                             `ma_phong` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                             `ma_dv` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                             PRIMARY KEY (`ma_hop_dong`,`ma_phong`,`ma_dv`),
                                             KEY `fk_ctdvhd_phong` (`ma_phong`),
                                             KEY `fk_ctdvhd_dv` (`ma_dv`),
                                             CONSTRAINT `chi_tiet_dich_vu_hop_dong_ibfk_1` FOREIGN KEY (`ma_hop_dong`) REFERENCES `hop_dong` (`ma_hop_dong`) ON DELETE CASCADE ON UPDATE CASCADE,
                                             CONSTRAINT `chi_tiet_dich_vu_hop_dong_ibfk_2` FOREIGN KEY (`ma_phong`) REFERENCES `phong` (`ma_phong`) ON DELETE CASCADE ON UPDATE CASCADE,
                                             CONSTRAINT `chi_tiet_dich_vu_hop_dong_ibfk_3` FOREIGN KEY (`ma_dv`) REFERENCES `dich_vu` (`ma_dv`) ON DELETE CASCADE ON UPDATE CASCADE,
                                             CONSTRAINT `fk_ctdvhd_dv` FOREIGN KEY (`ma_dv`) REFERENCES `dich_vu` (`ma_dv`) ON DELETE CASCADE ON UPDATE CASCADE,
                                             CONSTRAINT `fk_ctdvhd_hopdong` FOREIGN KEY (`ma_hop_dong`) REFERENCES `hop_dong` (`ma_hop_dong`) ON DELETE CASCADE ON UPDATE CASCADE,
                                             CONSTRAINT `fk_ctdvhd_phong` FOREIGN KEY (`ma_phong`) REFERENCES `phong` (`ma_phong`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chi_tiet_dich_vu_hop_dong`
--

LOCK TABLES `chi_tiet_dich_vu_hop_dong` WRITE;
/*!40000 ALTER TABLE `chi_tiet_dich_vu_hop_dong` DISABLE KEYS */;
INSERT INTO `chi_tiet_dich_vu_hop_dong` VALUES ('HD30802815','P101','DV03'),('HDC3FC86F4','P101','DV04'),('HDC7679CC3','P102','DV02'),('HDC7679CC3','P103','DV04'),('HDBEB7CE99','P301','DV02'),('HD185D9B','P401','DV02'),('HD82822EB2','P401','DV01'),('HDD22BD1B5','P402','DV02'),('HDF92EE7','P403','DV02'),('HDF92EE7','P403','DV04'),('HDD22BD1B5','P407','DV04'),('HD4E195A60','P501','DV02'),('HD4E195A60','P501','DV03'),('HD344c1a75','P503','DV04'),('HD344c1a75','P506','DV03'),('HD63adb022','P506','DV02'),('HD63adb022','P506','DV03'),('HDFE317758','P506','DV03'),('HDB0B16B6C','P602','DV03');
/*!40000 ALTER TABLE `chi_tiet_dich_vu_hop_dong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chi_tiet_hoa_don`
--

DROP TABLE IF EXISTS `chi_tiet_hoa_don`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chi_tiet_hoa_don` (
                                    `ma_hoa_don` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                    `ma_item` varchar(50) NOT NULL,
                                    `loai_item` varchar(20) NOT NULL,
                                    `don_gia` decimal(12,2) NOT NULL,
                                    `so_luong` int NOT NULL,
                                    `thanh_tien` decimal(12,2) NOT NULL,
                                    PRIMARY KEY (`ma_hoa_don`,`ma_item`,`loai_item`),
                                    CONSTRAINT `chi_tiet_hoa_don_ibfk_1` FOREIGN KEY (`ma_hoa_don`) REFERENCES `hoa_don` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chi_tiet_hoa_don`
--

LOCK TABLES `chi_tiet_hoa_don` WRITE;
/*!40000 ALTER TABLE `chi_tiet_hoa_don` DISABLE KEYS */;
INSERT INTO `chi_tiet_hoa_don` VALUES ('HD214126','P401','phong',1200000.00,7,8400000.00),('HD214126','P403','phong',500000.00,7,3500000.00),('HD214126','P501','phong',1200000.00,7,8400000.00),('HD214126','P503','phong',1200000.00,7,8400000.00),('HD383869','DV01','dich_vu',100000.00,1,100000.00),('HD383869','DV03','dich_vu',150000.00,1,150000.00),('HD383869','P701','phong',1200000.00,1,1200000.00),('HD393515','DV01','dich_vu',100000.00,1,100000.00),('HD393515','DV03','dich_vu',150000.00,1,150000.00),('HD393515','P701','phong',1200000.00,1,1200000.00),('HD399880','DV01','dich_vu',100000.00,1,100000.00),('HD399880','DV03','dich_vu',150000.00,1,150000.00),('HD399880','P701','phong',1200000.00,1,1200000.00),('HD408450','DV01','dich_vu',100000.00,1,100000.00),('HD408450','DV03','dich_vu',150000.00,1,150000.00),('HD408450','P701','phong',1200000.00,1,1200000.00),('HD626587','DV02','dich_vu',50000.00,1,50000.00),('HD626587','DV03','dich_vu',150000.00,1,150000.00),('HD626587','P403','phong',500000.00,1,500000.00),('HD650265','DV01','dich_vu',100000.00,1,100000.00),('HD650265','P503','phong',0.00,1,0.00),('HD660367','DV01','dich_vu',100000.00,1,100000.00),('HD660367','P503','phong',0.00,1,0.00),('HD671973','DV01','dich_vu',100000.00,1,100000.00),('HD671973','P503','phong',0.00,1,0.00),('HD675150','DV01','dich_vu',100000.00,1,100000.00),('HD675150','P503','phong',0.00,1,0.00),('HD679642','DV01','dich_vu',100000.00,1,100000.00),('HD679642','P503','phong',0.00,1,0.00),('HD683971','DV01','dich_vu',100000.00,1,100000.00),('HD683971','P503','phong',0.00,1,0.00),('HD693565','DV01','dich_vu',100000.00,1,100000.00),('HD693565','P503','phong',0.00,1,0.00),('HD698617','DV01','dich_vu',100000.00,1,100000.00),('HD698617','P503','phong',0.00,1,0.00),('HD701469','DV01','dich_vu',100000.00,1,100000.00),('HD701469','P503','phong',0.00,1,0.00),('HD809975','DV01','dich_vu',100000.00,1,100000.00),('HD809975','DV02','dich_vu',50000.00,1,50000.00),('HD809975','DV04','dich_vu',200000.00,1,200000.00),('HD809975','P501','phong',1200000.00,1,1200000.00),('HD857569','DV04','dich_vu',200000.00,1,200000.00),('HD857569','P401','phong',1200000.00,1,1200000.00);
/*!40000 ALTER TABLE `chi_tiet_hoa_don` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chi_tiet_hop_dong`
--

DROP TABLE IF EXISTS `chi_tiet_hop_dong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chi_tiet_hop_dong` (
                                     `ma_hop_dong` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                     `ma_phong` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                     `ngay_nhan_phong` datetime DEFAULT NULL,
                                     `ngay_tra_phong` datetime DEFAULT NULL,
                                     `trang_thai_phong` enum('chua_checkin','da_checkin','da_checkout') COLLATE utf8mb4_general_ci DEFAULT 'chua_checkin',
                                     `ghi_chu` text COLLATE utf8mb4_general_ci,
                                     PRIMARY KEY (`ma_hop_dong`,`ma_phong`),
                                     KEY `fk_cthd_phong` (`ma_phong`),
                                     CONSTRAINT `fk_cthd_hd` FOREIGN KEY (`ma_hop_dong`) REFERENCES `hop_dong` (`ma_hop_dong`) ON DELETE CASCADE ON UPDATE CASCADE,
                                     CONSTRAINT `fk_cthd_phong` FOREIGN KEY (`ma_phong`) REFERENCES `phong` (`ma_phong`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chi_tiet_hop_dong`
--

LOCK TABLES `chi_tiet_hop_dong` WRITE;
/*!40000 ALTER TABLE `chi_tiet_hop_dong` DISABLE KEYS */;
INSERT INTO `chi_tiet_hop_dong` VALUES ('HD0EA30DB7','P401',NULL,NULL,'chua_checkin',NULL),('HD0EA30DB7','P403',NULL,NULL,'chua_checkin',NULL),('HD0EA30DB7','P404',NULL,NULL,'chua_checkin',NULL),('HD0EA30DB7','P406',NULL,NULL,'chua_checkin',NULL),('HD185D9B','P401',NULL,NULL,'chua_checkin',NULL),('HD185D9B','P402',NULL,NULL,'chua_checkin',NULL),('HD185D9B','P501',NULL,NULL,'chua_checkin',NULL),('HD185D9B','P502',NULL,NULL,'chua_checkin',NULL),('HD30802815','P101',NULL,NULL,'chua_checkin',NULL),('HD30802815','P104',NULL,NULL,'chua_checkin',NULL),('HD30802815','P105',NULL,NULL,'chua_checkin',NULL),('HD30802815','P202',NULL,NULL,'chua_checkin',NULL),('HD344c1a75','P503',NULL,NULL,'chua_checkin',NULL),('HD344c1a75','P506',NULL,NULL,'chua_checkin',NULL),('HD4E195A60','P401',NULL,NULL,'chua_checkin',NULL),('HD4E195A60','P403',NULL,NULL,'chua_checkin',NULL),('HD4E195A60','P501',NULL,NULL,'chua_checkin',NULL),('HD4E195A60','P503',NULL,NULL,'chua_checkin',NULL),('HD63adb022','P506',NULL,NULL,'chua_checkin',NULL),('HD82822EB2','P401',NULL,NULL,'chua_checkin',NULL),('HD82822EB2','P403',NULL,NULL,'chua_checkin',NULL),('HD82822EB2','P404',NULL,NULL,'chua_checkin',NULL),('HD82822EB2','P406',NULL,NULL,'chua_checkin',NULL),('HDB0B16B6C','P503',NULL,NULL,'chua_checkin',NULL),('HDB0B16B6C','P601',NULL,NULL,'chua_checkin',NULL),('HDB0B16B6C','P602',NULL,NULL,'chua_checkin',NULL),('HDB0B16B6C','P603',NULL,NULL,'chua_checkin',NULL),('HDBEB7CE99','P301',NULL,NULL,'chua_checkin',NULL),('HDBEB7CE99','P302',NULL,NULL,'chua_checkin',NULL),('HDBEB7CE99','P303',NULL,NULL,'chua_checkin',NULL),('HDBEB7CE99','P304',NULL,NULL,'chua_checkin',NULL),('HDC3FC86F4','P101',NULL,NULL,'chua_checkin',NULL),('HDC3FC86F4','P104',NULL,NULL,'chua_checkin',NULL),('HDC3FC86F4','P105',NULL,NULL,'chua_checkin',NULL),('HDC3FC86F4','P202',NULL,NULL,'chua_checkin',NULL),('HDC7679CC3','P102',NULL,NULL,'chua_checkin',NULL),('HDC7679CC3','P103',NULL,NULL,'chua_checkin',NULL),('HDC7679CC3','P106',NULL,NULL,'chua_checkin',NULL),('HDC7679CC3','P201',NULL,NULL,'chua_checkin',NULL),('HDD22BD1B5','P305',NULL,NULL,'chua_checkin',NULL),('HDD22BD1B5','P402',NULL,NULL,'chua_checkin',NULL),('HDD22BD1B5','P405',NULL,NULL,'chua_checkin',NULL),('HDD22BD1B5','P407',NULL,NULL,'chua_checkin',NULL),('HDFE317758','P506',NULL,NULL,'chua_checkin',NULL),('HDFE317758','P601',NULL,NULL,'chua_checkin',NULL),('HDFE317758','P602',NULL,NULL,'chua_checkin',NULL),('HDFE317758','P603',NULL,NULL,'chua_checkin',NULL);
/*!40000 ALTER TABLE `chi_tiet_hop_dong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chi_tiet_phieu_dat_phong`
--

DROP TABLE IF EXISTS `chi_tiet_phieu_dat_phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chi_tiet_phieu_dat_phong` (
                                            `ma_phieu` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                            `ma_phong` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                            `don_gia` decimal(10,2) DEFAULT NULL,
                                            PRIMARY KEY (`ma_phieu`,`ma_phong`),
                                            KEY `ma_phong` (`ma_phong`),
                                            CONSTRAINT `chi_tiet_phieu_dat_phong_ibfk_1` FOREIGN KEY (`ma_phieu`) REFERENCES `phieu_dat_phong` (`ma_phieu`),
                                            CONSTRAINT `chi_tiet_phieu_dat_phong_ibfk_2` FOREIGN KEY (`ma_phong`) REFERENCES `phong` (`ma_phong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chi_tiet_phieu_dat_phong`
--

LOCK TABLES `chi_tiet_phieu_dat_phong` WRITE;
/*!40000 ALTER TABLE `chi_tiet_phieu_dat_phong` DISABLE KEYS */;
INSERT INTO `chi_tiet_phieu_dat_phong` VALUES ('PDP3F430','P503',0.00),('PDP41A66','P205',0.00),('PDP41A66','P302',0.00),('PDP74E76','P103',0.00),('PDP74E76','P201',0.00),('PDP74E76','P301',0.00);
/*!40000 ALTER TABLE `chi_tiet_phieu_dat_phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chitiet_phieu_dat_phong`
--

DROP TABLE IF EXISTS `chitiet_phieu_dat_phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chitiet_phieu_dat_phong` (
                                           `ma_phieu` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                           `ma_phong` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                           `don_gia` double NOT NULL,
                                           PRIMARY KEY (`ma_phieu`,`ma_phong`),
                                           KEY `ma_phong` (`ma_phong`),
                                           CONSTRAINT `chitiet_phieu_dat_phong_ibfk_1` FOREIGN KEY (`ma_phieu`) REFERENCES `phieu_dat_phong` (`ma_phieu`),
                                           CONSTRAINT `chitiet_phieu_dat_phong_ibfk_2` FOREIGN KEY (`ma_phong`) REFERENCES `phong` (`ma_phong`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chitiet_phieu_dat_phong`
--

LOCK TABLES `chitiet_phieu_dat_phong` WRITE;
/*!40000 ALTER TABLE `chitiet_phieu_dat_phong` DISABLE KEYS */;
INSERT INTO `chitiet_phieu_dat_phong` VALUES ('PDP0B664','P505',0),('PDP10A5D','P503',0),('PDP111A2','P501',0),('PDP11C66','P404',0),('PDP1D4D7','P401',0),('PDP27B57','P403',0),('PDP27BC4','P403',0),('PDP3C5AF','P103',0),('PDP413F7','P406',0),('PDP41B16','P401',0),('PDP4434A','P502',0),('PDP46774','P503',0),('PDP5D5EB','P504',0),('PDP5DA4B','P701',0),('PDP68DB0','P403',0),('PDP695F2','P401',0),('PDP8440B','P401',0),('PDP875F1','P502',0),('PDP8D779','P403',0),('PDP8D779','P404',0),('PDP8D8BD','P502',0),('PDPDC855','P501',0),('PDPE2F01','P501',0),('PDPE49D9','P404',0),('PDPF0EE9','P404',0),('PDPF8FD7','P406',0);
/*!40000 ALTER TABLE `chitiet_phieu_dat_phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chuc_nang`
--

DROP TABLE IF EXISTS `chuc_nang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chuc_nang` (
                             `ma_chuc_nang` varchar(20) NOT NULL,
                             `ten_chuc_nang` varchar(100) DEFAULT NULL,
                             PRIMARY KEY (`ma_chuc_nang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chuc_nang`
--

LOCK TABLES `chuc_nang` WRITE;
/*!40000 ALTER TABLE `chuc_nang` DISABLE KEYS */;
INSERT INTO `chuc_nang` VALUES ('BAO_CAO','Báo cáo thống kê'),('CAP_NHAT_TT','Cập nhật thông tin cá nhân'),('DAT_PHONG','Đặt phòng'),('DICH_VU','Quản lý dịch vụ'),('HUY_DAT_PHONG','Hủy đặt phòng'),('KHACH_HANG','Quản lý khách hàng'),('NHAN_VIEN','Quản lý nhân viên'),('PHAN_QUYEN','Phân quyền hệ thống'),('PHONG','Quản lý phòng'),('THANH_TOAN','Thanh toán'),('THEM_KHACH_HANG','Thêm khách hàng'),('XEM_PHONG','Xem danh sách phòng');
/*!40000 ALTER TABLE `chuc_nang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dich_vu`
--

DROP TABLE IF EXISTS `dich_vu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dich_vu` (
                           `ma_dv` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
                           `ten_dv` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
                           `gia` double DEFAULT NULL,
                           PRIMARY KEY (`ma_dv`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dich_vu`
--

LOCK TABLES `dich_vu` WRITE;
/*!40000 ALTER TABLE `dich_vu` DISABLE KEYS */;
INSERT INTO `dich_vu` VALUES ('DV01','Xông hơi',100000),('DV02','Hồ bơi',50000),('DV03','Massage',150000),('DV04','Buffet',200000);
/*!40000 ALTER TABLE `dich_vu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoa_don`
--

DROP TABLE IF EXISTS `hoa_don`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoa_don` (
                           `id` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
                           `ngay_nhan_phong` date DEFAULT NULL,
                           `ngay_tra_phong` date DEFAULT NULL,
                           `ngay_thanh_toan` date DEFAULT NULL,
                           `tong_tien` double DEFAULT NULL,
                           `phuong_thuc_thanh_toan` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
                           `trang_thai` enum('da_dat','dang_su_dung','da_thanh_toan','huy') COLLATE utf8mb4_general_ci DEFAULT NULL,
                           `ma_phieu` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
                           `ma_hop_dong` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `fk_hoa_don_phieu` (`ma_phieu`),
                           KEY `fk_hoa_don_hop_dong` (`ma_hop_dong`),
                           CONSTRAINT `fk_hd_hopdong` FOREIGN KEY (`ma_hop_dong`) REFERENCES `hop_dong` (`ma_hop_dong`) ON DELETE SET NULL ON UPDATE CASCADE,
                           CONSTRAINT `fk_hd_phieu` FOREIGN KEY (`ma_phieu`) REFERENCES `phieu_dat_phong` (`ma_phieu`) ON DELETE SET NULL ON UPDATE CASCADE,
                           CONSTRAINT `fk_hoa_don_hop_dong` FOREIGN KEY (`ma_hop_dong`) REFERENCES `hop_dong` (`ma_hop_dong`),
                           CONSTRAINT `fk_hoa_don_phieu` FOREIGN KEY (`ma_phieu`) REFERENCES `phieu_dat_phong` (`ma_phieu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoa_don`
--

LOCK TABLES `hoa_don` WRITE;
/*!40000 ALTER TABLE `hoa_don` DISABLE KEYS */;
INSERT INTO `hoa_don` VALUES ('HD151632','2025-05-12','2025-05-13','2025-05-12',500000,'tiền mặt','da_thanh_toan','PDP68DB0',NULL),('HD214126',NULL,NULL,'2025-05-15',28700000,NULL,'da_thanh_toan',NULL,'HD4E195A60'),('HD319981','2025-05-12','2025-05-13','2025-05-12',1200000,'tiền mặt','da_thanh_toan','PDPF8FD7',NULL),('HD356202',NULL,NULL,'2025-05-14',2400000,'tiền mặt','da_thanh_toan','PDP8440B',NULL),('HD383869',NULL,NULL,'2025-05-14',1450000,NULL,'da_thanh_toan','PDP5DA4B',NULL),('HD393515',NULL,NULL,'2025-05-14',1450000,NULL,'da_thanh_toan','PDP5DA4B',NULL),('HD399880',NULL,NULL,'2025-05-14',1450000,NULL,'da_thanh_toan','PDP5DA4B',NULL),('HD408450',NULL,NULL,'2025-05-14',1450000,NULL,'da_thanh_toan','PDP5DA4B',NULL),('HD626587',NULL,NULL,'2025-05-15',0,NULL,'da_thanh_toan','PDP27B57',NULL),('HD650265',NULL,NULL,'2025-05-14',1300000,NULL,'da_thanh_toan','PDP46774',NULL),('HD660367',NULL,NULL,'2025-05-14',1300000,NULL,'da_thanh_toan','PDP46774',NULL),('HD671973',NULL,NULL,'2025-05-14',1300000,NULL,'da_thanh_toan','PDP46774',NULL),('HD675150',NULL,NULL,'2025-05-14',1300000,NULL,'da_thanh_toan','PDP46774',NULL),('HD679642',NULL,NULL,'2025-05-14',1300000,NULL,'da_thanh_toan','PDP46774',NULL),('HD683971',NULL,NULL,'2025-05-14',1300000,NULL,'da_thanh_toan','PDP46774',NULL),('HD693565',NULL,NULL,'2025-05-14',1300000,NULL,'da_thanh_toan','PDP46774',NULL),('HD698617',NULL,NULL,'2025-05-14',1300000,NULL,'da_thanh_toan','PDP46774',NULL),('HD699137','2025-05-12','2025-05-13','2025-05-12',1200000,'tiền mặt','da_thanh_toan','PDP1D4D7',NULL),('HD701469',NULL,NULL,'2025-05-14',1300000,NULL,'da_thanh_toan','PDP46774',NULL),('HD706728',NULL,NULL,'2025-05-14',600000,'Tiền mặt','da_thanh_toan','PDP11C66',NULL),('HD809975',NULL,NULL,'2025-05-15',1550000,NULL,'da_thanh_toan','PDP111A2',NULL),('HD857569',NULL,NULL,'2025-05-15',1400000,NULL,'da_thanh_toan','PDP41B16',NULL),('HD883166','2025-05-07','2025-05-10','2025-05-12',0,'tiền mặt','da_thanh_toan',NULL,'HD001');
/*!40000 ALTER TABLE `hoa_don` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hop_dong`
--

DROP TABLE IF EXISTS `hop_dong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hop_dong` (
                            `ma_hop_dong` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
                            `ma_khach_hang` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                            `so_luong_phong_muon_thue` int DEFAULT NULL,
                            `lich_dat_phong` datetime DEFAULT NULL,
                            `ngay_bat_dau` date DEFAULT NULL,
                            `ngay_ket_thuc` date DEFAULT NULL,
                            `tong_ngay_thue` int DEFAULT NULL,
                            `dat_coc` decimal(10,2) DEFAULT NULL,
                            `tong_tien` decimal(10,2) DEFAULT NULL,
                            `phuong_thuc_thanh_toan` enum('tiền mặt','thẻ tín dụng','chuyển khoản') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
                            `ghi_chu` text COLLATE utf8mb4_general_ci,
                            `trang_thai` enum('dang_dat','dang_su_dung','da_tra','da_huy') COLLATE utf8mb4_general_ci DEFAULT 'dang_dat',
                            `hoa_don` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
                            `da_thanh_toan` tinyint(1) DEFAULT '0',
                            `ngay_thanh_toan` date DEFAULT NULL,
                            PRIMARY KEY (`ma_hop_dong`),
                            KEY `ma_khach_hang` (`ma_khach_hang`),
                            KEY `hop_dong_ibfk_2` (`hoa_don`),
                            CONSTRAINT `hop_dong_ibfk_1` FOREIGN KEY (`ma_khach_hang`) REFERENCES `khach_hang` (`ma_khach_hang`),
                            CONSTRAINT `hop_dong_ibfk_2` FOREIGN KEY (`hoa_don`) REFERENCES `hoa_don` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hop_dong`
--

LOCK TABLES `hop_dong` WRITE;
/*!40000 ALTER TABLE `hop_dong` DISABLE KEYS */;
INSERT INTO `hop_dong` VALUES ('HD001','KH001',NULL,'2025-05-07 00:00:00','2025-05-07','2025-05-10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,'2025-05-07'),('HD0EA30DB7','KH001',4,'2025-05-12 00:00:00','2025-05-12','2025-05-14',2,2040000.00,6800000.00,'tiền mặt','','da_tra',NULL,1,'2025-05-12'),('HD185D9B','KH001',4,'2025-05-09 00:00:00','2025-05-09','2025-05-13',4,0.00,0.00,NULL,'\nPhương thức thanh toán: Tiền mặt',NULL,NULL,1,'2025-05-09'),('HD30802815','KH001',4,'2025-05-11 00:00:00','2025-05-11','2025-05-13',2,2925000.00,9750000.00,'tiền mặt','',NULL,NULL,1,'2025-05-11'),('HD344c1a75','KH001',2,'2025-05-09 00:00:00','2025-05-09','2025-05-12',3,1635000.00,5450000.00,'tiền mặt','',NULL,NULL,1,'2025-05-09'),('HD4E195A60','KH005',4,'2025-05-15 00:00:00','2025-05-15','2025-05-22',7,8670000.00,28900000.00,'tiền mặt','','da_tra',NULL,1,'2025-05-15'),('HD63adb022','KH001',1,'2025-05-09 00:00:00','2025-05-09','2025-05-12',3,510000.00,1700000.00,'tiền mặt','',NULL,NULL,1,'2025-05-09'),('HD82822EB2','KH001',4,'2025-05-12 00:00:00','2025-05-13','2025-05-15',2,2070000.00,6900000.00,'tiền mặt','','da_tra',NULL,1,'2025-05-12'),('HDB0B16B6C','KH001',4,'2025-05-09 00:00:00','2025-05-09','2025-05-13',4,3285000.00,10950000.00,'tiền mặt','',NULL,NULL,1,'2025-05-09'),('HDBEB7CE99','KH001',4,'2025-05-11 00:00:00','2025-05-11','2025-05-13',2,2625000.00,8750000.00,'tiền mặt','',NULL,NULL,1,'2025-05-11'),('HDC3FC86F4','KH001',4,'2025-05-11 00:00:00','2025-05-11','2025-05-14',3,4380000.00,14600000.00,'tiền mặt','',NULL,NULL,1,'2025-05-11'),('HDC7679CC3','KH005',4,'2025-05-11 00:00:00','2025-05-11','2025-05-12',1,825000.00,2750000.00,'tiền mặt','',NULL,NULL,1,'2025-05-11'),('HDD22BD1B5','KH006',4,'2025-05-11 00:00:00','2025-05-12','2025-05-14',2,1995000.00,6650000.00,'thẻ tín dụng','cho 1 em đào',NULL,NULL,1,'2025-05-11'),('HDF92EE7','KH001',4,'2025-05-08 00:00:00','2025-05-08','2025-05-13',5,NULL,NULL,NULL,'',NULL,NULL,0,NULL),('HDFE317758','KH001',4,'2025-05-09 00:00:00','2025-05-09','2025-05-12',3,1845000.00,6150000.00,'tiền mặt','ở dơ nha',NULL,NULL,1,'2025-05-09');
/*!40000 ALTER TABLE `hop_dong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khach_hang`
--

DROP TABLE IF EXISTS `khach_hang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khach_hang` (
                              `ma_khach_hang` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
                              `ten_khach_hang` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
                              `cccd` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
                              `so_dien_thoai` varchar(15) COLLATE utf8mb4_general_ci DEFAULT NULL,
                              `dia_chi` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
                              `loai_khach` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
                              PRIMARY KEY (`ma_khach_hang`),
                              CONSTRAINT `khach_hang_ibfk_1` FOREIGN KEY (`ma_khach_hang`) REFERENCES `tai_khoan` (`ma_nguoi_dung`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khach_hang`
--

LOCK TABLES `khach_hang` WRITE;
/*!40000 ALTER TABLE `khach_hang` DISABLE KEYS */;
INSERT INTO `khach_hang` VALUES ('KH001','Nguyễn Văn A','123456789012','0901234567','TP.HCM','Cá nhân'),('KH003','Nguyễn Văn Thiên','123456789014','0901234569','Hải Phòng','Cá nhân'),('KH004','Nguyễn An','087205014641','0352653010','TP.HCM','Cá nhân'),('KH005','Nguyễn Bo','087205014642','0352653011','Hà Nội','Cá nhân'),('KH006','Võ Bảo Phương','087205014643','0352653013','40 Hồ Văn Long','Cá nhân'),('VL001','Khách vãng lai',NULL,NULL,NULL,'Vãng lai');
/*!40000 ALTER TABLE `khach_hang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loai_phong`
--

DROP TABLE IF EXISTS `loai_phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loai_phong` (
                              `ma_loai` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
                              `ten_loai` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
                              `gia` decimal(10,2) DEFAULT NULL,
                              PRIMARY KEY (`ma_loai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loai_phong`
--

LOCK TABLES `loai_phong` WRITE;
/*!40000 ALTER TABLE `loai_phong` DISABLE KEYS */;
INSERT INTO `loai_phong` VALUES ('LP01','Phòng đơn',500000.00),('LP02','Phòng đôi',750000.00),('LP03','VIP',1200000.00);
/*!40000 ALTER TABLE `loai_phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhan_vien`
--

DROP TABLE IF EXISTS `nhan_vien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhan_vien` (
                             `ma_nhan_vien` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
                             `ho_ten` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
                             `cccd` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
                             `so_dien_thoai` varchar(15) COLLATE utf8mb4_general_ci DEFAULT NULL,
                             `dia_chi` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
                             `ngay_sinh` date DEFAULT NULL,
                             `chuc_vu` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
                             `luong` decimal(15,2) DEFAULT NULL,
                             PRIMARY KEY (`ma_nhan_vien`),
                             UNIQUE KEY `cccd` (`cccd`),
                             CONSTRAINT `nhan_vien_ibfk_1` FOREIGN KEY (`ma_nhan_vien`) REFERENCES `tai_khoan` (`ma_nguoi_dung`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhan_vien`
--

LOCK TABLES `nhan_vien` WRITE;
/*!40000 ALTER TABLE `nhan_vien` DISABLE KEYS */;
/*!40000 ALTER TABLE `nhan_vien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phieu_dat_phong`
--

DROP TABLE IF EXISTS `phieu_dat_phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phieu_dat_phong` (
                                   `ma_phieu` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
                                   `ma_khach_hang` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                   `ngay_nhan` date DEFAULT NULL,
                                   `ngay_tra` date DEFAULT NULL,
                                   `ghi_chu` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                   `trang_thai` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                   `ngay_dat` datetime DEFAULT CURRENT_TIMESTAMP,
                                   PRIMARY KEY (`ma_phieu`),
                                   KEY `ma_khach_hang` (`ma_khach_hang`),
                                   CONSTRAINT `phieu_dat_phong_ibfk_1` FOREIGN KEY (`ma_khach_hang`) REFERENCES `khach_hang` (`ma_khach_hang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phieu_dat_phong`
--

LOCK TABLES `phieu_dat_phong` WRITE;
/*!40000 ALTER TABLE `phieu_dat_phong` DISABLE KEYS */;
INSERT INTO `phieu_dat_phong` VALUES ('PDP0B664','KH005','2025-05-12','2025-05-13','','da_tra','2025-05-12 00:00:00'),('PDP10A5D','KH001','2025-05-12','2025-05-13','','da_tra','2025-05-12 00:00:00'),('PDP111A2','KH006','2025-05-15','2025-05-16','','da_tra','2025-05-15 00:00:00'),('PDP11C66','KH005','2025-05-14','2025-05-15','','da_thanh_toan','2025-05-14 00:00:00'),('PDP1D4D7','KH004','2025-05-12','2025-05-13','','da_thanh_toan','2025-05-12 00:00:00'),('PDP27B57','KH001','2025-05-15','2025-05-16','','da_tra','2025-05-15 00:00:00'),('PDP27BC4','KH001','2025-05-14','2025-05-15','','da_tra','2025-05-14 00:00:00'),('PDP3C5AF','KH001','2025-05-11','2025-05-12','','da_tra','2025-05-11 00:00:00'),('PDP413F7','KH005','2025-05-15','2025-05-16','','dang_su_dung','2025-05-15 00:00:00'),('PDP41B16','KH004','2025-05-15','2025-05-16','','da_tra','2025-05-15 00:00:00'),('PDP4434A','KH005','2025-05-12','2025-05-13','','da_tra','2025-05-12 00:00:00'),('PDP46774','KH005','2025-05-12','2025-05-13','','da_tra','2025-05-12 00:00:00'),('PDP5D5EB','KH005','2025-05-12','2025-05-13','','da_tra','2025-05-12 00:00:00'),('PDP5DA4B','KH005','2025-05-12','2025-05-13','','da_tra','2025-05-12 00:00:00'),('PDP68DB0','KH001','2025-05-12','2025-05-13','','da_thanh_toan','2025-05-12 00:00:00'),('PDP695F2','KH006','2025-05-12','2025-05-13','','da_tra','2025-05-12 00:00:00'),('PDP8440B','KH005','2025-05-12','2025-05-13','','da_thanh_toan','2025-05-12 00:00:00'),('PDP875F1','KH006','2025-05-12','2025-05-13','','da_tra','2025-05-12 00:00:00'),('PDP8D779','KH003','2025-05-11','2025-05-12','cho thêm 2 em đào','da_tra','2025-05-11 00:00:00'),('PDP8D8BD','KH005','2025-05-15','2025-05-16','','dang_su_dung','2025-05-15 00:00:00'),('PDPDC855','KH001','2025-05-12','2025-05-13','','da_tra','2025-05-12 00:00:00'),('PDPE2F01','KH005','2025-05-12','2025-05-13','','da_tra','2025-05-12 00:00:00'),('PDPE49D9','KH003','2025-05-12','2025-05-13','','da_tra','2025-05-12 00:00:00'),('PDPF0EE9','KH003','2025-05-15','2025-05-16','','dang_su_dung','2025-05-15 00:00:00'),('PDPF8FD7','KH005','2025-05-12','2025-05-13','','da_thanh_toan','2025-05-12 00:00:00');
/*!40000 ALTER TABLE `phieu_dat_phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phong`
--

DROP TABLE IF EXISTS `phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phong` (
                         `ma_phong` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
                         `ma_loai` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
                         `tinh_trang` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
                         `nguon_dat` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
                         PRIMARY KEY (`ma_phong`),
                         KEY `ma_loai` (`ma_loai`),
                         CONSTRAINT `phong_ibfk_1` FOREIGN KEY (`ma_loai`) REFERENCES `loai_phong` (`ma_loai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phong`
--

LOCK TABLES `phong` WRITE;
/*!40000 ALTER TABLE `phong` DISABLE KEYS */;
INSERT INTO `phong` VALUES ('P101','LP03','Đã nhận phòng',NULL),('P102','LP02','Đang đặt',NULL),('P103','LP01','Đang đặt',NULL),('P104','LP03','Đã nhận phòng',NULL),('P105','LP03','Đã nhận phòng',NULL),('P106','LP01','Đang đặt',NULL),('P201','LP02','Đang đặt',NULL),('P202','LP03','Đã nhận phòng',NULL),('P301','LP02','Đang đặt',NULL),('P302','LP03','Đang đặt',NULL),('P303','LP03','Đang đặt',NULL),('P304','LP03','Đang đặt',NULL),('P305','LP03','Đang đặt','hop_dong'),('P401','LP03','Trống','hop_dong'),('P402','LP02','Đang đặt','hop_dong'),('P403','LP01','Trống','hop_dong'),('P404','LP01','Đang sử dụng','phieu'),('P405','LP02','Đang đặt','hop_dong'),('P406','LP03','Đang sử dụng','phieu'),('P407','LP01','Đang đặt','hop_dong'),('P501','LP03','Trống','hop_dong'),('P502','LP03','Đang sử dụng','phieu'),('P503','LP03','Trống','hop_dong'),('P504','LP03','Trống',''),('P505','LP03','Trống',''),('P506','LP03','Trống',NULL),('P507','LP03','Trống',NULL),('P508','LP03','Trống',NULL),('P701','LP03','Trống',''),('P702','LP03','Trống',NULL);
/*!40000 ALTER TABLE `phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quyen`
--

DROP TABLE IF EXISTS `quyen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quyen` (
                         `ma_quyen` varchar(20) NOT NULL,
                         `ten_quyen` varchar(100) DEFAULT NULL,
                         PRIMARY KEY (`ma_quyen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quyen`
--

LOCK TABLES `quyen` WRITE;
/*!40000 ALTER TABLE `quyen` DISABLE KEYS */;
INSERT INTO `quyen` VALUES ('KHACH','Quyền khách hàng'),('QUAN_TRI','Quyền quản trị hệ thống'),('TIEP_TAN','Quyền nhân viên lễ tân');
/*!40000 ALTER TABLE `quyen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quyen_chuc_nang`
--

DROP TABLE IF EXISTS `quyen_chuc_nang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quyen_chuc_nang` (
                                   `ma_quyen` varchar(20) NOT NULL,
                                   `ma_chuc_nang` varchar(20) NOT NULL,
                                   `ma_loai_quyen` enum('XEM','THEM','SUA','XOA') NOT NULL,
                                   PRIMARY KEY (`ma_quyen`,`ma_chuc_nang`,`ma_loai_quyen`),
                                   KEY `ma_chuc_nang` (`ma_chuc_nang`),
                                   CONSTRAINT `quyen_chuc_nang_ibfk_1` FOREIGN KEY (`ma_quyen`) REFERENCES `quyen` (`ma_quyen`),
                                   CONSTRAINT `quyen_chuc_nang_ibfk_2` FOREIGN KEY (`ma_chuc_nang`) REFERENCES `chuc_nang` (`ma_chuc_nang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quyen_chuc_nang`
--

LOCK TABLES `quyen_chuc_nang` WRITE;
/*!40000 ALTER TABLE `quyen_chuc_nang` DISABLE KEYS */;
INSERT INTO `quyen_chuc_nang` VALUES ('QUAN_TRI','BAO_CAO','XEM'),('QUAN_TRI','BAO_CAO','THEM'),('QUAN_TRI','BAO_CAO','SUA'),('QUAN_TRI','BAO_CAO','XOA'),('KHACH','CAP_NHAT_TT','XEM'),('KHACH','CAP_NHAT_TT','SUA'),('QUAN_TRI','CAP_NHAT_TT','XEM'),('QUAN_TRI','CAP_NHAT_TT','THEM'),('QUAN_TRI','CAP_NHAT_TT','SUA'),('QUAN_TRI','CAP_NHAT_TT','XOA'),('TIEP_TAN','CAP_NHAT_TT','XEM'),('TIEP_TAN','CAP_NHAT_TT','SUA'),('KHACH','DAT_PHONG','XEM'),('KHACH','DAT_PHONG','THEM'),('KHACH','DAT_PHONG','SUA'),('QUAN_TRI','DAT_PHONG','XEM'),('QUAN_TRI','DAT_PHONG','THEM'),('QUAN_TRI','DAT_PHONG','SUA'),('QUAN_TRI','DAT_PHONG','XOA'),('TIEP_TAN','DAT_PHONG','XEM'),('TIEP_TAN','DAT_PHONG','THEM'),('TIEP_TAN','DAT_PHONG','SUA'),('KHACH','DICH_VU','XEM'),('QUAN_TRI','DICH_VU','XEM'),('QUAN_TRI','DICH_VU','THEM'),('QUAN_TRI','DICH_VU','SUA'),('QUAN_TRI','DICH_VU','XOA'),('TIEP_TAN','DICH_VU','XEM'),('KHACH','HUY_DAT_PHONG','XEM'),('KHACH','HUY_DAT_PHONG','THEM'),('QUAN_TRI','HUY_DAT_PHONG','XEM'),('QUAN_TRI','HUY_DAT_PHONG','THEM'),('QUAN_TRI','HUY_DAT_PHONG','SUA'),('QUAN_TRI','HUY_DAT_PHONG','XOA'),('TIEP_TAN','HUY_DAT_PHONG','XEM'),('TIEP_TAN','HUY_DAT_PHONG','THEM'),('QUAN_TRI','KHACH_HANG','XEM'),('QUAN_TRI','KHACH_HANG','THEM'),('QUAN_TRI','KHACH_HANG','SUA'),('QUAN_TRI','KHACH_HANG','XOA'),('TIEP_TAN','KHACH_HANG','XEM'),('TIEP_TAN','KHACH_HANG','THEM'),('QUAN_TRI','NHAN_VIEN','XEM'),('QUAN_TRI','NHAN_VIEN','THEM'),('QUAN_TRI','NHAN_VIEN','SUA'),('QUAN_TRI','NHAN_VIEN','XOA'),('QUAN_TRI','PHAN_QUYEN','XEM'),('QUAN_TRI','PHAN_QUYEN','THEM'),('QUAN_TRI','PHAN_QUYEN','SUA'),('QUAN_TRI','PHAN_QUYEN','XOA'),('KHACH','PHONG','XEM'),('QUAN_TRI','PHONG','XEM'),('QUAN_TRI','PHONG','THEM'),('QUAN_TRI','PHONG','SUA'),('QUAN_TRI','PHONG','XOA'),('TIEP_TAN','PHONG','XEM'),('KHACH','THANH_TOAN','XEM'),('QUAN_TRI','THANH_TOAN','XEM'),('QUAN_TRI','THANH_TOAN','THEM'),('QUAN_TRI','THANH_TOAN','SUA'),('QUAN_TRI','THANH_TOAN','XOA'),('TIEP_TAN','THANH_TOAN','XEM'),('TIEP_TAN','THANH_TOAN','THEM'),('QUAN_TRI','THEM_KHACH_HANG','XEM'),('QUAN_TRI','THEM_KHACH_HANG','THEM'),('QUAN_TRI','THEM_KHACH_HANG','SUA'),('QUAN_TRI','THEM_KHACH_HANG','XOA'),('TIEP_TAN','THEM_KHACH_HANG','XEM'),('TIEP_TAN','THEM_KHACH_HANG','THEM'),('KHACH','XEM_PHONG','XEM'),('QUAN_TRI','XEM_PHONG','XEM'),('QUAN_TRI','XEM_PHONG','THEM'),('QUAN_TRI','XEM_PHONG','SUA'),('QUAN_TRI','XEM_PHONG','XOA'),('TIEP_TAN','XEM_PHONG','XEM');
/*!40000 ALTER TABLE `quyen_chuc_nang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quyen_vai_tro`
--

DROP TABLE IF EXISTS `quyen_vai_tro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quyen_vai_tro` (
                                 `ma_vai_tro` varchar(20) NOT NULL,
                                 `ma_quyen` varchar(20) NOT NULL,
                                 PRIMARY KEY (`ma_vai_tro`,`ma_quyen`),
                                 KEY `ma_quyen` (`ma_quyen`),
                                 CONSTRAINT `quyen_vai_tro_ibfk_1` FOREIGN KEY (`ma_vai_tro`) REFERENCES `vai_tro` (`ma_vai_tro`),
                                 CONSTRAINT `quyen_vai_tro_ibfk_2` FOREIGN KEY (`ma_quyen`) REFERENCES `quyen` (`ma_quyen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quyen_vai_tro`
--

LOCK TABLES `quyen_vai_tro` WRITE;
/*!40000 ALTER TABLE `quyen_vai_tro` DISABLE KEYS */;
INSERT INTO `quyen_vai_tro` VALUES ('USER','KHACH'),('ADMIN','QUAN_TRI'),('RECEPTIONIST','TIEP_TAN');
/*!40000 ALTER TABLE `quyen_vai_tro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tai_khoan`
--

DROP TABLE IF EXISTS `tai_khoan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tai_khoan` (
                             `ma_nguoi_dung` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
                             `so_dien_thoai` varchar(15) COLLATE utf8mb4_general_ci NOT NULL,
                             `ho_ten` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
                             `cccd` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
                             `email` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
                             `dia_chi` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
                             `mat_khau` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
                             PRIMARY KEY (`ma_nguoi_dung`),
                             UNIQUE KEY `so_dien_thoai` (`so_dien_thoai`),
                             UNIQUE KEY `cccd` (`cccd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tai_khoan`
--

LOCK TABLES `tai_khoan` WRITE;
/*!40000 ALTER TABLE `tai_khoan` DISABLE KEYS */;
INSERT INTO `tai_khoan` VALUES ('KH001','0901234567',NULL,NULL,NULL,NULL,'123456'),('KH003','0901234569',NULL,NULL,NULL,NULL,'123456'),('KH004','0352653010','Nguyễn An','087205014641',NULL,'TP.HCM','123456'),('KH005','0352653011','Nguyễn Bo','087205014642',NULL,'Hà Nội','123456'),('KH006','0352653013','Võ Bảo Phương','087205014643',NULL,'40 Hồ Văn Long','123456'),('VL001','0909123456',NULL,NULL,NULL,NULL,'123456');
/*!40000 ALTER TABLE `tai_khoan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tai_khoan_vai_tro`
--

DROP TABLE IF EXISTS `tai_khoan_vai_tro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tai_khoan_vai_tro` (
                                     `ma_nguoi_dung` varchar(20) NOT NULL,
                                     `ma_vai_tro` varchar(20) NOT NULL,
                                     PRIMARY KEY (`ma_nguoi_dung`,`ma_vai_tro`),
                                     KEY `ma_vai_tro` (`ma_vai_tro`),
                                     CONSTRAINT `tai_khoan_vai_tro_ibfk_1` FOREIGN KEY (`ma_vai_tro`) REFERENCES `vai_tro` (`ma_vai_tro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tai_khoan_vai_tro`
--

LOCK TABLES `tai_khoan_vai_tro` WRITE;
/*!40000 ALTER TABLE `tai_khoan_vai_tro` DISABLE KEYS */;
INSERT INTO `tai_khoan_vai_tro` VALUES ('NVA001','ADMIN'),('NV001','RECEPTIONIST'),('KH001','USER'),('KH004','USER'),('KH005','USER'),('KH006','USER');
/*!40000 ALTER TABLE `tai_khoan_vai_tro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vai_tro`
--

DROP TABLE IF EXISTS `vai_tro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vai_tro` (
                           `ma_vai_tro` varchar(20) NOT NULL,
                           `mo_ta` varchar(100) DEFAULT NULL,
                           PRIMARY KEY (`ma_vai_tro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vai_tro`
--

LOCK TABLES `vai_tro` WRITE;
/*!40000 ALTER TABLE `vai_tro` DISABLE KEYS */;
INSERT INTO `vai_tro` VALUES ('ADMIN','Quản trị viên hệ thống'),('RECEPTIONIST','Nhân viên lễ tân'),('USER','Khách hàng');
/*!40000 ALTER TABLE `vai_tro` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-15 15:35:33
