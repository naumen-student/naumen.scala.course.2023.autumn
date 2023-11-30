object Main extends App{
  def hello(greeting: String, name: String): Unit = {
    println(s"${greeting} Scala! This is ${name}")
  }


  val name = "Rinat Gilemshin"

  val greetings = List("Hello", "Guten tag", "Hola")
  for (greeting <- greetings)
    hello(greeting, name)

  for (greeting <- greetings)
    hello(greeting, name.reverse)
}