name := """filmes"""
organization := "gdsi-bolseiros"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.13.8"

libraryDependencies ++= Seq(
  guice,
  evolutions,
  jdbc,
  javaJdbc,
  // MySQL driver
  "mysql" % "mysql-connector-java" % "8.0.29",

)
