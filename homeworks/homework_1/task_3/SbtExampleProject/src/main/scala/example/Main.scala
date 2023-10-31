package example

object Main extends App {
  class Greeter(var name: String, val greetings: List[String]):
    private def greet(greeting: String): Unit = println(s"$greeting Scala! This is $name")

    def greetAll(): Unit = greetings.foreach(greet)

  val greetings = List("Hello", "Hola", "Guten tag")
  val greeter = Greeter("Andrey", greetings)

  greeter.greetAll()
  greeter.name = greeter.name.reverse
  greeter.greetAll()
}
