name := """airport-info"""
organization := "com.harmeet"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.2"

resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

libraryDependencies ++= Seq(
  guice,
  jdbc,
  evolutions,
  "mysql" % "mysql-connector-java" % "6.0.6",
  "com.h2database" % "h2" % "1.4.196" % "test",
  "org.scalactic" %% "scalactic" % "3.0.1",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0" % Test
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.harmeet.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.harmeet.binders._"

parallelExecution in Test := false

coverageMinimum := 90

coverageFailOnMinimum := true