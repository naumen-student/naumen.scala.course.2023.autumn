package example

object Main extends App {
  def getMessage(greeting: String, name: String): String = {
    s"$greeting Scala! This is $name"
  }

  def getGreetings(name: String, greetings: List[String]): String = {
    greetings.map(x => getMessage(x, name)).mkString("\n")
  }

  val myName = "Nazar Garipov"
  val greetings = List("Hello", "Hola", "Guten tag")

  println(getGreetings(myName, greetings))
  println(getGreetings(myName.reverse, greetings))
}
