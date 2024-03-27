object Main extends App {
  val timur = "Timur"
val helloMessage = (n: String) => s"Hello Scala! This is $n"

val holaMessage = helloMessage.andThen( 
	_.split(" ").map{
  case "Hello" => "Hola"
  case s => s
}.mkString(" ")
)

println(helloMessage(timur))
println(holaMessage(timur))
println(helloMessage(timur.reverse))
println(holaMessage(timur.reverse))
}
