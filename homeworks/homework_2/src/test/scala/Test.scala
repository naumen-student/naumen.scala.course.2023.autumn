import utest._

object Test extends TestSuite{

    val tests = Tests{
        'test_divBy3Or7 - {
            assert(Exercises.divBy3Or7(1, 3) == Seq(3))
            assert(Exercises.divBy3Or7(5, 9) == Seq(6, 7, 9))
            assert(Exercises.divBy3Or7(0, 100) == Seq(0, 3, 6, 7, 9, 12, 14, 15, 18, 21, 24, 27, 28, 30, 33, 35, 36, 39, 42, 45, 48, 49, 51, 54, 56, 57, 60, 63, 66, 69, 70, 72, 75, 77, 78, 81, 84, 87, 90, 91, 93, 96, 98, 99))
        }

        'test_sumOfDivBy30r5 - {
            assert(Main.sumOfDivBy3Or5(0, 3) == 3)
            assert(Main.sumOfDivBy3Or5(0, 2) == 0)
            assert(Main.sumOfDivBy3Or5(1, 6) == 14)
            assert(Main.sumOfDivBy3Or5(5, 5) == 5)
            assert(Main.sumOfDivBy3Or5(15, 16) == 15)
        }

        'test_primeFactor - {
            assert(Main.primeFactor(10) == Seq(2, 5))
            assert(Main.primeFactor(1) == Seq())
            assert(Main.primeFactor(7) == Seq(7))
        }

        'test_sumScalars - {
            assert(Main.sumScalars(Vector2D(1, 1), Vector2D(1, 1), Vector2D(1, 1), Vector2D(1, 1)) == 4)
            assert(Main.sumScalars(Vector2D(0, 0), Vector2D(1, 1), Vector2D(1, 1), Vector2D(2, 2)) == 4)
            assert(Main.sumScalars(Vector2D(1, 0), Vector2D(1, 0), Vector2D(2, 3), Vector2D(4, 5)) == 24)
        }

        'test_sumCosines - {
            assert(Main.sumCosines(Vector2D(0, 0), Vector2D(0, 0), Vector2D(0, 0), Vector2D(0, 0)).isNaN)
            assert((Main.sumCosines(Vector2D(1, 1), Vector2D(1, 1), Vector2D(1, 1), Vector2D(1, 1)) - 2.0).abs < 1e-6)
            assert(Main.sumCosines(Vector2D(-4, -4), Vector2D(2, 2), Vector2D(3, 3), Vector2D(-1, -1)) == -2)
        }

        'test_sortByHeavyweight - {
            assert(Main.sortByHeavyweight(Map.empty) == Seq.empty)
            assert(Main.sortByHeavyweight(Map("a" -> (0, 0))) == Seq("a"))
            assert(Main.sortByHeavyweight(Map("Tungsten" -> (2, 19.35), "Graphite" -> (12, 2.1), "Iron" -> (3, 7.874))) ==
              Seq("Tungsten", "Iron", "Graphite"))
        }
    }
}