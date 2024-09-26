object main extends App{
  def greeter(greet: String => String): String => String = name => greet(name)

  val greets: List[String => String] = List(name => s"Hello scala! This is ${name}",name => s"Hola scala! This is ${name}", name => s"Guttag,${name} ")

  println(greets)

  val name = "Konstantin"


  println(greets.map(greeter).map(_(name)))
  println(greets.map(greeter).map(_(name.reverse)))
}

