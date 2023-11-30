import scala.collection.mutable.ArrayBuffer

class Table(var n: Int, var m: Int) {
  private val table: ArrayBuffer[ArrayBuffer[Cell]] = ArrayBuffer.fill(n, m)(new EmptyCell)

  def getCell(x: Int, y: Int): Option[Cell] = {
    if (x < 0 || x >= n || y < 0 || y >= m)
      None
    else
      Some(table(x)(y))
  }

  def setCell(x: Int, y: Int, cell: Cell): Unit = {
    table(x)(y) = cell
  }
}
