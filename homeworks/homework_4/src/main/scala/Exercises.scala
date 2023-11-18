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

    def findSumFunctional(items: List[Int], sumValue: Int): (Int, Int) = {
        @tailrec
        def loop(items_last: List[Int], currIndex: Int, lastFound: (Int, Int)): (Int, Int) = items_last match {
            case Nil => lastFound
            case head :: tail =>
                items.indexOf(sumValue - head) match {
                    case otherIndex if otherIndex != -1 && otherIndex != currIndex =>
                        loop(tail, currIndex + 1, (currIndex, otherIndex))
                    case _ =>
                        loop(tail, currIndex + 1, lastFound)
                }
        }

        loop(items, 0, (-1, -1))
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

    def tailRecRecursion(items: List[Int], index: Int = 1): Int = {
        @tailrec
        def helper(items: List[Int], index: Int=1, acc: Int): Int = {
            items match {
                case head :: tail =>
                    val newAcc = if (head % 2 == 0) {
                        head * acc + index
                    } else {
                        -1 * head * acc + index
                    }
                    helper(tail, index - 1, newAcc)
                case _ => acc
            }
        }

        helper(items.reverse, items.length, 1)
    }


    /**
     * Задание №3
     * Реализуйте алгоритм бинарного поиска, который соответсвует всем правилам функционального программирования.
     * Необходимо возвращать индекс соответствующего элемента в массиве
     * Если ответ найден, то возвращается Some(index), если нет, то None
     */

    def functionalBinarySearch(items: List[Int], value: Int)(implicit ord: Ordering[Int]): Option[Int] = {
        @tailrec
        def binarySearchRecursive(low: Int, high: Int): Option[Int] = {
            if (low > high) {
                None
            } else {
                val mid = low + (high - low) / 2
                val midValue = items(mid)
                val comparison = ord.compare(midValue, value)

                if (comparison == 0) {
                    Some(mid)
                } else if (comparison < 0) {
                    binarySearchRecursive(mid + 1, high)
                } else {
                    binarySearchRecursive(low, mid - 1)
                }
            }
        }

        binarySearchRecursive(0, items.length - 1)
    }

    /**
     * Задание №4
     * Реализуйте функцию, которая генерирует список заданной длинны c именами.
     * Функция должна соответствовать всем правилам функционального программирования.
     *
     * Именем является строка, не содержащая иных символов, кроме буквенных, а также начинающаяся с заглавной буквы.
     */

    def generateNames(namesСount: Int): List[String] = {
        val alphabet = 'A' to 'Z'

        def generateName: String = {
            val nameLength = scala.util.Random.nextInt(10) + 1 // случайная длина имени от 1 до 10
            val name = (1 to nameLength).map(_ => alphabet(scala.util.Random.nextInt(alphabet.length))).mkString
            name.charAt(0).toString + name.substring(1).toLowerCase
        }

        List.fill(namesСount)(generateName)
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
        def findPhoneNumberSafe(num: String) = ???

        def addPhoneToBaseSafe(phone: String) = ???

        def deletePhone(phone: String) = ???
    }

    class ChangePhoneServiceSafe(phoneServiceSafety: PhoneServiceSafety) extends ChangePhoneService {
        override def changePhone(oldPhone: String, newPhone: String): String = ???
    }
}
