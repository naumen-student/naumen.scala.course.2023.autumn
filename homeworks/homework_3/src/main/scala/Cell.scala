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
  override def toString: String = table.getCell(ix, iy) match {
    case Some(refCell: ReferenceCell) =>
      table.getCell(refCell.ix, refCell.iy) match {
        case Some(cell) if cell != this => cell.toString
        case _ => "cyclic"
      }
    case Some(cell: Cell) => cell.toString
    case _ => "outOfRange"
  }
}