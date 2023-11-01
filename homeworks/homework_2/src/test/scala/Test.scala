import Exercises.Vector2D
import utest._

object Test extends TestSuite{

    val tests = Tests{
        'test_divBy3Or7 - {
            assert(Exercises.divBy3Or7(1, 3) == Seq(3))
            assert(Exercises.divBy3Or7(5, 9) == Seq(6, 7, 9))
            assert(Exercises.divBy3Or7(0, 100) == Seq(0, 3, 6, 7, 9, 12, 14, 15, 18, 21, 24, 27, 28, 30, 33, 35, 36, 39, 42, 45, 48, 49, 51, 54, 56, 57, 60, 63, 66, 69, 70, 72, 75, 77, 78, 81, 84, 87, 90, 91, 93, 96, 98, 99))
        }
        'test_sumOfDivBy3Or5 - {
            assert(Exercises.sumOfDivBy3Or5(1, 15) == 60)
            assert(Exercises.sumOfDivBy3Or5(-5, 5) == 0)
            assert(Exercises.sumOfDivBy3Or5(-5, 6) == 6)
            assert(Exercises.sumOfDivBy3Or5(0, 2) == 0)
        }
        'test_primeFactor - {
            assert(Exercises.primeFactor(80) == Seq(2, 5))
            assert(Exercises.primeFactor(98) == Seq(2, 7))
            assert(Exercises.primeFactor(126) == Seq(2, 3, 7))
            assert(Exercises.primeFactor(126) != Seq(2, 7, 3))
            assert(Exercises.primeFactor(1) == Seq())
            assert(Exercises.primeFactor(13) == Seq(13))
            assert(Exercises.primeFactor(39) == Seq(3, 13))
        }
        'test_sumScalars - {
            val a = Vector2D(1, 1)
            val b = Vector2D(3, 5)
            val c = Vector2D(-1, 3)
            val d = Vector2D(0, 8)
            assert(Exercises.sumScalars(a, b, c, d) == 32.0)
            assert(Exercises.sumScalars(d, b, c, a) == 42.0)
        }
        'test_sumCosines - {
            val l1 = Vector2D(1, 0)
            val l2 = Vector2D(0, 2)
            val r1 = Vector2D(-1, 0)
            val r2 = Vector2D(0, -4)
            assert(Exercises.sumCosines(l1, l2, r1, r2) == 0)
        }
    }
}
