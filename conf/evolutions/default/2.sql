
# --- !Ups

LOAD DATA INFILE '/var/lib/mysql-files/countries.csv' INTO TABLE countries FIELDS TERMINATED BY ','
OPTIONALLY ENCLOSED BY '"'  IGNORE 1 LINES;

LOAD DATA INFILE '/var/lib/mysql-files/airports.csv' INTO TABLE airports FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'  IGNORE 1 LINES
(id, ident, type, name, latitude_deg, longitude_deg, @elevation_ft, continent, continent, iso_region, municipality, scheduled_service, gps_code, iata_code, local_code, home_link, wikipedia_link, keywords)
SET elevation_ft = nullif(@elevation_ft, '');

LOAD DATA INFILE '/var/lib/mysql-files/runways.csv' INTO TABLE runways FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"' IGNORE 1 LINES
(id, airport_ref, airport_ident, @length_ft, @width_ft, surface, lighted, closed, le_ident, @le_latitude_deg, @le_longitude_deg, @le_elevation_ft, @le_heading_degT, @le_displaced_threshold_ft, he_ident, @he_latitude_deg, @he_longitude_deg, @he_elevation_ft, @he_heading_degT, @he_displaced_threshold_ft)
SET	length_ft = nullif(@length_ft,''),
	width_ft = nullif(@width_ft,''),
	le_latitude_deg = nullif(@le_latitude_deg,''),
	le_longitude_deg = nullif(@le_longitude_deg, ''),
	le_elevation_ft = nullif(@le_elevation_ft, ''),
	le_heading_degT = nullif(@le_heading_degT,''),
	le_displaced_threshold_ft = nullif(@le_displaced_threshold_ft, ''),
	he_latitude_deg = nullif(@he_latitude_deg, ''),
	he_longitude_deg = nullif(@he_longitude_deg, ''),
	he_elevation_ft = nullif(@he_elevation_ft, ''),
	he_heading_degT = nullif(@he_heading_degT,''),
	he_displaced_threshold_ft = nullif(@he_displaced_threshold_ft, '');