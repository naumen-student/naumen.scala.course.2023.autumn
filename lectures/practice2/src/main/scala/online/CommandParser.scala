package online

object CommandParser {
  def parse(commandLine: String): LibraryOperation[String] = commandLine.split(' ').toList match {
    case command :: arguments if arguments.nonEmpty =>
      command match {

        case "addClient" => LibraryOperation(db =>
          db.copy(clients = db.clients :+ Client(arguments.mkString(" "))) -> Right("ok")
        )

        case "addBook" => {
          val totalArgument = arguments.mkString(" ").split('|')
          val name = totalArgument.headOption
          val author = totalArgument.drop(1).headOption
          (name, author) match {
            case (Some(name), Some(author)) =>  LibraryOperation { db => db.copy(books = db.books :+ Book(name, author)) -> Right("ok") }
            case _ => LibraryOperation.failure(new RuntimeException("wrong syntax"))
          }
        }

        case _ => LibraryOperation.failure(new Exception("unknown command"))
      }
    case _ => LibraryOperation.failure(new Exception("wrong syntax"))
  }
}

trait Command {
  def couldParse(commandLine: String): Boolean

  def parse(commandLine: String): LibraryOperation[String]
}

class UniversalCommandParser(additionalCommands: List[Command]) {

  val defaultCommands: List[Command] = List(
    new Command {
      override def couldParse(commandLine: String): Boolean = commandLine.split(' ').toList match {
        case command :: arguments if arguments.nonEmpty =>
          command match {
            case "addClient" => true
            case _ => false
          }
        case _ => false
      }

      override def parse(commandLine: String): LibraryOperation[String] = commandLine.split(' ').toList match {
        case command :: arguments if arguments.nonEmpty =>
          command match {
            case "addClient" => LibraryOperation(db =>
              db.copy(clients = db.clients :+ Client(arguments.mkString(" "))) -> Right("ok")
            )
            case _ => LibraryOperation.failure(new Exception("unknownCommand"))
          }
        case _ => LibraryOperation.failure(new Exception("wrong syntax"))
      }
    }
  )

  def parse(commandLine: String): LibraryOperation[String] = (defaultCommands ++ additionalCommands)
    .dropWhile(!_.couldParse(commandLine))
    .headOption
    .map(_.parse(commandLine))
    .getOrElse(LibraryOperation.failure(new Exception("unknown command")))
}

