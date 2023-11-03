package example

object Main extends App{
  def greet(greeting: String, name: String): Unit = {
    println(s"${greeting} Scala! This is ${name}")
  }

  val name = "Alexander Kuzevanov"

  val greetings = List("Hello", "Guten tag", "Hola")
  for (greeting <- greetings)
    greet(greeting, name)

  for (greeting <- greetings)
    greet(greeting, name.reverse)
}
