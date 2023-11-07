class Table(width: Int, height: Int) {
  private val cells: Array[Cell] =
    Array.fill[Cell](width * height)(new EmptyCell)

  def setCell(ix: Int, iy: Int, cell: Cell): Unit =
    if (checkBordersFor(ix, iy)) cells(ix + iy * width) = cell

  def getCell(ix: Int, iy: Int): Option[Cell] =
    if (checkBordersFor(ix, iy)) Some(cells(ix + iy * width))
    else None

  private def checkBordersFor(ix: Int, iy: Int): Boolean =
    ix > -1 && ix < width && iy > -1 && iy < height
}
