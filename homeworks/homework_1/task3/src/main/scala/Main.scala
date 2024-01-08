object Main extends App {
  def greeting(name: String): String = s"Hello, scala! This is ${name}."

  val name: String = "Lena"
  val reversedName: String = name.reverse
  println(greeting(name))

  println(greeting(name).replace("Hello", "Guten tag"))

  println(greeting(reversedName).replace("Hello", "Guten tag"))

}