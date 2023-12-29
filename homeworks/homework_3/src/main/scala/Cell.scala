trait Cell

class StringCell(string: String) extends Cell {
  override def toString: String = string
}

class NumberCell(number: Int) extends Cell {
  override def toString: String = number.toString
}

class EmptyCell extends Cell {
  override def toString: String = "empty"
}

class ReferenceCell(x: Int, y: Int, table: Table) extends Cell
{
  override def toString: String = getRefType()

  private def getRefType(history: Set[ReferenceCell] = Set.empty[ReferenceCell]): String = {
    table.getCell(x, y).map {
      case refCell: ReferenceCell =>
        if (history.contains(refCell)) "cyclic"
        else refCell.getRefType(history ++ Set(refCell))
      case cell: Cell => cell.toString
    }.getOrElse("outOfRange")
  }
}