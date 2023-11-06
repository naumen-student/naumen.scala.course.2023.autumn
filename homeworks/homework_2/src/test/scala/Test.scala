import Exercises.Vector2D
import utest._

import scala.math.pow

object Test extends TestSuite{

    val tests = Tests{
        'test_divBy3Or7 - {
            assert(Exercises.divBy3Or7(1, 3) == Seq(3))
            assert(Exercises.divBy3Or7(5, 9) == Seq(6, 7, 9))
            assert(Exercises.divBy3Or7(0, 100) == Seq(0, 3, 6, 7, 9, 12, 14, 15, 18, 21, 24, 27, 28, 30, 33, 35, 36, 39, 42, 45, 48, 49, 51, 54, 56, 57, 60, 63, 66, 69, 70, 72, 75, 77, 78, 81, 84, 87, 90, 91, 93, 96, 98, 99))
        }

        'test_sumOfDivBy3Or5 - {
            assert(Exercises.sumOfDivBy3Or5(1, 3) == 3)
            assert(Exercises.sumOfDivBy3Or5(-3, 0) == -3)
            assert(Exercises.sumOfDivBy3Or5(-6, 6) == -6 + -5 + -3 + 3 + 5 + 6)
            assert(Exercises.sumOfDivBy3Or5(5, 9) == 5 + 6 + 9)
            assert(Exercises.sumOfDivBy3Or5(5, 9) == 5 + 6 + 9)
            assert(Exercises.sumOfDivBy3Or5(0, 100) == 2418)
        }

        'test_isPrime - {
            assert(Exercises.isPrime(2))
            assert(Exercises.isPrime(5))
            assert(Exercises.isPrime(7))
            assert(Exercises.isPrime(11))
            assert(Exercises.isPrime((1e9 + 7).toInt))
            assert(!Exercises.isPrime(6))
            assert(!Exercises.isPrime(15))
            assert(!Exercises.isPrime(25))
            assert(!Exercises.isPrime(512))
            assert(!Exercises.isPrime(144))
        }

        'test_primeFactor - {
            assert(Exercises.primeFactor(2) == Seq(2))
            assert(Exercises.primeFactor(3) == Seq(3))
            assert(Exercises.primeFactor(2 * 3) == Seq(2, 3))
            assert(Exercises.primeFactor(3 * 3) == Seq(3))
            assert(Exercises.primeFactor(pow(2, 10).toInt) == Seq(2))
            assert(Exercises.primeFactor(11) == Seq(11))
            assert(Exercises.primeFactor(-6) == Seq(2, 3))
            assert(Exercises.primeFactor(1) == Seq())
            assert(Exercises.primeFactor(0) == Seq())
        }

        'test_sumScalars - {
            assert((Exercises.sumScalars(Vector2D(1, 1), Vector2D(1, 1), Vector2D(1, 1), Vector2D(1, 1)) - 4.0).abs <= 1e-8)
            assert((Exercises.sumScalars(Vector2D(0, 1), Vector2D(0, 1), Vector2D(1, 0), Vector2D(1, 0)) - 2.0).abs <= 1e-8)
            assert((Exercises.sumScalars(Vector2D(256, 128), Vector2D(32, 64), Vector2D(100, 50), Vector2D(70, 47)) - 25734.0).abs <= 1e-8)
        }

        'test_sumCosines - {
            assert(Exercises.sumCosines(Vector2D(0, 1), Vector2D(1, 0), Vector2D(0, 1), Vector2D(1, 0)).abs <= 1e-8)
            assert((Exercises.sumCosines(Vector2D(1, 1), Vector2D(1, 1), Vector2D(1, 1), Vector2D(1, 1)) - 2).abs <= 1e-8)
            assert((Exercises.sumCosines(Vector2D(50, 100), Vector2D(70, 140), Vector2D(80, 160), Vector2D(60, 40)) - 1.8682431421244594).abs <= 1e-8)
        }

        'test_sortByHeavyWeight - {
            assert(Exercises.sortByHeavyweight(Map.empty) == Seq())
            assert(Exercises.sortByHeavyweight(Map("a" -> (1, 1.0))) == Seq("a"))
            assert(Exercises.sortByHeavyweight(Map("b" -> (1, 1.0), "a" -> (1, 10.0))) == Seq("b", "a"))
            assert(Exercises.sortByHeavyweight(Map("a" -> (100, 1.0), "b" -> (1, 100.0))) == Seq("b", "a"))
            assert(Exercises.sortByHeavyweight(Map(
                "a" -> (100, 1.0),
                "b" -> (1, 100.0),
                "c" -> (10, 100.0),
                "d" -> (10, 1000.0))) == Seq("b", "c", "a", "d"))
            assert(Exercises.sortByHeavyweight(Exercises.balls) == Seq(
                "Tin",
                "Platinum",
                "Nickel",
                "Aluminum",
                "Titanium",
                "Lead",
                "Sodium",
                "Uranium",
                "Gold",
                "Tungsten",
                "Zirconium",
                "Chrome",
                "Iron",
                "Copper",
                "Silver",
                "Plutonium",
                "Cobalt",
                "Cesium",
                "Calcium",
                "Lithium",
                "Magnesium",
                "Potassium",
                "Graphite"
            ))
        }
    }
}
