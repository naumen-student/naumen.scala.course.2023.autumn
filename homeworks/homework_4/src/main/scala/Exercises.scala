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

    def findSumFunctional(items: List[Int], sumValue: Int) = {
        (-1, -1)

        @tailrec
        def tail(index: Int): (Int, Int) = {
            if (index >= items.length) (-1, -1)
            else {
                val sumDiff = sumValue - items(index)
                val newIndex = items.indexOf(sumDiff)
                if (newIndex != index && newIndex != -1) (newIndex, index)
                else tail(index + 1)
            }
        }

        tail(0)
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
        def tail(items: List[Int], index: Int, acc: Int = 1): Int = {
            items match {
                case ::(head, tl) =>
                    if (head % 2 == 0) tail(tl, index - 1, head * acc + index)
                    else tail(tl, index - 1, -head * acc + index)
                case _ => acc
            }
        }

        tail(items.reverse, items.size)
    }

    /**
     * Задание №3
     * Реализуйте алгоритм бинарного поиска, который соответсвует всем правилам функционального программирования.
     * Необходимо возвращать индекс соответствующего элемента в массиве
     * Если ответ найден, то возвращается Some(index), если нет, то None
     */

    @tailrec
    def functionalBinarySearch(items: List[Int], value: Int, idx: Int = 0): Option[Int] = {
        if(items==Nil) return None
        if (items.length == 1) {
            items.indices.find(items(_) == value)
        }
        else {
            val c = items.length / 2
            if (items(c) == value) Some(idx + c)
            else if (items(c) > value) functionalBinarySearch(items.take(c), value, idx)
            else functionalBinarySearch(items.drop(c), value, idx + c)

        }
    }

    /**
     * Задание №4
     * Реализуйте функцию, которая генерирует список заданной длинны c именами.
     * Функция должна соответствовать всем правилам функционального программирования.
     *
     * Именем является строка, не содержащая иных символов, кроме буквенных, а также начинающаяся с заглавной буквы.
     */

    @tailrec
    def generateNames(namesСount: Int, acc: List[String] = Nil): List[String] = {
        if (namesСount < 0) throw new Throwable("Invalid namesCount")
        if (namesСount == 0) acc
        else generateNames(namesСount - 1, ("a" * (namesСount)).capitalize :: acc)
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
        def findPhoneNumberSafe(num: String):Option[String] =
            Option(unsafePhoneService.findPhoneNumber(num))


        def addPhoneToBaseSafe(phone: String):Either[String,Unit] = {
            if (checkPhoneNumber(phone)){
                findPhoneNumberSafe(phone) match {
                    case Some(value) => Left(s"Phone number ${value} already exists")
                    case None => Right(unsafePhoneService.addPhoneToBase(phone))
                }

            }
            else Left("Invalid phone string")
        }

        def deletePhone(phone: String):Either[String,Unit] = {
            findPhoneNumberSafe(phone) match {
                case Some(value) => Right(unsafePhoneService.deletePhone(value))
                case None => Left(s"Phone number ${phone} not found")
            }
        }
    }

    class ChangePhoneServiceSafe(phoneServiceSafety: PhoneServiceSafety) extends ChangePhoneService {
        override def changePhone(oldPhone: String, newPhone: String): String = {
            phoneServiceSafety.deletePhone(oldPhone) match {
                case Left(value) => value
                case Right(_) => phoneServiceSafety.addPhoneToBaseSafe(newPhone) match {
                    case Left(value) => value
                    case Right(_) => "ok"
                }
            }


        }
    }
}
