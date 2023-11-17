import Exercises.Vector2D
import utest._

object Test extends TestSuite{

    val tests = Tests{
        'test_divBy3Or7 - {
            assert(Exercises.divBy3Or7(1, 3) == Seq(3))
            assert(Exercises.divBy3Or7(5, 9) == Seq(6, 7, 9))
            assert(Exercises.divBy3Or7(0, 100) == Seq(0, 3, 6, 7, 9, 12, 14, 15, 18, 21, 24, 27, 28, 30, 33, 35, 36, 39, 42, 45, 48, 49, 51, 54, 56, 57, 60, 63, 66, 69, 70, 72, 75, 77, 78, 81, 84, 87, 90, 91, 93, 96, 98, 99))
        }

        'test_sumOfDivBy3Or5 - {
            assert(Exercises.sumOfDivBy3Or5(1, 5) == 8)
            assert(Exercises.sumOfDivBy3Or5(1, 10) == 33)
        }

        'test_primeFactor - {
            assert(Exercises.primeFactor(5) == Seq(5))
            //assert(Exercises.primeFactor(440) == Seq(2, 5, 11))
            //assert(Exercises.primeFactor(98) == Seq(2, 7))
        }

        'test_sumScalars - {
            val vec1 = Vector2D(1, 2)
            val vec2 = Vector2D(2, 3)
            val vec3 = Vector2D(-1, -1)
            val vec4 = Vector2D(3, 4)

            assert(Exercises.sumScalars(vec1, vec2, vec3, vec4) == Exercises.scalar(vec1, vec2) + Exercises.scalar(vec3, vec4))
        }

        'test_sumCosines - {
            val vec1 = Vector2D(1, 2)
            val vec2 = Vector2D(2, 3)
            val vec3 = Vector2D(-1, -1)
            val vec4 = Vector2D(3, 4)

            assert(Exercises.sumCosines(vec1, vec2, vec3, vec4) == Exercises.cosBetween(vec1, vec2) + Exercises.cosBetween(vec3, vec4))
        }

        'test_sortByHeavyweight - {
            val testBalls = Map(
                "A" -> (3, 2.0),
                "B" -> (1, 5.0),
                "C" -> (2, 3.0)
            )

            val sortedBalls = Exercises.sortByHeavyweight(testBalls)
            assert(sortedBalls == Seq("B", "C", "A")) // Сортировка по возрастанию массы
        }
    }
}