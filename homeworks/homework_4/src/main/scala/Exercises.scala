import scala.annotation.tailrec
import scala.util._

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
    items.zipWithIndex.flatMap(x => items.zipWithIndex.map(y => (x, y)))
      .filter { case ((a, ind1), (b, ind2)) => ind1 != ind2 && a + b == sumValue }.lastOption
      .map { case ((_, ind1), (_, ind2)) => (ind1, ind2) }
      .getOrElse((-1, -1))
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
    items.zipWithIndex.foldRight(1) {
      case ((v, index), acc) =>
        if (v % 2 == 0) {
          v * acc + index + 1
        } else {
          -1 * v * acc + index + 1
        }
    }
  }

    /**
     * Задание №3
     * Реализуйте алгоритм бинарного поиска, который соответсвует всем правилам функционального программирования.
     * Необходимо возвращать индекс соответствующего элемента в массиве
     * Если ответ найден, то возвращается Some(index), если нет, то None
     */

  def functionalBinarySearch(items: List[Int], value: Int): Option[Int] = {
    @tailrec
    def functionalBinarySearchAcc(innerItems: List[Int], st: Int, end: Int, value: Int): Option[Int] = {
      Some(st + (end - st) / 2).filter(_ => st <= end) match {
        case Some(ind) if innerItems(ind) == value => Some(ind)
        case Some(ind) if innerItems(ind) < value => functionalBinarySearchAcc(innerItems, ind + 1, end, value)
        case Some(ind) if innerItems(ind) > value => functionalBinarySearchAcc(innerItems, 0, ind - 1, value)
        case _ => None
      }
    }

    functionalBinarySearchAcc(items.sorted, 0, items.length - 1, value)
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
    else List.iterate("Name", namesСount)(_ => Random.shuffle(('a' to 'z').map(_.toString)).take(5).mkString.capitalize)
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

    def addPhoneToBaseSafe(phone: String): String Either Unit = Try(unsafePhoneService.addPhoneToBase(phone)) match {
      case Failure(exception) => Left(exception.getMessage)
      case Success(value) => Right(value)
    }

    def deletePhone(phone: String) = unsafePhoneService.deletePhone(phone)
  }

  class ChangePhoneServiceSafe(phoneServiceSafety: PhoneServiceSafety) extends ChangePhoneService {
    override def changePhone(oldPhone: String, newPhone: String): String =
      phoneServiceSafety
        .findPhoneNumberSafe(oldPhone)
        .map(phoneServiceSafety.deletePhone)
        .map(_ => phoneServiceSafety.addPhoneToBaseSafe(newPhone).fold(identity, _ => "ok"))
        .getOrElse("ok")
  }
}
