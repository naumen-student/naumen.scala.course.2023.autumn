trait Cell {}

class NumberCell(number: Int) extends Cell {
  override def toString: String = number.toString
}

class EmptyCell() extends Cell {
  override def toString: String = "empty"
}

class StringCell(str: String) extends Cell {
  override def toString: String = str
}

class ReferenceCell(ix: Int, iy: Int, table: Table) extends Cell {
  override def toString: String = getRefType()

  private def getRefType(history: Set[ReferenceCell] = Set.empty[ReferenceCell]): String = {
    table.getCell(ix, iy).map {
      case referenceCell: ReferenceCell =>
        if (history.contains(referenceCell)) {
          "cyclic"
        } else {
          referenceCell.getRefType(history ++ Set(referenceCell))
        }
      case cell: Cell => cell.toString
    }.getOrElse("outOfRange")
  }
}