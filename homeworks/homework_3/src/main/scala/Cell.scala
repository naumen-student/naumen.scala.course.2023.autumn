sealed trait Cell

class EmptyCell extends Cell {
  override def toString: String = "empty"
}

class NumberCell(value: Int) extends Cell {
  override def toString: String = value.toString
}

class StringCell(value: String) extends Cell {
  override def toString: String = value
}

class ReferenceCell(val ix: Int, val iy: Int, table: Table) extends Cell {
  private var visitedCells: Set[(Int, Int)] = Set.empty

  override def toString: String = {
    table.getCell(ix, iy) match {
      case Some(refCell: ReferenceCell) =>
        if (visitedCells.contains((refCell.ix, refCell.iy))) {
          "cyclic"
        } else {
          visitedCells += ((refCell.ix, refCell.iy))
          refCell.toString
        }
      case Some(cell: Cell) => cell.toString
      case None => "outOfRange"
    }
  }
}
