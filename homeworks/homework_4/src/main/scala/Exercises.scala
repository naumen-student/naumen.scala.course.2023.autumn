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
    @tailrec
    def helper(index: Int): (Int, Int) = {
      if (index >= items.length) (-1, -1)
      else {
        val diff = sumValue - items(index)
        val diffIndex = items.indexOf(diff)
        if (diffIndex != -1 && diffIndex != index) (diffIndex, index)
        else helper(index + 1)
      }
    }
    helper(0)
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
      def go(items: List[Int], index: Int, acc: Int = 1): Int = {
        items match {
          case head :: tail =>
            val res = if (head % 2 == 0) head * acc else -head * acc
            go(tail, index - 1, res + index)
          case _ => acc
        }
      }
      go(items.reverse, items.size)
    }

    /**
     * Задание №3
     * Реализуйте алгоритм бинарного поиска, который соответсвует всем правилам функционального программирования.
     * Необходимо возвращать индекс соответствующего элемента в массиве
     * Если ответ найден, то возвращается Some(index), если нет, то None
     */

    def functionalBinarySearch(items: List[Int], value: Int): Option[Int] = {
        @tailrec
        def go(low: Int, high: Int): Option[Int] =
            if (low > high) None
            else {
                val mid = (low + high) / 2
                items(mid) match {
                    case item if item == value => Some(mid)
                    case item if item <= value => go(mid + 1, high)
                    case _ => go(low, mid - 1)
                }
            }
        go(0, items.size - 1)
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
        val alphabet = ('a' to 'z').toList ::: ('A' to 'Z').toList
        @tailrec
        def generateNamesHelper(currentList: List[String], remainingCount: Int): List[String] = {
            if (remainingCount == 0) currentList.map(_.toLowerCase.capitalize)
            else {
                val newName = (1 to 10)
                  .map(_ => alphabet(Random.nextInt(alphabet.length)))
                  .mkString
                generateNamesHelper(newName :: currentList, remainingCount - 1)
            }
        }
        generateNamesHelper(Nil, namesCount)
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
        def findPhoneNumberSafe(num: String): Option[String] =
          Option(unsafePhoneService.findPhoneNumber(num))

        def addPhoneToBaseSafe(phone: String): Either[String, Unit] =
          if (checkPhoneNumber(phone)) Right(unsafePhoneService.addPhoneToBase(phone))
          else Left("Invalid phone string")

        def deletePhone(phone: String): Either[String, Unit] =
          findPhoneNumberSafe(phone) match {
            case Some(num) => Right(unsafePhoneService.deletePhone(num))
            case None => Left(s"Phone $phone not found")
          }
    }

    class ChangePhoneServiceSafe(phoneServiceSafety: PhoneServiceSafety) extends ChangePhoneService {
        override def changePhone(oldPhone: String, newPhone: String): String = {
          phoneServiceSafety.findPhoneNumberSafe(oldPhone).map(phoneServiceSafety.deletePhone)
          phoneServiceSafety.addPhoneToBaseSafe(newPhone) match {
            case Left(error) => error
            case Right(_)    => "ok"
          }
        }
    }
}
