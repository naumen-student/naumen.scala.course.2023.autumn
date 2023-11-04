package example

object Main extends App{
  val name = "Polina"
  val printGreeting = (name: String, greeting: String) => println(s"$greeting Scala! This is $name")
  val greetings = Array("Hello", "Hola", "Guten tag")
  val reversedName = name.reverse


  greetings.foreach(arg => printGreeting(name, arg))
  greetings.foreach(arg => printGreeting(reversedName, arg))
}

