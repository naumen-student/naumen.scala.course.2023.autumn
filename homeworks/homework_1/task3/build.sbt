ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "task3"
  )
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"