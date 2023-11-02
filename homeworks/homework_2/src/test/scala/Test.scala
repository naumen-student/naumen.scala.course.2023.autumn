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
      assert(Exercises.sumOfDivBy3Or5(4, 5) == 5)
      assert(Exercises.sumOfDivBy3Or5(1, 5) == 8)
      assert(Exercises.sumOfDivBy3Or5(5, 9) == 20)
      assert(Exercises.sumOfDivBy3Or5(1, 10) == 33)
      assert(Exercises.sumOfDivBy3Or5(-10, -1) == -33)
      assert(Exercises.sumOfDivBy3Or5(-10, 3) == -30)
      assert(Exercises.sumOfDivBy3Or5(-10, 5) == -25)
      assert(Exercises.sumOfDivBy3Or5(-10, 6) == -19)
    }

    'test_primeFactor - {
      assert(Exercises.primeFactor(80) == Seq(2, 5))
      assert(Exercises.primeFactor(98) == Seq(2, 7))
      assert(Exercises.primeFactor(1024) == Seq(2))
      assert(Exercises.primeFactor(2) == Seq(2))
      assert(Exercises.primeFactor(3) == Seq(3))
    }

    'test_sumScalars - {
      val leftVector0 = Exercises.Vector2D(1, 2)
      val leftVector1 = Exercises.Vector2D(2, 3)
      val rightVector0 = Exercises.Vector2D(3, 4)
      val rightVector1 = Exercises.Vector2D(4, 5)
      assert(Exercises.sumScalars(leftVector0, leftVector1, rightVector0, rightVector1) == 40)
      assert(Exercises.sumScalars(leftVector0, leftVector0, rightVector0, rightVector0) == 30)
      assert(Exercises.sumScalars(leftVector1, leftVector1, rightVector1, rightVector1) == 54)
    }

    'test_sumCosines - {
      val leftVector0 = Exercises.Vector2D(0, 2)
      val leftVector1 = Exercises.Vector2D(2, 0)
      val rightVector0 = Exercises.Vector2D(0, 4)
      val rightVector1 = Exercises.Vector2D(4, 0)
      assert(Exercises.sumCosines(leftVector0, leftVector1, rightVector0, rightVector1) == 0)
      assert(Exercises.sumCosines(leftVector0, leftVector0, rightVector0, rightVector0) == 2)
      assert(Exercises.sumCosines(leftVector1, leftVector1, rightVector1, rightVector1) == 2)
    }

    'test_sortByHeavyweight - {
      assert(Exercises.sortByHeavyweight() == Seq("Tin", "Platinum", "Nickel", "Aluminum", "Titanium", "Lead", "Sodium",
        "Uranium", "Gold", "Tungsten", "Zirconium", "Chrome", "Iron", "Copper", "Silver", "Plutonium", "Cobalt", "Cesium",
        "Calcium", "Lithium", "Magnesium", "Potassium", "Graphite"))
    }
  }
}
