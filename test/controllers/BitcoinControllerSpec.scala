package controllers

import java.time.LocalDate

import com.tookitaki.models.{BitcoinPriceHistory, Prediction, Price}
import com.tookitaki.service.BitcoinPriceHistoryService
import com.tookitaki.test.helpers.BitcoinPriceGenerator
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Helpers.{GET, status, stubControllerComponents}
import play.api.test.{FakeRequest, Injecting}
import play.api.test.Helpers._

class BitcoinControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {

  "GET history" should  {
    "for period 'all' should return all bitcoin history"  in fixture { fixture =>
      import  fixture._
      val history = bitcoinController.history.apply(FakeRequest(GET, "api/v1/bitcoin/usd/price/history?period=all"))
      status(history) mustBe OK
      contentAsString(history) must include ("USD")
    }

    "for period 'week' should return week bitcoin history" in fixture { fixture =>
      import  fixture._
      val history = bitcoinController.history.apply(FakeRequest(GET, "api/v1/bitcoin/usd/price/history?period=week"))
      status(history) mustBe OK
      contentAsString(history) must include ("USD")
    }

    "for period 'month' should return month bitcoin history" in fixture { fixture =>
      import  fixture._
      val history = bitcoinController.history.apply(FakeRequest(GET, "api/v1/bitcoin/usd/price/history?period=month"))
      status(history) mustBe OK
      contentAsString(history) must include ("USD")
    }

    "for period 'year' should return all year history" in fixture { fixture =>
      import  fixture._
      val history = bitcoinController.history.apply(FakeRequest(GET, "api/v1/bitcoin/usd/price/history?period=year"))
      status(history) mustBe OK
      contentAsString(history) must include ("USD")
    }

    "for given startDate and endDate should return history" in fixture { fixture =>
      import  fixture._
      val history = bitcoinController.history.apply(FakeRequest(GET, "api/v1/bitcoin/usd/price/history?startDate=2018-08-02&endDate=2018-08-15"))
      status(history) mustBe OK
      contentAsString(history) must include ("USD")
    }
  }

  "GET average" should {
    "should return max, min and average" in fixture { fixture =>
      import  fixture._
      val startDate = LocalDate.now.toString
      val endDate = LocalDate.now.plusDays(1)
      val average = bitcoinController.average.apply(FakeRequest(GET, s"api/v1/bitcoin/usd/price/average?startDate=${startDate}&endDate=${endDate}"))
      status(average) mustBe OK
      contentAsString(average) must include ("min")
      contentAsString(average) must include ("max")
      contentAsString(average) must include ("average")
    }
  }

  "GET predict" should {
    import BitcoinPriceGenerator._
    val queryParam = s"?startDate=${startDate}&endDate=${endDate}"

    s"should predict ${Prediction.BUY}" in {
      val bitcoinPriceHistoryService = mock[BitcoinPriceHistoryService]
      when(bitcoinPriceHistoryService.getHistory) thenReturn Right(BitcoinPriceHistory("BTC", "USD", BitcoinPriceGenerator.genBuyPrice))
      val bitcoinController = new BitcoinController(stubControllerComponents(), bitcoinPriceHistoryService)

      val predict = bitcoinController.predict.apply(FakeRequest(GET, s"/api/v1/bitcoin/usd/predict${queryParam}"))
      status(predict) mustBe OK
      contentAsString(predict) must include (Prediction.BUY)
    }

    s"should predict ${Prediction.SOLD}" in {
      val bitcoinPriceHistoryService = mock[BitcoinPriceHistoryService]
      when(bitcoinPriceHistoryService.getHistory) thenReturn Right(BitcoinPriceHistory("BTC", "USD", BitcoinPriceGenerator.genSoldPrice))
      val bitcoinController = new BitcoinController(stubControllerComponents(), bitcoinPriceHistoryService)

      val predict = bitcoinController.predict.apply(FakeRequest(GET, s"/api/v1/bitcoin/usd/predict${queryParam}"))
      status(predict) mustBe OK
      contentAsString(predict) must include (Prediction.SOLD)

    }
    s"should predict ${Prediction.HOLD}" in {
      val bitcoinPriceHistoryService = mock[BitcoinPriceHistoryService]
      when(bitcoinPriceHistoryService.getHistory) thenReturn Right(BitcoinPriceHistory("BTC", "USD", BitcoinPriceGenerator.genHoldPrice))
      val bitcoinController = new BitcoinController(stubControllerComponents(), bitcoinPriceHistoryService)

      val predict = bitcoinController.predict.apply(FakeRequest(GET, s"/api/v1/bitcoin/usd/predict${queryParam}"))
      status(predict) mustBe OK
      contentAsString(predict) must include (Prediction.HOLD)
    }
  }

  case class Fixture(bitcoinController: BitcoinController)

  private def fixture(f: Fixture => Unit): Unit = {

    val bitcoinPriceHistoryService = mock[BitcoinPriceHistoryService]
    when(bitcoinPriceHistoryService.getHistory) thenReturn Right(BitcoinPriceHistory("BTC", "USD", Seq.empty[Price]))
    val bitcoinController = new BitcoinController(stubControllerComponents(), bitcoinPriceHistoryService)
    val fixture = new Fixture(bitcoinController)
    f(fixture)
  }

}



