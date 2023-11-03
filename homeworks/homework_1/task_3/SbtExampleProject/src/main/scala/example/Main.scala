package example

object Main extends App {
  val name = "Dmitrii Miroshnik"

  private def printGreeting(greeting: String): Unit = {
    println(greeting + name)
  }

  private def printReverseGreeting(greeting: String): Unit = {
    println(greeting + name.reverse)
  }

  private val greetings = List(
    "Hello Scala! This is ",
    "Hola Scala! This is ",
    "Guten tag Scala! This is "
  )

  greetings.foreach(greeting => printGreeting(greeting))
  greetings.foreach(greeting => printReverseGreeting(greeting))
}
