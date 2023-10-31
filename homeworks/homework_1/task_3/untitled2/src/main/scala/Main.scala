object Main {
  def main(args: Array[String]): Unit = {
    val sayHello = (greeting: String, name: String) => {
      s"$greeting, Scala! This is $name"
    }
    println(sayHello("Hello", "Egor"))
    println(sayHello("Hola", "Egor"))
    println(sayHello("Guten Tag", "Egor"))
    println(sayHello("Hello", "Egor".reverse))
    println(sayHello("Hola", "Egor".reverse))
    println(sayHello("Guten Tag", "Egor".reverse))

  }
}