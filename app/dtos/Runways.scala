package dtos

import play.api.libs.json.Json

case class Runways (
  surface: String,
  leIdent: String,
  count: Int
)

object Runways {
  implicit val format = Json.format[Runways]
}
