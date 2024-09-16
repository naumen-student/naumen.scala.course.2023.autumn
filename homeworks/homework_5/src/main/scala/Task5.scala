import cats.MonadError

import scala.util.{Failure, Left, Success, Try}

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

  sealed trait MyEither[+E, +R] {
    def isError: Boolean
  }

  object MyEither {
    case class Left[+E, +R](error: E) extends MyEither[E, R] {
      override def isError: Boolean = true
    }

    case class Right[+E, +R](result: R) extends MyEither[E, R] {
      override def isError: Boolean = false
    }

    def apply[A](value: A): MyEither[Nothing, A] = Right(value)

    def error[E, A](error: E): MyEither[E, A] = Left(error)

    def possibleError[A](f: => A): MyEither[Throwable, A] = Try(f) match {
      case Success(result) => Right(result)
      case Failure(error) => Left(error)
    }

    implicit def myEitherMonad[E]: MonadError[MyEither, E] = new MonadError[MyEither, E] {
      override def raiseError[R](fa: MyEither[E, R])(exp: => E): MyEither[E, R] = Left(exp)

      override def pure[R](result: R): MyEither[E, R] = Right(result)

      override def flatMap[A, B](fa: MyEither[E, A])(f: A => MyEither[E, B]): MyEither[E, B] = fa match {
        case Right(result) => f(result)
        case Left(error) => Left(error)
      }

      override def handleError[R](fa: MyEither[E, R])(handle: E => R): MyEither[E, R] = fa match {
        case Right(result) => Right(result)
        case Left(error) => Right(handle(error))
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
