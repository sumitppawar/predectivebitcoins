package com.tookitaki.test.helpers

import java.time.LocalDate

import com.tookitaki.models.Price
import com.tookitaki.utils.Dates._

object BitcoinPriceGenerator {

  private val today = LocalDate.now

    val startDate = today.minusDays(8)
    val endDate = today
    val formatedToday  = s"${today.getYear}-${toTwoDigit(today.getMonthValue)}-${toTwoDigit(today.getDayOfMonth)}T00:00:00Z"

    val prices:Seq[Price] = {
      val dateOne = s"${startDate.getYear}-${toTwoDigit(startDate.getMonthValue)}-${toTwoDigit(startDate.getDayOfMonth + 1 - startDate.getDayOfWeek.getValue)}T00:00:00Z"
      val dateTwo = s"${startDate.getYear}-${toTwoDigit(startDate.getMonthValue)}-${toTwoDigit(startDate.getDayOfMonth + 3)}T00:00:00Z"
      Seq(Price("5000",dateOne), Price("4000",dateTwo))
    }

  val genBuyPrice: Seq[Price] =  {
    val today  = s"${startDate.getYear}-${toTwoDigit(startDate.getMonthValue)}-${toTwoDigit(startDate.getDayOfMonth + 1 - startDate.getDayOfWeek.getValue)}T00:00:00Z"
    val dateOne = s"${startDate.getYear}-${toTwoDigit(startDate.getMonthValue)}-${toTwoDigit(startDate.getDayOfMonth + 1)}T00:00:00Z"
    val dateTwo = s"${startDate.getYear}-${toTwoDigit(startDate.getMonthValue)}-${toTwoDigit(startDate.getDayOfMonth + 3)}T00:00:00Z"
    Seq(Price("90",formatedToday), Price("4000",dateTwo))
  }

  val genHoldPrice: Seq[Price] =  {
    val today = LocalDate.now
    val dateOne = s"${startDate.getYear}-${toTwoDigit(startDate.getMonthValue)}-${toTwoDigit(startDate.getDayOfMonth + 1)}T00:00:00Z"
    val dateTwo = s"${startDate.getYear}-${toTwoDigit(startDate.getMonthValue)}-${toTwoDigit(startDate.getDayOfMonth + 3)}T00:00:00Z"
    val dateThree = s"${startDate.getYear}-${toTwoDigit(startDate.getMonthValue)}-${toTwoDigit(startDate.getDayOfMonth + 4)}T00:00:00Z"
    Seq(Price("9000",formatedToday), Price("2000",dateTwo), Price("10000", dateThree))
  }

  val genSoldPrice: Seq[Price] =  {
    val today = LocalDate.now
    val dateOne = s"${startDate.getYear}-${toTwoDigit(startDate.getMonthValue)}-${toTwoDigit(startDate.getDayOfMonth + 1)}T00:00:00Z"
    val dateTwo = s"${startDate.getYear}-${toTwoDigit(startDate.getMonthValue)}-${toTwoDigit(startDate.getDayOfMonth + 3)}T00:00:00Z"
    Seq(Price("9000",formatedToday), Price("4000",dateTwo))
  }

}
