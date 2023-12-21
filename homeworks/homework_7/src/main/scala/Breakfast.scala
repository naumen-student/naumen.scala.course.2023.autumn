package ru.dru

import zio.CanFail.canFailAmbiguous1
import zio._

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
    
  type Breakfest = Map[String, LocalDateTime]  
  def makeBreakfast(
    eggsFiringTime: Duration,
    waterBoilingTime: Duration,
    saladInfoTime: SaladInfoTime,
    teaBrewingTime: Duration
  ): ZIO[Any, Throwable, Breakfest] = {
    val eff: RIO[Ref[Breakfest], Breakfest] = for {
      teaFiber <- (
        ZIO.sleep(waterBoilingTime) *>
          accRes(_.update(_.updated("water", LocalDateTime.now))) *>
          ZIO.sleep(teaBrewingTime) *>
          accRes(_.update(_.updated("tea", LocalDateTime.now)))
      ).fork
      eggsFiringFiber <- (
        ZIO.sleep(eggsFiringTime) *> 
          accRes(_.update(_.updated("eggs", LocalDateTime.now)))
        ).fork
      saladFiber <- (
        ZIO.sleep(saladInfoTime.cucumberTime) *> ZIO.sleep(saladInfoTime.tomatoTime) *>
          accRes(_.update(_.updated("saladWithSourCream", LocalDateTime.now)))
      ).fork
      _ <- teaFiber.join <*> eggsFiringFiber.join <*> saladFiber.join
      res <- ZIO.serviceWithZIO[Ref[Breakfest]](_.get)
    } yield res
    
    eff.provideLayer(ZLayer.fromZIO(Ref.make(Map.empty[String, LocalDateTime])))
  }


  private def accRes(fRef: Ref[Breakfest] => Task[Unit]) = 
    ZIO.serviceWithZIO[Ref[Breakfest]](fRef)

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = ZIO.succeed(println("Done"))

}
