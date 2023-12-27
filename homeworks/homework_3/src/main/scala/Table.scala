class Table(height: Int, width: Int) {
  private val data: Array[Cell] = Array.fill[Cell](height * width)(new EmptyCell)

  private def toInd(ix: Int, iy: Int): Option[Int] = Some(ix + iy * width).filter(inRange)
  private def inRange(ind: Int): Boolean = 0 <= ind && ind < data.length
  def getCell(ix: Int, iy: Int): Option[Cell] = toInd(ix,iy).map(data(_))

  def setCell(ix: Int, iy: Int, cell: Cell): Unit = toInd(ix,iy).map(data(_) = cell)
}