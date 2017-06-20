-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 20, 2017 at 06:47 PM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `nwtis_dhorvat3_bp_1`
--

-- --------------------------------------------------------

--
-- Table structure for table `adrese`
--

CREATE TABLE `adrese` (
  `id` int(11) NOT NULL,
  `adresa` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '''''',
  `latitude` double NOT NULL DEFAULT '0',
  `longitude` double NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `adrese`
--

INSERT INTO `adrese` (`id`, `adresa`, `latitude`, `longitude`) VALUES
(1, 'Pavlinska ul. 2, 42000, Varaždin, Croatia', 46.30772, 16.338095),
(2, 'Ul. Petra Krešimira IV 19, 42000, Varaždin, Croatia', 46.30941, 16.34405),
(3, 'foi', 46.30772, 16.338095);

-- --------------------------------------------------------

--
-- Table structure for table `dnevnik`
--

CREATE TABLE `dnevnik` (
  `id` int(11) NOT NULL,
  `tip` int(11) NOT NULL,
  `opis` varchar(255) COLLATE utf8_bin NOT NULL,
  `id_korisnik` int(11) NOT NULL,
  `vrijeme` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `dnevnik`
--

INSERT INTO `dnevnik` (`id`, `tip`, `opis`, `id_korisnik`, `vrijeme`) VALUES
(1, 1, 'Zahtjev - Status servera', 1, '2017-06-09 01:14:55'),
(2, 1, 'Zahtjev - Status servera', 1, '2017-06-09 01:14:55'),
(3, 1, 'Zahtjev - Status servera', 1, '2017-06-09 01:14:55'),
(4, 1, 'Zahtjev - Status servera', 1, '2017-06-11 22:01:02'),
(5, 1, 'Zahtjev - Status servera', 1, '2017-06-11 22:01:02'),
(6, 1, 'Zahtjev - Status servera', 1, '2017-06-11 22:01:02'),
(7, 1, 'Zahtjev - Status servera', 1, '2017-06-11 23:26:31'),
(8, 1, 'Zahtjev - Status servera', 1, '2017-06-11 23:26:31'),
(9, 1, 'Zahtjev - Status servera', 1, '2017-06-11 23:29:53'),
(10, 1, 'Zahtjev - Status servera', 1, '2017-06-11 23:29:53'),
(11, 1, 'Zahtjev - Status servera', 1, '2017-06-11 23:29:53'),
(12, 1, 'Zahtjev - Status servera', 1, '2017-06-12 00:10:04'),
(13, 1, 'Zahtjev - Status servera', 1, '2017-06-12 00:10:04'),
(14, 1, 'Zahtjev - Status servera', 1, '2017-06-12 00:10:04'),
(15, 1, 'Zahtjev - Status servera', 1, '2017-06-12 00:19:23'),
(16, 1, 'Zahtjev - Status servera', 1, '2017-06-12 00:19:23'),
(17, 1, 'Zahtjev - Status servera', 1, '2017-06-12 00:23:53'),
(18, 1, 'Zahtjev - Status servera', 1, '2017-06-12 00:23:53'),
(19, 1, 'Zahtjev - Status servera', 1, '2017-06-12 00:23:53'),
(20, 1, 'Zahtjev - Status servera', 1, '2017-06-13 07:07:57'),
(21, 1, 'Zahtjev - Status servera', 1, '2017-06-13 07:07:57'),
(22, 1, 'Zahtjev - Status servera', 1, '2017-06-13 07:07:57'),
(23, 1, 'Zahtjev - Status servera', 1, '2017-06-13 07:20:44'),
(24, 1, 'Zahtjev - Status servera', 1, '2017-06-13 07:20:44'),
(25, 1, 'Zahtjev - Status servera', 1, '2017-06-13 07:20:44'),
(26, 1, 'Zahtjev - Status servera', 1, '2017-06-13 07:39:11'),
(27, 1, 'Zahtjev - Status servera', 1, '2017-06-13 07:39:11'),
(28, 1, 'Zahtjev - Status servera', 1, '2017-06-13 07:40:04'),
(29, 1, 'Zahtjev - Status servera', 1, '2017-06-13 07:40:04'),
(30, 1, 'Zahtjev - Status servera', 1, '2017-06-13 07:41:57'),
(31, 1, 'Zahtjev - Status servera', 1, '2017-06-13 07:41:57'),
(32, 1, 'Zahtjev - Status servera', 1, '2017-06-13 07:49:21'),
(33, 1, 'Zahtjev - Status servera', 1, '2017-06-13 07:49:21'),
(34, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:00:35'),
(35, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:00:35'),
(36, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:02:29'),
(37, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:02:29'),
(38, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:02:29'),
(39, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:06:46'),
(40, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:06:46'),
(41, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:06:46'),
(42, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:07:58'),
(43, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:07:59'),
(44, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:11:31'),
(45, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:11:31'),
(46, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:11:31'),
(47, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:14:33'),
(48, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:14:33'),
(49, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:14:33'),
(50, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:18:38'),
(51, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:18:38'),
(52, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:18:38'),
(53, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:29:53'),
(54, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:29:53'),
(55, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:32:48'),
(56, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:32:48'),
(57, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:35:28'),
(58, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:35:28'),
(59, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:40:26'),
(60, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:40:26'),
(61, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:46:52'),
(62, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:46:52'),
(63, 1, 'Zahtjev - Status servera', 1, '2017-06-13 08:46:52'),
(64, 1, 'Zahtjev - Status servera', 1, '2017-06-13 09:37:55'),
(65, 1, 'Zahtjev - Status servera', 1, '2017-06-13 09:37:55'),
(66, 1, 'Zahtjev - Status servera', 1, '2017-06-13 09:37:55'),
(67, 1, 'Zahtjev - Status servera', 1, '2017-06-13 09:47:43'),
(68, 1, 'Zahtjev - Status servera', 1, '2017-06-13 09:47:43'),
(69, 1, 'Zahtjev - Status servera', 1, '2017-06-13 09:47:43'),
(70, 1, 'Zahtjev - Status servera', 1, '2017-06-13 09:54:38'),
(71, 1, 'Zahtjev - Status servera', 1, '2017-06-13 09:54:38'),
(72, 1, 'Zahtjev - Status servera', 1, '2017-06-13 09:55:41'),
(73, 1, 'Zahtjev - Status servera', 1, '2017-06-13 09:55:41'),
(74, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:07:04'),
(75, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:07:04'),
(76, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:07:04'),
(77, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:09:55'),
(78, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:09:55'),
(79, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:13:08'),
(80, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:13:08'),
(81, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:15:36'),
(82, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:15:36'),
(83, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:15:36'),
(84, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:20:21'),
(85, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:20:21'),
(86, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:20:21'),
(87, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:21:28'),
(88, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:21:28'),
(89, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:23:33'),
(90, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:23:33'),
(91, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:26:21'),
(92, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:26:21'),
(93, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:26:21'),
(94, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:29:40'),
(95, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:29:40'),
(96, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:29:40'),
(97, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:33:52'),
(98, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:33:52'),
(99, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:33:52'),
(100, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:36:22'),
(101, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:36:23'),
(102, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:36:58'),
(103, 1, 'Zahtjev - Status servera', 1, '2017-06-13 10:36:58'),
(104, 2, 'SOAP: Dohvati zadnje meteo podatke za uredaj: 1', 1, '2017-06-14 08:52:46'),
(105, 2, 'SOAP: Daj nekoliko za id: 1 n: 5', 1, '2017-06-14 09:05:34'),
(106, 2, 'SOAP: Daj nekoliko za id: 1 n: 10', 1, '2017-06-14 09:05:42'),
(107, 2, 'SOAP: dohvati za razdoblje id: 1', 1, '2017-06-14 09:18:12'),
(108, 2, 'SOAP: dohvati za razdoblje id: 1', 1, '2017-06-14 09:18:17'),
(109, 2, 'SOAP: dohvati za razdoblje id: 1', 1, '2017-06-14 09:20:36'),
(110, 2, 'SOAP: dohvati za razdoblje id: 1', 1, '2017-06-14 09:24:10'),
(111, 2, 'SOAP: dohvati za razdoblje id: 1', 1, '2017-06-14 09:28:26'),
(112, 2, 'SOAP: Najnoviji meteo id: 1', 1, '2017-06-14 09:48:01'),
(113, 2, 'SOAP: Adresa lat:46.30772 lon: 16.338095', 1, '2017-06-14 09:56:11'),
(114, 1, 'Zahtjev - dohvati uređaje', 1, '2017-06-15 09:04:25'),
(115, 1, 'Zahtjev - registriraj grupu', 1, '2017-06-15 09:07:18'),
(116, 1, 'Zahtjev - aktiviraj grupu', 1, '2017-06-15 09:10:15'),
(117, 1, 'Zahtjev - registriraj grupu', 1, '2017-06-15 10:36:50'),
(118, 1, 'Zahtjev - Status servera', 1, '2017-06-16 10:08:26'),
(119, 1, 'Zahtjev - Status servera', 1, '2017-06-16 10:26:27'),
(120, 1, 'Zahtjev - status grupe', 1, '2017-06-16 10:26:29'),
(121, 1, 'Zahtjev - Status servera', 1, '2017-06-16 10:27:10'),
(122, 1, 'Zahtjev - status grupe', 1, '2017-06-16 10:27:11'),
(123, 1, 'Zahtjev - Status servera', 1, '2017-06-16 10:33:53'),
(124, 1, 'Zahtjev - status grupe', 1, '2017-06-16 10:33:54'),
(125, 1, 'Zahtjev - Status servera', 1, '2017-06-16 10:42:20'),
(126, 1, 'Zahtjev - status grupe', 1, '2017-06-16 10:42:22'),
(127, 1, 'Zahtjev - Status servera', 1, '2017-06-16 22:01:01'),
(128, 1, 'Zahtjev - status grupe', 1, '2017-06-16 22:01:02'),
(129, 1, 'Zahtjev - Status servera', 1, '2017-06-16 22:05:35'),
(130, 1, 'Zahtjev - status grupe', 1, '2017-06-16 22:05:38'),
(131, 1, 'Zahtjev - Status servera', 1, '2017-06-16 22:08:56'),
(132, 1, 'Zahtjev - status grupe', 1, '2017-06-16 22:08:59'),
(133, 1, 'Zahtjev - Status servera', 1, '2017-06-16 22:13:01'),
(134, 1, 'Zahtjev - status grupe', 1, '2017-06-16 22:13:05');

-- --------------------------------------------------------

--
-- Table structure for table `korisnici`
--

CREATE TABLE `korisnici` (
  `id` int(11) NOT NULL,
  `ime` varchar(50) COLLATE utf8_bin NOT NULL,
  `prezime` varchar(50) COLLATE utf8_bin NOT NULL,
  `korime` varchar(50) COLLATE utf8_bin NOT NULL,
  `pass` varchar(50) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `korisnici`
--

INSERT INTO `korisnici` (`id`, `ime`, `prezime`, `korime`, `pass`) VALUES
(1, 'test', 'test', 'test', 'dassdagdfs'),
(2, 'Davorin', 'Horvat', 'dhorvat', '1234'),
(3, 'Ian', 'Žonja', 'izonja', '12'),
(4, 'Stjepan', 'Hradovi', 'shradovi', '15698'),
(5, 'Mislav', 'Bašić', 'mbasic', '3256'),
(6, 'Stipe', 'Bilokapić', 'sbilokapi', '9884854'),
(7, 'Mislav', 'Zebić', 'zenab92', '123456'),
(8, 'test', 'test', 'tedsfdf', 'sccdsx');

-- --------------------------------------------------------

--
-- Table structure for table `meteo`
--

CREATE TABLE `meteo` (
  `idMeteo` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `adresaStanice` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `latitude` float NOT NULL DEFAULT '0',
  `longitude` float NOT NULL DEFAULT '0',
  `vrijeme` varchar(25) COLLATE utf8_bin NOT NULL DEFAULT '',
  `vrijemeOpis` varchar(25) COLLATE utf8_bin NOT NULL DEFAULT '',
  `temp` float NOT NULL DEFAULT '-999',
  `tempMin` float NOT NULL DEFAULT '-999',
  `tempMax` float NOT NULL DEFAULT '-999',
  `vlaga` float NOT NULL DEFAULT '-999',
  `tlak` float NOT NULL DEFAULT '-999',
  `vjetar` float NOT NULL DEFAULT '-999',
  `vjetarSmjer` float NOT NULL DEFAULT '-999',
  `preuzeto` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `meteo`
--

INSERT INTO `meteo` (`idMeteo`, `id`, `adresaStanice`, `latitude`, `longitude`, `vrijeme`, `vrijemeOpis`, `temp`, `tempMin`, `tempMax`, `vlaga`, `tlak`, `vjetar`, `vjetarSmjer`, `preuzeto`) VALUES
(1, 1, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:30:29'),
(2, 1, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:32:05'),
(3, 2, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:32:06'),
(4, 3, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:32:06'),
(5, 1, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:32:56'),
(6, 2, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:32:56'),
(7, 3, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:32:56'),
(8, 1, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:33:47'),
(9, 2, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:33:47'),
(10, 3, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:33:47'),
(11, 1, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:34:38'),
(12, 2, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:34:38'),
(13, 3, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:34:38'),
(14, 1, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:35:29'),
(15, 2, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:35:29'),
(16, 3, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:35:29'),
(17, 1, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:36:20'),
(18, 2, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:36:20'),
(19, 3, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:36:20'),
(20, 1, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:37:10'),
(21, 2, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:37:11'),
(22, 3, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:37:11'),
(23, 1, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:37:51'),
(24, 2, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:37:51'),
(25, 3, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:37:51'),
(26, 1, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:39:00'),
(27, 2, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:39:00'),
(28, 3, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:39:00'),
(29, 1, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:39:51'),
(30, 2, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:39:52'),
(31, 3, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:39:52'),
(32, 1, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:40:43'),
(33, 2, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:40:43'),
(34, 3, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:40:43'),
(35, 1, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:41:13'),
(36, 2, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:41:13'),
(37, 3, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:41:13'),
(38, 1, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:42:03'),
(39, 2, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:42:04'),
(40, 3, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:42:04'),
(41, 1, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:43:07'),
(42, 2, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:43:07'),
(43, 3, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:43:07'),
(44, 1, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:43:58'),
(45, 2, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:43:58'),
(46, 3, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:43:58'),
(47, 1, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:44:51'),
(48, 2, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:44:51'),
(49, 3, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:44:51'),
(50, 1, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:45:42'),
(51, 2, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:45:42'),
(52, 3, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:45:42'),
(53, 1, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:46:32'),
(54, 2, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:46:32'),
(55, 3, '', 0, 0, '', '', 32, 32, 32, 38, 1014, -999, -999, '2017-06-20 16:46:33');

-- --------------------------------------------------------

--
-- Table structure for table `uredaji`
--

CREATE TABLE `uredaji` (
  `id` int(11) NOT NULL DEFAULT '1',
  `naziv` varchar(30) COLLATE utf8_bin NOT NULL DEFAULT '',
  `latitude` float NOT NULL DEFAULT '0',
  `longitude` float NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `vrijeme_promjene` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `vrijeme_kreiranja` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_adresa` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `uredaji`
--

INSERT INTO `uredaji` (`id`, `naziv`, `latitude`, `longitude`, `status`, `vrijeme_promjene`, `vrijeme_kreiranja`, `id_adresa`) VALUES
(1, 'FOI 1 - ulazna vrata', 46.3077, 16.3381, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(2, 'FOI 1 - dvorišna vrata', 46.3077, 16.3381, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(3, 'FOI 1 - parkirna rampa', 46.3077, 16.3381, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(4, 'FOI 1 - vijećnica', 46.3077, 16.3381, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(5, 'FOI 1 - knjižnica', 46.3077, 16.3381, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(6, 'FOI 1 - dvorana  1', 46.3077, 16.3381, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(7, 'FOI 1 - dvorana  2', 46.3077, 16.3381, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(8, 'FOI 1 - dvorana  3', 46.3077, 16.3381, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(9, 'FOI 1 - dvorana  4', 46.3077, 16.3381, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(10, 'FOI 1 - dvorana  5', 46.3077, 16.3381, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(11, 'FOI 1 - dvorana  6', 46.3077, 16.3381, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(12, 'FOI 1 - dvorana  7', 46.3077, 16.3381, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(13, 'FOI 1 - dvorana  8', 46.3077, 16.3381, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(14, 'FOI 1 - dvorana  9', 46.3077, 16.3381, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(15, 'FOI 1 - dvorana 10', 46.3077, 16.3381, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(16, 'FOI 1 - dvorana 11', 46.3077, 16.3381, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(17, 'FOI 1 - dvorana 12', 46.3077, 16.3381, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(18, 'FOI 1 - dvorana 13', 46.3077, 16.3381, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(19, 'FOI 1 - dvorana 14', 46.3077, 16.3381, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(20, 'FOI 1 - dvorana 15', 46.3077, 16.3381, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(21, 'FOI 1 - restoran', 46.3077, 16.3381, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(22, 'FOI 1 - sistem sala', 46.3077, 16.3381, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(31, 'FOI 2 - ulazna vrata', 46.3094, 16.344, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 2),
(32, 'FOI 2 - parkirna rampa', 46.3094, 16.344, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 2),
(33, 'FOI 2 - dvorana  1', 46.3094, 16.344, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 2),
(34, 'FOI 2 - dvorana  2', 46.3094, 16.344, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 2),
(35, 'FOI 2 - dvorana  3', 46.3094, 16.344, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 2),
(36, 'FOI 2 - dvorana  4', 46.3094, 16.344, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 2),
(37, 'FOI 2 - dvorana  5', 46.3094, 16.344, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 2),
(38, 'FOI 2 - dvorana  6', 46.3094, 16.344, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 2),
(39, 'FOI 2 - dvorana  7', 46.3094, 16.344, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 2),
(40, 'FOI 2 - dvorana  8', 46.3094, 16.344, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 2),
(41, 'FOI 2 - dvorana  9', 46.3094, 16.344, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 2),
(42, 'FOI 2 - dvorana 10', 46.3094, 16.344, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 2),
(43, 'FOI 2 - dvorana 11', 46.3094, 16.344, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 2),
(51, 'Stud. dom - ulazna vrata', 46.3091, 16.3481, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(52, 'Stud. dom - dvorišna vrata', 46.3091, 16.3481, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(53, 'Stud. dom - dvorana 1', 46.3091, 16.3481, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(54, 'Stud. dom - dvorana 2', 46.3091, 16.3481, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(55, 'Stud. dom - dvorana 3', 46.3091, 16.3481, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(56, 'Stud. dom - dvorana 4', 46.3091, 16.3481, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(61, 'Stud. restoran - ulazna vrata', 46.3084, 16.3477, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(62, 'Stud. restoran - veliki', 46.3084, 16.3477, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(63, 'Stud. restoran - mali', 46.3084, 16.3477, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(71, 'Bus kolodvor - ulazna vrata', 46.3044, 16.3344, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(72, 'Bus kolodvor - čekaonica', 46.3044, 16.3344, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(81, 'Želj. kolodvor - ulazna vrata', 46.3054, 116.346, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(82, 'Želj. kolodvor - čekaonica', 46.3054, 116.346, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(91, 'Gradski bazeni - ulazna vrata', 46.2964, 16.3431, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(92, 'Gradski bazeni - veliki', 46.2964, 16.3431, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(93, 'Gradski bazeni - mali', 46.2964, 16.3431, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(94, 'Gradski bazeni - ulazna vrata', 46.2964, 16.3431, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(101, 'Stari grad - ulazna vrata', 46.31, 16.3341, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(102, 'Stari grad - muzej', 46.31, 16.3341, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(111, 'Gradska dvorana - ulazna vrata', 46.3172, 16.3608, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(112, 'Gradska dvorana - velika', 46.3172, 16.3608, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(113, 'Gradska dvorana - mala', 46.3172, 16.3608, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(121, 'Motičnjak/Aquacity - jezero', 46.3079, 16.3903, 0, '2017-06-08 18:27:34', '2017-06-08 18:27:34', 1),
(122, 'FOI 1 - TEST', 0, 0, 0, '2017-06-20 14:33:20', '2017-06-20 14:33:20', 3),
(123, 'FOI 1 - TEST1', 0, 0, 0, '2017-06-20 14:37:24', '2017-06-20 14:37:24', 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `adrese`
--
ALTER TABLE `adrese`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `dnevnik`
--
ALTER TABLE `dnevnik`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_korisnik` (`id_korisnik`);

--
-- Indexes for table `korisnici`
--
ALTER TABLE `korisnici`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `meteo`
--
ALTER TABLE `meteo`
  ADD PRIMARY KEY (`idMeteo`),
  ADD KEY `meteo_FK1` (`id`);

--
-- Indexes for table `uredaji`
--
ALTER TABLE `uredaji`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_adresa` (`id_adresa`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `adrese`
--
ALTER TABLE `adrese`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `dnevnik`
--
ALTER TABLE `dnevnik`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=137;
--
-- AUTO_INCREMENT for table `korisnici`
--
ALTER TABLE `korisnici`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `meteo`
--
ALTER TABLE `meteo`
  MODIFY `idMeteo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `dnevnik`
--
ALTER TABLE `dnevnik`
  ADD CONSTRAINT `dnevnik_ibfk_1` FOREIGN KEY (`id_korisnik`) REFERENCES `korisnici` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `meteo`
--
ALTER TABLE `meteo`
  ADD CONSTRAINT `meteo_FK1` FOREIGN KEY (`id`) REFERENCES `uredaji` (`id`);

--
-- Constraints for table `uredaji`
--
ALTER TABLE `uredaji`
  ADD CONSTRAINT `uredaji_ibfk_1` FOREIGN KEY (`id_adresa`) REFERENCES `adrese` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
