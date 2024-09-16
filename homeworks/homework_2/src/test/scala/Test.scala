import utest._
import Exercises.Vector2D
object Test extends TestSuite{
    val balls: Map[String, (Int, Double)] =
        Map(
            "Aluminum" -> (3, 2.6889), "Tungsten" -> (2, 19.35), "Graphite" -> (12, 2.1), "Iron" -> (3, 7.874),
            "Gold" -> (2, 19.32), "Potassium" -> (14, 0.862), "Calcium" -> (8, 1.55), "Cobalt" -> (4, 8.90),
            "Lithium" -> (12, 0.534), "Magnesium" -> (10, 1.738), "Copper" -> (3, 8.96), "Sodium" -> (5, 0.971),
            "Nickel" -> (2, 8.91), "Tin" -> (1, 7.29), "Platinum" -> (1, 21.45), "Plutonium" -> (3, 19.25),
            "Lead" -> (2, 11.336), "Titanium" -> (2, 10.50), "Silver" -> (4, 4.505), "Uranium" -> (2, 19.04),
            "Chrome" -> (3, 7.18), "Cesium" -> (7, 1.873), "Zirconium" -> (3, 6.45)
        )

    val balls2: Map[String, (Int, Double)] =
        Map(
            "Aluminum" -> (3, 2.6889),"Tin" -> (1, 7.29),"Potassium" -> (14, 0.862),"Zirconium" -> (3, 6.45)
        )

    val balls3: Map[String, (Int, Double)] =
        Map(
            "Cesium" -> (7, 1.873), "Lithium" -> (12, 0.534), "Graphite" -> (12, 2.1), "Zirconium" -> (3, 6.45),
            "Potassium" -> (14, 0.862),"Tungsten" -> (2, 19.35)
        )

    val tests = Tests{
        'test_divBy3Or7 - {
            assert(Exercises.divBy3Or7(1, 3) == Seq(3))
            assert(Exercises.divBy3Or7(5, 9) == Seq(6, 7, 9))
            assert(Exercises.divBy3Or7(0, 100) == Seq(0, 3, 6, 7, 9, 12, 14, 15, 18, 21, 24, 27, 28, 30, 33, 35, 36, 39, 42, 45, 48, 49, 51, 54, 56, 57, 60, 63, 66, 69, 70, 72, 75, 77, 78, 81, 84, 87, 90, 91, 93, 96, 98, 99))
        }


        'test_sumOfDivBy3Or5 - {
            assert(Exercises.sumOfDivBy3Or5(1, 5) == 8)
            assert(Exercises.sumOfDivBy3Or5(1, 50) == 593)
            assert(Exercises.sumOfDivBy3Or5(-10, 10) == 0)
            assert(Exercises.sumOfDivBy3Or5(1, 100) == 2418)
            assert(Exercises.sumOfDivBy3Or5(1, 10000) == 23341668)
        }

        'test_primeFactor - {
            assert(Exercises.primeFactor(3) == Seq(3))
            assert(Exercises.primeFactor(6) == Seq(2,3))
            assert(Exercises.primeFactor(77) == Seq(7,11))
            assert(Exercises.primeFactor(80) == Seq(2, 5))

        }

        'test_sumCosines - {
            assert(Exercises.sumCosines(Vector2D(1,2),Vector2D(2,1),Vector2D(7,4),Vector2D(2,9)) == 1.4726727939963125)
            assert(Exercises.sumCosines(Vector2D(2,2),Vector2D(2,2),Vector2D(7,7),Vector2D(8,8)) == 2.0)
            assert(Exercises.sumCosines(Vector2D(4,-13),Vector2D(10,1),Vector2D(10,-4),Vector2D(4,-9)) == 0.9139939788825594)
            assert(Exercises.sumCosines(Vector2D(22,34),Vector2D(3,8),Vector2D(6,21),Vector2D(23,11)) == 1.6395524170733113)
            assert(Exercises.sumCosines(Vector2D(2,2),Vector2D(-2,2),Vector2D(2,-2),Vector2D(-2,-2)) == 0.0)
            assert(Exercises.sumCosines(Vector2D(2,0),Vector2D(0,8),Vector2D(-5,0),Vector2D(0,-11)) == 0.0)

        }

        'test_sumScalars - {
            assert(Exercises.sumScalars(Vector2D(1,2),Vector2D(2,1),Vector2D(7,4),Vector2D(2,9)) == 54.0)
            assert(Exercises.sumScalars(Vector2D(2,0),Vector2D(0,2),Vector2D(-3,0),Vector2D(0,-11)) == 0.0)
            assert(Exercises.sumScalars(Vector2D(22,34),Vector2D(3,8),Vector2D(6,21),Vector2D(23,11)) == 707.0)
            assert(Exercises.sumScalars(Vector2D(2,2),Vector2D(5,5),Vector2D(2,2),Vector2D(4,4)) == 36.0)
            assert(Exercises.sumScalars(Vector2D(4,-43),Vector2D(10,1),Vector2D(10,-4),Vector2D(4,-9)) == 73.0)
            assert(Exercises.sumScalars(Vector2D(1,1),Vector2D(-2,2),Vector2D(2,-2),Vector2D(-2,-2)) == 0.0)

        }

        'test_sortByHeavyweight - {
            assert(Exercises.sortByHeavyweight(balls) == Seq("Tin", "Platinum", "Nickel", "Aluminum",
                "Titanium", "Lead", "Sodium", "Uranium", "Gold", "Tungsten",
                "Zirconium", "Chrome", "Iron", "Copper", "Silver", "Plutonium",
                "Cobalt", "Cesium", "Calcium", "Lithium", "Magnesium", "Potassium", "Graphite"))
            assert(Exercises.sortByHeavyweight(balls2) == Seq("Tin", "Aluminum", "Zirconium", "Potassium"))
            assert(Exercises.sortByHeavyweight(balls3) == Seq("Tungsten", "Zirconium", "Cesium", "Lithium", "Potassium", "Graphite"))


        }

    }
}
