package example

object Main extends App{
  def sayHello(word: String, name: String): Unit = {
    println(word + " Scala! This is " + name)
  }

  val myName = "Sofya Schcadilova"

  for {name <- List(myName, myName.reverse)
       word <- List("Hello", "Hola", "Guten tag")
       } sayHello(word, name)
}
