package com.tookitaki.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import scala.util.Try

object Dates {

  def parse(date: String) : LocalDate =
    LocalDate.parse(date, DateTimeFormatter.ISO_DATE_TIME)

  def parseToOption(date: String) : Option[LocalDate] =
    Try(LocalDate.parse(date)).toOption

  def toTwoDigit(n: Int) = if(n>=10) n.toString else s"0$n"
}
