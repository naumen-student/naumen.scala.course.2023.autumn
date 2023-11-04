object Main extends App {
  val name = "Otman Anna"
  val greetings = List("Hello", "Hola", "Guten tag")

  def greeting(greet: String, name: String): Unit = println(s"$greet Scala! This is $name")

  for (el <- greetings) greeting(el, name)
  for (el <- greetings) greeting(el, name.reverse)
}
