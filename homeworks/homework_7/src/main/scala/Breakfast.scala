package ru.dru

import zio.CanFail.canFailAmbiguous1
import zio.{Duration, Exit, Fiber, Scope, ZIO, ZIOApp, ZIOAppArgs, ZIOAppDefault, durationInt}

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
    for {
      start <- ZIO.succeed(LocalDateTime.now())
      _ <- ZIO.sleep(waterBoilingTime).fork
      _ <- ZIO.sleep(eggsFiringTime).fork
      _ <- (ZIO.sleep(saladInfoTime.cucumberTime) *> ZIO.sleep(saladInfoTime.tomatoTime)).fork
      _ <- ZIO.sleep(waterBoilingTime.plus(teaBrewingTime))
      end <- ZIO.succeed(LocalDateTime.now())
    } yield Map(
      "eggs" -> start.plusSeconds(eggsFiringTime.toSeconds),
      "water" -> start.plusSeconds(waterBoilingTime.toSeconds),
      "saladWithSourCream" -> start.plusSeconds(saladInfoTime.cucumberTime.toSeconds + saladInfoTime.tomatoTime.toSeconds),
      "tea" -> start.plusSeconds(waterBoilingTime.toSeconds + teaBrewingTime.toSeconds)
    )
  }



  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ZIO.succeed(println("Done"))

}
