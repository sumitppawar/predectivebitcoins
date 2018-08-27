package controllers
import com.tookitaki.models.BitcoinPriceHistory
import com.tookitaki.models.request.{ProcessRequest, ProcessWindowRequest}
import com.tookitaki.service.BitcoinPriceHistoryService
import javax.inject.{Inject, Singleton}
import play.api.Logger
import play.api.libs.json._
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

@Singleton
class BitcoinController @Inject()(cc: ControllerComponents,
                                  bitcoinPriceHistoryService: BitcoinPriceHistoryService
                                 ) extends AbstractController(cc) {
  private  val logger = Logger(this.getClass())

  def history = Action { implicit request: Request[AnyContent] =>
    request match {
      case ProcessRequest(Some(period), _, _) if(period == "week") =>
        processResponse(bitcoinPriceHistory => bitcoinPriceHistory.thisWeek)
      case ProcessRequest(Some(period), _, _) if(period == "month") =>
        processResponse(bitcoinPriceHistory =>  bitcoinPriceHistory.thisMonth)
      case ProcessRequest(Some(period), _, _) if(period == "year") =>
        processResponse(bitcoinPriceHistory =>  bitcoinPriceHistory.thisYear)
      case ProcessRequest(Some(period), _, _) if(period == "all") =>
        processResponse(bitcoinPriceHistory =>  bitcoinPriceHistory)
      case ProcessRequest(None, Some(startDate), Some(endDate)) =>
        processResponse(bitcoinPriceHistory =>  bitcoinPriceHistory.byDate(startDate, endDate))
      case _ =>
        logger.error("Error while processing price history because of invalid period.")
        Ok(s"{'erro': 'Invalid period!'}")
    }
  }

  def average = Action { implicit request: Request[AnyContent] =>
    request match {
      case ProcessRequest(None, Some(startDate), Some(endDate)) =>
        processResponse(bitcoinPriceHistory => bitcoinPriceHistory.movement(startDate,endDate) )
      case _ => Ok(s"{'erro': 'Invalid period!'}")
    }
  }

  def predict = Action { implicit request: Request[AnyContent] =>
    request match {
      case ProcessRequest(None, Some(startDate), Some(endDate)) =>
        processResponse(bitcoinPriceHistory => bitcoinPriceHistory.predict(startDate,endDate) )
      case _ => Ok(s"{'erro': 'Invalid period!'}")
    }
  }

  def window = Action { implicit request: Request[AnyContent] =>
    request match {
      case ProcessWindowRequest(startDate, endDate,  window) =>
        processResponse(bitcoinPriceHistory => bitcoinPriceHistory.getMaxPriceByWindow(startDate,endDate, window) )
      case _ =>   Ok(s"{'erro': 'Invalid period! or Window'}")
    }
  }



  private  def processResponse[T](f: BitcoinPriceHistory => T)(implicit formate: Format[T]) = {
    bitcoinPriceHistoryService.getHistory
      .fold(
        error =>{
          logger.error(s"Error while getting history data . \n ${error.msg}")
          Ok(s"{'erro': ${error.msg} }")
        },
        data => Ok(Json.toJson(f(data)))
      )
  }
}
