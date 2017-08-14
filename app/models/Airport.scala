package models

import play.api.libs.json.Json

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

