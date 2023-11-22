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

    def findSumFunctional(items: List[Int], sumValue: Int): (Int, Int) = {
        @tailrec
        def findPair(items: List[Int], sumValue: Int, curIndex: Int): (Int, Int) = {
            if (curIndex >= items.length) {
                (-1, -1)
            } else {
                val addition = sumValue - items(curIndex)
                val additionIndex = items.indexOf(addition)
                if (additionIndex != -1 && additionIndex != curIndex) {
                    (additionIndex, curIndex)
                } else {
                    findPair(items, sumValue, curIndex + 1)
                }
            }
        }
        findPair(items, sumValue, 0)
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
        def recursionHelper(items: List[Int], index: Int, accumulator: Int = 1): Int = {
            items match {
                case head :: tail =>
                    if (head % 2 == 0) {
                        recursionHelper(tail, index - 1, index + accumulator * head)
                    } else {
                        recursionHelper(tail, index - 1, index + -1 * head * accumulator)
                    }
                case _ => accumulator
            }
        }
        recursionHelper(items.reverse, items.size)
    }

    /**
     * Задание №3
     * Реализуйте алгоритм бинарного поиска, который соответсвует всем правилам функционального программирования.
     * Необходимо возвращать индекс соответствующего элемента в массиве
     * Если ответ найден, то возвращается Some(index), если нет, то None
     */

    def functionalBinarySearch(items: List[Int], value: Int): Option[Int] = {
        if (items.isEmpty) {
            None
        } else {
            @tailrec
            def recursiveSearchHelper(items: List[Int], value: Int, leftValue: Int, rightValue: Int): Option[Int] = {
                if (leftValue > rightValue) {
                    None
                } else {
                    val mid = leftValue + (rightValue - leftValue) / 2
                    items(mid) match {
                        case midValue if midValue == value => Some(mid)
                        case midValue if midValue > value => recursiveSearchHelper(items, value, leftValue, mid - 1)
                        case _ => recursiveSearchHelper(items, value, mid + 1, rightValue)
                    }
                }
            }
            recursiveSearchHelper(items, value, 0, items.length - 1)
        }
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
        else if (namesСount == 0) List.empty
        else List.iterate("Name", namesСount)(_ =>
            Random
              .shuffle(('a' to 'z').map(_.toString) ++ ('а' to 'я').map(_.toString))
              .take(5)
              .mkString
              .capitalize)
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
            phoneServiceSafety.findPhoneNumberSafe(oldPhone).map(phoneServiceSafety.deletePhone)

            phoneServiceSafety.addPhoneToBaseSafe(newPhone) match {
                case Right(_) => "ok"
                case Left(exception) => exception
            }
        }
    }
}
