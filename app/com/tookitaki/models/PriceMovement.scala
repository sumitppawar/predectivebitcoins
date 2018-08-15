package com.tookitaki.models

import play.api.libs.json.Json

case class PriceMovement(max: Double, average: Double, min: Double)

 object PriceMovement {
   implicit val priceMovementFormat = Json.format[PriceMovement]
 }
