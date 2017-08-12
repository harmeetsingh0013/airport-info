package models

import play.api.libs.json.Json

/*
* LOAD DATA INFILE '/var/lib/mysql-files/runways.csv' INTO TABLE runways FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"' IGNORE 1 LINES
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
* */
case class Runway (
  id: Int,
  airportRef: Int,
  airportIdent: String,
  lengthFt: Option[Int] = None,
  widthFt: Option[Int] = None,
  surface: Option[String] = None,
  lighted: Int,
  closed: Int,
  leIdent: Option[String] = None,
  leLatitudeDeg: Option[Double] = None,
  leLongitudeDeg: Option[Double] = None,
  leElevationFt: Option[Int] = None,
  leHeadingDegT: Option[Double] = None,
  leDisplacedThresholdFt: Option[Int] = None,
  heIdent: Option[String] = None,
  heLatitudeDeg: Option[Double] = None,
  heLongitudeDeg: Option[Double] = None,
  heElevationFt: Option[Int] = None,
  heHeadingDegT: Option[Double] = None,
  heDisplacedThresholdFt: Option[Int] = None
)

object Runway{
  implicit val format = Json.format[Runway]
}
