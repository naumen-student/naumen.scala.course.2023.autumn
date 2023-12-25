import scala.util.{Failure, Success, Try}

/*
  Задание №5
  Задание аналогично предыдущему задания, но теперь мы уходим от использования стандартного Either.
  Нужно:
  1) Доделать реализацию MyEither (нужны аналоги Right и Left)
  2) Написать для MyEither инстанс MonadError
  3) Написать функции apply, error, possibleError
 */
object Task5 extends App {

  import Task4.MonadError

  sealed trait MyEither[+E, +A] {
    def isError: Boolean
  }

  case class RightAnalogue[+E, +A](value: A) extends MyEither[E, A] {
    override def isError: Boolean = false
  }

  case class LeftAnalogue[+E, +A](value: E) extends MyEither[E, A] {
    override def isError: Boolean = true
  }

  object MyEither {

    def apply[A](value: A): MyEither[Nothing, A] = RightAnalogue(value)

    def error[E, A](error: E): MyEither[E, A] = LeftAnalogue(error)

    def possibleError[A](f: => A): MyEither[Throwable, A] = Try(f) match {
      case Failure(exception) => LeftAnalogue(exception)
      case Success(value) => RightAnalogue(value)
    }

    implicit def myEitherMonad[E]: MonadError[MyEither, E] = new MonadError[MyEither, E] {
      override def pure[A](value: A): MyEither[E, A] = MyEither(value)

      override def flatMap[A, B](fa: MyEither[E, A])(f: A => MyEither[E, B]): MyEither[E, B] = fa match {
        case RightAnalogue(value) => f(value)
        case LeftAnalogue(value) => LeftAnalogue(value)
      }

      override def raiseError[A](fa: MyEither[E, A])(error: => E): MyEither[E, A] = LeftAnalogue(error)

      override def handleError[A](fa: MyEither[E, A])(handle: E => A): MyEither[E, A] =
        fa match {
          case RightAnalogue(value) => RightAnalogue(value)
          case LeftAnalogue(error) => RightAnalogue(handle(error))
        }
    }
  }

  object MyEitherSyntax {
    implicit class MyEitherOps[E, A](val either: MyEither[E, A]) {
      def flatMap[B](f: A => MyEither[E, B]): MyEither[E, B] =
        MyEither.myEitherMonad[E].flatMap(either)(f)

      def map[B](f: A => B): MyEither[E, B] = MyEither.myEitherMonad.map(either)(f)

      def handleError(f: E => A): MyEither[E, A] =
        MyEither.myEitherMonad.handleError(either)(f)
    }
  }
}