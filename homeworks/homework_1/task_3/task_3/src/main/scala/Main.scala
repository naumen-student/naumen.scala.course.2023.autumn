object Main {
  def main(args: Array[String]): Unit = {
    def printGreetings(name: String, greetings: List[String] = List("Hello")): Unit = {
      greetings.foreach(greeting => println(s"$greeting Scala! This is $name"))
    }

    val name = "Andrey Komendantov"
    val greetingsList = List("Hello", "Hola", "Guten tag")

    printGreetings(name)
    printGreetings(name, greetingsList)
    printGreetings(name.reverse, greetingsList)
  }
}