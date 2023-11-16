class Table(width: Int, height: Int)
{
  private val cells: Array[Cell] = Array.fill(width * height)(new EmptyCell)

  private def inTableRange(x: Int, y: Int): Boolean = x >= 0 && x < width && y>=0 && y < height
  private def getCellIndexInArray(x: Int, y: Int): Int = x + y * width

  def getCell(x: Int, y: Int): Option[Cell] = if (inTableRange(x, y)) Some(cells(getCellIndexInArray(x, y))) else None
  def setCell(x: Int, y: Int, cell: Cell): Unit = if (inTableRange(x, y)) cells(getCellIndexInArray(x, y)) = cell
}