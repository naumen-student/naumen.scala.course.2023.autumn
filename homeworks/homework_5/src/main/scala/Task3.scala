import cats._
import cats.implicits._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

/*
  Задание №3
  Всё просто, нужно посчитать количество строк.
  Реализуйте функцию countWords, которая принимает список строк.
  Обязательно использовать функцию mapReduce.
 */
object Task3 extends App {
    def mapReduce[A, B: Monoid](values: Vector[A])(func: A => B): Future[B] = {
        val numCores = Runtime.getRuntime.availableProcessors
        val groupSize = (1.0 * values.size / numCores).ceil.toInt
        values
            .grouped(groupSize)
            .toVector
            .traverse(group => Future(group.foldMap(func)))
            .map(_.combineAll)
    }

    case class Count(word: String, count: Int)

    case class WordsCount(count: Seq[Count])

    object WordsCount {
        implicit val monoid: Monoid[WordsCount] = new Monoid[WordsCount] {
            override def empty: WordsCount = WordsCount(Seq.empty[Count])

            override def combine(x: WordsCount, y: WordsCount): WordsCount = WordsCount((x.count ++ y.count)
                    .groupBy { case Count(word, _) => word }
                    .mapValues(_.map(_.count).sum)
                    .toSeq
                    .map { case (w, c) => Count(w, c) }
                )
        }
    }

    def countWords(lines: Vector[String]): WordsCount = Await.result(mapReduce(lines.flatMap(_.split(" ")))
    (x => WordsCount(Seq(Count(x, 1)))), 1.seconds)

}
