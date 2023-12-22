package ru.dru

import zio.CanFail.canFailAmbiguous1
import zio.Clock.{currentDateTime, sleep}
import zio.{Duration, Exit, Fiber, Scope, ZIO, ZIOApp, ZIOAppArgs, ZIOAppDefault, durationLong}

import java.time.LocalDateTime
import scala.concurrent.TimeoutException

case class SaladInfoTime(tomatoTime: Duration, cucumberTime: Duration)


object Breakfast extends ZIOAppDefault {
  def makeBreakfast(eggsFiringTime: Duration,
                    waterBoilingTime: Duration,
                    saladInfoTime: SaladInfoTime,
                    teaBrewingTime: Duration): ZIO[Any, Throwable, Map[String, LocalDateTime]] = {
    val totalTimeSalad = durationLong(saladInfoTime.cucumberTime.toSeconds + saladInfoTime.tomatoTime.toSeconds)

    for {
      startTime <- ZIO.succeed(LocalDateTime.now())
      eggsFryFiber <- ZIO.sleep(eggsFiringTime).as(startTime.plusSeconds(eggsFiringTime.toSeconds)).fork
      waterBoilFiber <- ZIO.sleep(waterBoilingTime).as(startTime.plusSeconds(waterBoilingTime.toSeconds)).fork
      saladWithSourCreamTime <- ZIO.sleep(totalTimeSalad.seconds)
        .as(startTime.plusSeconds(saladInfoTime.cucumberTime.toSeconds).plusSeconds(saladInfoTime.tomatoTime.toSeconds))
      waterBoiledTime <- waterBoilFiber.join
      teaTime <- ZIO.sleep(teaBrewingTime).as(waterBoiledTime.plusSeconds(teaBrewingTime.toSeconds))
      eggsFriedTime <- eggsFryFiber.join
    } yield Map(
      "eggs" -> eggsFriedTime,
      "water" -> waterBoiledTime,
      "saladWithSourCream" -> saladWithSourCreamTime,
      "tea" -> teaTime
    )
  }

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ZIO.succeed(println("Done"))

}
