package com.tookitaki.service

import com.google.inject.Singleton
import com.tookitaki.guice.ApplicationConfiguration
import com.tookitaki.models.BitcoinPriceHistory
import javax.inject.Inject
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.WSClient

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.Try

trait BitcoinPriceHistoryService {
  def getHistory: Either[BitcoinPriceHistoryServiceFailure, BitcoinPriceHistory]
}

@Singleton
class LoadBitcoinPriceHistoryFromUrl @Inject()(ws: WSClient, config: ApplicationConfiguration)
  extends BitcoinPriceHistoryService {

  override def getHistory: Either[BitcoinPriceHistoryServiceFailure, BitcoinPriceHistory] = {
    for {
      json <-  makeRequest(config.bitcoinPriceHistoryUrl, config.timeOut)
      history <- parseJson(json)
    } yield  history
  }

  private def makeRequest(url: String, waitTime: Long): Either[BitcoinPriceHistoryServiceFailure, JsValue] =
    Try {
      val json = Await.result(ws.url(url).get, waitTime seconds).json
      (json \ "data").get
    }.toEither
      .left
      .map(t => BitcoinPriceHistoryServiceFailure(s"Failure while making request ${t.getMessage})"))

 private def parseJson(json: JsValue):Either[BitcoinPriceHistoryServiceFailure, BitcoinPriceHistory] =
    Try(Json.fromJson[BitcoinPriceHistory](json).get)
      .toEither
      .left
      .map(t => BitcoinPriceHistoryServiceFailure(s"Request failure ${t.getMessage})"))
}

case class BitcoinPriceHistoryServiceFailure(msg: String)


