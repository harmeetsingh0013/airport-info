package repo

import akka.actor.ActorSystem
import dtos.{AirportRunways, CountryAirports}
import org.scalatest.{AsyncFlatSpec, AsyncWordSpec, BeforeAndAfterAll, Matchers}
import org.scalatestplus.play.PlaySpec
import play.api.db.evolutions.Evolutions
import play.api.db.{Database, Databases}
import play.api.inject.{Injector, bind}
import play.api.inject.guice.GuiceInjectorBuilder
import repo.impl.AirportRepoImpl

class AirportRepoSpec extends AsyncWordSpec with Matchers with BeforeAndAfterAll {

  private var database: Database = _
  private var guice: Injector =  _
  private var repo: AirportRepo = _

  override protected def beforeAll(): Unit = {
    database = Databases.inMemory(name = "test", urlOptions = Map("MODE" -> "MYSQL"))

    guice = new GuiceInjectorBuilder()
      .overrides(bind[Database].toInstance(database))
      .overrides(bind(classOf[AirportRepo]).to(classOf[AirportRepoImpl]))
      .overrides(bind[ActorSystem].toInstance(ActorSystem("test")))
      .build()

    repo = guice.instanceOf[AirportRepo]

    Evolutions.applyEvolutions(database = database)
  }

  override protected def afterAll(): Unit = {
    Evolutions.cleanupEvolutions(database)
    database.shutdown()
  }

  "AirportRepo" should {
    "fetch the details for all airport runways" in {
      repo.findAirportRunwaysByCountryNameOrCode(None, None, 0, 10).map { result =>
        result should have length 6
      }
    }

    "fetch the details for Burkina Faso airports and runways By code" in {
      repo.findAirportRunwaysByCountryNameOrCode(None, Some("BF"), 0, 10).map { result =>
        result should have length 3
      }
    }

    "fetch the details for Angola airports and runways By name" in {
      repo.findAirportRunwaysByCountryNameOrCode(None, Some("AO"), 0, 10).map { result =>
        result should contain theSameElementsAs Vector(
          AirportRunways("AO", "Angola", Some("FN18"), Some("Matala Airport"), Some("small_airport"), Some(8015), Some(98), Some("ASP")),
          AirportRunways("AO", "Angola", Some("FN19"), Some("Cabo Ledo Airport"), Some("small_airport"), Some(9830), Some(148), Some("ASP")),
          AirportRunways("AO", "Angola", Some("FNBC"), Some("Mbanza Congo Airport"), Some("medium_airport"), Some(5905), Some(98), Some("ASP"))
        )
      }
    }

    "fetch countries with highest number of airports" in {
      repo.countriesWithHighestNumberOfAirport(10, "DESC").map { result =>
        result should contain theSameElementsAs Vector(
          CountryAirports("AO", "Angola", 3),
          CountryAirports("BF", "Burkina Faso", 2)
        )
      }
    }
  }

}
