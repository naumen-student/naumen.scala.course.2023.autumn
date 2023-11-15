import scala.annotation.tailrec

trait Cell

class EmptyCell extends Cell {
  override def toString: String = "empty"
}

class NumberCell(number: Int) extends Cell {
  override def toString: String = number.toString
}

class StringCell(str: String) extends Cell {
  override def toString: String = str
}

class ReferenceCell(ix: Int, iy: Int, table: Table) extends Cell {

  override def toString: String = toStringRef()

  @tailrec
  private def toStringRef(historyCells: Set[ReferenceCell] = Set.empty[ReferenceCell]): String = table.getCell(ix, iy) match {
    case Some(refCell: ReferenceCell) =>
      if (historyCells.contains(refCell)) "cyclic"
      else refCell.toStringRef(historyCells ++ Set(refCell))
    case Some(cell: Cell) => cell.toString
    case None => "outOfRange"
  }
}
