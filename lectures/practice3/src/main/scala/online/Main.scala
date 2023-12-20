package online

import java.time.LocalDateTime
import java.util.concurrent.ForkJoinPool
import scala.concurrent.ExecutionContext
//import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

object Main {

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(new ForkJoinPool(10000))

  def foo(start: Int = 0) = {
    var i: Long = 0
    var s: BigInt = start

    //println(s"${Thread.currentThread()} started at ${LocalDateTime.now()}")
    while (i < 1000000) {
      s = s + BigInt(i).pow(2)
      i = i + 1
    }
    //println(s"${Thread.currentThread()} ended at ${LocalDateTime.now()}")
  }


  def main(args: Array[String]): Unit = {
    println(LocalDateTime.now())

    val hardCalculations = Future.sequence((1 to 10000).map(n => Future(foo(n))))

    Await.result(hardCalculations, 360.seconds)

    println(LocalDateTime.now())
  }
}
