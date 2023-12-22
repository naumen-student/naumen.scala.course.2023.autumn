package ru.dru

import zio.CanFail.canFailAmbiguous1
import zio.{Duration, Exit, Fiber, Scope, ZIO, ZIOApp, ZIOAppArgs, ZIOAppDefault, durationLong}

import java.time.LocalDateTime
import scala.concurrent.TimeoutException

case class SaladInfoTime(tomatoTime: Duration, cucumberTime: Duration)


object Breakfast extends ZIOAppDefault {

  def makeBreakfast(eggsFiringTime: Duration,
                    waterBoilingTime: Duration,
                    saladInfoTime: SaladInfoTime,
                    teaBrewingTime: Duration): ZIO[Any, Throwable, Map[String, LocalDateTime]] = {
    val saladTime = durationLong(saladInfoTime.cucumberTime.toSeconds + saladInfoTime.tomatoTime.toSeconds)
    for {
      startTime <- ZIO.succeed(LocalDateTime.now())
      eggsTimes <- ZIO.sleep(eggsFiringTime).as(addDurations(startTime, eggsFiringTime)).fork
      waterBoiling <- ZIO.sleep(waterBoilingTime).as(addDurations(startTime, waterBoilingTime)).fork
      salad <- ZIO.sleep(saladTime.seconds).as(addDurations(startTime, saladInfoTime.tomatoTime, saladInfoTime.cucumberTime))
      waterTime <- waterBoiling.join
      teaTime <- ZIO.sleep(teaBrewingTime).as(addDurations(waterTime, teaBrewingTime))
      eggsTime <- eggsTimes.join
    } yield Map (
      "eggs" -> eggsTime,
      "water" -> waterTime,
      "saladWithSourCream" -> salad,
      "tea" -> teaTime
    )
  }
  private def addDurations(currentTime: LocalDateTime, durations: Duration*): LocalDateTime = {
    durations.foldLeft(currentTime)((date, duration) => date.plusSeconds(duration.toSeconds))
  }


  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ZIO.succeed(println("Done"))

}