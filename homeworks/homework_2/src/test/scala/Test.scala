import utest._
import Exercises.Vector2D
import scala.math.abs
import scala.math.pow
import scala.math.Pi

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

    'test_sumOfDivBy3Or5 - {
      assert(Exercises.sumOfDivBy3Or5(1, 3) == 3)
      assert(Exercises.sumOfDivBy3Or5(4, 5) == 5)
      assert(Exercises.sumOfDivBy3Or5(1, 5) == 8)
      assert(Exercises.sumOfDivBy3Or5(5, 8) == 11)
      assert(Exercises.sumOfDivBy3Or5(1, 10) == 33)

      assert(Exercises.sumOfDivBy3Or5(-3, -1) == -3)
      assert(Exercises.sumOfDivBy3Or5(-5, -4) == -5)
      assert(Exercises.sumOfDivBy3Or5(-5, -1) == -8)
      assert(Exercises.sumOfDivBy3Or5(-8, -5) == -11)
      assert(Exercises.sumOfDivBy3Or5(-10, -1) == -33)

      assert(Exercises.sumOfDivBy3Or5(-10, 10) == 0)
      assert(Exercises.sumOfDivBy3Or5(-10, 6) == -19)
      assert(Exercises.sumOfDivBy3Or5(-9, 5) == -15)
    }

    'test_primeFactor - {
      assert(Exercises.primeFactor(1) == Seq())
      assert(Exercises.primeFactor(2) == Seq(2))
      assert(Exercises.primeFactor(3) == Seq(3))
      assert(Exercises.primeFactor(64) == Seq(2))
      assert(Exercises.primeFactor(24) == Seq(2, 3))
      assert(Exercises.primeFactor(30) == Seq(2, 3, 5))
    }

    'test_sumScalars - {
      assert(
        Exercises.sumScalars(
          Vector2D(0, 0),
          Vector2D(0, 0),
          Vector2D(0, 0),
          Vector2D(0, 0)
        ) == 0
      )
      assert(
        Exercises.sumScalars(
          Vector2D(1, 1),
          Vector2D(1, 1),
          Vector2D(1, 1),
          Vector2D(-1, -1)
        ) == 0
      )
      assert(
        Exercises.sumScalars(
          Vector2D(1, -1),
          Vector2D(2, 2),
          Vector2D(3, 3),
          Vector2D(4, 4)
        ) == 24
      )
    }

    'test_sumCosines - {
      assert(
        Exercises.sumCosines(
          Exercises.Vector2D(1, 0),
          Exercises.Vector2D(0, 1),
          Exercises.Vector2D(1, 0),
          Exercises.Vector2D(0, 1)
        ) == 0
      )
      assert(
        Exercises.sumCosines(
          Vector2D(0, 2),
          Vector2D(0, 2),
          Vector2D(0, 4),
          Vector2D(0, 4)
        ) == 2
      )
    }

    'test_calcWeight - {
      assert(Exercises.calcWeight(0, 0.0) == 0.0)
      assert(Exercises.calcWeight(0, 1.0) == 0.0)
      assert(Exercises.calcWeight(1, 0.0) == 0.0)
      assert(abs(Exercises.calcWeight(4, 3.0) - pow(4, 4) * Pi) < 1e-10)
    }

    'test_sortByHeavyweight - {
      assert(
        Exercises.sortByHeavyweight() == Seq(
          "Tin",
          "Platinum",
          "Nickel",
          "Aluminum",
          "Titanium",
          "Lead",
          "Sodium",
          "Uranium",
          "Gold",
          "Tungsten",
          "Zirconium",
          "Chrome",
          "Iron",
          "Copper",
          "Silver",
          "Plutonium",
          "Cobalt",
          "Cesium",
          "Calcium",
          "Lithium",
          "Magnesium",
          "Potassium",
          "Graphite"
        )
      )
    }
  }
}
