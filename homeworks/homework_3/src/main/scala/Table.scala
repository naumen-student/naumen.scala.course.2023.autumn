class Table(width: Int, height: Int) {
  private val cells: Array[Cell] = Array.fill(width * height)(new EmptyCell)

  private def checkIndex(ix: Int, iy: Int): Boolean = {
    ix < 0 || ix > height || iy < 0 || iy > width
  }

  def getCell(ix: Int, iy: Int): Option[Cell] = {
    if (checkIndex(ix, iy)) None else Some(cells(ix + iy * width))
  }

  def setCell(ix: Int, iy: Int, cell: Cell): Unit = {
    if (!checkIndex(ix, iy)) cells(ix + iy * width) = cell
  }
}
