import Exercises._
import utest._

object Test extends TestSuite{

    val tests = Tests{
        'test_divBy3Or7 - {
            assert(Exercises.divBy3Or7(1, 3) == Seq(3))
            assert(Exercises.divBy3Or7(5, 9) == Seq(6, 7, 9))
            assert(Exercises.divBy3Or7(0, 100) == Seq(0, 3, 6, 7, 9, 12, 14, 15, 18, 21, 24, 27, 28, 30, 33, 35, 36, 39, 42, 45, 48, 49, 51, 54, 56, 57, 60, 63, 66, 69, 70, 72, 75, 77, 78, 81, 84, 87, 90, 91, 93, 96, 98, 99))
        }
        'test_sumOfDivBy3Or5 - {
          assert(sumOfDivBy3Or5(1, 3) == 3)
          assert(sumOfDivBy3Or5(5, 9) == 20)
          assert(sumOfDivBy3Or5(0, 100) == 2418)
          assert(sumOfDivBy3Or5(-3, 3) == 0)
        }
        'test_primeFactor - {
          assert(primeFactor(5) == Seq(5))
          assert(primeFactor(100) == Seq(2, 5))
          assert(primeFactor(99) == Seq(3, 11))
        }
        val leftVec0 = Vector2D(1, 2)
        val leftVec1 = Vector2D(3, 4)
        val rightVec0 = Vector2D(5, 6)
        val rightVec1 = Vector2D(7, 8)
        'test_sumScalars - {
          val result = sumScalars(leftVec0, leftVec1, rightVec0, rightVec1)
          val expected = 94 // 3 + 8 + 35 + 48 = 94
          assert(result == expected)
        }
        'test_sumCosines - {
          val result = sumCosines(leftVec0, leftVec1, rightVec0, rightVec1)
          val measure = Math.pow(10, -2)
          val expected = 1.98
          assert(Math.abs(result - expected) <= measure)
        }
        'test_sortByHeavyweight - {
          val balls: Map[String, (Int, Double)] = Map(
            "1" -> (1, 1), "2" -> (0, 1000), "3" -> (1000, 0.0001)
          )
          assert(sortByHeavyweight() == Seq("Tin", "Platinum", "Nickel", "Aluminum", "Titanium", "Lead", "Sodium", "Uranium", "Gold", "Tungsten", "Zirconium", "Chrome", "Iron", "Copper", "Silver", "Plutonium", "Cobalt", "Cesium", "Calcium", "Lithium", "Magnesium", "Potassium", "Graphite"))
          assert(sortByHeavyweight(balls) == Seq("2", "1", "3"))
        }
    }
}
