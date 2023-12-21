package ru.dru

import zio._

import java.time.LocalDateTime

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

  private type Breakfast = Map[String, LocalDateTime]

  def makeBreakfast(
                     eggsFiringTime: Duration,
                     waterBoilingTime: Duration,
                     saladInfoTime: SaladInfoTime,
                     teaBrewingTime: Duration
                   ): ZIO[Any, Throwable, Breakfast] = {


    for {
      eggsFryFiber <- ZIO.sleep(eggsFiringTime).as(LocalDateTime.now()).fork
      waterBoilFiber <- ZIO.sleep(waterBoilingTime).as(LocalDateTime.now()).fork
      saladWithSourCreamFiber <- ZIO.sleep(saladInfoTime.tomatoTime.plus(saladInfoTime.cucumberTime)).as(LocalDateTime.now()).fork
      waterTimeBoiled <- waterBoilFiber.join
      teaTime <- ZIO.sleep(teaBrewingTime).as(LocalDateTime.now())
      eggsTimeFried <- eggsFryFiber.join
      saladTime <- saladWithSourCreamFiber.join
    } yield Map(
      "eggs" -> eggsTimeFried,
      "water" -> waterTimeBoiled,
      "saladWithSourCream" -> saladTime,
      "tea" -> teaTime
    )
  }

  private def accRes(fRef: Ref[Breakfast] => Task[Unit]) =
    ZIO.serviceWithZIO[Ref[Breakfast]](fRef)

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ZIO.succeed(println("Done"))
}