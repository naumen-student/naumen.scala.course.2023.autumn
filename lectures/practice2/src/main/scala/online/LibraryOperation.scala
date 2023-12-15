package online

case class LibraryOperation[+R](run: LibraryDatabase => (LibraryDatabase, Either[Throwable, R])) {

  def map[R2](f: R => R2): LibraryOperation[R2] = LibraryOperation { db =>
    val (newDb, res) = run(db)

    res match {
      case Left(error) => newDb -> Left(error)
      case Right(value) => newDb -> Right(f(value))
    }

  }

  def flatMap[R2](f: R => LibraryOperation[R2]): LibraryOperation[R2] = LibraryOperation { db =>
    val (newDb, res: Either[Throwable, R]) = run(db)

    res match {
      case Left(error) => newDb -> Left(error)
      case Right(value) =>

        val r1: LibraryOperation[R2] = f(value)

        val r2: (LibraryDatabase, Either[Throwable, R2]) = r1.run(newDb)
        val r3: Either[Throwable, R2] = r2._2


        r2._1 -> r2._2
    }
  }
}

object LibraryOperation{

  def failure(e: Throwable) = LibraryOperation(db => db -> Left(e))

  def pure[R](value: R): LibraryOperation[R] = LibraryOperation(db => db -> Right(value))

}
