package models

import play.api.libs.json.Json

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
