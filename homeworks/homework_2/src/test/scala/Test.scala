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
            assert(Exercises.sumOfDivBy3Or5(10, 15) == 37)
            assert(Exercises.sumOfDivBy3Or5(25, 50) == 450)
            assert(Exercises.sumOfDivBy3Or5(1, 20) == 98)
        }
        'test_primeFactor - {
            assert(Exercises.primeFactor(80) == Seq(2, 2, 2, 2, 5))
            assert(Exercises.primeFactor(98) == Seq(2, 7, 7))
            assert(Exercises.primeFactor(10) == Seq(2, 5))
        }
        'test_sumScalars - {
            assert(Exercises.sumScalars(Vector2D(0, 3), Vector2D(2, 4), Vector2D(5, 1), Vector2D(0, 2)) == 14)
            assert(Exercises.sumScalars(Vector2D(0, 0), Vector2D(0, 0), Vector2D(1, 0), Vector2D(1, 0)) == 1)
            assert(Exercises.sumScalars(Vector2D(1, 4), Vector2D(7, 2), Vector2D(3, 5), Vector2D(6, -7)) == -2)
        }

        'test_sumCosines - {
            assert(Exercises.sumCosines(Vector2D(0, 2), Vector2D(0, 3), Vector2D(0, 4), Vector2D(0, 5)) == 2.0)
            assert(Exercises.sumCosines(Vector2D(2, 3), Vector2D(3, 2), Vector2D(2, 5), Vector2D(4, 3)) > 1.77)
            assert(Exercises.sumCosines(Vector2D(0, 5), Vector2D(4, 5), Vector2D(5, 4), Vector2D(5, 0)) > 1.56)
        }
        'test_sortByHeavyWeight - {
            assert(sortByHeavyweight() == Seq("Tin", "Platinum", "Nickel", "Aluminum", "Titanium", "Lead",
                "Sodium", "Uranium", "Gold", "Tungsten", "Zirconium", "Chrome", "Iron", "Copper", "Silver",
                "Plutonium", "Cobalt", "Cesium", "Calcium", "Lithium", "Magnesium", "Potassium", "Graphite"))
            assert(sortByHeavyweight(Map("test1" -> (1, 14), "test2" -> (1, 5),
                "test3" -> (1, 6), "test4" -> (1, 2),
            )) == Seq("test4", "test2", "test3", "test1"))
            assert(sortByHeavyweight(Map("test1" -> (4, 224.7), "test2" -> (7, 35.8),
                "test3" -> (5, 25), "test4" -> (1, 1),
            )) == Seq("test4", "test3", "test2", "test1"))
        }
    }
}
