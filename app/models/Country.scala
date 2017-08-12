package models

import play.api.libs.json.Json

//LOAD DATA INFILE '/var/lib/mysql-files/countries.csv' INTO TABLE countries FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'  IGNORE 1 LINES;
case class Country (
  code: String,
  name: String,
  continent: String,
  wikipediaLink: String,
  keyword: Option[String] = None
)

object Country {
  implicit val format = Json.format[Country]
}