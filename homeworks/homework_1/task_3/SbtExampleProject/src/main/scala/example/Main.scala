package example

object Main extends App {
  val constStr = " Scala! This is "
  val helloArr = Array("Hello", "Hola", "Guten tag")
  val name = "Evgeny Nikolaev"
  val reverseName = name.reverse
  def printStr(helloArr: Array[String], constStr: String, name: String): Unit = {
    for (hello <- helloArr) println(hello + constStr + name)
  }

  printStr(helloArr, constStr, name)
  printStr(helloArr, constStr, reverseName)
}
