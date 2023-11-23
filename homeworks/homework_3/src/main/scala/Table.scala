class Table(Width: Int, Height: Int) {
  private val cells: Array[Cell] = Array.fill[Cell](Width * Height)(new EmptyCell)

  def getCell(ix: Int, iy: Int): Option[Cell] = {
    validCell(ix, iy).map(_ => cells(ix + iy * Width))
  }

  def setCell(ix: Int, iy: Int, cell: Cell): Unit = {
    validCell(ix, iy).foreach(_ => cells(ix + iy * Width) = cell)
  }

  private def validCell(ix: Int, iy: Int): Option[Boolean] = {
    Some(ix < Width && iy < Height && ix >= 0 && iy >= 0).filter(_ == true)
  }
}