package ru.dru

import zio.CanFail.canFailAmbiguous1
import zio.{Duration, Exit, Fiber, Scope, ZIO, ZIOApp, ZIOAppArgs, ZIOAppDefault, durationInt}
import zio.{Ref, ZIO, Clock}
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

    val makeEggs = ZIO.sleep(eggsFiringTime)
    val boilWater = ZIO.sleep(waterBoilingTime)
    val cutCucumbers = ZIO.sleep(saladInfoTime.cucumberTime)
    val cutTomatoes = ZIO.sleep(saladInfoTime.tomatoTime)
    val addSourCream = ZIO.unit
    val brewTea = ZIO.sleep(teaBrewingTime)

    for {
      times <- Ref.make(Map.empty[String, LocalDateTime])
      currentTime = Clock.currentDateTime.map(_.toLocalDateTime)
      _ <- (for {
        _ <- makeEggs *> currentTime.flatMap(time => times.update(_ + ("eggs" -> time)))
        _ <- boilWater *> currentTime.flatMap(time => times.update(_ + ("water" -> time)))
        _ <- (for {
          _ <- cutCucumbers *> currentTime.flatMap(time => times.update(_ + ("cucumbers" -> time)))
          _ <- cutTomatoes *> currentTime.flatMap(time => times.update(_ + ("tomatoes" -> time)))
          _ <- addSourCream *> currentTime.flatMap(time => times.update(_ + ("saladWithSourCream" -> time)))
        } yield ()) *> currentTime.flatMap(time => times.update(_ + ("salad" -> time)))
      } yield ()).zipPar(
        brewTea *> currentTime.flatMap(time => times.update(_ + ("tea" -> time)))
      )
      finalTimes <- times.get
    } yield finalTimes
  }

  // Происходит вот это, что делать - не знаю :с Написала что-то, но тесты не запускаются
  //Error: Could not find or load main class ru.dru.BreakfastTest
  //Caused by: java.lang.ClassNotFoundException: ru.dru.BreakfastTest

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ZIO.succeed(println("Done"))

}
