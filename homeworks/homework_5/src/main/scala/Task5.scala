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
    private case class MyLeft[+E](error: E) extends MyEither[E, Nothing] {
      def isError: Boolean = true
    }

    private case class MyRight[+A](value: A) extends MyEither[Nothing, A] {
      def isError: Boolean = false
    }

    def apply[A](value: A): MyEither[Nothing, A] = MyRight(value)

    def error[E, A](error: E): MyEither[E, A] = MyLeft(error)

    def possibleError[A](f: => A): MyEither[Throwable, A] = Try(f) match {
      case Success(value) => MyRight(value)
      case Failure(exception) => MyLeft(exception)
    }

    implicit def myEitherMonad[E]: MonadError[MyEither, E] = new MonadError[MyEither, E] {
      def pure[A](value: A): MyEither[E, A] = MyRight(value)

      def flatMap[A, B](fa: MyEither[E, A])(f: A => MyEither[E, B]): MyEither[E, B] =
        if (fa.isError) MyLeft(fa.asInstanceOf[MyLeft[E]].error)
        else f(fa.asInstanceOf[MyRight[A]].value)

      override def map[A, B](fa: MyEither[E, A])(f: A => B): MyEither[E, B] =
        if (fa.isError) MyLeft(fa.asInstanceOf[MyLeft[E]].error)
        else MyRight(f(fa.asInstanceOf[MyRight[A]].value))

      def handleError[A](fa: MyEither[E, A])(handle: E => A): MyEither[E, A] =
        if (fa.isError) MyRight(handle(fa.asInstanceOf[MyLeft[E]].error))
        else fa

      override def raiseError[A](fa: MyEither[E, A])(error: => E): MyEither[E, A] = MyLeft(error)
    }
  }

  object MyEitherSyntax {
    implicit class MyEitherOps[E, A](val either: MyEither[E, A]) {
      def flatMap[B](f: A => MyEither[E, B]): MyEither[E, B] = MyEither.myEitherMonad[E].flatMap(either)(f)

      def map[B](f: A => B): MyEither[E, B] = MyEither.myEitherMonad.map(either)(f)

      def handleError(f: E => A): MyEither[E, A] = MyEither.myEitherMonad.handleError(either)(f)
    }
  }
}

