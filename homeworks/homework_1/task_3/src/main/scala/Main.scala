object Main {
  def main(args: Array[String]): Unit = {
    val greetings = "Hello" :: "Hola" :: "Guten tag" :: Nil
    val userName = "Andrew"
    val userNames = userName :: userName.reverse :: Nil

    def formatPrint(greeting: String, name: String): Unit = println(s"${greeting}, scala! This is ${name}")

    formatPrint(greetings.head, userName)
    greetings.foreach(formatPrint(_, userName))
    (for (name <- userNames; data <- greetings) yield (data, name)).foreach(formatPrint)
  }
}