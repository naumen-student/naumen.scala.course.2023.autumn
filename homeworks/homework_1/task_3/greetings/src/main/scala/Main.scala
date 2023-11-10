object Main extends App {
  val name = "Alex Nikolaev"
  val initialGreeting = s"Hello Scala! This is $name"
  println(initialGreeting)
  val greetings = List("Hello", "Hola", "Guten tag")
  greetings.foreach(greeting =>
    println(s"$greeting Scala! This is $name")
  )
  val reversedName = name.reverse
  greetings.foreach(greeting =>
    println(s"$greeting Scala! This is $reversedName")
  )

}