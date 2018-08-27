package com.tookitaki.models

import play.api.libs.json.Json

case class Prediction(value: String)

object Prediction {
  val BUY = "BUY"
  val SOLD = "SOLD"
  val HOLD = "HOLD"

  implicit  val jsonFormat = Json.format[Prediction]
}


