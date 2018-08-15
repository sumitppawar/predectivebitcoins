package com.tookitaki.service

import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Injecting

class LoadBitcoinPriceHistoryFromUrlSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {


  case class Fixture(bitcoinPriceHistoryService: BitcoinPriceHistoryService)

  private def fixture(f: Fixture => Unit ): Unit = {
  }
}
