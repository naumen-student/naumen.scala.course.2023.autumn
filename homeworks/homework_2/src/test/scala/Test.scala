import Exercises.Vector2D
import utest._

object Test extends TestSuite{

    val tests: Tests = Tests{
        'test_divBy3Or7 - {
            assert(Exercises.divBy3Or7(10, 11) == Seq())
            assert(Exercises.divBy3Or7(1, 3) == Seq(3))
            assert(Exercises.divBy3Or7(3, 3) == Seq(3))
            assert(Exercises.divBy3Or7(5, 9) == Seq(6, 7, 9))
            assert(Exercises.divBy3Or7(0, 100) == Seq(0, 3, 6, 7, 9, 12, 14, 15, 18, 21, 24, 27, 28, 30, 33, 35, 36, 39, 42, 45, 48, 49, 51, 54, 56, 57, 60, 63, 66, 69, 70, 72, 75, 77, 78, 81, 84, 87, 90, 91, 93, 96, 98, 99))
        }
        'test_sumOfDivBy3Or5 - {
            assert(Exercises.sumOfDivBy3Or5(1, 3) == 3)
            assert(Exercises.sumOfDivBy3Or5(13, 14) == 0)
            assert(Exercises.sumOfDivBy3Or5(5, 9) == 20)
            assert(Exercises.sumOfDivBy3Or5(3, 3) == 3)
        }
        'test_primeFactor - {
            assert(Exercises.primeFactor(3) == Seq(3))
            assert(Exercises.primeFactor(4) == Seq(2))
            assert(Exercises.primeFactor(10) == Seq(2, 5))
            assert(Exercises.primeFactor(100000) == Seq(2, 5))
        }
        'test_sumScalars - {
            val left0 = Vector2D(1,2)
            val left1 = Vector2D(1,3)
            val right0 = Vector2D(2,5)
            val right1 = Vector2D(2,7)
            val expected = Exercises.scalar(left0, left1) + Exercises.scalar(right0, right1)
            val actual = Exercises.sumScalars(left0, left1, right0, right1)
            assert(scala.math.abs(expected - actual) <= 1e-9)
        }
        'test_sumCosines - {
            val left0 = Vector2D(1, 2)
            val left1 = Vector2D(1, 3)
            val right0 = Vector2D(2, 5)
            val right1 = Vector2D(2, 7)
            val expected = Exercises.cosBetween(left0, left1) + Exercises.cosBetween(right0, right1)
            val actual = Exercises.sumCosines(left0, left1, right0, right1)
            assert(scala.math.abs(expected - actual) <= 1e-9)
        }
        'test_sortByHeavyweight - {
            var balls = Map(
                "Ball1" -> (1, 1.0),
                "Ball2" -> (1, 4.0),
                "Ball3" -> (1, 5.0))
            assert(Exercises.sortByHeavyweight(balls) == Seq("Ball1", "Ball2", "Ball3"))

            balls = Map(
                "Ball1" -> (1, 5.0),
                "Ball2" -> (1, 4.0),
                "Ball3" -> (1, 3.0))
            assert(Exercises.sortByHeavyweight(balls) == Seq("Ball3", "Ball2", "Ball1"))

            balls = Map(
                "Ball1" -> (1, 4.0),
                "Ball2" -> (1, 4.0))
            assert(Exercises.sortByHeavyweight(balls) == Seq("Ball1", "Ball2"))
        }
    }
}
