package com.tookitaki.models

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import play.api.libs.json._

case class BitcoinPriceHistory(base: String, currency: String, prices: Seq[Price])
case class Price(price: String, time: String)

object BitcoinPriceHistory {
  LocalDate.parse("2013-01-08T00:00:00Z", DateTimeFormatter.ISO_DATE_TIME)
  implicit val priceJsonFormat = Json.format[Price]
  implicit  val bitcoinPriceHistoryJsonFormat = Json.format[BitcoinPriceHistory]

  implicit def toBitcoinPriceHistoryOps(bitcoinPriceHistory: BitcoinPriceHistory): BitcoinPriceHistoryOps =
    new BitcoinPriceHistoryOps(bitcoinPriceHistory)

}



