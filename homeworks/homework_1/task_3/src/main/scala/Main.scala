object Main {
  def main(args: Array[String]): Unit = {
    def greeting(greet: String, name: String) = s"${greet}, scala! This is ${name}"

    val name = "Slava"
    val reversedName = name.reverse

    val englishGreeting = "Hello"
    val spanishGreeting = "Hola"
    val germanGreeting = "Guten tag"

    val greetings = Seq(englishGreeting, spanishGreeting, germanGreeting)

    for (greet <- greetings) {
      println(greeting(greet, name))
    }

    for (greet <- greetings) {
      println(greeting(greet, reversedName))
    }
  }
}