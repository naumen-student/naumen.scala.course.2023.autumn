object Main {
  def main(args: Array[String]): Unit = {
    val Greetings = List("Hello", "Salut", "Hi")
    var name = "Alex Panov"

    for (greeting <- Greetings) GetFinishedText(greeting, name)
    for (greeting <- Greetings) GetFinishedText(greeting, name.reverse)

    def GetFinishedText(greeting: String, name: String): Unit = {
      println(greeting + " Scala! This is " + name)
    }
  }
}