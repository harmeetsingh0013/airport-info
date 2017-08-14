package controllers

import akka.stream.Materializer
import dtos.{AirportRunways, CountryAirports, Runways}
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Injecting}
import repo.AirportRepo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ReportControllerSpec extends PlaySpec with GuiceOneAppPerSuite with MockitoSugar with Injecting {

  implicit lazy val materializer: Materializer = app.materializer

  "ReportController" should {
    "fetch number of airports in countries" in {
      val mockResult = Vector(
        CountryAirports("AO", "Angola", 3),
        CountryAirports("BF", "Burkina Faso", 2)
      )

      val airportRepo = mock[AirportRepo]
      when(airportRepo.countriesWithHighestNumberOfAirport(10, "DESC"))
        .thenReturn(Future.successful(mockResult))

      val controller = new ReportController(airportRepo, stubControllerComponents())
      val airports = controller.countriesNumberOfAirports("high").apply(FakeRequest(GET, "/country/airport-counts?severity=high"))

      val expectedResult = Json.toJson(mockResult).toString()

      status(airports) mustBe OK
      contentType(airports) mustBe Some("application/json")
      contentAsString(airports) must include(expectedResult)
    }

    "fetch number of airports in countries return internal server error" in {
      val airportRepo = mock[AirportRepo]
      when(airportRepo.countriesWithHighestNumberOfAirport(10, "DESC")).thenReturn(Future.failed(new Exception))

      val controller = new ReportController(airportRepo, stubControllerComponents())
      val airports = controller.countriesNumberOfAirports("high").apply(FakeRequest(GET, "/country/airport-counts?severity=high"))

      val expectedResult = """{"status":false,"message":"unable to find airports count","data":-1}"""

      status(airports) mustBe INTERNAL_SERVER_ERROR
      contentType(airports) mustBe Some("application/json")
      contentAsString(airports) must include(expectedResult)
    }

    "fetch number of airports in countries in low with return internal server error" in {
      val airportRepo = mock[AirportRepo]
      when(airportRepo.countriesWithHighestNumberOfAirport(10, "ASC")).thenReturn(Future.failed(new Exception))

      val controller = new ReportController(airportRepo, stubControllerComponents())
      val airports = controller.countriesNumberOfAirports("low").apply(FakeRequest(GET, "/country/airport-counts?severity=low"))

      val expectedResult = """{"status":false,"message":"unable to find airports count","data":-1}"""

      status(airports) mustBe INTERNAL_SERVER_ERROR
      contentType(airports) mustBe Some("application/json")
      contentAsString(airports) must include(expectedResult)
    }

    "fetch country runways" in {
      val mockResult = Vector (
        AirportRunways(countryCode = "AO", countryName = "Angola", runwaySurface = Some("ASP")),
        AirportRunways(countryCode = "BF", countryName = "Burkina Faso", runwaySurface = Some("LAT")),
        AirportRunways(countryCode = "BF", countryName = "Burkina Faso", runwaySurface = Some("ASP"))
      )

      val airportRepo = mock[AirportRepo]
      when(airportRepo.countriesRunway(5)).thenReturn(Future.successful(mockResult))

      val controller = new ReportController(airportRepo, stubControllerComponents())
      val runways = controller.countriesRunways(5).apply(FakeRequest(GET, "/country/runways?countriesLimit=5"))

      val expectedResult = Json.toJson(mockResult).toString()

      status(runways) mustBe OK
      contentType(runways) mustBe Some("application/json")
      contentAsString(runways) must include(expectedResult)
    }

    "fetch country runways return internal server error" in {
      val airportRepo = mock[AirportRepo]
      when(airportRepo.countriesRunway(5)).thenReturn(Future.failed(new Exception))

      val controller = new ReportController(airportRepo, stubControllerComponents())
      val runways = controller.countriesRunways(5).apply(FakeRequest(GET, "/country/runways?countriesLimit=5"))

      val expectedResult = """{"status":false,"message":"unable to find countries runways","data":-1}"""

      status(runways) mustBe INTERNAL_SERVER_ERROR
      contentType(runways) mustBe Some("application/json")
      contentAsString(runways) must include(expectedResult)
    }

    "runway common identification" in {
      val mockResult = Vector (
        Runways("ASP", "10", 1),
        Runways("ASP", "08", 1),
        Runways("ASP", "16", 1),
        Runways("ASP", "04L", 1),
        Runways("ASP", "06", 1),
        Runways("ASP", "07", 1),
        Runways("LAT", "04R", 1)
      )

      val airportRepo = mock[AirportRepo]
      when(airportRepo.runwaysCommonIdentifications(0, 10)).thenReturn(Future.successful(mockResult))

      val controller = new ReportController(airportRepo, stubControllerComponents())
      val identification = controller.runwayCommonIdentification(1).apply(FakeRequest(GET, "/runways/identifications?page=1"))

      val expectedResult = Json.toJson(mockResult).toString()

      status(identification) mustBe OK
      contentType(identification) mustBe Some("application/json")
      contentAsString(identification) must include(expectedResult)
    }

    "runway common identification return internal server error" in {
      val airportRepo = mock[AirportRepo]
      when(airportRepo.runwaysCommonIdentifications(0, 10)).thenReturn(Future.failed(new Exception))

      val controller = new ReportController(airportRepo, stubControllerComponents())
      val identification = controller.runwayCommonIdentification(1).apply(FakeRequest(GET, "/runways/identifications?page=1"))

      val expectedResult = """{"status":false,"message":"unable to find most common runways identifications","data":-1}"""

      status(identification) mustBe INTERNAL_SERVER_ERROR
      contentType(identification) mustBe Some("application/json")
      contentAsString(identification) must include(expectedResult)
    }
  }
}
