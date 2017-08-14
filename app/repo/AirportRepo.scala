package repo

import dtos.{AirportRunways, CountryAirports, Runways}

import scala.concurrent.Future

trait AirportRepo {

  def findAirportRunwaysByCountryNameOrCode(name: Option[String], code: Option[String],
                                            offset: Int, limit: Int): Future[Vector[AirportRunways]]
  def countriesWithHighestNumberOfAirport(limit: Int, order: String): Future[Vector[CountryAirports]]
  def countriesRunway(countriesLimit: Int): Future[Vector[AirportRunways]]
  def runwaysCommonIdentifications(offset: Int, limit: Int): Future[Vector[Runways]]
}
