package repo

import akka.actor.ActorSystem
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
    "fetch the details for airport runways by country code or name" in {
      repo.findAirportRunwaysByCountryNameOrCode(None, None, 0, 10).map { result =>
        result.size should ===(6)
      }
    }
  }

}
