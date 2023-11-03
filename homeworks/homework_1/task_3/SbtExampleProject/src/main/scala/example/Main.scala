package example

object Main {
  def main(args: Array[String]): Unit = {
    val name = "Daniil"
    val func1: String => String = _ + s" Scala! This is $name"
    val func2: String => String = _ + s" Scala! This is $name.reverse"
    val words = List("Hola", "Guten Tag", "Hello")
    println(words.map(func1(_)).mkString("\n"))
    println(words.map(func2(_)).mkString("\n"))


  }
}
