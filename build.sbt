name := """filmes"""
organization := "gdsi-bolseiros"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.13.8"

libraryDependencies ++= Seq(
  // MySQL driver
  "mysql" % "mysql-connector-java" % "8.0.29",
  "dom4j" % "dom4j" % "1.6.1" intransitive(),
  guice,
  evolutions,
  jdbc,
  javaJdbc
)
