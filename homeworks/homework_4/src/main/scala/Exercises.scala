import scala.annotation.tailrec
import scala.util.Random

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

  def findSumFunctional(items: List[Int], sumValue: Int): (Int, Int) =
    items.zipWithIndex.flatMap(x => items.zipWithIndex.map(y => (x, y)))
      .filter { case ((n1: Int, i1: Int), (n2: Int, i2: Int)) => i1 != i2 && n1 + n2 == sumValue }.lastOption
      .map { case ((_: Int, i1: Int), (_: Int, i2: Int)) => (i1, i2) }
      .getOrElse((-1, -1))


  /**
   * Задание №2
   *
   * Дана рекурсивная функция simpleRecursion.
   * Перепишите ее так, чтобы получилась хвостовая рекурсивная функция.
   *
   * Для прохождения теста на большое количество элементов в списке
   * используйте аннотацию @tailrec к вашей функции.
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
    def go(items: List[Int], index: Int = 1, acc: Int = 1): Int = items match {
      case head :: tail =>
        if (head % 2 == 0) go(tail, index - 1, head * acc + index)
        else go(tail, index - 1, -head * acc + index)
      case Nil => acc
    }

    go(items.reverse, items.size)
  }

  /**
   * Задание №3
   * Реализуйте алгоритм бинарного поиска, который соответствует всем правилам функционального программирования.
   * Необходимо возвращать индекс соответствующего элемента в массиве
   * Если ответ найден, то возвращается Some(index), если нет, то None
   */

  def functionalBinarySearch(items: List[Int], value: Int): Option[Int] = {
    @tailrec
    def go(left: Int, right: Int): Option[Int] = {
      if (left > right) None
      else {
        val middle = (right + left) / 2
        items(middle) match {
          case x if x == value => Some(middle)
          case x if x > value => go(left, middle - 1)
          case _ => go(middle + 1, right)
        }
      }
    }

    go(0, items.length - 1)
  }

  /**
   * Задание №4
   * Реализуйте функцию, которая генерирует список заданной длины c именами.
   * Функция должна соответствовать всем правилам функционального программирования.
   *
   * Именем является строка, не содержащая иных символов, кроме буквенных, а также начинающаяся с заглавной буквы.
   */

  def generateNames(namesCount: Int): List[String] = {
    if (namesCount < 0) throw new Throwable("Invalid namesCount")
    else List.iterate("Egor", namesCount)(_ => Random.shuffle(('a' to 'z').map(_.toString)).take(4).mkString.capitalize)
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

    def addPhoneToBaseSafe(phone: String): Either[String, Unit] =
      if (checkPhoneNumber(phone)) Right(unsafePhoneService.addPhoneToBase(phone))
      else Left("Error: invalidPhoneNumberException")

    def deletePhone(phone: String): Either[String, Unit] = findPhoneNumberSafe(phone) match {
      case Some(num) => Right(unsafePhoneService.deletePhone(num))
      case None => Left("Error: notFoundPhoneNumberException")
    }
  }

  class ChangePhoneServiceSafe(phoneServiceSafety: PhoneServiceSafety) extends ChangePhoneService {
    def addPhoneNumberCase(phone: String): String = phoneServiceSafety.addPhoneToBaseSafe(phone) match {
      case Right(_) => "ok"
      case Left(error) => s"Error: can't add the new phone number\n$error"
    }

    override def changePhone(oldPhone: String, newPhone: String): String =
      phoneServiceSafety.findPhoneNumberSafe(oldPhone) match {
        case Some(value) => phoneServiceSafety.deletePhone(value) match {
          case Right(_) => addPhoneNumberCase(newPhone)
          case Left(error) => s"Error: can't delete the old number\n$error"
        }
        case None => addPhoneNumberCase(newPhone)
      }
  }
}
