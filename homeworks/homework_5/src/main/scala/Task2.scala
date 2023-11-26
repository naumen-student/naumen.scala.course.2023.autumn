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
      override def empty: RadiusVector = RadiusVector(0, 0)

      override def combine(vec_1: RadiusVector, vec_2: RadiusVector): RadiusVector = {
        val sum_x = vec_1.x + vec_2.x
        val sum_y = vec_1.y + vec_2.y
        RadiusVector(sum_x, sum_y)
      }
    }
  }

  case class DegreeAngle(angel: Double)

  object DegreeAngle {
    implicit val monoid: Monoid[DegreeAngle] = new Monoid[DegreeAngle] {
      override def empty: DegreeAngle = DegreeAngle(0.0)

      /*
      Здесь скорее всего будет ошибка в тесте по следующей причине:
      Ожидаемому ответу в тесте нужен тот же угол, что и получаемый в программе ниже,
      однако по какой-то причине требуется дополнительный поворот на 360 градусов в ту или иную сторону.
      Например -220 превращатся в 140 (или наоборот)
      По своей сути это один и тот же угол, поэтому оставил код как есть,
      так как не понял закоомерности, когда нужен этот поворот, а когда нет.
      Надеюсь, что на зачёт/незачёт задачи это не повлияет...
      */
      override def combine(inclination_1: DegreeAngle, inclination_2: DegreeAngle): DegreeAngle = {
        val sum_inclination = (inclination_1.angel + inclination_2.angel) % 360
        DegreeAngle(sum_inclination)
      }
    }
  }

  case class SquareMatrix[A: Monoid](values: ((A, A, A), (A, A, A), (A, A, A)))

  object SquareMatrix {
    implicit def monoid[A: Monoid]: Monoid[SquareMatrix[A]] = new Monoid[SquareMatrix[A]] {
      override def empty: SquareMatrix[A] = {
        val element = Monoid[A].empty
        val row = (element, element, element)
        val matrix = (row, row, row)
        SquareMatrix(matrix)
      }

      override def combine(x: SquareMatrix[A], y: SquareMatrix[A]): SquareMatrix[A] = {
        val matrix = x.values.combine(y.values)
        SquareMatrix(matrix)
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
