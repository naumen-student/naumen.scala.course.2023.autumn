object Main {
  def main(args: Array[String]): Unit = {
    val myName = "Anna Sokolova"
    val reversedName = myName.reverse

    val greetings = List("Hello", "Hola", "Guten tag", "Привет", "Hey")

    def printGreeting(greeting: String, name: String): Unit = {
      println(s"$greeting Scala! This is $name")
    }

    greetings.foreach(greeting => printGreeting(greeting, myName))
    greetings.foreach(greeting => printGreeting(greeting, reversedName))
  }
}