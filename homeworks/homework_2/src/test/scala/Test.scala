import utest._
import Exercises.Vector2D

object Test extends TestSuite{

    val tests = Tests {
        'test_divBy3Or7 - {
            assert(Exercises.divBy3Or7(1, 3) == Seq(3))
            assert(Exercises.divBy3Or7(5, 9) == Seq(6, 7, 9))
            assert(Exercises.divBy3Or7(0, 100) == Seq(0, 3, 6, 7, 9, 12, 14, 15, 18, 21, 24, 27, 28, 30, 33, 35, 36, 39, 42, 45, 48, 49, 51, 54, 56, 57, 60, 63, 66, 69, 70, 72, 75, 77, 78, 81, 84, 87, 90, 91, 93, 96, 98, 99))
        }

        'test_sumOfDivBy3Or5 - {
            assert(Exercises.sumOfDivBy3Or5(1, 2) == 0)
            assert(Exercises.sumOfDivBy3Or5(1, 5) == 8)
            assert(Exercises.sumOfDivBy3Or5(3, 6) == 14)
            assert(Exercises.sumOfDivBy3Or5(1, 20) == 98)
        }

        'test_primeFactor - {
            assert(Exercises.primeFactor(5) == Seq(5))
            assert(Exercises.primeFactor(30) == Seq(2, 3, 5))
            assert(Exercises.primeFactor(210) == Seq(2, 3, 5, 7))
            assert(Exercises.primeFactor(1) == Seq.empty)
        }

        'test_sumScalars - {
            assert(Exercises.sumScalars(Vector2D(0, 0), Vector2D(1, 4), Vector2D(1, 2), Vector2D(0, 2)) == 4)
            assert(Exercises.sumScalars(Vector2D(1, 4), Vector2D(3, 2), Vector2D(0, 8), Vector2D(8, 2)) == 27)
            assert(Exercises.sumScalars(Vector2D(4, 0), Vector2D(0, 2), Vector2D(0, 13), Vector2D(8, 0)) == 0)
            assert(Exercises.sumScalars(Vector2D(1, 1), Vector2D(0, 1), Vector2D(1, 1), Vector2D(1, -1)) == 1)
        }

        'test_sumCosines - {
            assert(Exercises.sumCosines(Vector2D(1, 0), Vector2D(0, 1), Vector2D(1, 1), Vector2D(1, -1)) == 0)
            assert(Exercises.sumCosines(Vector2D(0, 3), Vector2D(1, 0), Vector2D(3, 4), Vector2D(0, 2)) == 0.8)
            assert(Exercises.sumCosines(Vector2D(-4, -4), Vector2D(2, 2), Vector2D(3, 3), Vector2D(-1, -1)) == -2)
        }

        'test_sortByHeavyWeight - {
            assert(Exercises.sortByHeavyweight(Map.empty) == Seq.empty)
            assert(Exercises.sortByHeavyweight(Map("Steel" -> (1, 7.86))) == Seq("Steel"))
            assert(Exercises.sortByHeavyweight(Map("Steel" -> (1, 7.86), "Iron" -> (1, 7.874))) == Seq("Steel", "Iron"))
            assert(Exercises.sortByHeavyweight(Map("Steel" -> (3, 7.86), "Iron" -> (3, 7.874), "Aluminum" -> (3, 2.7))) == Seq("Aluminum", "Steel", "Iron"))
        }
    }
}
