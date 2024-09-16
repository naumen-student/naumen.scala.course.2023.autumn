class Table(width: Int, height: Int) {
  private val cells: Array[Cell] = Array.fill[Cell](width * height)(new EmptyCell)
  private def inRange(ix: Int, iy: Int): Option[Boolean] = Some(ix < width && ix > -1 && iy < height && iy > -1).filter(_ == true)
  def getCell(ix: Int, iy: Int): Option[Cell] = inRange(ix, iy).map(_ => cells(ix + iy * width))
  def setCell(ix: Int, iy: Int, cell: Cell): Unit = inRange(ix, iy).foreach(_ => cells(ix + iy * width) = cell)
}