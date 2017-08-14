package controllers

import akka.stream.Materializer
import dtos.AirportRunways
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Injecting}
import repo.AirportRepo
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class QueryControllerSpec extends PlaySpec with GuiceOneAppPerSuite with MockitoSugar with Injecting {

  implicit lazy val materializer: Materializer = app.materializer

  "QueryController" should {
    "fetch country airports runways" in {
      val mockResult = Vector(
        AirportRunways("AO", "Angola", Some("FN18"), Some("Matala Airport"), Some("small_airport"), Some(8015), Some(98), Some("ASP")),
        AirportRunways("AO", "Angola", Some("FN19"), Some("Cabo Ledo Airport"), Some("small_airport"), Some(9830), Some(148), Some("ASP")),
        AirportRunways("AO", "Angola", Some("FNBC"), Some("Mbanza Congo Airport"), Some("medium_airport"), Some(5905), Some(98), Some("ASP"))
      )
      val airportRepo = mock[AirportRepo]
      when(airportRepo.findAirportRunwaysByCountryNameOrCode(None, Some("AO"), 0, 10))
        .thenReturn(Future.successful(mockResult))

      val controller = new QueryController(airportRepo, stubControllerComponents())
      val runways = controller.findCountryAirportRunways(None, Some("AO"), 1).apply(FakeRequest(GET, "/airport-runways"))

      val expectedResult = Json.toJson(mockResult).toString()

      status(runways) mustBe OK
      contentType(runways) mustBe Some("application/json")
      contentAsString(runways) must include(expectedResult)
    }

    "precondition fail expected" in {
      val airportRepo = mock[AirportRepo]
      val controller = new QueryController(airportRepo, stubControllerComponents())
      val runways = controller.findCountryAirportRunways(None, None, 1).apply(FakeRequest(GET, "/airport-runways"))

      val expectedResult = """{"status":false,"message":"need to pass company code or name","data":-1}"""

      status(runways) mustBe PRECONDITION_FAILED
      contentType(runways) mustBe Some("application/json")
      contentAsString(runways) must include(expectedResult)
    }

    "internal server error expected" in {
      val airportRepo = mock[AirportRepo]
      when(airportRepo.findAirportRunwaysByCountryNameOrCode(None, Some("AD"), 0, 10))
        .thenReturn(Future.failed(new Exception))

      val controller = new QueryController(airportRepo, stubControllerComponents())
      val runways = controller.findCountryAirportRunways(None, Some("AD"), 1)
        .apply(FakeRequest(GET, "/airport-runways"))

      val expectedResult = """{"status":false,"message":"unable to fetch airport runways","data":-1}"""

      status(runways) mustBe INTERNAL_SERVER_ERROR
      contentType(runways) mustBe Some("application/json")
      contentAsString(runways) must include(expectedResult)
    }
  }
}
