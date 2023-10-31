object Main {
  def main(args: Array[String]): Unit = {
    val greetings: List[String] = List("Hola", "Hello", "Salam")
    val name: String = "Danil Titarenko"
    val reversedName: String = name.reverse
    val names: List[String] = List(name, reversedName)
    greetings.foreach(g => names.foreach(n => greeting(g, n)))
  }
  def greeting(greet: String, name: String): Unit = {
    println(s"$greet Scala! This is $name")
  }
}