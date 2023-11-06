package example

object Main extends App {
  def greeting(name: String): String = s"Hello, scala! This is ${name}."

  val name: String = "Christina"
  println(greeting(name))

  def spanishGreeting(name: String): String =
    greeting(name).replace("Hello", "Hola")

  println(spanishGreeting(name))

  val reversedName: String = name.reverse

  println(greeting(reversedName))
  println(spanishGreeting(reversedName))
}
