package models

import play.api.libs.json.{JsValue, Json}

case class ApiResult(error: Option[String] = None, message: Option[String] = None, result: Option[JsValue] = None)

object ApiResult {
  implicit val format = Json.format[ApiResult]
}
