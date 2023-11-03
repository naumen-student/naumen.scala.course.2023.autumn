package example

object Main extends App {
  val immutablePart = ", Scala! This is "
  val greetings = List("Hello", "Hola", "Guten tag")
  val names = List("Lina", "aniL")

  def printGreeting(s: String, mutableGreetings: List[String], mutableNames: List[String]): Unit = {
    for {
      name <- mutableNames
      greeting <- mutableGreetings
    } {
      println(s"${greeting}${s}${name}")
    }
  }

  printGreeting(immutablePart, greetings, names)
}
