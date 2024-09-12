package example

object Main extends App{
  def helloPrint(h: Array[String], n: Array[String], s: String): Unit = {
    n.foreach(nArg => {
      h.foreach(hArg => println(hArg + s + nArg + "\n"))
    })
    println()
  }

  val hello = Array("Hello", "Hi", "Привет")
  val name = Array("Полина", "анилоП")
  val str = " Scala! This is "

  helloPrint(hello, name, str)
}
