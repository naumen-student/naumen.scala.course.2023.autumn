import utest._

object Test extends TestSuite{

    val tests = Tests{
        'test_divBy3Or7 - {
            assert(Exercises.divBy3Or7(1, 3) == Seq(3))
            assert(Exercises.divBy3Or7(5, 9) == Seq(6, 7, 9))
            assert(Exercises.divBy3Or7(0, 100) == Seq(0, 3, 6, 7, 9, 12, 14, 15, 18, 21, 24, 27, 28, 30, 33, 35, 36, 39, 42, 45, 48, 49, 51, 54, 56, 57, 60, 63, 66, 69, 70, 72, 75, 77, 78, 81, 84, 87, 90, 91, 93, 96, 98, 99))
        }
        'test_sumOfDivBy3Or5 - {
            assert(Exercises.sumOfDivBy3Or5(1, 3) == 3)
            assert(Exercises.sumOfDivBy3Or5(5, 15) == 57)
            assert(Exercises.sumOfDivBy3Or5(0, 100) == 2418)
            assert(Exercises.sumOfDivBy3Or5(-5, 7) == 6)
            assert(Exercises.sumOfDivBy3Or5(-5, -1) == -8)
        }
        'test_primeFactor - {
            assert(Exercises.primeFactor(1) == Seq.empty)
            assert(Exercises.primeFactor(98) == Seq(2, 7))
            assert(Exercises.primeFactor(64) == Seq(2))
            assert(Exercises.primeFactor(2310) == Seq(2, 3, 5, 7, 11))
            assert(Exercises.primeFactor(-10) == Seq(2, 5))
        }
        'test_sumScalars - {
            assert(Exercises.sumScalars(
              Exercises.Vector2D(1, 2),
              Exercises.Vector2D(3, 2),
              Exercises.Vector2D(2, 4),
              Exercises.Vector2D(1, 4)
            ) == 25)
            assert(Exercises.sumScalars(
              Exercises.Vector2D(-1, 2),
              Exercises.Vector2D(4, -2),
              Exercises.Vector2D(-2, 4),
              Exercises.Vector2D(4, 4)
            ) == 0)
            assert(Exercises.sumScalars(
              Exercises.Vector2D(-1, 2),
              Exercises.Vector2D(4, -2),
              Exercises.Vector2D(-2, 4),
              Exercises.Vector2D(4, -4)
            ) == -32)
        }
        'test_sumCosines - {
            val epsilon = 10E-7
            assert(Math.abs(Exercises.sumCosines(
              Exercises.Vector2D(1, 0),
              Exercises.Vector2D(1, 1),
              Exercises.Vector2D(2, 2),
              Exercises.Vector2D(0, 4)
            ) - Math.sqrt(2)) < epsilon)
            assert(Math.abs(Exercises.sumCosines(
              Exercises.Vector2D(1, 0),
              Exercises.Vector2D(0, 4),
              Exercises.Vector2D(-2, 0),
              Exercises.Vector2D(0, 4)
            )) < epsilon)
            assert(Math.abs(Exercises.sumCosines(
              Exercises.Vector2D(1, 0),
              Exercises.Vector2D(0, 4),
              Exercises.Vector2D(1, 0),
              Exercises.Vector2D(-3, 0)
            ) + 1) < epsilon)
        }
        'test_sortByHeavyweight - {
            assert(Exercises.sortByHeavyweight(Map(
              "Zirconium" -> (3, 6.45)
            )) == Seq("Zirconium"))
            assert(Exercises.sortByHeavyweight(Map(
              "Tungsten" ->  (2,   19.35),
              "Aluminum" -> (3,   2.6889),
              "Graphite" ->  (12,  2.1)
            )) == Seq("Aluminum", "Tungsten", "Graphite"))
            assert(Exercises.sortByHeavyweight(Map(
              "Aluminum" -> (3,   2.6889), "Tungsten" ->  (2,   19.35),
              "Graphite" ->  (12,  2.1),   "Iron" ->      (3,   7.874),
              "Gold" ->     (2,   19.32),  "Potassium" -> (14,  0.862),
              "Calcium" ->   (8,   1.55),  "Cobalt" ->    (4,   8.90)
            )) == Seq("Aluminum", "Gold", "Tungsten", "Iron", "Cobalt", "Calcium", "Potassium", "Graphite"))
        }
    }
}
