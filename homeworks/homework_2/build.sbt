version := "0.1"

scalaVersion := "2.11.10"


lazy val root = (project in file("."))
  .settings(
    name := "homework_2"
  )

libraryDependencies += "com.lihaoyi" %% "utest" % "0.5.3" % "test"

testFrameworks += new TestFramework("utest.runner.Framework")