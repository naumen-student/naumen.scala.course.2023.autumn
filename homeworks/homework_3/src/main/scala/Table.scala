class Table(width: Int, height:  Int)
{
  val table: Array[Array[Cell]] = Array.tabulate(width, height)((x, y) => new EmptyCell)

  def setCell(i_x: Int, i_y: Int, cell: Cell): Unit =
  {
    if (i_x >= 0 && i_y >= 0 && i_x < width && i_y < height) {
      table(i_x)(i_y) = cell
    }
  }
  def getCell(i_x: Int, i_y: Int): Option[Cell] =
  {
    if (i_x >= 0 && i_y >= 0 && i_x < width && i_y < height) {
      Some(table(i_x)(i_y))
    } else {
      None
    }
  }

}