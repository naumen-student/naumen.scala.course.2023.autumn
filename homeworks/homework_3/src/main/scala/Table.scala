class Table(width: Int, height: Int) {
  private val cells: Array[Cell] = Array.fill[Cell](width * height)(new EmptyCell)

  def getCell(ix: Int, iy: Int): Option[Cell] =
    if (validCell(ix, iy))
      Some(cells(ix + iy * width))
    else
      None

  def setCell(ix: Int, iy: Int, cell: Cell): Unit =
    if (validCell(ix, iy))
      cells(ix + iy * width) = cell

  private def validCell(ix: Int, iy: Int): Boolean =
    ix < width && ix >= 0 && iy >= 0 && iy < height
}
