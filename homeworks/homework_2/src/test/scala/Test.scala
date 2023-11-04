import utest._
import Exercises.Vector2D, Exercises.scalar, Exercises.cosBetween, Exercises.balls

object Test extends TestSuite{

    val tests = Tests{
        'test_divBy3Or7 - {
            assert(Exercises.divBy3Or7(1, 3) == Seq(3))
            assert(Exercises.divBy3Or7(5, 9) == Seq(6, 7, 9))
            assert(Exercises.divBy3Or7(0, 100) == Seq(0, 3, 6, 7, 9, 12, 14, 15, 18, 21, 24, 27, 28, 30, 33, 35, 36, 39, 42, 45, 48, 49, 51, 54, 56, 57, 60, 63, 66, 69, 70, 72, 75, 77, 78, 81, 84, 87, 90, 91, 93, 96, 98, 99))
        }

        'sumOfDivBy3Or5 - {
            assert(Exercises.sumOfDivBy3Or5(1, 10) == 33)
            assert(Exercises.sumOfDivBy3Or5(0, 1) == 0)
        }

        'primeFactor - {
            assert(Exercises.primeFactor(80) == Seq(2, 5))
            assert(Exercises.primeFactor(98) == Seq(2, 7))
            assert(Exercises.primeFactor(33) == Seq(3, 11))
        }

        'sumByFunc - {
            val vec1 = Vector2D(1, 2)
            val vec2 = Vector2D(3, 4)
            val vec3 = Vector2D(5, 6)
            val vec4 = Vector2D(7, 8)

            assert(Exercises.sumScalars(vec1, vec2, vec3, vec4) == scalar(vec1, vec2) + scalar(vec3, vec4))
            assert(Exercises.sumCosines(vec1, vec2, vec3, vec4) == cosBetween(vec1, vec2) + cosBetween(vec3, vec4))
        }

        'sortByHeavyweight - {
            println(Exercises.sortByHeavyweight(balls))
            assert(Exercises.sortByHeavyweight(balls) == Seq("Tin", "Platinum", "Nickel", "Aluminum", "Titanium",
                "Lead", "Sodium", "Uranium", "Gold", "Tungsten", "Zirconium", "Chrome", "Iron", "Copper",
                "Silver", "Plutonium", "Cobalt", "Cesium", "Calcium", "Lithium", "Magnesium", "Potassium", "Graphite"))
        }
    }
}
