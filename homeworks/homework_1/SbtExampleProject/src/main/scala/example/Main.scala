package example

object Main extends App {

    val name = "Vorotelenko Andrei"
    val hello: List[String] = List("Hello", "Salemetsizbe", "Hola")

    for (h <- hello)
      println(h + " Scala! This is " + name)
    printf("Hello Scala! This is " + name.reverse)

}
