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
            assert(Exercises.sumOfDivBy3Or5(3,6)==14)
            assert(Exercises.sumOfDivBy3Or5(0,3)==3)
            assert(Exercises.sumOfDivBy3Or5(13,14)==0)
        }
        'test_primeFactor - {
            assert(Exercises.primeFactor(7) == Seq(7))
            assert(Exercises.primeFactor(1) == Seq())
            assert(Exercises.primeFactor(6) == Seq(2,3))
            assert(Exercises.primeFactor(18) == Seq(2,3))
            assert(Exercises.primeFactor(30) == Seq(2,3,5))
            assert(Exercises.primeFactor(98) == Seq(2,7))
        }
        'test_sumScalars - {
            assert(Exercises.sumScalars(Vector2D(-1, -1), Vector2D(1, 1), Vector2D(1, 1), Vector2D(1, -1)) == -2)
            assert(Exercises.sumScalars(Vector2D(0, 0), Vector2D(0, 0), Vector2D(0, 0), Vector2D(0, 0)) == 0)
            assert(Exercises.sumScalars(Vector2D(1, 1), Vector2D(1, 1), Vector2D(1, 1), Vector2D(-1, -1)) == 0)
        }

        'test_sumCosines - {
            assert(Exercises.sumCosines(Exercises.Vector2D(1, 0), Exercises.Vector2D(0, 1), Exercises.Vector2D(1, 0), Exercises.Vector2D(0, 1)) == 0)
            assert(Exercises.sumCosines(Vector2D(1, 0), Vector2D(1, 0), Vector2D(0, 1), Vector2D(0, 1)) == 2)
        }
        'test_sortByHeavyweight - {
            assert(Exercises.sortByHeavyweight(Map("Aluminum" -> (3, 2.6889),"Graphite" ->  (12,  2.1))) == Seq("Aluminum", "Graphite"))
            assert(Exercises.sortByHeavyweight(Map("Chrome" -> (3, 7.18), "Gold" ->     (2,   19.32), "Lead" ->     (2,   11.336))) == Seq("Lead", "Gold", "Chrome"))
            assert(Exercises.sortByHeavyweight(Map.empty) == Seq())
        }
    }
}
