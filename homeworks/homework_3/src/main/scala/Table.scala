class Table(height: Int, width: Int) {

  private val data: Array[Cell] = Array.fill[Cell](height * width)(new EmptyCell)

  def getCell(ix: Int, iy: Int): Option[Cell] = toIndex(ix, iy).map(data(_))

  def setCell(ix: Int, iy: Int, cell: Cell): Unit = toIndex(ix, iy).map(data(_) = cell)

  private def toIndex(ix: Int, iy: Int): Option[Int] = Some(ix + iy * width).filter(checkIndex)

  private def checkIndex(index: Int): Boolean = 0 <= index && index < data.length
}
