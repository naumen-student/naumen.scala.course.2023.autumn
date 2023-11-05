import Exercises.{Vector2D, cosBetween, scalar}
import utest._

object Test extends TestSuite {

    val tests = Tests {
        'test_divBy3Or7 - {
            assert(Exercises.divBy3Or7(10, 30) == Seq(12, 14, 15, 18, 21, 24, 27, 28, 30))
            assert(Exercises.divBy3Or7(40, 50) == Seq(42, 45, 49))
            assert(Exercises.divBy3Or7(100, 200) == Seq(102, 105, 108, 111, 114, 117, 119, 120, 123, 126, 129, 132, 133, 135, 138, 140, 141, 144, 147, 150, 153, 154, 156, 159, 161, 162, 165, 168, 171, 174, 175, 177, 180, 182, 183, 186, 189, 192, 195, 196, 198))
        }

        'test_sumOfDivBy3Or5 - {
            assert(Exercises.sumOfDivBy3Or5(10, 20) == 63)
            assert(Exercises.sumOfDivBy3Or5(20, 40) == 258)
            assert(Exercises.sumOfDivBy3Or5(100, 200) == 5250)
        }

        'test_primeFactor - {
            assert(Exercises.primeFactor(45) == Seq(3, 5))
            assert(Exercises.primeFactor(64) == Seq(2))
            assert(Exercises.primeFactor(13) == Seq(13))
        }

        'test_sumByFunc - {
            val vec0 = Vector2D(1, 3)
            val vec1 = Vector2D(2, 5)
            val vec2 = Vector2D(4, 7)
            val vec3 = Vector2D(6, 8)

            assert(Exercises.sumByFunc(vec0, vec1, Exercises.scalar, vec2, vec3) == scalar(vec0, vec1) + scalar(vec2, vec3))
            assert(Exercises.sumByFunc(vec0, vec1, Exercises.cosBetween, vec2, vec3) == cosBetween(vec0, vec1) + cosBetween(vec2, vec3))
        }

        'test_sortByHeavyweight - {
            val customBalls = Map(
                "Brass" -> (2, 8.73), "Zinc" -> (3, 7.133), "Manganese" -> (4, 7.21),
                "Beryllium" -> (1, 1.848), "Silver" -> (2, 10.49), "Boron" -> (5, 2.37),
                "Carbon" -> (1, 2.267), "Silicon" -> (3, 2.329), "Mercury" -> (2, 13.546),
                "Gallium" -> (1, 5.91), "Arsenic" -> (4, 5.776), "Rubidium" -> (6, 1.532),
                "Strontium" -> (2, 2.54), "Yttrium" -> (3, 4.472), "Niobium" -> (1, 8.57)
            )

            assert(Exercises.sortByHeavyweight(customBalls) == Seq("Beryllium", "Carbon", "Boron", "Strontium", "Silicon", "Yttrium", "Gallium", "Arsenic", "Manganese", "Zinc", "Niobium", "Brass", "Silver", "Mercury"))
        }
    }
}
