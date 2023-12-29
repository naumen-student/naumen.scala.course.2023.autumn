class Table(width: Int, height:  Int)
{
  val table: Array[Array[Cell]] = Array.tabulate(width, height)((x, y) => new EmptyCell)

  def setCell(x: Int, y: Int, value: Cell): Unit =
  {
    if (x >= 0 && y >= 0 && x < width && y < height) table(x)(y) = value
  }
  def getCell(x: Int, y: Int): Option[Cell] =
  {
    if (x >= 0 && y >= 0 && x < width && y < height) Some(table(x)(y))
    else None
  }
}