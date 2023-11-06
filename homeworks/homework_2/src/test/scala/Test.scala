import Exercises.{Vector2D, primeFactor, sumCosines, sumOfDivBy3Or5, sumScalars, sortByHeavyweight}

import java.lang.Double
import utest._

object Test extends TestSuite{

    val tests = Tests{
        'test_divBy3Or7 - {
            assert(Exercises.divBy3Or7(1, 3) == Seq(3))
            assert(Exercises.divBy3Or7(5, 9) == Seq(6, 7, 9))
            assert(Exercises.divBy3Or7(0, 100) == Seq(0, 3, 6, 7, 9, 12, 14, 15, 18, 21, 24, 27, 28, 30, 33, 35, 36, 39, 42, 45, 48, 49, 51, 54, 56, 57, 60, 63, 66, 69, 70, 72, 75, 77, 78, 81, 84, 87, 90, 91, 93, 96, 98, 99))
        }
        'test_sumOfDivBy3Or5 - {
            assert(sumOfDivBy3Or5(1, 2) == 0)
            assert(sumOfDivBy3Or5(1, 3) == 3)
            assert(sumOfDivBy3Or5(3, 5) == 8)
            assert(sumOfDivBy3Or5(0, 100) == 2418)
        }
        'test_primeFactor - {
            assert(primeFactor(80) == Seq(2, 5))
            assert(primeFactor(98) == Seq(2, 7))
            assert(primeFactor(13) == Seq(13))
            assert(primeFactor(60) == Seq(2, 3, 5))
            assert(primeFactor(44) == Seq(2, 11))
            assert(primeFactor(1) == Seq.empty)
        }
        'test_sumScalars - {
            assert(Double.compare(sumScalars(Vector2D(1, 0), Vector2D(0, 1), Vector2D(0, 1), Vector2D(1, 0)), 0.0) == 0)
            assert(Double.compare(sumScalars(Vector2D(1, 1), Vector2D(1, 1), Vector2D(0, 1), Vector2D(1, 0)), 2.0) == 0)
            assert(Double.compare(sumScalars(Vector2D(4, 3), Vector2D(1, 2), Vector2D(2, 1), Vector2D(1, 0)), 12.0) == 0)
        }
        'test_sumCosines - {
            assert(Double.compare(sumCosines(Vector2D(1, 0), Vector2D(0, 1), Vector2D(0, 1), Vector2D(1, 0)), 0.0) == 0)
            assert(Double.compare(sumCosines(Vector2D(1, 1), Vector2D(2, 1), Vector2D(3, 1), Vector2D(2, 2)), 1.8431104890504295) == 0)
            assert(Double.compare(sumCosines(Vector2D(5, 0), Vector2D(2, 3), Vector2D(3, 4), Vector2D(1, 2)), 1.5385701063251367) == 0)
        }
        'test_sortByHeavyweight - {
            assert(sortByHeavyweight().head == "Tin")
            assert(sortByHeavyweight().last == "Graphite")
            assert(sortByHeavyweight().dropRight(2).last == "Magnesium")
        }
    }
}
