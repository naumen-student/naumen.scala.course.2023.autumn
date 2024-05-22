trait Cell


class EmptyCell() extends Cell {
  override def toString: String = "empty"
}

class NumberCell(number: Int) extends Cell {
  override def toString: String = number.toString
}


class StringCell(text: String) extends Cell {
  override def toString: String = text;
}

class ReferenceCell(ix: Int, iy: Int, table: Table) extends Cell {
  private var visitedCells: Set[ReferenceCell] = Set()

  override def toString: String = {
    getValueFromReference(this.ix, this.iy)
  }

  private def getValueFromReference(ix: Int, iy: Int): String = {
    table.getCell(ix, iy) match {

      case None => "outOfRange"
      case Some(refCell: ReferenceCell) =>
        if (visitedCells.contains(refCell)) {
          "cyclic"
        } else {
          visitedCells += refCell
          refCell.toString
        }
      case Some(cell: Cell) => cell.toString
    }
  }

}
