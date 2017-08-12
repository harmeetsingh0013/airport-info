package repo

import dtos.AirportRunways

import scala.concurrent.Future

trait AirportRepo {

  def findAirportRunwaysByCountryNameOrCode(name: Option[String], code: Option[String],
                                            offset: Int, limit: Int): Future[Vector[AirportRunways]]
}
