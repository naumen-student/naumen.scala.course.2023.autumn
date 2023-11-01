package example

object Main extends App {
  val messages = Array("Hello Scala! This is Ivan",
    "Aloha Scala! This is Ivan",
    "Guten tag Scala! This is Ivan")
  for (m <- messages) println(m)
  for (m <- messages) println(m.reverse)
}
