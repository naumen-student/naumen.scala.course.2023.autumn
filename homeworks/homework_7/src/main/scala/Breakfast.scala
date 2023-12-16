package ru.dru

import zio.CanFail.canFailAmbiguous1
import zio.{Duration, Exit, Fiber, Scope, UIO, ZIO, ZIOApp, ZIOAppArgs, ZIOAppDefault, durationInt}

import java.time.LocalDateTime
import scala.concurrent.TimeoutException

case class SaladInfoTime(tomatoTime: Duration, cucumberTime: Duration)


object Breakfast extends ZIOAppDefault {

  /**
   * Функция должна эмулировать приготовление завтрака. Продолжительные операции необходимо эмулировать через ZIO.sleep.
   * Правила приготовления следующие:
   *  1. Нобходимо вскипятить воду (время кипячения waterBoilingTime)
   *     2. Параллельно с этим нужно жарить яичницу eggsFiringTime
   *     3. Параллельно с этим готовим салат:
   *     * сначала режим  огурцы
   *     * после этого режим помидоры
   *     * после этого добавляем в салат сметану
   *     4. После того, как закипит вода необходимо заварить чай, время заваривания чая teaBrewingTime
   *     5. После того, как всё готово, можно завтракать
   *
   * @param eggsFiringTime   время жарки яичницы
   * @param waterBoilingTime время кипячения воды
   * @param saladInfoTime    информация о времени для приготовления салата
   * @param teaBrewingTime   время заваривания чая
   * @return Мапу с информацией о том, когда завершился очередной этап (eggs, water, saladWithSourCream, tea)
   */
  def makeBreakfast(eggsFiringTime: Duration,
                    waterBoilingTime: Duration,
                    saladInfoTime: SaladInfoTime,
                    teaBrewingTime: Duration): ZIO[Any, Throwable, Map[String, LocalDateTime]] = for {
    waterBoil <- boilWater(waterBoilingTime).fork
    eggsFry <- fryEggs(eggsFiringTime).fork
    saladMaker <- makeSalad(saladInfoTime).fork
    teaBrew <- waterBoil.join.zip(brewTea(teaBrewingTime)).fork
    result <- eggsFry.zip(saladMaker).zip(teaBrew).join
  } yield Map("eggs" -> result._1, "saladWithSourCream" -> result._2, "water" -> result._3._1, "tea" -> result._3._2)

  private def fryEggs(eggsFiringTime: Duration): ZIO[Any, Throwable, LocalDateTime] = for {
    _ <- ZIO.sleep(eggsFiringTime)
  } yield LocalDateTime.now()

  private def boilWater(waterBoilingTime: Duration): ZIO[Any, Throwable, LocalDateTime] = for {
    _ <- ZIO.sleep(waterBoilingTime)
  } yield LocalDateTime.now()


  private def makeSalad(saladInfoTime: SaladInfoTime): ZIO[Any, Throwable, LocalDateTime] = for {
    _ <- ZIO.sleep(saladInfoTime.tomatoTime).awaitAllChildren.zip(ZIO.sleep(saladInfoTime.cucumberTime))
  } yield LocalDateTime.now()

  private def brewTea(teaBrewingTime: Duration): ZIO[Any, Throwable, LocalDateTime] = for {
    _ <- ZIO.sleep(teaBrewingTime)
  } yield LocalDateTime.now()

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ZIO.succeed(println("Done"))

}
