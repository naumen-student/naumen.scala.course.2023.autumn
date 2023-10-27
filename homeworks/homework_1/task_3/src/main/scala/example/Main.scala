package example

object Main extends App {
  def run(): Unit = {
    def buildResult(name: String, greeting: String): String =
      s"$greeting, Scala! This is $name"

    val names = List("Nastya", "aytsaN")
    val greetings = List("Hello", "Hola", "Guten tag")

    for {
      name <- names
      greeting <- greetings
    } yield println(buildResult(name, greeting))
  }
  run()
}