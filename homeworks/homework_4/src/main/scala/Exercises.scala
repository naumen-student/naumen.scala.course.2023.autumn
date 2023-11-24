import scala.annotation.tailrec
import scala.util.{Random, Try}

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

  @tailrec
  def findSumFunctional(items: List[Int], sumValue: Int, step: Int = 0, result: (Int, Int) = (-1, -1)): (Int, Int) = {
    val i: Int = step / items.length
    val j: Int = step % items.length
    if (step >= items.length * items.length) result
    else if (items(i) + items(j) == sumValue && i != j) findSumFunctional(items, sumValue, step + 1, (i, j))
    else findSumFunctional(items, sumValue, step + 1, result)
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
  def tailRecRecursion(items: List[Int], index: Int = 1, result: Int = 1): Int = {
    val tailIndex: Int = items.length - index
    if (index > items.length) result
    else if (items(tailIndex) % 2 == 0)
      tailRecRecursion(items, index + 1, items(tailIndex) * result + tailIndex + 1)
    else tailRecRecursion(items, index + 1, -1 * items(tailIndex) * result + tailIndex + 1)
  }

  /**
   * Задание №3
   * Реализуйте алгоритм бинарного поиска, который соответсвует всем правилам функционального программирования.
   * Необходимо возвращать индекс соответствующего элемента в массиве
   * Если ответ найден, то возвращается Some(index), если нет, то None
   */

  @tailrec
  def functionalBinarySearch(items: List[Int], value: Int, low: Int = 0): Option[Int] = {
    val middle: Int = low + (items.length - 1 - low) / 2
    if (items.isEmpty || low > items.length - 1) None
    else if (items(middle) < value) functionalBinarySearch(items, value, middle + 1)
    else if (items(middle) > value) functionalBinarySearch(items.slice(0, middle), value, low)
    else Some(middle)
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
    else if (namesСount == 0) Nil
    else (Random.nextInt(90 - 65) + 65).toChar +
      Random.alphanumeric.take(25).filter(_.isLetter).mkString.toLowerCase() :: generateNames(namesСount - 1)
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

  // Так как тестов нет, не уверен, что будет работать так, как и задумывалось создателем задачи
  class PhoneServiceSafety(unsafePhoneService: SimplePhoneService) {
    def findPhoneNumberSafe(num: String): Option[String] = Option(unsafePhoneService.findPhoneNumber(num))

    def addPhoneToBaseSafe(phone: String): Either[String, Unit] = {
      val result: Either[String, Unit] = Try(unsafePhoneService.addPhoneToBase(phone)).toEither.left.map(_.getMessage)
      result
    }

    def deletePhone(phone: String): Either[String, Unit] = {
      val result: Either[String, Unit] = Try(unsafePhoneService.deletePhone(phone)).toEither.left.map(_.getMessage)
      result
    }
  }

  class ChangePhoneServiceSafe(phoneServiceSafety: PhoneServiceSafety) extends ChangePhoneService {
    override def changePhone(oldPhone: String, newPhone: String): String = {
      phoneServiceSafety.findPhoneNumberSafe(oldPhone).map(phoneServiceSafety.deletePhone)
      phoneServiceSafety.addPhoneToBaseSafe(newPhone) match {
        case Left(exp) => exp
        case Right(_) => "ok"
      }
    }
  }
}
