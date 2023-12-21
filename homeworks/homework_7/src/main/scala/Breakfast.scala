package ru.dru

import java.time.LocalDateTime

import zio.{Duration, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, UIO}
import zio.Clock

import scala.util.chaining._

case class SaladInfoTime(tomatoTime: Duration, cucumberTime: Duration)

object Breakfast extends ZIOAppDefault {

  /**
   * Функция должна эмулировать приготовление завтрака. Продолжительные операции необходимо
   * эмулировать через ZIO.sleep. Правила приготовления следующие:
   *   1. Нобходимо вскипятить воду (время кипячения waterBoilingTime) 
   *   2. Параллельно с этим нужно жарить яичницу eggsFiringTime 
   *   3. Параллельно с этим готовим салат: * сначала режим огурцы * после этого режим помидоры * после этого добавляем в салат сметану 
   *   4. После того, как закипит вода необходимо заварить чай, время заваривания чая teaBrewingTime 
   *   5. После того, как всё готово, можно завтракать
   *
   * @param eggsFiringTime
   *   время жарки яичницы
   * @param waterBoilingTime
   *   время кипячения воды
   * @param saladInfoTime
   *   информация о времени для приготовления салата
   * @param teaBrewingTime
   *   время заваривания чая
   * @return
   *   Мапу с информацией о том, когда завершился очередной этап (eggs, water, saladWithSourCream, tea)
   */
  def makeBreakfast(
      eggsFiringTime: Duration,
      waterBoilingTime: Duration,
      saladInfoTime: SaladInfoTime,
      teaBrewingTime: Duration
  ): ZIO[Any, Throwable, Map[String, LocalDateTime]] = {
    def timed[A](event: String)(fa: UIO[A]) = for {
      _ <- fa
      time <- Clock.localDateTime
    } yield event -> time

    val boilWater = ZIO.sleep(waterBoilingTime) pipe timed("water")
    val fryEgg = ZIO.sleep(eggsFiringTime) pipe timed("eggs")
    val cutCucumbers = ZIO.sleep(saladInfoTime.cucumberTime)
    val cutTomatoes = ZIO.sleep(saladInfoTime.tomatoTime)
    val brewTea = ZIO.sleep(teaBrewingTime) pipe timed("tea")

    (for {
      boilTime <- boilWater
      brewTime <- brewTea
    } yield (boilTime, brewTime))
      .zipPar(fryEgg)
      .zipPar((cutCucumbers *> cutTomatoes) pipe timed("saladWithSourCream"))
      .map { case (boil, brew, fry, make) =>
        Map(boil, brew, fry, make)
      }
  }

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ZIO.succeed(println("Done"))

}
