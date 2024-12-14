import scala.util.{Random, Try}

object Exercises {
    def findSumFunctional(items: List[Int], sumValue: Int) = {
        items
          .zipWithIndex
          .flatMap(x => items.zipWithIndex.map(y => (x, y)))
          .collect { case ((x, i), (y, j)) if i != j && x + y == sumValue => (i, j) }
          .lastOption
          .getOrElse((-1, -1))
    }

    def tailRecRecursion(items: List[Int]): Int = {
        @tailrec
        def func(items: List[Int], index: Int = 1, acc: Int = 1): Int = items match {
            case head :: tail =>
                if (head % 2 == 0) func(tail, index - 1, index + head * acc)
                else func(tail, index - 1, index + -head * acc)
            case _ => acc
        }

        func(items.reverse, items.size)
    }

    def functionalBinarySearch(items: List[Int], value: Int): Option[Int] = {
        def search(left: Int, right: Int): Option[Int] =
            if (left > right) None
            else {
                val middle = (left + right) / 2
                items(middle) match {
                    case item if item == value => Some(middle)
                    case item if item <= value => search(middle + 1, right)
                    case _ => search(left, middle - 1)
                }
            }

        search(0, items.size - 1)
    }

    def generateNames(namesСount: Int): List[String] = {
        if (namesСount < 0) throw new Throwable("Invalid namesCount")
        else List
          .fill(namesСount)(Random.alphanumeric
            .filter(_.isLetter)
            .take(10)
            .mkString
            .toLowerCase
            .capitalize
          )
    }
}

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

        def addPhoneToBaseSafe(phone: String): Either[String, Unit] =
            Try(unsafePhoneService.addPhoneToBase(phone)).toEither.left.map(_.getMessage)

        def deletePhone(phone: String): Either[String, Unit] =
            Try(unsafePhoneService.deletePhone(phone)).toEither.left.map(_.getMessage)
    }

    class ChangePhoneServiceSafe(phoneServiceSafety: PhoneServiceSafety) extends ChangePhoneService {
        override def changePhone(oldPhone: String, newPhone: String): String = {
            phoneServiceSafety.findPhoneNumberSafe(oldPhone).map(phoneServiceSafety.deletePhone)
            phoneServiceSafety.addPhoneToBaseSafe(newPhone) match {
                case Left(error) => error
                case Right(_) => "ok"
            }
        }
    }
}