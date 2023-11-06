import utest._

object Test extends TestSuite{

    val tests = Tests{
        'test_divBy3Or7 - {
            assert(Exercises.divBy3Or7(1, 3) == Seq(3))
            assert(Exercises.divBy3Or7(5, 9) == Seq(6, 7, 9))
            assert(Exercises.divBy3Or7(0, 100) == Seq(0, 3, 6, 7, 9, 12, 14, 15, 18, 21, 24, 27, 28, 30, 33, 35, 36, 39, 42, 45, 48, 49, 51, 54, 56, 57, 60, 63, 66, 69, 70, 72, 75, 77, 78, 81, 84, 87, 90, 91, 93, 96, 98, 99))
        }
    }
}

'test_sumOfDivBy3Or5 - {
    assert(Exercises.sumOfDivBy3Or5(3,6)==14)
    assert(Exercises.sumOfDivBy3Or5(1, 5) == 8)
    assert(Exercises.sumOfDivBy3Or5(4, 6) == 11)
    assert(Exercises.sumOfDivBy3Or5(1, 20) == 98)
}

'test_primeFactor - {
    assert(Exercises.primeFactor(7) == Seq(7))
    assert(Exercises.primeFactor(60) == Seq(2, 2, 3, 5))
    assert(Exercises.primeFactor(44) == Seq(2, 2, 11))
    assert(Exercises.primeFactor(1) == Seq.empty
}

'test_sumScalars - {
    assert(Exercises.sumScalars(Vector2D(0, 0), Vector2D(1, 4), Vector2D(1, 2), Vector2D(0, 2)) == 4)
    assert(Exercises.sumScalars(Vector2D(1, 4), Vector2D(3, 2), Vector2D(0, 8), Vector2D(8, 2)) == 27)
    assert(Exercises.sumScalars(Vector2D(4, 0), Vector2D(0, 2), Vector2D(0, 13), Vector2D(8, 0)) == 0)
    assert(Exercises.sumScalars(Vector2D(1, 1), Vector2D(0, 1), Vector2D(1, 1), Vector2D(1, -1)) == 1)
}

'test_sumCosines - {
    assert(Exercises.sumCosines(Vector2D(0, 0), Vector2D(0, 0), Vector2D(0, 0), Vector2D(0, 0)).isNaN)
    assert((Exercises.sumCosines(Vector2D(1, 1), Vector2D(1, 1), Vector2D(1, 1), Vector2D(1, 1)) - 2.0).abs < 1e-6)
    assert(Exercises.sumCosines(Vector2D(-4, -4), Vector2D(2, 2), Vector2D(3, 3), Vector2D(-1, -1)) == -2)
}

'test_sortByHeavyweight - {
    assert(Exercises.sortByHeavyweight(Map.empty) == Seq.empty)
    assert(Exercises.sortByHeavyweight(Map("a" -> (0, 0))) == Seq("a"))
    assert(Exercises.sortByHeavyweight(Map("Tungsten" -> (2, 19.35), "Graphite" -> (12, 2.1), "Iron" -> (3, 7.874))) ==
      Seq("Tungsten", "Iron", "Graphite"))
}
}
}