import scala.annotation.tailrec
import scala.util.Random
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
        items
        .zipWithIndex
        .flatMap(x => items.zipWithIndex.map(y => (x, y)))
        .filter {
            case ((fir, firInd), (sec, secInd)) => firInd != secInd && fir + sec == sumValue
        }
        .lastOption
        .map { case ((_, i), (_, j)) => (i, j) }
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
        @tailrec
        def recursion(tail: List[Int], size: Int, cur: Int): Int =
            tail match {
                case h :: t if h % 2 == 0 => recursion(t, size - 1, h * cur + size)
                case h :: t => recursion(t, size - 1, -1 * h * cur + size)
                case _ => cur
            }
        recursion(items.reverse, items.size, 1)
    }

    /**
     * Задание №3
     * Реализуйте алгоритм бинарного поиска, который соответсвует всем правилам функционального программирования.
     * Необходимо возвращать индекс соответствующего элемента в массиве
     * Если ответ найден, то возвращается Some(index), если нет, то None
     */

    def functionalBinarySearch(items: List[Int], value: Int): Option[Int] = {
        @tailrec
        def binSearchRec(low: Int, high: Int): Option[Int] = {
            if (low > high) None
            else {
                val mid = low + (high - low) / 2
                val midVal = items(mid)
                
                if (midVal == value) Some(mid)
                else if (midVal < value) binSearchRec(mid + 1, high)
                else binSearchRec(low, mid - 1)
            }
        }
        binSearchRec(0, items.length - 1)
    }

    /**
     * Задание №4
     * Реализуйте функцию, которая генерирует список заданной длинны c именами.
     * Функция должна соответствовать всем правилам функционального программирования.
     *
     * Именем является строка, не содержащая иных символов, кроме буквенных, а также начинающаяся с заглавной буквы.
     */

    def generateNames(namesCount: Int): List[String] = {
        def generateName: String = {
            val letters = 'A' to 'Z'
            val nameLen = Random.nextInt(10) + 1
            val name = (1 to nameLen)
                .map(_ => letters(Random.nextInt(letters.length)))
                .mkString
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

        def addPhoneToBaseSafe(phone: String): String Either Unit = 
            Try(unsafePhoneService.addPhoneToBase(phone)) match {
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
