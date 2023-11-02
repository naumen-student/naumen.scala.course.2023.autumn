import scala.collection.mutable
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
        items.zipWithIndex.flatMap { case (element1, index1) =>
            items.zipWithIndex.collect {
                case (element2, index2) if index1 != index2 && element1 + element2 == sumValue =>
                    (index1, index2)
            }
        }.lastOption.getOrElse((-1, -1))
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
        @scala.annotation.tailrec
        def innerRec(items: List[Int], index: Int, acc: Int = 1): Int = {
            items match {
                case Nil => acc
                case head :: tail =>
                    if (head % 2 == 0) innerRec(tail, index - 1, head * acc + index)
                    else innerRec(tail, index - 1, -head * acc + index)
            }
        }
        innerRec(items.reverse, items.size)
    }

    /**
     * Задание №3
     * Реализуйте алгоритм бинарного поиска, который соответсвует всем правилам функционального программирования.
     * Необходимо возвращать индекс соответствующего элемента в массиве
     * Если ответ найден, то возвращается Some(index), если нет, то None
     */

    def functionalBinarySearch(items: List[Int], value: Int): Option[Int] = {
        @scala.annotation.tailrec
        def recSearch(low: Int, high: Int): Option[Int] = {
            if (low <= high) {
                val mid = low + (high - low) / 2
                items(mid) match {
                    case x if x == value => Some(mid)
                    case x if x < value => recSearch(mid + 1, high)
                    case _ => recSearch(low, mid - 1)
                }
            } else None
        }
        recSearch(0, items.length - 1)
    }

    /**
     * Задание №4
     * Реализуйте функцию, которая генерирует список заданной длинны c именами.
     * Функция должна соответствовать всем правилам функционального программирования.
     *
     * Именем является строка, не содержащая иных символов, кроме буквенных, а также начинающаяся с заглавной буквы.
     */

    def generateNames(namesCount: Int): List[String] = {
        val names = mutable.Set[String]()
        val letters = 'a' to 'z'
        def getSymbol = letters(Random.nextInt(letters.size))

        @scala.annotation.tailrec
        def generateName(size: Int, acc: List[Char] = Nil): String = {
            val symbol = if (acc.isEmpty) getSymbol.toUpper else getSymbol

            if (size > 0) generateName(size - 1, acc :+ symbol)
            else acc.mkString("") match {
                case name: String =>
                    if (names.contains(name)) generateName(1, name.toList)
                    else {
                      names += name
                      name
                    }
            }
        }


        @scala.annotation.tailrec
        def generateNamesRec(namesCount: Int, acc: List[String]): List[String] = {
            if (namesCount > 0) generateNamesRec(namesCount - 1, acc :+ generateName(5))
            else acc
        }

        if (namesCount < 0) throw new Throwable("Invalid namesCount")
        else generateNamesRec(namesCount, Nil)
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

    class PhoneServiceSafety(unsafePhoneService: SimplePhoneService) {
        def findPhoneNumberSafe(num: String): Option[String] = Option(unsafePhoneService.findPhoneNumber(num))

        def addPhoneToBaseSafe(phone: String): Either[String, Unit] =
            Try(unsafePhoneService.addPhoneToBase(phone)).toEither.left.map(_.getMessage)

        def deletePhoneSafe(phone: String): Either[String, Unit] =
            Try(unsafePhoneService.deletePhone(phone)).toEither.left.map(_.getMessage)
    }

    class ChangePhoneServiceSafe(phoneServiceSafety: PhoneServiceSafety) extends ChangePhoneService {
        private def processNewPhone(newPhone: String): Either[String, String] =
            phoneServiceSafety.addPhoneToBaseSafe(newPhone) match {
                case Right(_) => Right("ok")
                case Left(error) => Left(s"Failed to add new phone: $error")
            }

        override def changePhone(oldPhone: String, newPhone: String): Either[String, String] =
            phoneServiceSafety.findPhoneNumberSafe(oldPhone) match {
                case Some(oldPhoneRecord) =>
                    phoneServiceSafety.deletePhoneSafe(oldPhoneRecord) match {
                        case Right(_) => processNewPhone(newPhone)
                        case Left(error) => Left(s"Failed to delete old phone: $error")
                    }
                case None => processNewPhone(newPhone)
            }
    }
}
