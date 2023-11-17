import scala.annotation.tailrec
sealed trait Cell

class EmptyCell extends Cell {
  override def toString: String = "empty"
}

class NumberCell(numbrer: Int) extends Cell {
  override def toString: String = numbrer.toString
}

class StringCell(string: String) extends Cell {
  override def toString: String = string
}

class ReferenceCell(private val ix: Int, private val iy: Int, private val table: Table)
  extends Cell {

  override def toString: String = {
    @tailrec
    def loop(cellOpt: Option[Cell]): String = cellOpt match {
      case Some(cell) if cell == this => "cyclic"
      case Some(cell: ReferenceCell)  => loop(cell.table.getCell(cell.ix, cell.iy))
      case Some(cell)                 => cell.toString
      case _                          => "outOfRange"
    }
    loop(table.getCell(ix, iy))
  }

}
