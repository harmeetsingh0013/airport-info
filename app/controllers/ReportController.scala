package controllers

import javax.inject.Inject

import com.typesafe.config.ConfigFactory
import play.api.Logger
import play.api.mvc.{AbstractController, ControllerComponents}
import repo.AirportRepo
import utils.Utility.createJsonResponse

import scala.concurrent.ExecutionContext
import scala.util.Try

class ReportController @Inject()(airportRepo: AirportRepo, cc: ControllerComponents)
                                (implicit ec: ExecutionContext) extends AbstractController(cc) {

  private val LIMIT = Try(ConfigFactory.load().getInt("page.limit")).toOption.getOrElse(10);

  def countriesNumberOfAirports(severity: String) = Action.async {
    Logger.info("countriesNumberOfAirports action performed")

    val order = if(severity.trim.equalsIgnoreCase("high")) "DESC" else "ASC"
    airportRepo.countriesWithHighestNumberOfAirport(LIMIT, order).map { result =>
      Ok(createJsonResponse(data = result))
    }.recover {
      case ex =>
        Logger.error("unable to find airports count", ex)
        InternalServerError(createJsonResponse(status = false, msg = "unable to find airports count", data = -1))
    }
  }

  def countriesRunways(countriesLimit: Int) = Action.async {
    Logger.info("countriesRunways action performed")

    airportRepo.countriesRunway(countriesLimit).map { result =>
      Ok(createJsonResponse(data = result))
    }.recover {
      case ex =>
        Logger.error("unable to find countries runways", ex)
        InternalServerError(createJsonResponse(status = false, msg = "unable to find countries runways", data = -1))
    }
  }

  def runwayCommonIdentification(page: Int) = Action.async {
    Logger.info("runwayCommonIdentification action performed")

    val offset = (page - 1) * LIMIT
    airportRepo.runwaysCommonIdentifications(offset, LIMIT).map { result =>
      Ok(createJsonResponse(data = result))
    }.recover {
      case ex =>
        Logger.error("unable to find most common runways identifications", ex)
        InternalServerError(createJsonResponse(status = false, msg = "unable to find most common runways identifications", data = -1))
    }
  }
}
