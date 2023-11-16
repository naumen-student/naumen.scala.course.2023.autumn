import scala.collection.mutable.ArrayBuffer

class Table(w: Int, h: Int) {
  private val table: ArrayBuffer[ArrayBuffer[Cell]] = ArrayBuffer.fill(h, w)(new EmptyCell)

  def getCell(x: Int, y: Int): Option[Cell] = {
    if (x < 0 || x >= w || y < 0 || y >= h)
      return None
    else
      return Some(table(x)(y))
  }

  def setCell(x: Int, y: Int, cell: Cell): Unit = {
    table(x)(y) = cell
//    if (getCell(x, y) != None) {
//
//    }
  }
}