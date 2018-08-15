package com.tookitaki.models

import java.time.LocalDate

import com.tookitaki.test.helpers.BitcoinPriceGenerator
import org.scalatest.{FlatSpec, Matchers}

class BitcoinPriceHistoryOpsSpec extends FlatSpec with Matchers {

  "thisWeek" should "return BitcoinPriceHistory for current week" in fixure { fixure =>
    import fixure._
    bitcoinPriceHistory.thisWeek.prices.length shouldBe 1
  }

  "thisMonth" should "return BitcoinPriceHistory for current week" in fixure { fixure =>
    import fixure._
    bitcoinPriceHistory.thisMonth.prices.length shouldBe 2
  }

  "thisYear" should "return BitcoinPriceHistory for current week" in fixure { fixure =>
    import fixure._
    bitcoinPriceHistory.thisYear.prices.length shouldBe 2
  }

  "byDate" should "return BitcoinPriceHistory for given date range" in fixure { fixure =>
    import fixure._
    import BitcoinPriceGenerator._
    bitcoinPriceHistory.byDate(startDate, endDate).prices.length shouldBe 1
  }

  private case class Fixture(bitcoinPriceHistory: BitcoinPriceHistory)

  private  def fixure(f: Fixture => Unit) = {
    val bitcoinPriceHistory = BitcoinPriceHistory("BTC", "USD", BitcoinPriceGenerator.prices)
    val fixture = Fixture(bitcoinPriceHistory)
    f(fixture)
  }

}
