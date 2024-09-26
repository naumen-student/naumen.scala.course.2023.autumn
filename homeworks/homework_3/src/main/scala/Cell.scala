trait Cell {
  def toString: String
}

class EmptyCell extends Cell {
  override def toString: String = "empty"
}

class NumberCell(number: Int) extends Cell {
  override def toString: String = number.toString
}

class StringCell(string: String) extends Cell {
  override def toString: String = string
}

class ReferenceCell(ix: Int, iy: Int, table: Table) extends Cell {
  override def toString: String = toStringRef()

  private def toStringRef(
      cellsHistory: Set[ReferenceCell] = Set.empty[ReferenceCell]
  ): String = {
    table
      .getCell(ix, iy)
      .map {
        case refCell: ReferenceCell =>
          if (cellsHistory.contains(refCell)) "cyclic"
          else refCell.toStringRef(cellsHistory ++ Set(refCell))
        case cell: Cell => cell.toString
      }
      .getOrElse("outOfRange")
  }
}
