package repo.impl

import javax.inject.{Inject, Singleton}

import dtos.{AirportRunways, CountryAirports, Runways}
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
                                                     offset: Int, limit: Int): Future[Vector[AirportRunways]] = Future {
      logger.info("In findAirportRunwaysByCountryNameOrCode repository method")

      db.withConnection { conn =>
        val sql =
          """
            |SELECT cnt.code AS code, cnt.name AS name, ap.ident AS ident, ap.name AS airport_name,
            |ap.type AS airport_type, rw.length_ft AS runway_length, rw.width_ft AS runway_width, rw.surface AS runway_surface
            |FROM countries cnt
            |INNER JOIN airports ap on code = ap.iso_country
            |INNER JOIN runways rw ON rw.airport_ident = ap.ident
            |WHERE cnt.name LIKE ? AND code LIKE ?
            |ORDER BY ap.ident LIMIT ?, ?
          """.stripMargin

        val stmt = conn.prepareStatement(sql)
        stmt.setString(1, name.map(_ + "%").getOrElse("%"))
        stmt.setString(2, code.getOrElse("%"))
        stmt.setInt(3, offset)
        stmt.setInt(4, limit)

        val rs = stmt.executeQuery()

        rs.toStream.map { row =>
          AirportRunways(
            countryCode = row.getString("code"),
            countryName = row.getString("name"),
            airportIdent = row.getStringOption("ident"),
            airportName = row.getStringOption("airport_name"),
            airportType = row.getStringOption("airport_type"),
            runwayLengthFt = row.getIntOption("runway_length"),
            runwayWidthFt = row.getIntOption("runway_width"),
            runwaySurface = row.getStringOption("runway_surface")
          )
        }.toVector
      }
  }

  override def countriesWithHighestNumberOfAirport(limit: Int, order: String): Future[Vector[CountryAirports]] = Future {
    logger.info("In countriesWithHighestNumberOfAirport repository method")

    db.withConnection { conn =>
      val sql =
        s"""
          |	SELECT cnt.code AS code, cnt.name AS name, COUNT(ap.ident) airports_count
          |	FROM countries cnt
          |	INNER JOIN airports ap on cnt.code = ap.iso_country
          |	GROUP BY cnt.code, cnt.name
          |	ORDER BY airports_count ${order} LIMIT ?
        """.stripMargin

      val stmt = conn.prepareStatement(sql)
      stmt.setInt(1, limit)

      val rs = stmt.executeQuery()
      rs.toStream.map { row =>
        CountryAirports(
          countryCode = row.getString("code"),
          countryName = row.getString("name"),
          airportCount = row.getInt("airports_count")
        )
      }.toVector
    }
  }

  override def countriesRunway(countriesLimit: Int): Future[Vector[AirportRunways]] = Future {
    logger.info("In countriesRunway repository method")

    db.withConnection { conn =>

      val countriesSql =
        """
          |SELECT code FROM countries
          |ORDER BY code limit ?;
        """.stripMargin
      val countriesStmt = conn.prepareStatement(countriesSql)
      countriesStmt.setInt(1, countriesLimit)

      val countriesRs = countriesStmt.executeQuery()
      val countriesCode = countriesRs.toStream.map(_.getString("code")).toList

      val runwaysSql =
        s"""
          |SELECT DISTINCT (rw.surface) AS surface, cnt.code AS code, cnt.name AS name
          |FROM countries cnt
          |INNER JOIN airports ap on cnt.code = ap.iso_country
          |INNER JOIN runways rw ON rw.airport_ident = ap.ident
          |WHERE rw.surface <> '' AND cnt.code IN ('${countriesCode.mkString("', '")}')
          |ORDER BY cnt.code
        """.stripMargin

      val runwayStmt = conn.prepareStatement(runwaysSql)
      val runwayRs = runwayStmt.executeQuery()
      runwayRs.toStream.map { row =>
        AirportRunways(
          countryCode = row.getString("code"),
          countryName = row.getString("name"),
          runwaySurface = row.getStringOption("surface")
        )
      }.toVector
    }
  }

  override def runwaysCommonIdentifications(offset: Int, limit: Int): Future[Vector[Runways]] = Future {
    logger.info("In runwaysCommonIdentifications repository method")

    db.withConnection { conn =>
      val sql =
        """
          |SELECT surface, le_ident, COUNT(le_ident) as count FROM runways
          |WHERE surface <> ''
          |GROUP BY surface ,le_ident
          |ORDER BY count DESC LIMIT ?, ?
        """.stripMargin

      val stmt = conn.prepareStatement(sql)
      stmt.setInt(1, offset)
      stmt.setInt(2, limit)

      stmt.executeQuery().toStream.map { row =>
        Runways(
          surface = row.getString("surface"),
          leIdent = row.getString("le_ident"),
          count = row.getInt("count")
        )
      }.toVector
    }
  }
}
