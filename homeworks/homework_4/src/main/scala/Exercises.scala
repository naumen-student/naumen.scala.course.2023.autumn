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
    items
    .zipWithIndex
    .combinations(2)
    .foldLeft((-1, -1)) {
      case (res, (a, indexA) :: (b, indexB) :: _) => if (a + b == sumValue) (indexB, indexA) else res
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

    def tailRecRecursion(items: List[Int]): Int =
      items
        .zipWithIndex
        .foldRight(1) {
          case ((el, index), acc) => (if (el % 2 == 0) el else -el) * acc + index + 1
        }

    /**
     * Задание №3
     * Реализуйте алгоритм бинарного поиска, который соответсвует всем правилам функционального программирования.
     * Необходимо возвращать индекс соответствующего элемента в массиве
     * Если ответ найден, то возвращается Some(index), если нет, то None
     */

    def functionalBinarySearch(items: List[Int], value: Int): Option[Int] = {
      @scala.annotation.tailrec
      def loop(left: Int, right: Int): Option[Int] = {
        if (left > right) None
        else {
          val midIndx = (right + left) / 2
          items.lift(midIndx) match {
            case Some(el) if el == value => Some(midIndx)
            case Some(el)                => if (value < el) loop(left, midIndx - 1) else loop(midIndx + 1, right)
            case _                       => None
          }
        }
      }
      loop(0, items.size - 1)
    }

    /**
     * Задание №4
     * Реализуйте функцию, которая генерирует список заданной длинны c именами.
     * Функция должна соответствовать всем правилам функционального программирования.
     *
     * Именем является строка, не содержащая иных символов, кроме буквенных, а также начинающаяся с заглавной буквы.
     */

    val alphabet = ('a' to 'z') ++ ('а' to 'я')

    def generateName(nameLenght: Int): String = {
      if (nameLenght < 0) throw new Throwable("Invalid nameLenght")
      List.fill(nameLenght) {
        alphabet(Random.nextInt(alphabet.size))
      }.mkString.capitalize
    }
    def generateNames(namesСount: Int): List[String] = {
        if (namesСount < 0) throw new Throwable("Invalid namesCount")
        val maxNameLen = 50
        val minNameLen = 1
        List.fill(namesСount){
          val nameLen = Random.nextInt(maxNameLen - minNameLen + 1) + minNameLen
          generateName(nameLen)
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

        def addPhoneToBaseSafe(phone: String): Either[Throwable, Unit] =
          try Right(unsafePhoneService.addPhoneToBase(phone))
          catch { case err: Throwable => Left(err) }

        def deletePhone(phone: String): Unit = unsafePhoneService.deletePhone(phone)
    }

    class ChangePhoneServiceSafe(phoneServiceSafety: PhoneServiceSafety) extends ChangePhoneService {
        override def changePhone(oldPhone: String, newPhone: String): String = {
          phoneServiceSafety.findPhoneNumberSafe(oldPhone).foreach(phoneServiceSafety.deletePhone)
          phoneServiceSafety.addPhoneToBaseSafe(newPhone).fold(err => s"Failed to change the number: ${err.getMessage}", _ => "ok")
        }
    }
}
