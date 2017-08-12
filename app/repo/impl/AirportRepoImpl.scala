package repo.impl

import javax.inject.{Inject, Singleton}

import dtos.AirportRunways
import org.slf4j.{Logger, LoggerFactory}
import play.api.db.Database
import repo.AirportRepo
import repo.ec.DatabaseExecutionContext
import utils.Implicits._

import scala.concurrent.Future

@Singleton
class AirportRepoImpl @Inject() (db: Database) (implicit ec: DatabaseExecutionContext)
    extends AirportRepo {

  val logger: Logger = LoggerFactory.getLogger(this.getClass())

  override def findAirportRunwaysByCountryNameOrCode(name: Option[String], code: Option[String],
                                                     offset: Int, limit: Int): Future[Vector[AirportRunways]] =
    Future {
      db.withConnection { conn =>
        val sql =
          """
            |select cnt.code, cnt.name, ap.ident, ap.name, ap.type,
            |rw.length_ft, rw.width_ft, rw.surface
            |FROM countries cnt
            |INNER JOIN airports ap on cnt.continent = ap.continent
            |INNER JOIN runways rw ON rw.airport_ident = ap.ident
            |WHERE cnt.name LIKE ? AND cnt.code LIKE ?
            |ORDER BY ap.ident LIMIT ?, ?
          """.stripMargin

        val stmt = conn.prepareStatement(sql)
        stmt.setString(1, name.map(_ + "%").getOrElse("%"))
        stmt.setString(2, code.map(_ + "%").getOrElse("%"))
        stmt.setInt(3, offset)
        stmt.setInt(4, limit)

        val rs = stmt.executeQuery()

        rs.toStream.map { rs =>
          AirportRunways(
            countryCode = rs.getString("cnt.code"),
            countryName = rs.getString("cnt.name"),
            airportIdent = rs.getString("ap.ident"),
            airportName = rs.getString("ap.name"),
            airportType = rs.getString("ap.type"),
            runwayLengthFt = rs.getIntOption("rw.length_ft"),
            runwayWidthFt = rs.getIntOption("rw.width_ft"),
            runwaySurface = rs.getStringOption("rw.surface")
          )
        }.toVector
      }
  }
}
