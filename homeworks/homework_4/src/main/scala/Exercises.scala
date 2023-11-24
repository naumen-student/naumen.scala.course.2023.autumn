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
        items.indices
          .flatMap(i => items.indices.map(j => (i, j)))
          .filter(x => x._1 != x._2 && items(x._1) + items(x._2) == sumValue)
          .lastOption
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


    def tailRecRecursion(items: List[Int], index: Int = 1): Int = {
        @tailrec
        def helper(list: List[Int], index: Int, accumulator: Int): Int = {
            list match {
                case head :: tail =>
                    val newAccumulator = if (head % 2 == 0) head * accumulator + items.length - index + 1
                    else -1 * head * accumulator + items.length - index + 1

                    helper(tail, index + 1, newAccumulator)
                case _ => accumulator
            }
        }

        helper(items.reverse, index, 1)
    }

    /**
     * Задание №3
     * Реализуйте алгоритм бинарного поиска, который соответсвует всем правилам функционального программирования.
     * Необходимо возвращать индекс соответствующего элемента в массиве
     * Если ответ найден, то возвращается Some(index), если нет, то None
     */

    def functionalBinarySearch(items: List[Int], value: Int): Option[Int] = {
        @tailrec
        def helper(low: Int, high: Int): Option[Int] = {
            if (low > high) None
            else {
                val mid = low + (high - low) / 2
                items(mid) match {
                    case mv if mv == value => Some(mid)
                    case mv if mv < value => helper(mid + 1, high)
                    case _ => helper(low, mid - 1)
                }
            }
        }

        helper(0, items.length - 1)
    }

    /**
     * Задание №4
     * Реализуйте функцию, которая генерирует список заданной длинны c именами.
     * Функция должна соответствовать всем правилам функционального программирования.
     *
     * Именем является строка, не содержащая иных символов, кроме буквенных, а также начинающаяся с заглавной буквы.
     */

    @tailrec
    def generateNames(namesCount: Int, namesSet: Set[String] = Set.empty): List[String] = {
        if (namesCount < 0) throw new Throwable("Invalid namesCount")
        if (namesSet.size == namesCount) namesSet.toList
        else {
            val nameLength = Random.nextInt(10) + 2
            val name = Random.alphanumeric.filter(_.isLetter).take(nameLength - 1).mkString + Random.alphanumeric.filter(_.isLower).take(1).mkString
            val capName = name.toLowerCase.capitalize
            if (!namesSet.contains(capName)) generateNames(namesCount, namesSet + capName)
            else generateNames(namesCount, namesSet)
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
        def findPhoneNumberSafe(num: String): Either[String, String] = {
            val result = unsafePhoneService.findPhoneNumber(num)
            if (result == null) Left("Phone number not found")
            else Right(result)
        }

        def addPhoneToBaseSafe(phone: String): Either[String, Unit] = {
            if (Utils.checkPhoneNumber(phone)) Right(unsafePhoneService.addPhoneToBase(phone))
            else Left("Incorrect phone number format")
        }

        def deletePhone(phone: String): Either[String, Unit] = {
            val phoneRecord = unsafePhoneService.findPhoneNumber(phone)
            if (phoneRecord != null) Right(unsafePhoneService.deletePhone(phone))
            else Left("Phone number not found")
        }
    }

    class ChangePhoneServiceSafe(phoneServiceSafety: PhoneServiceSafety) extends ChangePhoneService {
        override def changePhone(oldPhone: String, newPhone: String): String = {
            val oldPhoneRecord = phoneServiceSafety.findPhoneNumberSafe(oldPhone)
            if (oldPhoneRecord.isLeft) return oldPhoneRecord.left.get

            val deleteResult = phoneServiceSafety.deletePhone(oldPhoneRecord.right.get)
            if (deleteResult.isLeft) return deleteResult.left.get

            val addResult = phoneServiceSafety.addPhoneToBaseSafe(newPhone)
            if (addResult.isLeft) return addResult.left.get

            "Phone changed"
        }
    }
}
