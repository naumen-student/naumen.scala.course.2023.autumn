import org.scalatest.*
import org.scalatest.featurespec.AnyFeatureSpec

val funcs = Exercises

class Test extends AnyFeatureSpec {
  Feature("div 30 or 7"){
    val (from, to) = (1, 10)
    Scenario("get 3,6,7,9"){
      assert(
        funcs.divBy3Or7(from, to).sorted == List(3,6,7,9)
      )
    }
    Scenario("get 6,7,9"){
      val (from, to) = (5, 9)
      assert(
        Exercises.divBy3Or7(from, to) == Seq(6, 7, 9)
      )
    }
  }
  Feature("sum of div by 3 or 5"){
    Scenario("mess"){
      assert(Exercises.sumOfDivBy3Or5(1, 3) == 3)
      assert(Exercises.sumOfDivBy3Or5(1, 5) == 8)
      assert(Exercises.sumOfDivBy3Or5(4, 5) == 5)
      assert(Exercises.sumOfDivBy3Or5(5, 9) == 20)
      assert(Exercises.sumOfDivBy3Or5(1, 10) == 33)
      assert(Exercises.sumOfDivBy3Or5(-10, 3) == -30)
      assert(Exercises.sumOfDivBy3Or5(-10, -1) == -33)
      assert(Exercises.sumOfDivBy3Or5(-10, 6) == -19)
      assert(Exercises.sumOfDivBy3Or5(-10, 5) == -25)
    }
  }
  Feature("primes") {
    Scenario("mess") {
      assert(Exercises.primeFactor(80) == Seq(2, 5))
      assert(Exercises.primeFactor(98) == Seq(2, 7))
      assert(Exercises.primeFactor(1024) == Seq(2))
      assert(Exercises.primeFactor(2) == Seq(2))
      assert(Exercises.primeFactor(3) == Seq(3))    }
  }
  Feature("vectors"){
    Scenario("sum scalars"){
      val leftVector0 = Exercises.Vector2D(1, 2)
      val leftVector1 = Exercises.Vector2D(2, 3)
      val rightVector0 = Exercises.Vector2D(3, 4)
      val rightVector1 = Exercises.Vector2D(4, 5)
      assert(Exercises.sumScalars(leftVector0, leftVector1, rightVector0, rightVector1) == 40)
      assert(Exercises.sumScalars(leftVector0, leftVector0, rightVector0, rightVector0) == 30)
      assert(Exercises.sumScalars(leftVector1, leftVector1, rightVector1, rightVector1) == 54)    
    }
    Scenario("sum coses") {
      val leftVector0 = Exercises.Vector2D(0, 2)
      val leftVector1 = Exercises.Vector2D(2, 0)
      val rightVector0 = Exercises.Vector2D(0, 4)
      val rightVector1 = Exercises.Vector2D(4, 0)
      assert(Exercises.sumCosines(leftVector0, leftVector1, rightVector0, rightVector1) == 0)
      assert(Exercises.sumCosines(leftVector0, leftVector0, rightVector0, rightVector0) == 2)
      assert(Exercises.sumCosines(leftVector1, leftVector1, rightVector1, rightVector1) == 2)
    }
  }
  Feature("heavy"){
    Scenario("sort"){
      assert(
        Exercises.sortByHeavyweight() == Seq(
          "Tin", "Platinum", "Nickel", "Aluminum", "Titanium", "Lead", "Sodium",
          "Uranium", "Gold", "Tungsten", "Zirconium", "Chrome", "Iron", "Copper", 
          "Silver", "Plutonium", "Cobalt", "Cesium", "Calcium", "Lithium", 
          "Magnesium", "Potassium", "Graphite"
        )
      )
    }
  }
}
