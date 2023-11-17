import cats.syntax.all._
import scala.annotation.tailrec
import scala.util.Try
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

    def findSumFunctional(items: List[Int], sumValue: Int) = {
        val itemsR = items.zipWithIndex.reverse

        itemsR
          .map{ case (v,i) => sumValue - v -> i}
          .collectFirstSome{ 
            case (v1,i) => 
              itemsR.collectFirst{ 
               case (v2, j) if v2 == v1 && j != i => i -> j
              }
          }
            .getOrElse( -1 -> -1)
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
      def step(tail: List[Int], index: Int, acc: Int): Int =
        tail match {
          case h :: t if h % 2 == 0 => step(t, index - 1, h * acc + index)
          case h :: t => step(t, index - 1, -1 * h * acc + index)
          case _ => acc
        }

      step(items.reverse, items.size, 1)
    }

    /**
     * Задание №3
     * Реализуйте алгоритм бинарного поиска, который соответсвует всем правилам функционального программирования.
     * Необходимо возвращать индекс соответствующего элемента в массиве
     * Если ответ найден, то возвращается Some(index), если нет, то None
     */

    def functionalBinarySearch(items: List[Int], value: Int): Option[Int] = {
        // А зачем? А нафига? И где условие что items отсортирован?
        val sortedItems = items.toArray
        // TODO: Добавить сдвиг l и r, чтобы обойти случай когда r = l + 1
        @tailrec
        def step(l: Int, r: Int): Option[Int] = 
          sortedItems((l + r) / 2) match {
            case v if value == v => ((l + r) / 2).some
            case _ if l == r => None
            case v if value < v => step(l, math.floor((l + r) / 2f).toInt)
            case v if value > v => step(math.ceil((l + r) / 2f).toInt, r)
          }

        Try(step(0, sortedItems.size - 1)).toOption.flatten
    }

    /**
     * Задание №4
     * Реализуйте функцию, которая генерирует список заданной длинны c именами.
     * Функция должна соответствовать всем правилам функционального программирования.
     *
     * Именем является строка, не содержащая иных символов, кроме буквенных, а также начинающаяся с заглавной буквы.
     */

    def generateNames(namesСount: Int): List[String] = 
      List.fill(namesСount)(
        s"Nig${Random.alphanumeric.take(30).filter(!_.isDigit).mkString.toLowerCase()}"
      )

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
        def findPhoneNumberSafe(num: String) = Option(unsafePhoneService.findPhoneNumber(num))

        def addPhoneToBaseSafe(phone: String) = Try(unsafePhoneService.addPhoneToBase(phone))

        def deletePhone(phone: String) = unsafePhoneService.deletePhone(phone)
    }

    class ChangePhoneServiceSafe(phoneServiceSafety: PhoneServiceSafety) extends ChangePhoneService {
        override def changePhone(oldPhone: String, newPhone: String): String = {
          phoneServiceSafety.findPhoneNumberSafe(oldPhone)
            .map(phoneServiceSafety.deletePhone)

            phoneServiceSafety.addPhoneToBaseSafe(newPhone).as("ok").get
        }
    }
}
