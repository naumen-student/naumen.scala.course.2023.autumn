object Main extends App{
  private def greet(name: String, greeting: String): Unit = println(s"$greeting Scala! This is $name.")

  private val greetingsCollection = Seq(greet(_, "Hello"), greet(_, "Hola"), greet(_, "Guten tag"))
  private val personName = "Alina Mukhametzhanova"

  greetingsCollection.foreach(func => func(personName))
  greetingsCollection.foreach(func => func(personName.reverse))
}
