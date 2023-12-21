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
      fiberEggs <- fryEggs(eggsFiringTime).fork
      fiberWater <- boilWater(waterBoilingTime).fork
      fiberSalad <- makeSalad(saladInfoTime).fork
      waterDone <- fiberWater.join
      fiberTea <- brewTea(teaBrewingTime).fork
      eggsDone <- fiberEggs.join
      saladDone <- fiberSalad.join
      teaDone <- fiberTea.join
    } yield Map("eggs" -> eggsDone, "water" -> waterDone, "saladWithSourCream" -> saladDone, "tea" -> teaDone)
  }

  def fryEggs(time: Duration): ZIO[Any, Nothing, LocalDateTime] =
    ZIO.sleep(time).as(LocalDateTime.now())

  def boilWater(time: Duration): ZIO[Any, Nothing, LocalDateTime] =
    ZIO.sleep(time).as(LocalDateTime.now())

  def makeSalad(info: SaladInfoTime): ZIO[Any, Nothing, LocalDateTime] =
    for {
      _ <- cutCucumbers(info.cucumberTime)
      _ <- cutTomatoes(info.tomatoTime)
      done <- addSourCream
    } yield done

  def cutCucumbers(time: Duration): ZIO[Any, Nothing, Unit] =
    ZIO.sleep(time)

  def cutTomatoes(time: Duration): ZIO[Any, Nothing, Unit] =
    ZIO.sleep(time)

  def addSourCream: ZIO[Any, Nothing, LocalDateTime] =
    ZIO.succeed(LocalDateTime.now())

  def brewTea(time: Duration): ZIO[Any, Nothing, LocalDateTime] =
    ZIO.sleep(time).as(LocalDateTime.now())


  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ZIO.succeed(println("Done"))

}
