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

object Price {
  implicit val ordering = new Ordering[Price] {
    override def compare(x: Price, y: Price): Int  =
      if(x.price.toDouble > y.price.toDouble)
        1
      else if (x.price.toDouble < y.price.toDouble)
        -1
      else 0
  }

  implicit val priceJsonFormat = Json.format[Price]
}


