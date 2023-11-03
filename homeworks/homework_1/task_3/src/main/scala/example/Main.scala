package example

object Main extends App {
  def getText(word: String, name: String): String = {
    f"$word Scala! This is $name"
  }

  def getResults(name: String, words: List[String]): String = {
    words.map(x => getText(x, name)).mkString("\n")
  }

  val myName = "Egor Zykov"
  val words = List("Hello", "Ola", "Guten morgen")

  println(getResults(myName, words))
  println(getResults(myName.reverse, words))
}
