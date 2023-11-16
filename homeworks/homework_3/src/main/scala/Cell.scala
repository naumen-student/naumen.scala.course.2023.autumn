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
    def go(cell: Option[Cell]): String = {
      cell match {
        case Some(ReferenceCell(_, _, _)) if start == cell.get => "cyclic"
        case Some(ReferenceCell(ix, iy, table)) => go(table.getCell(ix, iy))
        case None => "outOfRange"
        case _ => cell.get.toString
      }
    }
    go(table.getCell(ix, iy))
  }
}
