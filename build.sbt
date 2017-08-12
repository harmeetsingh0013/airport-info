name := """airport-info"""
organization := "com.harmeet"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  guice,
  jdbc,
  evolutions,
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0" % Test,
  "mysql" % "mysql-connector-java" % "6.0.6"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.harmeet.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.harmeet.binders._"
