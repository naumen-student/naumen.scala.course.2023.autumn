import scala.collection.mutable.ListBuffer

class Table(Width: Int, Height: Int) {
  private val cells: ListBuffer[Cell] = ListBuffer.fill[Cell](Width * Height) {
    new EmptyCell
  }

  def getCell(ix: Int, iy: Int): Option[Cell] = if (canAccessCell(ix, iy))
    Option(cells(ix + iy * Width)) else None;

  def setCell(ix: Int, iy: Int, cell: Cell): Unit = {
    if (canAccessCell(ix, iy))
      cells(ix + iy * Width) = cell
  }

  private def canAccessCell(ix: Int, iy: Int): Boolean = ix < Width && ix >= 0 && iy < Height && iy >= 0
}