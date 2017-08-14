package dtos

import play.api.libs.json.Json

case class AirportRunways (
  countryCode: String,
  countryName: String,
  airportIdent: Option[String] = None,
  airportName: Option[String] = None,
  airportType: Option[String] = None,
  runwayLengthFt: Option[Int] = None,
  runwayWidthFt: Option[Int] = None,
  runwaySurface: Option[String] = None
)

object AirportRunways {
  implicit val format = Json.format[AirportRunways]
}