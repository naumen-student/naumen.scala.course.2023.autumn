import utest._
import Exercises.Vector2D

object Test extends TestSuite {

  val tests = Tests {
    'test_divBy3Or7 - {
      assert(Exercises.divBy3Or7(1, 3) == Seq(3))
      assert(Exercises.divBy3Or7(5, 9) == Seq(6, 7, 9))
      assert(Exercises.divBy3Or7(0, 100) == Seq(0, 3, 6, 7, 9, 12, 14, 15, 18, 21, 24, 27, 28, 30, 33, 35, 36, 39, 42, 45, 48, 49, 51, 54, 56, 57, 60, 63, 66, 69, 70, 72, 75, 77, 78, 81, 84, 87, 90, 91, 93, 96, 98, 99))
    }

    'sumOfDivBy3Or5 - {
      assert(Exercises.sumOfDivBy3Or5(1, 20) == 98)
      assert(Exercises.sumOfDivBy3Or5(10, 200) == 9345)
      assert(Exercises.sumOfDivBy3Or5(300, 500) == 37568)
    }
    'primeFactor - {
      assert(Exercises.primeFactor(10) == Seq(2, 5))
      assert(Exercises.primeFactor(80) == Seq(2, 5))
      assert(Exercises.primeFactor(98) == Seq(2, 7))
    }
    'test_sumByFunc - {
      assert(Exercises.sumScalars(Vector2D(2, 4), Vector2D(0, 0), Vector2D(8, 8), Vector2D(1, 1)) == (0 + 16).toDouble)
      assert(Math.abs(Exercises.sumCosines(Vector2D(2, 2), Vector2D(5, 2), Vector2D(1, 1), Vector2D(-1, -1)) - -0.08) < 0.01)
    }
    'sortByHeavyweight - {
      assert(Exercises.sortByHeavyweight() == Seq("Tin",
        "Platinum", "Nickel", "Aluminum", "Titanium", "Lead", "Sodium", "Uranium", "Gold", "Tungsten",
        "Zirconium", "Chrome", "Iron", "Copper", "Silver", "Plutonium", "Cobalt", "Cesium", "Calcium",
        "Lithium", "Magnesium", "Potassium", "Graphite"))
    }
  }
}
