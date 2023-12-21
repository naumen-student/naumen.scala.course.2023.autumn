ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file(".")).settings(
  name := "homework-7",
  idePackagePrefix := Some("ru.dru"),
  // https://mvnrepository.com/artifact/dev.zio/zio
  libraryDependencies += "dev.zio" %% "zio" % "2.0.20",
  libraryDependencies ++= Seq("dev.zio" %% "zio-test" % "2.0.5" % Test),
  libraryDependencies ++= Seq(
    "dev.zio" %% "zio-test" % "2.0.20" % Test,
    "dev.zio" %% "zio-test-sbt" % "2.0.20" % Test,
    "dev.zio" %% "zio-test-magnolia" % "2.0.20" % Test
  ),
  testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
  scalacOptions += "-Wunused"
)
