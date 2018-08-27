package com.tookitaki.models.request

import java.time.LocalDate

import com.tookitaki.utils.Dates
import play.api.mvc.{AnyContent, Request}

import scala.util.Try

case class ProcessWindowRequest(startDate: LocalDate,
                           endDate: LocalDate, window: String)

object ProcessWindowRequest {
  def unapply(request: Request[AnyContent]): Option[(LocalDate, LocalDate, Int)] =  {
    val startDateOpt = request.getQueryString("startDate").flatMap(date => Dates.parseToOption(date))
    val endDateOpt = request.getQueryString("endDate").flatMap(date => Dates.parseToOption(date))
    val windowOpt = Try(request.getQueryString("window").map(_.toInt)).toOption.flatten
    for {
      startDate <- startDateOpt
      endDate <- endDateOpt
      window <- windowOpt
    } yield (startDate, endDate, window)
  }
}
