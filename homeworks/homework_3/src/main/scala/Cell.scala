import scala.annotation.tailrec

trait Cell {
  def toString: String
}

class EmptyCell extends Cell {
  override def toString: String = "empty"
}

class NumberCell(number: Int) extends Cell {
  override def toString: String = number.toString
}

class StringCell(text: String) extends Cell {
  override def toString: String = text
}

class ReferenceCell(ix: Int, iy: Int, table: Table) extends Cell {

  override def toString: String = toStringWithHistory(Set.empty)

  @tailrec
  private def toStringWithHistory(history: Set[ReferenceCell]): String = {
    table.getCell(ix, iy) match {
      case Some(refCell: ReferenceCell) =>
        if (history.contains(refCell)) "cyclic"
        else refCell.toStringWithHistory(history + this)
      case Some(cell) => cell.toString
      case None => "outOfRange"
    }
  }
}