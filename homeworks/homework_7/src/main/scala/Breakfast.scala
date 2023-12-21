package ru.dru

import zio.CanFail.canFailAmbiguous1
import zio.{Duration, Exit, Fiber, Scope, ZIO, ZIOApp, ZIOAppArgs, ZIOAppDefault, durationInt}
import zio.{Ref, ZIO, Clock}
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



  def makeBreakfast(eggsFiringTime: Duration,
                    waterBoilingTime: Duration,
                    saladInfoTime: SaladInfoTime,
                    teaBrewingTime: Duration): ZIO[Any, Throwable, Map[String, LocalDateTime]] = {

    for {
      eggFiber <- (ZIO.sleep(eggsFiringTime).as(LocalDateTime.now())).fork
      waterFiber <- (ZIO.sleep(waterBoilingTime).as(LocalDateTime.now())).fork
      saladFiber <- {
        ZIO.sleep(saladInfoTime.cucumberTime) *> ZIO.sleep(saladInfoTime.tomatoTime) *> ZIO.sleep(saladInfoTime.tomatoTime).as(LocalDateTime.now())
      }.fork
      teaFiber <- (ZIO.sleep(teaBrewingTime).as(LocalDateTime.now())).fork
      eggs <- eggFiber.join
      water <- waterFiber.join
      salad <- saladFiber.join
      tea <- teaFiber.join
    } yield Map(
      "eggs" -> eggs,
      "water" -> water,
      "saladWithSourCream" -> salad,
      "tea" -> tea
    )
  }

  // Происходит вот это, что делать - не знаю :с Написала что-то, но тесты не запускаются
  //Error: Could not find or load main class ru.dru.BreakfastTest
  //Caused by: java.lang.ClassNotFoundException: ru.dru.BreakfastTest

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ZIO.succeed(println("Done"))

}
