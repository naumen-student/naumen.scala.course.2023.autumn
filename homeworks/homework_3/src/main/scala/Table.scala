import Table._

class Table(height: Int, width: Int) {
  private val table                           = Array.fill[Cell](height * width)(new EmptyCell)
  def getCell(ix: Int, iy: Int): Option[Cell] = table.lift(ix + iy * width)

  @throws(classOf[PositionOutOfTableBoundsException])
  def setCell(ix: Int, iy: Int, cell: Cell): Unit = {
    val iTableArr = ix + iy * width
    if (!table.isDefinedAt(iTableArr)) throw new PositionOutOfTableBoundsException(ix, iy)
    table.update(iTableArr, cell)
  }

}

object Table {
  class PositionOutOfTableBoundsException(ix: Int, iy: Int) extends Exception(s"($ix, $iy)")
}
