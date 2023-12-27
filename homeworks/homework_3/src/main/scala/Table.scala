import scala.collection.mutable.ArrayBuffer

class Table(width: Int, height: Int) {
  private val table: ArrayBuffer[Cell] = ArrayBuffer.fill(width * height)(new EmptyCell)

  private def isInBoundary(col: Int, row: Int): Boolean = col < width && col >= 0 && row >= 0 && row < height

  def getCell(ix: Int, iy: Int): Option[Cell] =
    if (isInBoundary(ix, iy)) Some(table(ix + iy * width)) else None

  def setCell(ix: Int, iy: Int, cell: Cell): Unit =
    if (isInBoundary(ix, iy)) table(ix + iy * width) = cell
}