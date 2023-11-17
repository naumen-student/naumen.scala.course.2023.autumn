trait Cell {
  def toString: String
}

class EmptyCell extends Cell {
  override def toString: String = "empty"
}

class NumberCell(val number: Int) extends Cell {
  override def toString: String = number.toString
}

class StringCell(val text: String) extends Cell {
  override def toString: String = text
}

class ReferenceCell(val ix: Int, val iy: Int, table: Table) extends Cell {
  override def toString: String = table.getCell(ix, iy).map {
    case refCell: ReferenceCell =>
      table
        .getCell(refCell.ix, refCell.iy)
        .filter(_ != this).map(_.toString)
        .getOrElse("cyclic")
    case cell: Cell => cell.toString
  }.getOrElse("outOfRange")
}
