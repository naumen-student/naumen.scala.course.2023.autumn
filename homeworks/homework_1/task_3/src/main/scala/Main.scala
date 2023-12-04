object Main {
  def main(args: Array[String]): Unit = {
    val name: String = "Игорь"
    val greats: List[String] = "Hello" :: "Hi" :: "Guten tag" :: Nil

    def printVariantedGreating(variants: List[String], name: String): Unit = for (
      great <- variants
    ) println(s"${great} Scala! This is ${name}")

    printVariantedGreating(greats, name)
    printVariantedGreating(greats, name.reverse)

  }
}