
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
        items.indices
          .flatMap(x => items.indices.map(y => (x, y)))
          .foldLeft((-1, -1)) {
              case (lastFound, (x, y)) =>
              if (x != y && items(x) + items(y) == sumValue)
                  (x, y)
              else
                  lastFound
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

    def tailRecRecursion(items: List[Int], index: Int = 1, accumulator: Int = 1): Int = {
        if (index > items.length) {
            accumulator
        } else {
            val cIndex = items.length - index
            val head = items(cIndex)
            val updAccumulator = if (head % 2 == 0) {
                head * accumulator + cIndex + 1
            } else {
                -1 * head * accumulator + cIndex + 1
            }

            tailRecRecursion(items, index + 1, updAccumulator)
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
        def search(leftInd: Int, rightInd: Int): Option[Int] =
            if (leftInd > rightInd) {
                None
            } else {
                val middleIndex = (leftInd + rightInd) / 2

                items(middleIndex) match {
                    case item if (item == value) =>
                      Some(middleIndex)
                    case item if (item <= value) =>
                      search(middleIndex + 1, rightInd)
                    case _ =>
                      search(leftInd, middleIndex - 1)
                }
            }

        search(0, items.size - 1)
    }

    /**
     * Задание №4
     * Реализуйте функцию, которая генерирует список заданной длинны c именами.
     * Функция должна соответствовать всем правилам функционального программирования.
     *
     * Именем является строка, не содержащая иных символов, кроме буквенных, а также начинающаяся с заглавной буквы.
     */

    def generateNames(namesСount: Int): List[String] = {
      if (namesCount < 0) {
        throw new Throwable("Invalid namesCount")
      }

      def generateName: String = {
        val alphabet = 'A' to 'Z'
        val nameLen = scala.util.Random.nextInt(10) + 1
        val name = (1 to nameLen).map(_ => alphabet(scala.util.Random.nextInt(alphabet.length))).mkString
        name.charAt(0).toString + name.substring(1).toLowerCase
      }

      List.fill(namesCount)(generateName)
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
        Try(unsafePhoneService.addPhoneToBase(phone)).toEither.left.map(_.getMessage)

      def deletePhone(phone: String): Either[String, Unit] =
        Try(unsafePhoneService.deletePhone(phone)).toEither.left.map(_.getMessage)
    }

    class ChangePhoneServiceSafe(phoneServiceSafety: PhoneServiceSafety) extends ChangePhoneService {
      override def changePhone(oldPhone: String, newPhone: String): String = {
        phoneServiceSafety
          .findPhoneNumberSafe(oldPhone)
          .map(phoneServiceSafety.deletePhone)

        phoneServiceSafety.addPhoneToBaseSafe(newPhone) match {
          case Success(_) =>
            "ok"
          case Failure(exception) =>
            exception.toString
        }
      }
    }
}
