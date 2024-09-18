package example

object Main extends App {
  val name = "Kseniya"
  val reversedName = name.reverse
  val greetings = List("Hello", "Hola", "Guten tag")

  def printGreeting(greeting: String, name: String): Unit = {
    println(s"$greeting Scala! This is $name")
  }

  greetings.foreach(greeting => printGreeting(greeting, name))

  greetings.foreach(greeting => printGreeting(greeting, reversedName))
}
