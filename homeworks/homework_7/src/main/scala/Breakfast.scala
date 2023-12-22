package ru.dru

import zio.CanFail.canFailAmbiguous1
import zio.{Duration, Exit, Fiber, Scope, ZIO, ZIOApp, ZIOAppArgs, ZIOAppDefault, durationLong}

import java.time.LocalDateTime
import scala.concurrent.TimeoutException

case class SaladInfoTime(tomatoTime: Duration, cucumberTime: Duration)


object Breakfast extends ZIOAppDefault {

  /**
   * Функция должна эмулировать приготовление завтрака. Продолжительные операции необходимо эмулировать через ZIO.sleep.
   * Правила приготовления следующие:
   *  1. Нобходимо вскипятить воду (время кипячения waterBoilingTime)
   *  2. Параллельно с этим нужно жарить яичницу eggsFiringTime
   *  3. Параллельно с этим готовим салат:
   *    * сначала режим  огурцы
   *    * после этого режим помидоры
   *    * после этого добавляем в салат сметану
   *  4. После того, как закипит вода необходимо заварить чай, время заваривания чая teaBrewingTime
   *  5. После того, как всё готово, можно завтракать
   *
   * @param eggsFiringTime время жарки яичницы
   * @param waterBoilingTime время кипячения воды
   * @param saladInfoTime информация о времени для приготовления салата
   * @param teaBrewingTime время заваривания чая
   * @return Мапу с информацией о том, когда завершился очередной этап (eggs, water, saladWithSourCream, tea)
   */
  def makeBreakfast(eggsFiringTime: Duration,
                    waterBoilingTime: Duration,
                    saladInfoTime: SaladInfoTime,
                    teaBrewingTime: Duration): ZIO[Any, Throwable, Map[String, LocalDateTime]] = {
    val saladDur = durationLong(saladInfoTime.tomatoTime.toSeconds + saladInfoTime.cucumberTime.toSeconds)
    for {
      startDuration <- ZIO.succeed(LocalDateTime.now())
      eggsDuration <- ZIO.sleep(eggsFiringTime).as(plusDuration(startDuration, eggsFiringTime)).fork
      waterDuration <- ZIO.sleep(waterBoilingTime).as(plusDuration(startDuration, waterBoilingTime)).fork
      saladWithCreamDuration <-
        ZIO.sleep(saladDur.seconds).as(plusDurations(startDuration, saladInfoTime.cucumberTime, saladInfoTime.tomatoTime))

      waterBoiledDuration <- waterDuration.join
      teaDuration <- ZIO.sleep(teaBrewingTime).as(plusDuration(waterBoiledDuration, teaBrewingTime))
      eggsFriedDuration <- eggsDuration.join
    } yield Map(
      "eggs" -> eggsFriedDuration,
      "water" -> waterBoiledDuration,
      "saladWithSourCream" -> saladWithCreamDuration,
      "tea" -> teaDuration
    )
  }

  private def plusDuration(time: LocalDateTime, duration: Duration): LocalDateTime =
    time.plusSeconds(duration.toSeconds)

  private def plusDurations(time: LocalDateTime, durations: Duration*): LocalDateTime =
    durations.foldLeft(time)(plusDuration)

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ZIO.succeed(println("Done"))

}
