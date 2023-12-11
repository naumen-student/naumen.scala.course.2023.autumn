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
    def apply[A](value: A): MyEither[Nothing, A] = Right(value)
    def error[E, A](error: E): MyEither[E, A] = Left(error)
    def possibleError[A](f: => A): MyEither[Throwable, A] = Try(f).fold(error, apply[A])

    implicit def myEitherMonad[E]: MonadError[MyEither, E] = new MonadError[MyEither, E] {
      override def pure[A](value: A): MyEither[E, A] = MyEither.apply(value)

      override def flatMap[A, B](fa: MyEither[E, A])(f: A => MyEither[E, B]): MyEither[E, B] = fa match {
        case Left(error) => MyEither.error[E, B](error)
        case Right(value) => f(value)
      }

      override def raiseError[A](fa: MyEither[E, A])(error: => E): MyEither[E, A] = MyEither.error(error)

      override def handleError[A](fa: MyEither[E, A])(handle: E => A): MyEither[E, A] = fa match {
        case Left(error) => Right(handle(error))
        case _ => fa
      }
    }

    case class Left[E](err: E) extends MyEither[E, Nothing] {
      override def isError: Boolean = true
    }

    case class Right[A](value: A) extends MyEither[Nothing, A] {
      override def isError: Boolean = false
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
