import scala.collection.mutable.ListBuffer

class Table(width: Int, height: Int) {
  private val cells: ListBuffer[Cell] = ListBuffer.fill[Cell](width * height) { new EmptyCell }

  def getCell(ix: Int, iy: Int): Option[Cell] = {
    if (ix >= 0 && ix < width && iy >= 0 && iy < height) {
      Some(cells(iy * width + ix))
    }
    else None
  }

  def setCell(ix: Int, iy: Int, cell: Cell): Unit = {
    cells(ix + iy * width) = cell
  }
}