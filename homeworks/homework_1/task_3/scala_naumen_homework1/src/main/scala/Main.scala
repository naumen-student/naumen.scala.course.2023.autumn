object Main extends App {

  def helloPhrase(hello: String, name: String): Unit = {
    println(hello + " Scala! This is " + name)
  }

  val name = "Konstantin Semenov"

  helloPhrase("Hello", name)
  helloPhrase("Hola", name)
  helloPhrase("Guten tag", name)

  helloPhrase("Hello", name.reverse)
  helloPhrase("Hola", name.reverse)
  helloPhrase("Guten tag", name.reverse)


}
