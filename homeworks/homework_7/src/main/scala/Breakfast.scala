package ru.dru

import zio.{Duration, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

import java.time.LocalDateTime

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
  def makeBreakfast(eggsFryingDuration: Duration,
                    waterBoilingDuration: Duration,
                    saladPreparationTime: SaladInfoTime,
                    teaSteepingDuration: Duration): ZIO[Any, Throwable, Map[String, LocalDateTime]] = {
    for {
      waterBoilFiber <- ZIO.sleep(waterBoilingDuration).as(LocalDateTime.now).fork
      eggsFryFiber <- ZIO.sleep(eggsFryingDuration).as(LocalDateTime.now).fork
      saladPrepareFiber <- ZIO.sleep(saladPreparationTime.cucumberTime.plus(saladPreparationTime.tomatoTime)).as(LocalDateTime.now).fork
      waterBoilTime <- waterBoilFiber.join
      teaFiber <- ZIO.sleep(teaSteepingDuration).as(LocalDateTime.now).fork
      eggsFryTime <- eggsFryFiber.join
      saladPreparedTime <- saladPrepareFiber.join
      teaTime <- teaFiber.join
    } yield Map(
      "eggs" -> eggsFryTime,
      "water" -> waterBoilTime,
      "saladWithSourCream" -> saladPreparedTime,
      "tea" -> teaTime
    )
  }

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ZIO.succeed(println("Done"))

}
