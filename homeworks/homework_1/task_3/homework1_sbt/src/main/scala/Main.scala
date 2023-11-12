object Main {
  def get_greeting(greet_word: String, name: String): String =
    s"${greet_word} Scala! I'm ${name}"

  val name = "Viktor Mikhailov"


  def main(args: Array[String]): Unit = {
    for (
      greet_word <- Seq("Hola", "Hello", "Guren tag");
      name <- Seq(name, name.reverse)
    ) println(get_greeting(greet_word, name))
  }
}