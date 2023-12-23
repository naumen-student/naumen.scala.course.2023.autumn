package ru.dru

import zio.{Duration, Scope, ZIO, ZIOAppArgs, ZIOAppDefault, durationInt}

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
                    teaBrewingTime: Duration): ZIO[Any, Throwable, Map[String, LocalDateTime]] =
    for {
      water <- ZIO.sleep(waterBoilingTime).fork
      timeWater <- water.join *> ZIO.succeed("water" -> LocalDateTime.now())

      tea <- water.join *> ZIO.sleep(teaBrewingTime).fork
      timeTea <- tea.join *> ZIO.succeed("tea" -> LocalDateTime.now())

      eggs <- ZIO.sleep(eggsFiringTime).fork
      timeEggs <- eggs.join *> ZIO.succeed("eggs" -> LocalDateTime.now())

      salad <- ZIO.sleep(saladInfoTime.tomatoTime.plus(saladInfoTime.cucumberTime)).fork
      timeSalad <- salad.join *> ZIO.succeed("saladWithSourCream" -> LocalDateTime.now())

    } yield Map[String, LocalDateTime](timeWater, timeTea, timeEggs, timeSalad)


  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    for {
      map <- makeBreakfast(
        durationInt(3).second,
        durationInt(2).second,
        SaladInfoTime(durationInt(3).second, durationInt(3).second),
        durationInt(2).second
      )
      _ <- ZIO.succeed(println(map))
      _ <- ZIO.succeed(println("Done"))
    } yield ()
  }

}
