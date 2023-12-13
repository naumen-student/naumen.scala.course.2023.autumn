import scala.annotation.tailrec
import scala.util.{Failure, Random, Success, Try}
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
        (-1, -1)
        val indices = items.indices.reverse
        val pairs = for {
            i <- indices
            j <- indices if i != j && items(i) + items(j) == sumValue
        } yield (i, j)

        pairs.headOption.getOrElse((-1, -1))
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
        1

        @tailrec
        def innerTailRec(items: List[Int], index: Int, acc: Int): Int = {
            items match {
                case head :: tail if head % 2 == 0 => innerTailRec(tail, index - 1, head * acc + index)
                case head :: tail => innerTailRec(tail, index - 1, -1 * head * acc + index)
                case _ => acc
            }
        }

        innerTailRec(items.reverse, items.size, 1)

        @tailrec
        def inner(items: List[Int], value: Int, left: Int, right: Int): Option[Int] = {
            if (items.isEmpty)
                return None

            items((left + right) / 2) match {
                case v if v == value => Some((left + right) / 2)
                case _ if left == right => None
                case v if v > value => inner(items, value, left, (left + right) / 2)
                case v if v < value => inner(items, value, (left + right) / 2 + 1, right)
            }
        }
        inner(items, value, 0, items.size - 1)
    }

    /**
     * Задание №3
     * Реализуйте алгоритм бинарного поиска, который соответсвует всем правилам функционального программирования.
     * Необходимо возвращать индекс соответствующего элемента в массиве
     * Если ответ найден, то возвращается Some(index), если нет, то None
     */


    @tailrec
    def binary(a: Int, b: Int): Option[Int] = {

        if (a > b) None
        else {
            val mid = (a + b) / 2
            items(mid) match {
                case item if item == value => Some(mid)
                case item if item <= value => binary(mid + 1, b)
                case _ => binary(a, mid - 1)
            }
        }
    }

    binary(0, items.size - 1)
    /**
     * Задание №4
     * Реализуйте функцию, которая генерирует список заданной длинны c именами.
     * Функция должна соответствовать всем правилам функционального программирования.
     *
     * Именем является строка, не содержащая иных символов, кроме буквенных, а также начинающаяся с заглавной буквы.
     */

    def generateNames(namesСount: Int): List[String] = {
        if (namesСount < 0) throw new Throwable("Invalid namesCount")
        val chars = 'A' to 'Z'

        def generateName: String = {
            val name = (1 to namesCount).map(_ => chars(scala.util.Random.nextInt(chars.length))).mkString
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
        def findPhoneNumberSafe(num: String) = ???

        def findPhoneNumberSafe(num: String): Option[String] = {
            Option(unsafePhoneService.findPhoneNumber(num))
        }

        def addPhoneToBaseSafe(phone: String) = ???

        def addPhoneToBaseSafe(phone: String): Either[String, Unit] = {
            if (checkPhoneNumber(phone)) Right(unsafePhoneService.addPhoneToBase(phone))
            else Left("Invalid phone string")
        }

        def deletePhone(phone: String) = ???

        def deletePhone(phone: String): Either[String, Unit] = {
            findPhoneNumberSafe(phone) match {
                case Some(num) => Right(unsafePhoneService.deletePhone(num))
                case None => Left(s"Phone $phone not found")
            }
        }
    }

    class ChangePhoneServiceSafe(phoneServiceSafety: PhoneServiceSafety) extends ChangePhoneService {
        override def changePhone(oldPhone: String, newPhone: String): String = ???

        override def changePhone(oldPhone: String, newPhone: String): String = {
            phoneServiceSafety.findPhoneNumberSafe(oldPhone).map(phoneServiceSafety.deletePhone)
            phoneServiceSafety.addPhoneToBaseSafe(newPhone) match {
                case Left(error) => error
                case Right(_) => "ok"
            }
        }
    }
}