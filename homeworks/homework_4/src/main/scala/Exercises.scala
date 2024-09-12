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

  def findSumFunctional(items: List[Int], sumValue: Int): (Int, Int) = {
    items.combinations(2).toList.map {
      case first :: tail => tail match {
        case second :: _ =>
          val firstIndex = items.lastIndexOf(first)
          val secondIndex = items.lastIndexOf(second)
          if (second + first == sumValue && firstIndex != secondIndex)
            (secondIndex, firstIndex)
          else (-1, -1)
        case _ => (-1, -1)
      }
      case _ => (-1, -1)
    }.reverse.find(pair => pair._1 != -1) match {
      case Some(value) => value
      case None => (-1, -1)
    }
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

  @tailrec
  def tailRecRecursion(items: List[Int], current: Int = 1): Int = {
    items.reverse match {
      case head :: tail =>
        if (head % 2 == 0) {
          tailRecRecursion(tail.reverse, head * current + items.length)
        } else {
          tailRecRecursion(tail.reverse, -1 * head * current + items.length)
        }
      case _ => current
    }
  }

  /**
   * Задание №3
   * Реализуйте алгоритм бинарного поиска, который соответсвует всем правилам функционального программирования.
   * Необходимо возвращать индекс соответствующего элемента в массиве
   * Если ответ найден, то возвращается Some(index), если нет, то None
   */

  @tailrec
  def functionalBinarySearch(items: List[Int], value: Int, droppedCount: Int = 0): Option[Int] = {
    items match {
      case Nil => None
      case _ =>
        val middle = items.length / 2
        if (items(middle) < value)
          functionalBinarySearch(items.drop(middle), value, droppedCount + middle)
        else if (items(middle) > value)
          functionalBinarySearch(items.take(middle), value, droppedCount)
        else
          Some(droppedCount + middle)
    }
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
    else {
      (1 to namesCount).map(_ => Iterator.continually(Random.nextPrintableChar()).filter(_.isLetter).take({
        val number = Random.nextInt(30)
        if (number > 0)
          number
        else number * (-1) + 1
      }).mkString.toLowerCase.capitalize).toList
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
    def findPhoneNumberSafe(num: String): Option[String] = unsafePhoneService.findPhoneNumber(num) match {
      case value => Some(value)
      case null => None
    }

    def addPhoneToBaseSafe(phone: String): Either[Throwable, Unit] =
      try {
        Right(unsafePhoneService.findPhoneNumber(phone))
      }
      catch {
        case ex: Throwable => Left(ex)
      }

    def deletePhone(phone: String): Unit = unsafePhoneService.deletePhone(phone)
  }

  class ChangePhoneServiceSafe(phoneServiceSafety: PhoneServiceSafety) extends ChangePhoneService {
    override def changePhone(oldPhone: String, newPhone: String): String = {
      phoneServiceSafety.findPhoneNumberSafe(oldPhone) match {
        case None => "The old number does not exist in the database."
        case Some(_) => phoneServiceSafety.findPhoneNumberSafe(newPhone) match {
          case Some(_) => "The new number belongs to another subscriber."
          case None => phoneServiceSafety.addPhoneToBaseSafe(newPhone) match {
            case Right(_) =>
              phoneServiceSafety.deletePhone(oldPhone)
              "ok"
            case Left(value) => value.getMessage
          }
        }
      }
    }
  }
}
