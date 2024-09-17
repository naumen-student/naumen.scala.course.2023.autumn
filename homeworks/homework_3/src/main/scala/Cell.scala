trait Cell;

case class EmptyCell() extends Cell {
  override def toString: String = "empty"
}

case class NumberCell(number: Int) extends Cell {
  override def toString: String = number.toString;
}

case class StringCell(string: String) extends Cell {
  override def toString: String = string;
}

case class ReferenceCell(cellX: Int, cellY: Int, table: Table) extends Cell {
  override def toString: String = {
    recursiveSearch(this)
  }

  def recursiveSearch(startCell: ReferenceCell): String = {
    next match {
      case None => "outOfRange"

      case Some(cell: ReferenceCell) =>
        if (cell.next.contains(this)) "cyclic" else cell.toString

      case Some(cell: Cell) => cell.toString
    }
  }

  def next: Option[Cell] = table.getCell(cellX, cellY);
}