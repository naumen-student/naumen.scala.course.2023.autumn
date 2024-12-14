import cats._
import cats.implicits._

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

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
      override def empty: WordsCount = WordsCount(Seq.empty)

      override def combine(wc_1: WordsCount, wc_2: WordsCount): WordsCount = {
        val wc_total = wc_1.count ++ wc_2.count
        WordsCount(
          wc_total
            .groupBy(_.word)
            .map { case (word, temp_wc) => Count(word, temp_wc.map(_.count).sum) }
            .toSeq
        )
      }
    }
  }

  def countWords(lines: Vector[String]): WordsCount = {
    val split_lines = lines.flatMap(_.split(" "))
    val words_count = mapReduce(split_lines)(word => WordsCount(Seq(Count(word, 1))))
    Await.result(words_count, 6.second)
  }
}
