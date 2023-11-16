import scala.annotation.tailrec

trait Cell

case class EmptyCell() extends Cell {
  override def toString: String = "empty"
}

case class NumberCell(num: Int) extends Cell {
  override def toString: String = num.toString
}

case class StringCell(str: String) extends Cell {
  override def toString: String = str
}

case class ReferenceCell(ix: Int, iy: Int, table: Table) extends Cell {
  override def toString: String = {
    val start = ReferenceCell(ix: Int, iy: Int, table: Table)
    @tailrec
    def go(cell: Cell): String = {
      cell match {
        case ReferenceCell(_, _, _) if start == cell => "cyclic"
        case ReferenceCell(ix, iy, table) => go(table.getCell(ix, iy).get)
        case _ => cell.toString

      }
    }
    go(table.getCell(ix, iy).get)
  }
}
