import utest._

object Test extends TestSuite {

  val tests = Tests {
    'test_divBy3Or7 - {
      assert(Exercises.divBy3Or7(1, 3) == Seq(3))
      assert(Exercises.divBy3Or7(5, 9) == Seq(6, 7, 9))
      assert(
        Exercises.divBy3Or7(0, 100) == Seq(0, 3, 6, 7, 9, 12, 14, 15, 18, 21,
          24, 27, 28, 30, 33, 35, 36, 39, 42, 45, 48, 49, 51, 54, 56, 57, 60,
          63, 66, 69, 70, 72, 75, 77, 78, 81, 84, 87, 90, 91, 93, 96, 98, 99)
      )
    }
    test_sumOfDivBy3Or5 - {
      assert(Exercises.sumOfDivBy3Or5(0, 3) == 3)
      assert(Exercises.sumOfDivBy3Or5(1, 9) == 25)
      assert(Exercises.sumOfDivBy3Or5(10, 100) == 2208)
    }
    test_primeFactor - {
      assert(Exercises.primeFactor(17) == Seq(17))
      assert(Exercises.primeFactor(20) == Seq(2, 5))
      assert(Exercises.primeFactor(99) == Seq(3, 11))
    }
    test_sumScalars - {
      val vector1 = Exercises.Vector2D(0, 1)
      val vector2 = Exercises.Vector2D(1, 0)
      val vector3 = Exercises.Vector2D(1, 50)
      val vector4 = Exercises.Vector2D(50, 10)
      val vector5 = Exercises.Vector2D(100, 1)
      val vector6 = Exercises.Vector2D(100, 50)
      assert(Exercises.sumScalars(vector1, vector2, vector3, vector1) == 50)
      assert(Exercises.sumScalars(vector3, vector4, vector5, vector6) == 10600)
      assert(Exercises.sumScalars(vector6, vector1, vector4, vector5) == 5060)
    }
    test_sumCosines - {
      val vector1 = Exercises.Vector2D(0, 1)
      val vector2 = Exercises.Vector2D(1, 0)
      val vector3 = Exercises.Vector2D(1, 50)
      val vector4 = Exercises.Vector2D(50, 1)
      assert(Exercises.sumCosines(vector1, vector2, vector3, vector3) == 1)
      assert(Exercises.sumCosines(vector1, vector2, vector3, vector4) == 0)
      assert(Exercises.sumCosines(vector1, vector1, vector3, vector3) == 2)
    }
    test_sortByHeavyweight - {
      val ballsArray = Map(
        "Iron" -> (3, 7.874),
        "Tungsten" -> (2, 19.35),
        "Nickel" -> (2, 8.91),
        "Gold" -> (2, 19.32),
        "Potassium" -> (14, 0.862),
        "Calcium" -> (8, 1.55),
        "Cobalt" -> (4, 8.90),
        "Lithium" -> (12, 0.534),
        "Sodium" -> (5, 0.971),
        "Tin" -> (1, 7.29),
        "Platinum" -> (1, 21.45),
        "Plutonium" -> (3, 19.25),
        "Titanium" -> (2, 10.50),
        "Silver" -> (4, 4.505),
        "Uranium" -> (2, 19.04),
        "Chrome" -> (3, 7.18),
        "Cesium" -> (7, 1.873)
      )

      assert(
        Exercises.sortByHeavyweight(ballsArray) == Seq(
          "Tin",
          "Platinum",
          "Sodium",
          "Nickel",
          "Titanium",
          "Chrome",
          "Iron",
          "Silver",
          "Uranium",
          "Lithium",
          "Gold",
          "Tungsten",
          "Cesium",
          "Calcium",
          "Cobalt",
          "Potassium",
          "Plutonium"
        )
      )
    }
  }
}
