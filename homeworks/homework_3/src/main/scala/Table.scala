class Table(width: Int, height:  Int)
{
  val table: Array[Array[Cell]] = Array.tabulate(width, height)((ix, iy) => new EmptyCell)

  def getCell(ix: Int, iy: Int): Option[Cell] =
  {
    if (ix >= 0 && iy >= 0 && ix < width && iy < height) Some(table(ix)(iy))
    else None
  }
  def setCell(ix: Int, iy: Int, value: Cell): Unit =
  {
    if (ix >= 0 && iy >= 0 && ix < width && iy < height) table(ix)(iy) = value
  }
}