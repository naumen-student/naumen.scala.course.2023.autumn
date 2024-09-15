import Exercises.{Vector2D, cosBetween, scalar}
import utest._

object Test extends TestSuite {

  val tests = Tests {
    'test_divBy3Or7 - {
      assert(Exercises.divBy3Or7(1, 3) == Seq(3))
      assert(Exercises.divBy3Or7(5, 9) == Seq(6, 7, 9))
      assert(Exercises.divBy3Or7(0, 100) == Seq(0, 3, 6, 7, 9, 12, 14, 15, 18, 21, 24, 27, 28, 30, 33, 35, 36, 39, 42, 45, 48, 49, 51, 54, 56, 57, 60, 63, 66, 69, 70, 72, 75, 77, 78, 81, 84, 87, 90, 91, 93, 96, 98, 99))
    }

    'sumOfDivBy3Or5 - {
      assert(Exercises.sumOfDivBy3Or5(1, 10) == 33)
      assert(Exercises.sumOfDivBy3Or5(5, 9) == 20)
      assert(Exercises.sumOfDivBy3Or5(1, 3) == 3)
      assert(Exercises.sumOfDivBy3Or5(15, 20) == 53)
    }

    'primeFactor - {
      assert(Exercises.primeFactor(2) == Seq(2))
      assert(Exercises.primeFactor(80) == Seq(2, 5))
      assert(Exercises.primeFactor(98) == Seq(2, 7))
      assert(Exercises.primeFactor(100) == Seq(2, 5))
      assert(Exercises.primeFactor(101) == Seq(101))
    }

    'test_sumScalars - {
      val v1 = Vector2D(1, 2)
      val v2 = Vector2D(3, 4)
      val v3 = Vector2D(5, 6)
      val v4 = Vector2D(7, 8)
      assert(Exercises.sumScalars(v1, v2, v3, v4) == scalar(v1, v2) + scalar(v3, v4))
    }

    'test_sumCosines - {
      val v1 = Vector2D(1, 2)
      val v2 = Vector2D(3, 4)
      val v3 = Vector2D(5, 6)
      val v4 = Vector2D(7, 8)
      assert(Exercises.sumCosines(v1, v2, v3, v4) == cosBetween(v1, v2) + cosBetween(v3, v4))
    }

    'test_sortByHeavyweight - {
      assert(Exercises.sortByHeavyweight(Map.empty) == Seq.empty)
      assert(Exercises.sortByHeavyweight(Map("a" -> (0, 0))) == Seq("a"))
      assert(Exercises.sortByHeavyweight(Map("Tungsten" -> (2, 19.35), "Graphite" -> (12, 2.1), "Iron" -> (3, 7.874))) ==
        Seq("Tungsten", "Iron", "Graphite"))
    }
  }
}
