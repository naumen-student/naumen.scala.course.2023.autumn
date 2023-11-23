trait Cell

case class EmptyCell() extends Cell {
  override def toString: String = "empty"
}

case class NumberCell(number: Int) extends Cell {
  override def toString: String = number.toString
}

case class StringCell(string: String) extends Cell {
  override def toString: String = string
}

case class ReferenceCell(ix: Int, iy: Int, table: Table) extends Cell {
  override def toString: String = {
    toStringImpl(Set.empty[ReferenceCell])
  }

  private def toStringImpl(cellsHistory: Set[ReferenceCell] = Set.empty[ReferenceCell]): String = {
    table.getCell(ix, iy).map {
      case referenceCell: ReferenceCell =>
        if (cellsHistory.contains(referenceCell)) "cyclic" else {
          referenceCell.toStringImpl(cellsHistory ++ Set(referenceCell))
        }
      case cell: Cell => cell.toString
    }.getOrElse("outOfRange")
  }
}