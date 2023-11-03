object Main extends App {
  def printHello(helloWord: String, name: String) = println(s"$helloWord Scala! This is $name")

  val name = "Aleksandr Menshikov"

  printHello("Hello", name)

  for {
    name      <- List(name, name.reverse)
    helloWord <- List("Hi", "Hola", "Guten tag")
  } printHello(helloWord, name)
}