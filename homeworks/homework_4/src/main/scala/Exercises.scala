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

    def findSumFunctional(items: List[Int], sumValue: Int): (Int, Int) = items.zipWithIndex.flatMap(x => items.zipWithIndex.map(y => (x, y)))
        .filter { case ((a: Int, ind1: Int), (b: Int, ind2: Int)) => ind1 != ind2 && a + b == sumValue }.lastOption
        .map { case ((_: Int, ind1: Int), (_: Int, ind2: Int)) => (ind1, ind2) }
        .getOrElse((-1, -1))

    def main(args: Array[String]): Unit = {
        val testList: List[Int] = List(268887775, 672085131, 1092998511, 797155206, -2013969991, -828344674, -133301439, -1463785192, 1186430636, 993362444, 808258113, 1254241019, -1754519820, -52135679, -1127141896, -810010600, -1276254678, 1566251524, -1056818726, 2096388829, -1815835467, -526318760, 1978938079, -1246056131, 1912554880, -382711643, 1260412498)
        println(simpleRecursion(testList))
        println(tailRecRecursion(testList))
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
        def tailRecRecursionAcc(items: List[Int], ind: Int = 1, acc: Int = 1): Int = items match {
            case x :: tail if x % 2 == 0 => tailRecRecursionAcc(tail, ind - 1, x * acc + ind)
            case x :: tail if x % 2 != 0 => tailRecRecursionAcc(tail, ind - 1, -1 * x * acc + ind)
            case Nil => acc
        }

        tailRecRecursionAcc(items.reverse, items.size)
    }

    /**
     * Задание №3
     * Реализуйте алгоритм бинарного поиска, который соответсвует всем правилам функционального программирования.
     * Необходимо возвращать индекс соответствующего элемента в массиве
     * Если ответ найден, то возвращается Some(index), если нет, то None
     */


    def functionalBinarySearch(items: List[Int], value: Int): Option[Int] = {
        @scala.annotation.tailrec
        def functionalBinarySearchAcc(innerItems: List[Int], st: Int, end: Int, value: Int): Option[Int] = {
            Some(st + (end - st) / 2).filter(_ => st <= end) match {
                case Some(ind) if innerItems(ind) == value => Some(ind)
                case Some(ind) if innerItems(ind) < value => functionalBinarySearchAcc(innerItems, ind + 1, end, value)
                case Some(ind) if innerItems(ind) > value => functionalBinarySearchAcc(innerItems, 0, ind - 1, value)
                case _ => None
            }
        }

        functionalBinarySearchAcc(items.sorted, 0, items.length - 1, value)
    }

    /**
     * Задание №4
     * Реализуйте функцию, которая генерирует список заданной длинны c именами.
     * Функция должна соответствовать всем правилам функционального программирования.
     *
     * Именем является строка, не содержащая иных символов, кроме буквенных, а также начинающаяся с заглавной буквы.
     */
    private val alphabet = "qwertyuiopasdfghjklzxcvbnm"

    def generateNames(namesCount: Int): List[String] = {
        def getRandomChar: Char = alphabet(Random.nextInt(alphabet.length))

        def getName(len: Int): String = {
            @tailrec
            def getLineAcc(n: Int, acc: String): String =
                n match {
                    case 0 => acc
                    case _ => getLineAcc(n - 1, acc + getRandomChar)
                }

            getLineAcc(len - 1, getRandomChar.toUpper.toString)
        }

        @tailrec
        def genNamesAcc(len: Int, acc: List[String] = List.empty[String]): List[String] = len match {
            case 0 => acc
            case _ => genNamesAcc(len - 1, getName(10) :: acc)
        }

        genNamesAcc(namesCount)
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
        def findPhoneNumberSafe(num: String): Option[String] = Option[String](unsafePhoneService.findPhoneNumber(num))

        def addPhoneToBaseSafe(phone: String): Either[String, Unit] =
            Try(unsafePhoneService.addPhoneToBase(phone)).toEither.swap.map { x => x.getMessage }.swap

        def deletePhone(phone: String): Option[Unit] = findPhoneNumberSafe(phone).map(unsafePhoneService.deletePhone)
    }

    class ChangePhoneServiceSafe(phoneServiceSafety: PhoneServiceSafety) extends ChangePhoneService {
        override def changePhone(oldPhone: String, newPhone: String): String = {
            phoneServiceSafety.deletePhone(oldPhone)
            phoneServiceSafety.addPhoneToBaseSafe(newPhone) match {
                case Left(err) => err
                case Right(_) => "ok"
            }
        }
    }
}
 