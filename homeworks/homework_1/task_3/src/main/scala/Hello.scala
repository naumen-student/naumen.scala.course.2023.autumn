object Hello extends App {
  def greet(greeting: String, name: String): Unit =
    println(s"$greeting Scala! This is $name")

  val name = "Guldena"

  val greeting1 = "Hello"
  val greeting2 = "Hola"
  val greeting3 = "Guten tag"

  greet(greeting1, name)
  greet(greeting2, name)
  greet(greeting3, name)

  greet(greeting1, name.reverse)
  greet(greeting2, name.reverse)
  greet(greeting3, name.reverse)
}