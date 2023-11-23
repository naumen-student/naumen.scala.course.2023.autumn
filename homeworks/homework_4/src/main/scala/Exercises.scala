import scala.annotation.tailrec
import scala.util.{Failure, Random, Success, Try}

object Exercises extends App{

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
        val listWithIndex = items.zipWithIndex
        listWithIndex.foldLeft((-1, -1))((acc1, el1) => listWithIndex.foldLeft(acc1)((acc2, el2) =>
            if (el1._1 + el2._1 == sumValue && el1._2 != el2._2) (el1._2, el2._2)
            else acc2
        ))
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
        def loop(list: List[Int], res: Int, ind: Int): Int = {
            list match {
                case head :+ tail =>
                    if (tail % 2 == 0) {
                        loop(head, tail * res + ind, ind - 1)
                    } else {
                        loop(head, -1 * tail * res + ind, ind - 1)
                    }
                case _ => res
            }
        }
        loop(items, 1, items.length)
    }


    /**
     * Задание №3
     * Реализуйте алгоритм бинарного поиска, который соответсвует всем правилам функционального программирования.
     * Необходимо возвращать индекс соответствующего элемента в массиве
     * Если ответ найден, то возвращается Some(index), если нет, то None
     */

    def functionalBinarySearch(items: List[Int], value: Int): Option[Int] = {
        @tailrec
        def loop(list: List[(Int, Int)]): Option[Int] = {
            val (left, right) = list.splitAt(list.length / 2)
            (left, right) match {
                case (head :+ tail, _) if tail._1 < value => loop(right)
                case (head :+ tail, _) if tail._1 > value => loop(head)
                case (head :+ tail, _) if tail._1 == value => Some(tail._2)
                case (_, head :: tail) if head._1 == value => Some(head._2)
                case _ => None
            }
        }
        loop(items.sorted.zipWithIndex)
    }

    /**
     * Задание №4
     * Реализуйте функцию, которая генерирует список заданной длинны c именами.
     * Функция должна соответствовать всем правилам функционального программирования.
     *
     * Именем является строка, не содержащая иных символов, кроме буквенных, а также начинающаяся с заглавной буквы.
     */

    def generateNames(namesCount: Int): List[String] = {
        val chars = 'a' to 'z'

        def createName(): String =
            (1 to namesCount).map(_ => chars(Random.nextInt(chars.length))).mkString.capitalize

        List.fill(namesCount)(createName())
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
            unsafePhoneService.findPhoneNumber(num) match {
                case res: String => Some(res)
                case _ => None
            }

        def addPhoneToBaseSafe(phone: String): String =
            Try(unsafePhoneService.addPhoneToBase(phone)) match {
                case Success(_) => "ok"
                case Failure(exception) => exception.getMessage
            }

        def deletePhone(phone: String): Unit =
            unsafePhoneService.deletePhone(phone)
    }

    class ChangePhoneServiceSafe(phoneServiceSafety: PhoneServiceSafety) extends ChangePhoneService {
        override def changePhone(oldPhone: String, newPhone: String): String = {
            val oldPhoneRecord = phoneServiceSafety.findPhoneNumberSafe(oldPhone)
            oldPhoneRecord.foreach(phoneServiceSafety.deletePhone)
            phoneServiceSafety.addPhoneToBaseSafe(newPhone)
        }
    }
}
