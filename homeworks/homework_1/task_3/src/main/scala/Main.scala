object Main {
  def main(args: Array[String]): Unit = {
    val name = "Vladislav Zubkov"

    val enGreeting = "Hello"
    val spGreeting = "Hola"
    val gerGreeting = "Guten tag"

    def greet(greeting: String, name: String): Unit = {
      println(s"$greeting Scala! This is $name")
    }

    greet(enGreeting, name)
    greet(spGreeting, name)
    greet(gerGreeting, name)

    greet(gerGreeting, name.reverse)
    greet(spGreeting, name.reverse)
    greet(enGreeting, name.reverse)
  }
}