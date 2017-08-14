package models

import play.api.libs.json.Json

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
