package example

object Main extends App {
  def greeting(hello: String, name: String) = s"${hello}, scala! This is ${name}"

  val name = "Grisha"
  val reversedName = name.reverse

  val english = "Hello"
  val spanish = "Hola"
  val german = "Guten tag"

  val hellos = Seq(english, spanish, german)

  for (hello <- hellos) {
    println(greeting(hello, name))
  }

  for (hello <- hellos) {
    println(greeting(hello, reversedName))
  }
}
