object Main {
  def main(args: Array[String]): Unit = {
    val constantPart = " Scala! This is "
    val greetings = Array("Hello", "Hola", "Guten tag")
    val name = "Dan"
    val names = Array(name, name.reverse)
    for (el <- names) {
      for (greeting <- greetings) {
        println(greeting + constantPart + el)
      }
    }
  }
}