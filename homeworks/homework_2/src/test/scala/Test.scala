import Exercises.{Vector2D, sortByHeavyweight}
import utest._

import scala.math.BigDecimal.RoundingMode

object Test extends TestSuite{

    val tests: Tests = Tests{
        'test_divBy3Or7 - {
            assert(Exercises.divBy3Or7(1, 3) == Seq(3))
            assert(Exercises.divBy3Or7(5, 9) == Seq(6, 7, 9))
            assert(Exercises.divBy3Or7(0, 100) == Seq(0, 3, 6, 7, 9, 12, 14, 15, 18, 21, 24, 27, 28, 30, 33, 35, 36, 39, 42, 45, 48, 49, 51, 54, 56, 57, 60, 63, 66, 69, 70, 72, 75, 77, 78, 81, 84, 87, 90, 91, 93, 96, 98, 99))
        }

        'test_sumOfDivBy3Or5 - {
            assert(Exercises.sumOfDivBy3Or5(1, 10) == 33)
            assert(Exercises.sumOfDivBy3Or5(1, 20) == 98)
            assert(Exercises.sumOfDivBy3Or5(1, 100) == 2418)
            assert(Exercises.sumOfDivBy3Or5(10, 15) == 37)
            assert(Exercises.sumOfDivBy3Or5(-10, 15) == 27)
            assert(Exercises.sumOfDivBy3Or5(25, 25) == 25)
        }

        'test_primeFactor - {
            assert(Exercises.primeFactor(-21) == List(3, 7))
            assert(Exercises.primeFactor(12) == List(2, 3))
            assert(Exercises.primeFactor(-100) == List(2, 5))
            assert(Exercises.primeFactor(1) == List())
            assert(Exercises.primeFactor(-7) == List(7))
            assert(Exercises.primeFactor(25) == List(5))
        }

        val v1 = Vector2D(1, 2)
        val v2 = Vector2D(4, 2)
        val v3 = Vector2D(7, 5)
        val v4 = Vector2D(0, 3)
        val v5 = Vector2D(9, 0)
        val v6 = Vector2D(6, 10)

        def round(res: Double): BigDecimal = {
            BigDecimal(res).setScale(3, RoundingMode.UP)
        }

        'test_sumScalars - {
            assert(Exercises.sumScalars(v1, v2, v3, v4) == 23)
            assert(Exercises.sumScalars(v1, v2, v5, v6) == 62)
            assert(Exercises.sumScalars(v3, v4, v5, v6) == 69)
            assert(round(Exercises.sumCosines(v1, v2, v3, v4)) == 1.382)
            assert(round(Exercises.sumCosines(v1, v2, v5, v6)) == 1.315)
            assert(round(Exercises.sumCosines(v3, v4, v5, v6)) == 1.096)
        }

        val m1 = Map("Aluminum" -> (3,   2.6889), "Tungsten" ->  (2,   19.35), "Graphite" ->  (12,  2.1),   "Iron" ->      (3,   7.874))
        val m2 = Map("Gold" ->     (2,   19.32),  "Potassium" -> (14,  0.862), "Calcium" ->   (8,   1.55),  "Cobalt" ->    (4,   8.90))
        val m3 = Map("Lead" ->     (2,   11.336), "Titanium" ->  (2,   10.50), "Silver" ->    (4,   4.505), "Uranium" ->   (2,   19.04))

        'test_sortByHeavyweight - {
            assert(sortByHeavyweight(m1) == List("Aluminum", "Tungsten", "Iron", "Graphite"))
            assert(sortByHeavyweight(m2) == List("Gold", "Cobalt", "Calcium", "Potassium"))
            assert(sortByHeavyweight(m3) == List("Titanium", "Lead", "Uranium", "Silver"))
        }
    }
}
