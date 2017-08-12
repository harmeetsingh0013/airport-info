package utils

import java.sql.ResultSet

object Implicits {

  implicit class ResultSetStream(resultSet: ResultSet) {
    def toStream: Stream[ResultSet] = {
      new Iterator[ResultSet] {
        def hasNext = resultSet.next()
        def next() = resultSet
      }.toStream
    }
  }

  implicit class ResultSetPimp(val resultSet: ResultSet) extends AnyVal {

    def getStringOption(columnLabel: String): Option[String] = {
      Option(resultSet.getString(columnLabel))
    }

    def getStringOption(columnIndex: Int): Option[String] = {
      Option(resultSet.getString(columnIndex))
    }

    def getIntOption(columnLabel: String): Option[Int] = {
      Option(resultSet.getInt(columnLabel))
    }

    def getIntOption(columnIndex: Int): Option[Int] = {
      Option(resultSet.getInt(columnIndex))
    }
  }

}
