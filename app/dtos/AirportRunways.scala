package dtos

import play.api.libs.json.Json

case class AirportRunways (
  countryCode: String,
  countryName: String,
  airportIdent: String,
  airportName: String,
  airportType: String,
  runwayLengthFt: Option[Int] = None,
  runwayWidthFt: Option[Int] = None,
  runwaySurface: Option[String] = None
)

object AirportRunways {
  implicit val format = Json.format[AirportRunways]
}