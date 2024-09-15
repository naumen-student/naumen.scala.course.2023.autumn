package example

object Main extends App {
  val myName = "Andrew Kuklinov"

  val greetings = Seq("Hello", "Heya", "Good day to you")

  def greet(greeting: String, name: String): Unit = {
    println(s"$greeting Scala! This is $name")
  }

  greetings.foreach(greet(_, myName))
  greetings.foreach(greet(_, myName.reverse))
}
