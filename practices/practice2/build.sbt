ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "practice_2023",
    // https://mvnrepository.com/artifact/org.typelevel/cats-core
    libraryDependencies += "org.typelevel" %% "cats-core" % "2.10.0",
    // https://mvnrepository.com/artifact/org.typelevel/cats-effect
    libraryDependencies += "org.typelevel" %% "cats-effect" % "3.6-0142603"
  )
