package example

object Main extends App{
  def printGreetings(greetings: Array[String], name: String): Unit = {
    for (greeting <- greetings) println(s"$greeting Scala! This is $name")
  }

  var name = "Denis Golov"

  val greetings = Array("Hello", "Hola","Guten tag")

  printGreetings(greetings, name)

  name = name.reverse

  printGreetings(greetings, name)
}
