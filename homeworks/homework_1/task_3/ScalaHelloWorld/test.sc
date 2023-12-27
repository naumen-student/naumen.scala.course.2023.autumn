def greeting(greet: String, name: String): Unit = {
  println(s"$greet Scala! This is $name")
}

val hola: String = "Hola"
val hello: String = "Hello"
val salam: String = "Salam"
val name: String = "Danil Titarenko"
val reversedName: String = name.reverse

greeting(hello, name)
greeting(hola, name)
greeting(salam, name)

greeting(hello, reversedName)
greeting(hola, reversedName)
greeting(salam, reversedName)
