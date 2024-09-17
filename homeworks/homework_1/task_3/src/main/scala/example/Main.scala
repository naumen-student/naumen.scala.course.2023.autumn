package example

object Main extends App {
    def hello(name: String, hi: String): Unit = {
      println(s"${hi} Scala! This is ${name}")
    }

    def allLine(names: List[String], greetings: List[String]): Unit = {
      names.map(name => greetings.map(greeting => hello(name, greeting)))
    }
    val name = "Mary Medvedeva"
    val nameList = List(name, name.reverse)
    val hiList = List("Hello", "Hola", "Guten tag", "Bonjour")

    allLine(nameList, hiList)
}
