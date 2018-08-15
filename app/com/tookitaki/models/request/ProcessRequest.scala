package com.tookitaki.models.request

import java.time.LocalDate

import com.tookitaki.utils.Dates
import play.api.mvc.{AnyContent, Request}

case class ProcessRequest(period:Option[String],
                          startDate: Option[LocalDate],
                          endDate: Option[LocalDate]
                              )

object  ProcessRequest {
  def unapply(request: Request[AnyContent]): Option[(Option[String], Option[LocalDate], Option[LocalDate])] = {
    val period = request.getQueryString("period").map(_.toLowerCase)
    val startDate = request.getQueryString("startDate").flatMap(date => Dates.parseToOption(date))
    val endDate = request.getQueryString("endDate").flatMap(date => Dates.parseToOption(date))
    Some(period,startDate,endDate)
  }
}
