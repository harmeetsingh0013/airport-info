package models

import play.api.libs.json.Json

/*
* LOAD DATA INFILE '/var/lib/mysql-files/airports.csv' INTO TABLE airports FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'  IGNORE 1 LINES
(id, ident, type, name, latitude_deg, longitude_deg, @elevation_ft, continent, continent, iso_region, municipality, scheduled_service, gps_code, iata_code, local_code, home_link, wikipedia_link, keywords)
SET elevation_ft = nullif(@elevation_ft, '');
* */
case class Airport (
  id: Int,
  ident: String,
  `type`: String,
  name: String,
  latitudeDeg: Double,
  longitudeDeg: Double,
  elevationFt: Option[Int] = None,
  continent: String,
  isoCountry: String,
  isoRegion: String,
  municipality: Option[String] = None,
  scheduledService: String,
  gpsCode: Option[String] = None,
  iataCode: Option[String] = None,
  localCode: Option[String] = None,
  homeLink: Option[String] = None
)

object Airport {
  implicit val format = Json.format[Airport]
}
