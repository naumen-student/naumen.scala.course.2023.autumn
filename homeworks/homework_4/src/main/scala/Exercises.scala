import scala.annotation.tailrec
import scala.util.{Random, Try, Left, Right, Success, Failure}

object Exercises {

  /**
   * Задание №1
   * Дана императивная функция findSumImperative.
   * Напишите ее аналог (findSumFunctional) в функциональном стиле.
   *
   * ПОДСКАЗКА
   * Стоит воспользоваться методами, которые предоставляет объект List или рекурсией.
   * Страница с полезностями List: https://alvinalexander.com/scala/list-class-methods-examples-syntax/
   */
  def findSumImperative(items: List[Int], sumValue: Int): (Int, Int) = {
    var result: (Int, Int) = (-1, -1)
    for (i <- 0 until items.length) {
      for (j <- 0 until items.length) {
        if (items(i) + items(j) == sumValue && i != j) {
          result = (i, j)
        }
      }
    }
    result
  }

  def findSumFunctional(items: List[Int], sumValue: Int) = {
    val resultItems: List[(Int, Int)] =
      (-1, -1) +:
        items
          .zipWithIndex
          .flatMap { pair1 =>
            items
              .zipWithIndex
              .map(pair2 => (pair1, pair2))
              .filter { case (pair1, pair2) => pair1._2 != pair2._2 && pair1._1 + pair2._1 == sumValue }

          }
          .map { case (pair1, pair2) => (pair1._2, pair2._2)
          }

    resultItems.last
  }


  /**
   * Задание №2
   *
   * Дана рекурсивная функция simpleRecursion.
   * Перепишите ее так, чтобы получилась хвостовая рекурсивная функция.
   *
   * Для прохождения теста на большое количество элементов в списке
   * используйте анотацию @tailrec к вашей функции.
   */
  def simpleRecursion(items: List[Int], index: Int = 1): Int = {
    items match {
      case head :: tail =>
        if (head % 2 == 0) {
          head * simpleRecursion(tail, index + 1) + index
        } else {
          -1 * head * simpleRecursion(tail, index + 1) + index
        }
      case _ => 1
    }
  }

  def tailRecRecursion(items: List[Int]): Int = {
    @tailrec
    def tailRecRecursionAcc(items: List[Int] = items.reverse, index: Int = items.size, acc: Int = 1): Int = {
      items match {
        case head :: tail =>
          val sign = if (head % 2 == 0) 1 else -1
          tailRecRecursionAcc(tail, index - 1, index + sign * head * acc)
        case _ => acc
      }
    }

    tailRecRecursionAcc()
  }

  /**
   * Задание №3
   * Реализуйте алгоритм бинарного поиска, который соответсвует всем правилам функционального программирования.
   * Необходимо возвращать индекс соответствующего элемента в массиве
   * Если ответ найден, то возвращается Some(index), если нет, то None
   */

  def functionalBinarySearch(items: List[Int], value: Int): Option[Int] = {
    @tailrec
    def BinarySearch(items: List[Int] = items, value: Int = value, left: Int = 0, right: Int = items.length - 1): Option[Int] = {
      val center = (left + right) / 2
      if (left > right) {
        None
      } else {
        items(center) match {
          case centerValue if value > centerValue => BinarySearch(items, value, center + 1, right)
          case centerValue if value == centerValue => Some(center)
          case _ => BinarySearch(items, value, left, center - 1)
        }
      }
    }

    BinarySearch()
  }

  /**
   * Задание №4
   * Реализуйте функцию, которая генерирует список заданной длинны c именами.
   * Функция должна соответствовать всем правилам функционального программирования.
   *
   * Именем является строка, не содержащая иных символов, кроме буквенных, а также начинающаяся с заглавной буквы.
   */

  def generateNames(namesСount: Int): List[String] = {
    if (namesСount < 0) throw new Throwable("Invalid namesCount")
    else {
      val symbols = List("q", "a", "z", "w", "s", "x", "e", "d", "c", "r", "f", "v", "t", "g", "b", "y", "h", "n", "u", "j", "m", "i", "k", "o", "l", "p")
      val rand = new scala.util.Random

      @tailrec
      def generateName(nameLength: Int, currCount: Int = 0, acc: String = ""): String = {
        val symbol = symbols(rand.nextInt(symbols.length))
        currCount match {
          case currCount if currCount == nameLength => acc
          case currCount if currCount + 1 == nameLength => generateName(namesСount, currCount + 1, symbol.toUpperCase() + acc)
          case _ => generateName(namesСount, currCount + 1, symbol + acc)
        }
      }

      @tailrec
      def generateNamesAcc(namesСount: Int = namesСount, currCount: Int = 0, acc: List[String] = List()): List[String] = {
        currCount match {
          case currCount if currCount == namesСount => acc
          case _ => generateNamesAcc(namesСount, currCount + 1, generateName(20) :: acc)
        }
      }

      generateNamesAcc()
    }
  }
}

/**
 * Задание №5
 *
 * Дана реализация сервиса по смене номера SimpleChangePhoneService с методом changePhone
 * Необходимо написать реализацию этого сервиса с учетом правил работы со сторонними эффектами (SideEffects).
 *
 * Для этого необходимо сначала реализовать собственный сервис работы с телефонными номерами (PhoneServiceSafety),
 * используя при этом методы из unsafePhoneService.
 * Методы должны быть безопасными, поэтому тип возвращаемых значений необходимо определить самостоятельно.
 * Рекомендуется воспользоваться стандартными типами Scala (например Option или Either).
 *
 * Затем, с использованием нового сервиса, необходимо реализовать "безопасную" версию функции changePhone.
 * Функция должна возвращать ok в случае успешного завершения или текст ошибки.
 *
 * Изменять методы внутри SimplePhoneService не разрешается.
 */

object SideEffectExercise {

  import Utils._

  class SimpleChangePhoneService(phoneService: SimplePhoneService) extends ChangePhoneService {
    override def changePhone(oldPhone: String, newPhone: String): String = {
      val oldPhoneRecord = phoneService.findPhoneNumber(oldPhone)
      if (oldPhoneRecord != null) {
        phoneService.deletePhone(oldPhoneRecord)
      }
      phoneService.addPhoneToBase(newPhone)
      "ok"
    }
  }


  class PhoneServiceSafety(unsafePhoneService: SimplePhoneService) {
    def findPhoneNumberSafe(num: String): Option[String] = Option(unsafePhoneService.findPhoneNumber(num))

    def addPhoneToBaseSafe(phone: String): Either[String, Unit] = {
      Try(unsafePhoneService.addPhoneToBase(phone)) match {
        case Success(msg) => Right(msg)
        case Failure(ex) => Left(ex.getMessage)
      }
    }

    def deletePhone(phone: String): Option[Unit] = {
      Option(findPhoneNumberSafe(phone).map(unsafePhoneService.deletePhone))
    }
  }

  class ChangePhoneServiceSafe(phoneServiceSafety: PhoneServiceSafety) extends ChangePhoneService {
    override def changePhone(oldPhone: String, newPhone: String): String = {

      phoneServiceSafety.findPhoneNumberSafe(oldPhone).map(phoneServiceSafety.deletePhone)
      phoneServiceSafety.addPhoneToBaseSafe(newPhone) match {
        case Right(msg) => "ok"
        case Left(ex) => ex
      }
    }
  }

}
