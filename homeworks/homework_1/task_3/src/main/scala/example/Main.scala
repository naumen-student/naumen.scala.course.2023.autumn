package example

object Main extends App {
  val name = "Artem"
  val greet = (greeting: String, name: String) => s"$greeting Scala! This is $name"

  List(name, name.reverse)
    .flatMap(n => List("Hello", "Hola", "Guten tag").map(g => greet(g, n)))
    .foreach(g => println(g))
}