object Main extends App {
  var name: String = "Danil Tribushko"

  def printMessages(): Unit = {
    val greetings = List("Hi", "Hello", "Привет", "Guten tag", "Bonjur")
    for (greeting <- greetings) {
      if (greeting.equals("Guten tag")) {
        println(s"$greeting Scala This is ${name.reverse}")
      } else {
        println(s"$greeting Scala! This is $name")
      }
    }
  }

  printMessages()
}
