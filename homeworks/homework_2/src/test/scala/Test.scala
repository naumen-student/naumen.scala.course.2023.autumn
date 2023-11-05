import utest._

object Test extends TestSuite{

    val tests = Tests{
        'test_divBy3Or7 - {
            assert(Exercises.divBy3Or7(1, 3) == Seq(3))
            assert(Exercises.divBy3Or7(5, 9) == Seq(6, 7, 9))
            assert(Exercises.divBy3Or7(0, 100) == Seq(0, 3, 6, 7, 9, 12, 14, 15, 18, 21, 24, 27, 28, 30, 33, 35, 36, 39, 42, 45, 48, 49, 51, 54, 56, 57, 60, 63, 66, 69, 70, 72, 75, 77, 78, 81, 84, 87, 90, 91, 93, 96, 98, 99))
        }
        'test_sumOfDivBy3Or5 - {
            assert(Exercises.sumOfDivBy3Or5(1,2)==0)
            assert(Exercises.sumOfDivBy3Or5(1,10)==23)
            assert(Exercises.sumOfDivBy3Or5(1,30)==195)
        }
        'tes_primeFactor - {
            assert(Exercises.primeFactor(80)==Seq(2,5))
            assert(Exercises.primeFactor(98)==Seq(2,7))
            assert(Exercises.primeFactor(126)==Seq(2,3,7))

        }
        'test_sumScalars - {
            assert(Exercises.sumScalars(
                Exercises.Vector2D(0,0),
                Exercises.Vector2D(0,0),
                Exercises.Vector2D(0,0),
                Exercises.Vector2D(0,0))==0)

            assert(Exercises.sumScalars(
                Exercises.Vector2D(1,2),
                Exercises.Vector2D(0,3),
                Exercises.Vector2D(5,2),
                Exercises.Vector2D(3,3),


            )==6+21)
            assert(Exercises.sumScalars(
                Exercises.Vector2D(5,5),
                Exercises.Vector2D(3,3),
                Exercises.Vector2D(3,3),
                Exercises.Vector2D(3,3)
            )==30+18)
        }
        'test_sumCosines - {
            assert(Exercises.sumCosines(
                Exercises.Vector2D(1,1),
                Exercises.Vector2D(2,2),
                Exercises.Vector2D(3,2),
                Exercises.Vector2D(6,4)

            )==2)

            val v1 = Exercises.Vector2D(3,4)
            val v2 = Exercises.Vector2D(5,3)
            val v4 = Exercises.Vector2D(3,3)
            val v3 = Exercises.Vector2D(6,7)
            assert(Exercises.sumCosines(v1,v2,v4,v3)==
              Exercises.cosBetween(v1,v2)+Exercises.cosBetween(v4,v3)
            )
            val v11 = Exercises.Vector2D(10, 4)
            val v12 = Exercises.Vector2D(5, 15)
            val v14 = Exercises.Vector2D(16, 3)
            val v13 = Exercises.Vector2D(6, 2)
            assert(Exercises.sumCosines(v11, v12, v14, v13) ==
              Exercises.cosBetween(v11, v12) + Exercises.cosBetween(v14, v13)
            )
        }
        'test_sortByHeavyweight - {
            val t1:Map[String,(Int,Double)]=Map("Heavy"->(13,1.0),"Light"->(1,10.0),"Medium"->(5,5.0))
            assert(Exercises.sortByHeavyweight(t1)==Seq("Light","Medium","Heavy"))
            val t2:Map[String,(Int,Double)]=Map("Heavy"->(1,5.0),"Light"->(2,4.0),"Medium"->(3,10.0))
            assert(Exercises.sortByHeavyweight(t2)==Seq("Light","Medium","Heavy"))
        }
    }
}
