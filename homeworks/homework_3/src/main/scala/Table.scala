import scala.collection.mutable

class Table(cols: Int, rows: Int) {
  private val xyToCell = mutable.Map[(Int, Int), Cell]()

  def getCell(x: Int, y: Int): Option[Cell] = {
    Some(xyToCell.getOrElse((x, y), new EmptyCell()))
      .filter(_ => x >= 0 && x < cols && y >= 0 && y < rows)
  }

  def setCell(x: Int, y: Int, cell: Cell): Unit = {
    xyToCell += (x, y) -> cell
  }
}