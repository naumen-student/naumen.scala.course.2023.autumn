package Main

object Main extends App {
  val person = "Kalinin Alexander"
  val hellos = Array("Hello", "Hola", "Guten Tag", "Szia", "Ohayo")
  printResult(hellos: Array[String], person: String)
  printResult(hellos: Array[String], person.reverse: String)

  def printResult(hellos: Array[String], name: String) = {
    for(hello <- hellos) println(s"$hello Scala! This is $name")
  }
}