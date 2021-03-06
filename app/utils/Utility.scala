package utils

import play.api.libs.json.{Json, Writes}

object Utility {

  def createJsonResponse[T](status: Boolean = true, msg: String = "done", data: T)(implicit writes: Writes[T]): _root_.play.api.libs.json.JsValue =
    Json.obj("status" -> status, "message" -> msg, "data" -> Json.toJson[T](data))
}
