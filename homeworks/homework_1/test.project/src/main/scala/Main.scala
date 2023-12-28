object Main {
  def main(args: Array[String]): Unit = {
    val greeting = ", Scala! This is "
    val greetingWords = List("Hello", "Hola", "Guten tag")
    val names = List("Pasha", "ahsaP")

    def ConsoleGreet(s: String, greetingWords: List[String], names: List[String]): Unit = {
      for {
        name <- names
        greeting <- greetingWords
      } {
        println(s"${greeting}${s}${name}")
      }
    }

    ConsoleGreet(greeting, greetingWords, names)
  }
}