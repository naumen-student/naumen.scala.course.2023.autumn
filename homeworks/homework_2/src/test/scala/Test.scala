import utest._
import Exercises._

object Test extends TestSuite{

    val tests = Tests{
        'test_divBy3Or7 - {
            assert(Exercises.divBy3Or7(1, 3) == Seq(3))
            assert(Exercises.divBy3Or7(5, 9) == Seq(6, 7, 9))
            assert(Exercises.divBy3Or7(0, 100) == Seq(0, 3, 6, 7, 9, 12, 14, 15, 18, 21, 24, 27, 28, 30, 33, 35, 36, 39, 42, 45, 48, 49, 51, 54, 56, 57, 60, 63, 66, 69, 70, 72, 75, 77, 78, 81, 84, 87, 90, 91, 93, 96, 98, 99))
        }

        'test_sumOfDivBy3Or5 - {
            assert(Exercises.sumOfDivBy3Or5(1, 2) == 0)
            assert(Exercises.sumOfDivBy3Or5(4, 10) == 30)
            assert(Exercises.sumOfDivBy3Or5(0, 50) == 593)
        }

        'test_primeFactor - {
            assert(Exercises.primeFactor(1) == Seq())
            assert(Exercises.primeFactor(7) == Seq(7))
            assert(Exercises.primeFactor(350) == Seq(2, 5, 7))
        }

        'test_sumScalars - {
            assert(Exercises.sumScalars(Vector2D(-5, -5), Vector2D(2, 1), Vector2D(1, 2), Vector2D(-4, -4)) == -27)
            assert(Exercises.sumScalars(Vector2D(0, 0), Vector2D(2, 1), Vector2D(1, 2), Vector2D(2, 1)) == 4)
        }

        'test_sumCosines - {
            assert(Exercises.sumCosines(Vector2D(-3, -4), Vector2D(2, 0), Vector2D(0, 5), Vector2D(0, 1)) == 0.4)
            assert(Exercises.sumCosines(Vector2D(1, 1), Vector2D(0, 1), Vector2D(1, 0), Vector2D(1, 1)) - java.lang.Math.sqrt(2) < 1e-5)
        }

        'test_sortByHeavyweight - {
            assert(Exercises.sortByHeavyweight(Map.empty) == Seq())
            assert(Exercises.sortByHeavyweight(Map("Aluminum" -> (3, 2.6889), "Tungsten" -> (2, 19.35))) == Seq("Aluminum", "Tungsten"))
            assert(Exercises.sortByHeavyweight(Map("Chrome" -> (3, 7.18), "Cesium" -> (7, 1.873), "Zirconium" -> (3, 6.45))) == Seq("Zirconium", "Chrome", "Cesium"))
        }
    }
}
