class Table(width: Int, height: Int) {
  private val table: Array[Cell] = Array.fill(width*height)(new EmptyCell)
  private def isRange(ix: Int, iy: Int): Boolean = ix + iy * width >= 0 && ix + iy * width < table.length
  def getCell(ix: Int, iy: Int): Option[Cell] = {
    val position = ix + iy * width
    position match {
      case a if isRange(ix, iy) => Some(a).map(table(_))
      case _ => None
    }
  }
  def setCell(ix: Int, iy: Int, cell: Cell): Unit = {
    Some(ix + iy * width) match {
      case a if isRange(ix, iy) => a.foreach(table(_) = cell)
    }
  }
}