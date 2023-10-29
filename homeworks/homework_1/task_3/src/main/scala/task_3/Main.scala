package task_3

object Main extends App{
  val name = "Nikita Gavrilov"

  val a = f"Hello Scala! This is $name%s"
  println(a)

  val b = a.replace("Hello", "Hola")
  println(b)

  val c = a.replace("Hello", "Guten Tag")
  println(c)

  val a1 = a.replace(name, name.reverse)
  println(a1)

  val b1 = b.replace(name, name.reverse)
  println(b1)

  val c1 = c.replace(name, name.reverse)
  println(c1)

}
