package example

object Main extends App {
  def hello(greeting: String, name: String): Unit = {
    println(s"$greeting Scala! This is $name")
  }

  var name = "Timur"
  var greetings = Array("Hello", "Hola", "Guten Tag")
  var names = Array(name, name.reverse)

  for (greeting <- greetings) {
    for (name <- names) {
      hello(greeting, name)
    }
  }
}


