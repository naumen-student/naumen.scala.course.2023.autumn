import scala.annotation.tailrec

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
    items.indices
      .combinations(2)
      .map(pair => (pair(1), pair(0)))
      .filter(pair => items(pair._1) + items(pair._2) == sumValue)
      .find(pair => pair._1 != pair._2)
      .getOrElse((-1, -1))


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
    def simpleRecursionHelper(items: List[Int], acc1: Int, acc2: Int, index: Int): Int = {
      items match {
        case head :: tail =>
          if (head % 2 == 0) {
            simpleRecursionHelper(tail, acc1 * head, acc1 * index + acc2, index + 1)
          } else {
            simpleRecursionHelper(tail, acc1 * head * -1, acc1 * index + acc2, index + 1)
          }
        case _ => acc1 + acc2
      }
    }

    simpleRecursionHelper(items, 1, 0, 1)
  }

  /**
   * Задание №3
   * Реализуйте алгоритм бинарного поиска, который соответсвует всем правилам функционального программирования.
   * Необходимо возвращать индекс соответствующего элемента в массиве
   * Если ответ найден, то возвращается Some(index), если нет, то None
   */

  def functionalBinarySearch(items: List[Int], value: Int): Option[Int] = {
    @tailrec
    def binarySearchHelper(low: Int, high: Int): Option[Int] = {
      val mid = (low + high) / 2;
      if (low > high) return None;
      if (items(mid) == value) return Some(mid);
      if (items(mid) > value) binarySearchHelper(low, mid - 1)
      else
        binarySearchHelper(mid + 1, high);
    }

    binarySearchHelper(0, items.length - 1)
  }

  /**
   * Задание №4
   * Реализуйте функцию, которая генерирует список заданной длинны c именами.
   * Функция должна соответствовать всем правилам функционального программирования.
   *
   * Именем является строка, не содержащая иных символов, кроме буквенных, а также начинающаяся с заглавной буквы.
   */

  def generateNames(namesCount: Int): List[String] = {
    if (namesCount < 0) throw new Throwable("Invalid namesCount")

    val alphabet = ('a' to 'z').toList

    def generateName: String = {
      val nameLength = scala.util.Random.nextInt(10) + 1
      val name = (1 to nameLength).map { i =>
        if (i == 1) alphabet(scala.util.Random.nextInt(alphabet.size)).toUpper
        else alphabet(scala.util.Random.nextInt(alphabet.size))
      }.mkString
      name
    }

    (1 to namesCount).map(_ => generateName).toList
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

    def addPhoneToBaseSafe(phone: String): (Boolean, Option[String]) = {
      if (findPhoneNumberSafe(phone).isDefined) {
        return (false, Some("Conflict"))
      }
      try {
        unsafePhoneService.addPhoneToBase(phone)
        (true, None)
      } catch {
        case e: Throwable => (false, Some(e.getMessage))
      }
    }

    def deletePhone(phone: String): (Boolean, Option[String]) = {
      if (findPhoneNumberSafe(phone).isEmpty) {
        return (false, Some("Not found"))
      }
      unsafePhoneService.deletePhone(phone)
      (true, None)

    }
  }

  class ChangePhoneServiceSafe(phoneServiceSafety: PhoneServiceSafety) extends ChangePhoneService {
    override def changePhone(oldPhone: String, newPhone: String): String = {
      val newPhoneExists = phoneServiceSafety.findPhoneNumberSafe(newPhone);
      if (newPhoneExists.isDefined)
        return "Номер уже существует в базе"
      val phoneDeleteResult = phoneServiceSafety.deletePhone(oldPhone)
      if (!phoneDeleteResult._1) {
        return phoneDeleteResult._2.get;
      }
      val phoneAddResult = phoneServiceSafety.addPhoneToBaseSafe(newPhone);
      if (phoneAddResult._1)
        return "ok";
      phoneAddResult._2.get;
    }
  }
}
