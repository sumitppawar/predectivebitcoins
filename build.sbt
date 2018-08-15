name := """PredectiveBitcoins"""
organization := "com.tookitaki"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
    "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
    "org.mockito" % "mockito-core" % "2.10.0" % "test",
    "org.scalacheck" %% "scalacheck" % "1.14.0" % Test,
    guice,
    ws
)

