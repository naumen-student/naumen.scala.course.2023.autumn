ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "Lecture2",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % "test"
  )