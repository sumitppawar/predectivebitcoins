package com.tookitaki.guice

import com.google.inject.AbstractModule
import com.tookitaki.service.{BitcoinPriceHistoryService, LoadBitcoinPriceHistoryFromUrl}
import play.api.{Configuration, Environment}

//Better to use compile to time DI here!

class CustomModule ( environment: Environment, config: Configuration) extends AbstractModule {
  override def configure(): Unit = {
    val appConfig = ApplicationConfiguration.loadOrDie(config)
    bind(classOf[ApplicationConfiguration]).toInstance(appConfig)
    bind(classOf[BitcoinPriceHistoryService]).to(classOf[LoadBitcoinPriceHistoryFromUrl])
  }
}

case class ApplicationConfiguration ( bitcoinPriceHistoryUrl: String,
                                      timeOut: Long
                                    )

object ApplicationConfiguration {

  def loadOrDie(config: Configuration): ApplicationConfiguration = {
    val appConfig = for {
      bitcoinPriceHistoryUrl <- config.getOptional[String]("bitcoin.priceHistory.url")
        .toRight("Config 'bitcoin.priceHistory.url' is not found.")
      timeOut <- config.getOptional[Long]("bitcoin.priceHistory.timeoutInSecond")
        .toRight("Config 'bitcoin.priceHistory.timeoutInSecond' is not found.")
    } yield ApplicationConfiguration(bitcoinPriceHistoryUrl, timeOut)
    if(appConfig.isLeft) {

      println(
        s"""
           |Shutting down application !
           |${appConfig.left.get}
           |
         """.stripMargin)
      System.exit(0)
    }
    appConfig.right.get
  }
}