import cats._
import cats.implicits._

/*
  Задание №2
  Всё просто, для каждого кейс класса необходимо описать логику его сложения.
  Радиус-вектор должен складываться, как и любой другой вектор.
  GradeAngle всегда выражает угол [0, 360).
  SquareMatrix просто сложение квадратных матриц
 */
object Task2 extends App {
  case class RadiusVector(x: Int, y: Int)

  object RadiusVector {
    implicit val monoid: Monoid[RadiusVector] = new Monoid[RadiusVector] {
      def empty: RadiusVector = RadiusVector(0, 0)

      def combine(x: RadiusVector, y: RadiusVector): RadiusVector = RadiusVector(x.x + y.x, x.y + y.y)
    }
  }

  case class DegreeAngle(private val _angle: Double) {
    val angle: Double = {
      val correctedAngle = _angle % 360
      if (correctedAngle < 0) correctedAngle + 360 else correctedAngle
    }
  }


  object DegreeAngle {
    implicit val monoid: Monoid[DegreeAngle] = new Monoid[DegreeAngle] {
      def empty: DegreeAngle = DegreeAngle(0)

      def combine(x: DegreeAngle, y: DegreeAngle): DegreeAngle = DegreeAngle((x.angle + y.angle) % 360)
    }
  }

  case class SquareMatrix[A: Monoid](values: ((A, A, A), (A, A, A), (A, A, A)))

  object SquareMatrix {
    implicit def monoid[A: Monoid]: Monoid[SquareMatrix[A]] = new Monoid[SquareMatrix[A]] {
      def empty: SquareMatrix[A] = SquareMatrix(((Monoid[A].empty, Monoid[A].empty, Monoid[A].empty), (Monoid[A].empty, Monoid[A].empty, Monoid[A].empty), (Monoid[A].empty, Monoid[A].empty, Monoid[A].empty)))

      def combine(x: SquareMatrix[A], y: SquareMatrix[A]): SquareMatrix[A] = {
        val ((a1, a2, a3), (b1, b2, b3), (c1, c2, c3)) = x.values
        val ((d1, d2, d3), (e1, e2, e3), (f1, f2, f3)) = y.values
        SquareMatrix(((a1 |+| d1, a2 |+| d2, a3 |+| d3), (b1 |+| e1, b2 |+| e2, b3 |+| e3), (c1 |+| f1, c2 |+| f2, c3 |+| f3)))
      }
    }
  }

  val radiusVectors = Vector(RadiusVector(0, 0), RadiusVector(0, 1), RadiusVector(-1, 1))
  Monoid[RadiusVector].combineAll(radiusVectors) // RadiusVector(-1, 2)

  val gradeAngles = Vector(DegreeAngle(380), DegreeAngle(60), DegreeAngle(30))
  Monoid[DegreeAngle].combineAll(gradeAngles) // GradeAngle(90)

  val matrixes = Vector(
    SquareMatrix(
      (
        (1, 2, 3),
        (4, 5, 6),
        (7, 8, 9)
      )
    ),
    SquareMatrix(
      (
        (-1, -2, -3),
        (-3, -4, -5),
        (-7, -8, -9)
      )
    )
  )
  Monoid[SquareMatrix[Int]].combineAll(matrixes)
  //  [0, 0, 0]
  //  |1, 1, 1|
  //  [0, 0, 0]
}
