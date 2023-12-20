package online

import cats.effect.IO
import cats.effect.unsafe.implicits.global

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContextExecutor, Future}

/**
 * Написать программу, которая реализует потребности базы данных библиотеки:
 * 1. Пополнение фонда книгой
 * 2. Регистрация пользователя (выдача читательского билета)
 * 3. Выдача книги на руки
 * 4. Возврат книги
 * ....
 *
 * Программе на вход подается набор инструкций (с клавиатуры, по сети, из файла, ...),
 * а программа должна их исполнять
 *
 * Набор команд программы может расширяться
 */


object Main extends App {
  val lib = LibraryDatabase(Nil, Nil, Map.empty)

//  val totalOperation = for {
//    _ <- LibraryOperation(db => db.copy(clients = db.clients :+ Client("Vasya")) -> Right("ok"))
//    _ <- LibraryOperation(db => db.copy(books = db.books :+ Book("Властелин колец", "Tolkin")) -> Right("ok"))
//    r <- LibraryOperation(db => db.copy(books = db.books :+ Book("Designing data intensive applications", "Клепинин")) -> Right("ok"))
//  } yield r
//
//  val (newDb, result) = totalOperation.run(lib)
//  println(newDb)
//  println(result)

  //// -------------------------------



//  val parsedCommands = commands.map(CommandParser.parse)
//
//  val resultOperation: LibraryOperation[String] = parsedCommands.foldLeft(LibraryOperation.pure(""))((op1, op2) => op1.flatMap(_ => op2))
//
//  val (newDb, res) = resultOperation.run(lib)
//  println(newDb, res)

  val dropClientCommand = new Command {
    override def couldParse(commandLine: String): Boolean = commandLine.split(' ').toList match {
      case command :: arguments if arguments.nonEmpty =>
        command match {
          case "dropClient" => true
          case _ => false
        }
      case _ => false
    }

    override def parse(commandLine: String): LibraryOperation[String] = commandLine.split(' ').toList match {
      case command :: arguments if arguments.nonEmpty =>
        command match {
          case "dropClient" => LibraryOperation(db => db.copy(clients = db.clients.filter(_.name != arguments.mkString(" "))) -> Right("ok"))
          case _ => LibraryOperation.failure(new Exception("unknown command"))
        }
      case _ => LibraryOperation.failure(new Exception("wrong syntax"))
    }
  }

//  val commands = List(
//    "addClient Ivanov Ivan Ivanovich",
//    "addClient Petya",
//    "dropClient Ivanov Ivan Ivanovich"
//  )

  //implicit val context: ExecutionContextExecutor = scala.concurrent.ExecutionContext.global

 val program = for {
    commands <- IO (scala.io.Source.fromFile("<change_to_your_path>\\commands.txt").getLines())

    parsedCommands <- IO(commands.map( new UniversalCommandParser(List(dropClientCommand)).parse))
    resultOperation <- IO {parsedCommands.foldLeft(LibraryOperation.pure("")) ((op1, op2) => op1.flatMap(_ => op2))}

    _res <- IO { resultOperation.run(lib) }
   (newDb, res) = (_res._1, _res._2)
     _ <- IO { println (newDb, res) }
  } yield ()

  //Await.result(program, 1.minutes)

  program.unsafeRunSync()

}








