-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 21. Okt 2016 um 15:40
-- Server-Version: 10.1.13-MariaDB
-- PHP-Version: 5.6.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `lexiconjpa`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `language`
--

CREATE TABLE `language` (
  `LANGUAGE_ID` int(11) NOT NULL,
  `LANGUAGE_NAME` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `sequence`
--

CREATE TABLE `sequence` (
  `SEQ_NAME` varchar(50) NOT NULL,
  `SEQ_COUNT` decimal(38,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `specialty`
--

CREATE TABLE `specialty` (
  `TERM_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `technicalterm`
--

CREATE TABLE `technicalterm` (
  `TERM_ID` int(11) NOT NULL,
  `SPECIALTY_TERM_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `translation`
--

CREATE TABLE `translation` (
  `TRANSLATION_ID` int(11) NOT NULL,
  `TRANSLATION_DESCRIPTION` varchar(255) DEFAULT NULL,
  `TRANSLATION_NAME` varchar(255) DEFAULT NULL,
  `TERM_TERM_ID` int(11) DEFAULT NULL,
  `LANGUAGE_LANGUAGE_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `language`
--
ALTER TABLE `language`
  ADD PRIMARY KEY (`LANGUAGE_ID`);

--
-- Indizes für die Tabelle `sequence`
--
ALTER TABLE `sequence`
  ADD PRIMARY KEY (`SEQ_NAME`);

--
-- Indizes für die Tabelle `specialty`
--
ALTER TABLE `specialty`
  ADD PRIMARY KEY (`TERM_ID`);

--
-- Indizes für die Tabelle `technicalterm`
--
ALTER TABLE `technicalterm`
  ADD PRIMARY KEY (`TERM_ID`),
  ADD KEY `FK_TECHNICALTERM_SPECIALTY_TERM_ID` (`SPECIALTY_TERM_ID`);

--
-- Indizes für die Tabelle `translation`
--
ALTER TABLE `translation`
  ADD PRIMARY KEY (`TRANSLATION_ID`),
  ADD KEY `FK_TRANSLATION_LANGUAGE_LANGUAGE_ID` (`LANGUAGE_LANGUAGE_ID`);

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `technicalterm`
--
ALTER TABLE `technicalterm`
  ADD CONSTRAINT `FK_TECHNICALTERM_SPECIALTY_TERM_ID` FOREIGN KEY (`SPECIALTY_TERM_ID`) REFERENCES `specialty` (`TERM_ID`);

--
-- Constraints der Tabelle `translation`
--
ALTER TABLE `translation`
  ADD CONSTRAINT `FK_TRANSLATION_LANGUAGE_LANGUAGE_ID` FOREIGN KEY (`LANGUAGE_LANGUAGE_ID`) REFERENCES `language` (`LANGUAGE_ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
