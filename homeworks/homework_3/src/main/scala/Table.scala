import scala.collection.mutable

sealed trait TableT {
  def setCell(a: Int, b: Int, cell: Cell): Unit
  def getCell(a: Int, b: Int): Option[Cell]
}

class Table(rows: Int, columns: Int) extends TableT {
  val table: mutable.Map[(Int, Int), Cell] = mutable.Map()

  override def setCell(a: Int, b: Int, cell: Cell): Unit = table += ((a, b) -> cell)

  override def getCell(a: Int, b: Int): Option[Cell] = if (0 <= a && a < rows && 0 <= b && b < columns) {
    if (!table.contains((a, b))) Option(EmptyCell()) else table.get((a, b))
  } else
    None
}