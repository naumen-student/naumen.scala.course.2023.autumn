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

  object MyEither {
    case class Right[+E, +R](res: R) extends MyEither[E, R] {
      override def isError: Boolean = false
    }

    case class Left[+E, +R](exp: E) extends MyEither[E, R] {
      override def isError: Boolean = true
    }

    def apply[A](value: A): MyEither[Nothing, A] = Right(value)

    def error[E, A](exp: E): MyEither[E, A] = Left(exp)

    def possibleError[A](f: => A): MyEither[Throwable, A] = Try(f) match {
      case Success(res) => Right(res)
      case Failure(exp) => Left(exp)
    }

    implicit def myEitherMonad[E]: MonadError[MyEither, E] = new MonadError[MyEither, E] {
      override def raiseError[A](fa: MyEither[E, A])(exp: => E): MyEither[E, A] = Left(exp)

      override def pure[A](res: A): MyEither[E, A] = Right(res)

      override def flatMap[A, B](fa: MyEither[E, A])(f: A => MyEither[E, B]): MyEither[E, B] = fa match {
        case Right(res) => f(res)
        case Left(exp) => Left(exp)
      }

      override def handleError[A](fa: MyEither[E, A])(handle: E => A): MyEither[E, A] = fa match {
        case Right(res) => Right(res)
        case Left(exp) => Right(handle(exp))
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
