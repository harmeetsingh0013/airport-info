package dtos

import play.api.libs.json.Json

case class CountryAirports (
  countryCode: String,
  countryName: String,
  airportCount: Int
)

object CountryAirports {
  implicit val format = Json.format[CountryAirports]
}
