package com.tookitaki.models

import play.api.libs.json._

case class BitcoinPriceHistory(base: String, currency: String, prices: Seq[Price])
case class Price(price: String, time: String)

object BitcoinPriceHistory {
  implicit val priceJsonFormat = Json.format[Price]
  implicit  val bitcoinPriceHistoryJsonFormat = Json.format[BitcoinPriceHistory]

  implicit def toBitcoinPriceHistoryOps(bitcoinPriceHistory: BitcoinPriceHistory): BitcoinPriceHistoryOps =
    new BitcoinPriceHistoryOps(bitcoinPriceHistory)

}



