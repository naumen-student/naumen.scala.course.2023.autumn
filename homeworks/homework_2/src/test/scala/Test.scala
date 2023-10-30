import utest._
import Exercises._

object Test extends TestSuite {

    val tests = Tests {
        'test_divBy3Or7 - {
            assert(divBy3Or7(1, 3) == Seq(3))
            assert(divBy3Or7(5, 9) == Seq(6, 7, 9))
            assert(divBy3Or7(0, 100) == Seq(0, 3, 6, 7, 9, 12, 14, 15, 18, 21, 24, 27, 28, 30, 33, 35, 36, 39, 42, 45, 48, 49, 51, 54, 56, 57, 60, 63, 66, 69, 70, 72, 75, 77, 78, 81, 84, 87, 90, 91, 93, 96, 98, 99))
        }

        'test_sumOfDivBy3Or5 - {
            assert(sumOfDivBy3Or5(1, 3) == 3)
            assert(sumOfDivBy3Or5(4, 5) == 5)
            assert(sumOfDivBy3Or5(1, 5) == 8)
            assert(sumOfDivBy3Or5(200, 500) == 49250)
        }

        'test_primeFactor - {
            assert(primeFactor(1) == Seq.empty)
            assert(primeFactor(80) == Seq(2, 5))
            assert(primeFactor(98) == Seq(2, 7))
            assert(primeFactor(61) == Seq(61))
            assert(primeFactor(223092870) == Seq(2, 3, 5, 7, 11, 13, 17, 19, 23))
        }

        'test_sumScalars - {
            assert(sumScalars(Vector2D(0, 0), Vector2D(0, 0), Vector2D(1, 0), Vector2D(1, 0)) == 1)
            assert(sumScalars(Vector2D(1, 0), Vector2D(0, 1), Vector2D(1, 1), Vector2D(0, 0)) == 0)
        }

        'test_sumCosines - {
            assert(sumCosines(Vector2D(1, 0), Vector2D(3, 0), Vector2D(1, 1), Vector2D(-1, 1)) == 1)
            assert((sumCosines(Vector2D(1, 0), Vector2D(1, 1), Vector2D(1, 1), Vector2D(0, 1)) - Math.sqrt(2)).abs < 1e-14)
            assert(sumCosines(Vector2D(-1, 0), Vector2D(1, 0), Vector2D(-1, 1), Vector2D(1, -1)) == -2)
            assert((sumCosines(Vector2D(1 / 2, Math.sqrt(3) / 2), Vector2D(1, 0), Vector2D(1 / 2, -Math.sqrt(3) / 2), Vector2D(1, 0)) - 1) < 1e-14)
        }

        'test_sortByHeavyWeight - {
            assert(sortByHeavyweight() == Seq("Tin", "Platinum", "Nickel", "Aluminum", "Titanium", "Lead",
                "Sodium", "Uranium", "Gold", "Tungsten", "Zirconium", "Chrome", "Iron", "Copper", "Silver",
                "Plutonium", "Cobalt", "Cesium", "Calcium", "Lithium", "Magnesium", "Potassium", "Graphite"))
            assert(sortByHeavyweight(Map("a" -> (1, 1), "b" -> (1, 2),
                "c" -> (1, 3), "d" -> (1, 4),
            )) == Seq("a", "b", "c", "d"))
            assert(sortByHeavyweight(Map("a" -> (4, 1), "b" -> (3, 1),
                "c" -> (2, 1), "d" -> (1, 1),
            )) == Seq("d", "c", "b", "a"))
        }
    }
}
