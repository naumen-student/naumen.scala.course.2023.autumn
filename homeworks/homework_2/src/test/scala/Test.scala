import utest._
import Exercises._

object Test extends TestSuite{

    val tests = Tests{
        'test_divBy3Or7 - {
            assert(divBy3Or7(1, 3) == Seq(3))
            assert(divBy3Or7(5, 9) == Seq(6, 7, 9))
            assert(divBy3Or7(0, 100) == Seq(0, 3, 6, 7, 9, 12, 14, 15, 18, 21, 24, 27, 28, 30, 33, 35, 36, 39, 42, 45, 48, 49, 51, 54, 56, 57, 60, 63, 66, 69, 70, 72, 75, 77, 78, 81, 84, 87, 90, 91, 93, 96, 98, 99))
        }

        'test_sumOfDivBy3Or5 - {
            assert(sumOfDivBy3Or5(0, 10) == 33)
            assert(sumOfDivBy3Or5(0, 2) == 0)
            assert(sumOfDivBy3Or5(-5, 5) == 0)
            assert(sumOfDivBy3Or5(-10, 0) == -33)
            assert(sumOfDivBy3Or5(5, 1) == 0)
            assert(sumOfDivBy3Or5(-1, -5) == 0)
        }

        'test_primeFactor - {
            assert(primeFactor(2) == Seq(2))
            assert(primeFactor(9) == Seq(3))
            assert(primeFactor(10) == Seq(2, 5))
            assert(primeFactor(16) == Seq(2))
        }

        'sumScalars - {
            assert(sumScalars(Vector2D(2, -5), Vector2D(-1, 0), Vector2D(1, -3), Vector2D(-2, -3)) == -2 + 7)
            assert(sumScalars(Vector2D(1, 0), Vector2D(0, 1), Vector2D(1, 1), Vector2D(1, 1)) == 0 + 2)
            assert(sumScalars(Vector2D(-5, -4), Vector2D(5, 4), Vector2D(0, 0), Vector2D(2, 2)) == -41 + 0)
        }

        'sumCosines - {
            assert((sumCosines(Vector2D(4, 0), Vector2D(5, 0), Vector2D(2, 2), Vector2D(-2, -2)) - (1 + -1)) < 1e-8)
            assert((sumCosines(Vector2D(6, 8), Vector2D(1, 0), Vector2D(3, 4), Vector2D(0, -1)) - (0.6 + -0.8)) < 1e-8)
        }

        'sortByHeavyweight - {
            assert(sortByHeavyweight(Map.empty) == Seq.empty)
            assert(sortByHeavyweight(Map("1" -> (1, 1))) == Seq("1"))
            assert(sortByHeavyweight(Map("1" -> (1, 2), "2" -> (1, 1))) == Seq("2", "1"))
        }
    }
}
