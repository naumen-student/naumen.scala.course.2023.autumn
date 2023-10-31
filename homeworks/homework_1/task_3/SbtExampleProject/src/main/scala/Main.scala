object Main {
  def main(args: Array[String]): Unit = {

    def Get_output(greeting: String, my_name: String): Unit = {
      println(greeting + " Scala! This is " + my_name)
    }

    val my_name = "Semen"

    val reverse_name = my_name.reverse

    val greetings = List("Hello", "Hola", "Guten tag", "Good morning")

    for (greeting <- greetings) Get_output(greeting, my_name)

    for (greeting <- greetings) Get_output(greeting, reverse_name)
  }
}