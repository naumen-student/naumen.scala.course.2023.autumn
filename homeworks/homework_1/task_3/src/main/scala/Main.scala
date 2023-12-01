object Main extends App {
  val name = "Artsybashev Gregory"
  val greetings = List(
    "Hello",
    "Hola",
    "Guten tag"
  )

  def hello(greet: String, name: String): Unit = println(s"$greet Scala! This is $name")

  for (gr <- greetings) hello(gr, name)
  for (gr <- greetings) hello(gr, name.reverse)
}
