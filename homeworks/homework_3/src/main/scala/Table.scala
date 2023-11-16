import scala.collection.mutable.ArrayBuffer

class Table(val width: Int, val height: Int) {
  val cells: ArrayBuffer[Cell] = ArrayBuffer.fill(width * height)(new EmptyCell)

  def getCell(ix: Int, iy: Int): Option[Cell] = {
    val index = ix + iy * width
    if (ix >= 0 && ix < width && iy >= 0 && iy < height) Some(cells(index))
    else None
  }

  def setCell(ix: Int, iy: Int, cell: Cell): Unit = {
    val index = ix + iy * width
    if (ix >= 0 && ix < width && iy >= 0 && iy < height) {
      cells(index) = cell
    }
  }
}