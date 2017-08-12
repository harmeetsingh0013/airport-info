# schema

# --- !Ups

CREATE TABLE `countries` (
  `id` int(11) NOT NULL,
  `code` varchar(45) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `continent` varchar(45) DEFAULT NULL,
  `wikipedia_link` varchar(255) DEFAULT NULL,
  `keywords` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `airports` (
  `id` int(11) NOT NULL,
  `ident` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `latitude_deg` double DEFAULT NULL,
  `longitude_deg` double DEFAULT NULL,
  `elevation_ft` int(11) DEFAULT NULL,
  `continent` varchar(45) DEFAULT NULL,
  `iso_country` varchar(45) DEFAULT NULL,
  `iso_region` varchar(45) DEFAULT NULL,
  `municipality` varchar(150) DEFAULT NULL,
  `scheduled_service` varchar(45) DEFAULT NULL,
  `gps_code` varchar(45) DEFAULT NULL,
  `iata_code` varchar(45) DEFAULT NULL,
  `local_code` varchar(45) DEFAULT NULL,
  `home_link` varchar(255) DEFAULT NULL,
  `wikipedia_link` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `runways` (
  `id` int(11) NOT NULL,
  `airport_ref` int(11) DEFAULT NULL,
  `airport_ident` varchar(45) DEFAULT NULL,
  `length_ft` int(11) DEFAULT NULL,
  `width_ft` int(11) DEFAULT NULL,
  `surface` varchar(255) DEFAULT NULL,
  `lighted` int(11) DEFAULT NULL,
  `closed` int(11) DEFAULT NULL,
  `le_ident` varchar(45) DEFAULT NULL,
  `le_latitude_deg` double DEFAULT NULL,
  `le_longitude_deg` double DEFAULT NULL,
  `le_elevation_ft` int(11) DEFAULT NULL,
  `le_heading_degT` double DEFAULT NULL,
  `le_displaced_threshold_ft` int(11) DEFAULT NULL,
  `he_ident` varchar(45) DEFAULT NULL,
  `he_latitude_deg` double DEFAULT NULL,
  `he_longitude_deg` double DEFAULT NULL,
  `he_elevation_ft` int(11) DEFAULT NULL,
  `he_heading_degT` double DEFAULT NULL,
  `he_displaced_threshold_ft` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# --- !Downs

DROP TABLE IF EXISTS `countries`;

DROP TABLE IF EXISTS `airports`;

DROP TABLE IF EXISTS `runways`;