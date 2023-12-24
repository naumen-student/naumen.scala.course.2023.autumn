class Table(width: Int, height: Int) {
  private val cells: Array[Cell] = Array.fill(width * height)(new EmptyCell)

  def getCell(ix: Int, iy: Int): Option[Cell] =
    if (isValidCell(ix, iy)) {
      val i = getIndexInFlatList(ix, iy)
      Some(cells(i))
    } else {
      None
    }

  def setCell(ix: Int, iy: Int, cell: Cell): Unit =
    if (isValidCell(ix, iy)) {
      val i = getIndexInFlatList(ix, iy)
      cells(i) = cell
    }

  private def isValidCell(ix: Int, iy: Int): Boolean =
    ix >= 0 && ix < width && iy >= 0 && iy < height

  private def getIndexInFlatList(ix: Int, iy: Int): Int = ix + iy * width
}

