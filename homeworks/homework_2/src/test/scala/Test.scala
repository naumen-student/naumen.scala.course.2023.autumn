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
            assert(Exercises.sumOfDivBy3Or5(1, 2) == 0)
            assert(Exercises.sumOfDivBy3Or5(1, 3) == 3)
            assert(Exercises.sumOfDivBy3Or5(1, 5) == 8)
            assert(Exercises.sumOfDivBy3Or5(5, 5) == 5)
            assert(Exercises.sumOfDivBy3Or5(6, 5) == 0)
            assert(Exercises.sumOfDivBy3Or5(4, 10) == 30)
        }

        'test_primeFactor - {
            assert(Exercises.primeFactor(5) == Seq(5))
            assert(Exercises.primeFactor(10) == Seq(2, 5))
            assert(Exercises.primeFactor(16) == Seq(2))
            assert(Exercises.primeFactor(80) == Seq(2, 5))
            assert(Exercises.primeFactor(98) == Seq(2, 7))
            assert(Exercises.primeFactor(121) == Seq(11))
            assert(Exercises.primeFactor(137) == Seq(137))
            assert(Exercises.primeFactor(210) == Seq(2, 3, 5, 7))
        }

        'test_sumScalars - {
            assert(Exercises.sumScalars(Vector2D(1, 0), Vector2D(0, 1), Vector2D(1, 1), Vector2D(1, -1)) == 0)
            assert(Exercises.sumScalars(Vector2D(1, 1), Vector2D(0, 1), Vector2D(1, 1), Vector2D(1, -1)) == 1)
            assert(Exercises.sumScalars(Vector2D(2, 2), Vector2D(0, 1.5), Vector2D(1, 1), Vector2D(1, -1)) == 3)
            assert(Exercises.sumScalars(Vector2D(2, 2), Vector2D(0, 1.5), Vector2D(1.5, 1), Vector2D(1, -1)) == 3.5)
            assert(Exercises.sumScalars(Vector2D(0, 0), Vector2D(0, 0), Vector2D(1.5, 1), Vector2D(1, -1)) == 0.5)
            assert(Exercises.sumScalars(Vector2D(0, -3), Vector2D(0, -7), Vector2D(1.5, 1), Vector2D(1, -1)) == 21.5)
            assert(Exercises.sumScalars(Vector2D(0, 3), Vector2D(0, -7), Vector2D(1.5, 1), Vector2D(1, -1)) == -20.5)
        }

        'test_sumCosines - {
            assert(Exercises.sumCosines(Vector2D(1, 0), Vector2D(0, 1), Vector2D(1, 1), Vector2D(1, -1)) == 0)
            assertWithDelta(Exercises.sumCosines(Vector2D(1, 1), Vector2D(0, 1), Vector2D(1, 1), Vector2D(1, -1)), math.sqrt(2)/2, 1e-9)
            assertWithDelta(Exercises.sumCosines(Vector2D(1, 1), Vector2D(0, 1), Vector2D(1, 0), Vector2D(1, 1)), math.sqrt(2), 1e-9)
            assertWithDelta(Exercises.sumCosines(Vector2D(2, 2), Vector2D(-1, 1), Vector2D(-1, 0), Vector2D(-1, -1)), math.sqrt(2)/2, 1e-9)
            assert(Exercises.sumCosines(Vector2D(0, 0), Vector2D(1, 0), Vector2D(1.5, 1), Vector2D(1, -1)).isNaN)
        }

        'test_sortByHeavyweight - {
            assert(Exercises.sortByHeavyweight() == Seq("Tin", "Platinum", "Nickel", "Aluminum", "Titanium", "Lead", "Sodium", "Uranium", "Gold", "Tungsten", "Zirconium", "Chrome", "Iron", "Copper", "Silver", "Plutonium", "Cobalt", "Cesium", "Calcium", "Lithium", "Magnesium", "Potassium", "Graphite"))
            assert(Exercises.sortByHeavyweight(Map("Aluminum" -> (3, 2.6889), "Tungsten" -> (3, 2.6889))) == Seq("Aluminum", "Tungsten"))
            assert(Exercises.sortByHeavyweight(Map.empty) == Seq.empty)
        }
    }

    def assertWithDelta(actual: Double, expected: Double, delta: Double): Unit = {
        assert(actual * expected >= 0)
        assert(math.abs(actual - expected) <= delta)
    }
}
