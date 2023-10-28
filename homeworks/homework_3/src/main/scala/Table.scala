class Table(width: Int, height: Int) {
  private val table: Array[Cell] = Array.fill(width * height)(new EmptyCell)

  private def checkBoundary(col: Int, row: Int): Boolean = col < width && col >= 0 && row >= 0 && row < height

  def getCell(ix: Int, iy: Int): Option[Cell] =
    if (checkBoundary(ix, iy)) Some(table(ix + iy * width)) else None

  def setCell(ix: Int, iy: Int, cell: Cell): Unit =
    if (checkBoundary(ix, iy)) table(ix + iy * width) = cell
}