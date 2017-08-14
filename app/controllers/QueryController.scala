package controllers

import javax.inject.{Inject, Singleton}

import com.typesafe.config.ConfigFactory
import play.api.Logger
import play.api.mvc.{AbstractController, ControllerComponents}
import repo.AirportRepo
import utils.Utility.createJsonResponse

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

@Singleton
class QueryController @Inject()(airportRepo: AirportRepo, cc: ControllerComponents)
                               (implicit ec: ExecutionContext) extends AbstractController(cc) {

  private val LIMIT = Try(ConfigFactory.load().getInt("page.limit")).toOption.getOrElse(10);

  def findCountryAirportRunways(name: Option[String], code: Option[String], page: Int) = Action.async {
    Logger.info("findCountryAirportRunways action performed")

    if(name.isEmpty && code.isEmpty) {
      Future.successful(PreconditionFailed(createJsonResponse(status = false, msg = "need to pass company code or name", data = -1)))
    }else {
      val offset = (page - 1) * LIMIT
      val future = airportRepo.findAirportRunwaysByCountryNameOrCode(name, code, offset, LIMIT)
      future.map { result =>
        Ok(createJsonResponse(data = result))
      }.recover {
        case ex =>
          Logger.error("unable to insert new company", ex)
          InternalServerError(createJsonResponse(status = false, msg = "unable to fetch airport runways", data = -1))
      }
    }
  }
}
