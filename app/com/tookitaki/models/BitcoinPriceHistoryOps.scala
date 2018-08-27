package com.tookitaki.models

import java.time.LocalDate

import com.tookitaki.utils.Dates

class BitcoinPriceHistoryOps(history: BitcoinPriceHistory) {

  def thisWeek: BitcoinPriceHistory = {
    val today = LocalDate.now
    val startDayOfWeek = LocalDate.parse(s"${today.getYear}-${Dates.toTwoDigit(today.getMonthValue)}-${Dates.toTwoDigit(today.getDayOfMonth + 1 - today.getDayOfWeek.getValue)}")
    val newBitcoinPrices = history.prices.filter(price => {
      val parsedDate = Dates.parse(price.time)
      parsedDate.isAfter(startDayOfWeek) || parsedDate.isEqual(startDayOfWeek)
    })
    history.copy(prices = newBitcoinPrices)
  }

  def thisMonth: BitcoinPriceHistory = {
    val today = LocalDate.now
    val startDateOfMonth = LocalDate.of(today.getYear, today.getMonth, 1)
    val newBitcoinPrices = history.prices.filter(price => {
      val parsedDate = Dates.parse(price.time)
      parsedDate.isAfter(startDateOfMonth) || parsedDate.isEqual(startDateOfMonth)
    })
    history.copy(prices = newBitcoinPrices)
  }

  def thisYear: BitcoinPriceHistory = {
    val today = LocalDate.now
    val startDateOfYear = LocalDate.of(today.getYear, 1, 1)
    val newBitcoinPrices = history.prices.filter(price => {
        val parsedDate = Dates.parse(price.time)
      parsedDate.isAfter(startDateOfYear) || parsedDate.isEqual(startDateOfYear)
      }
    )
    history.copy(prices = newBitcoinPrices)
  }

  def byDate(startDate: LocalDate, endDate: LocalDate): BitcoinPriceHistory = {
    val newBitcoinPrices = history.prices.filter( price => {
      val parsedDate = Dates.parse(price.time)
      (parsedDate.isAfter(startDate)  || parsedDate.isEqual(startDate)) &&
        parsedDate.isBefore(endDate)
      }
    )
    history.copy(prices = newBitcoinPrices)
  }
  def predict(startDate:LocalDate, endDate: LocalDate): Prediction = {
    val filteredBitcoinPrice = byDate(startDate, endDate)
    val filteredPrices = filteredBitcoinPrice.prices.map(_.price.toDouble)
    val max = filteredPrices.max
    val average = (filteredPrices.fold(0.0)((a, v) => a+v))/filteredPrices.length
    val todaysBitcoinPrice = thisDay.prices(0).price.toDouble

    todaysBitcoinPrice match {
      case _ if(todaysBitcoinPrice >= max) => Prediction(Prediction.SOLD)
      case _ if(todaysBitcoinPrice < max && todaysBitcoinPrice >= average) => Prediction(Prediction.HOLD)
      case _ if(todaysBitcoinPrice < average) => Prediction(Prediction.BUY)
    }
  }

  def thisDay: BitcoinPriceHistory = {
    val today = LocalDate.now
    val newPrices = history.prices.filter(price => {
      val parsedDate = Dates.parse(price.time)
      parsedDate.isEqual(today)
      }
    )
    history.copy(prices = newPrices)
  }

  def movement(startDate:LocalDate, endDate: LocalDate): PriceMovement =
    byDate(startDate, endDate)
    .prices
      .map(_.price.toDouble) match {
      case Nil => PriceMovement(0,0,0)
      case prices =>
        val max = prices.max
        val average = (prices.fold(0.0)((a, v) => a+v))/prices.length
        val min = prices.min
        PriceMovement(max,average,min)
    }


  def getMaxPriceByWindow(startDate:LocalDate, endDate: LocalDate, windowSize: Int) : Seq[Price] =
    byDate(startDate, endDate).prices.grouped(windowSize).
      toSeq
      .map(_.max)
}

