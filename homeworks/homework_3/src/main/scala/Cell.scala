trait Cell

class EmptyCell extends Cell {
  override def toString: String = "empty"
}

class NumberCell(number: Int) extends Cell {
  override def toString: String = number.toString
}

class StringCell(string: String) extends Cell {
  override def toString: String = string
}

class ReferenceCell(cellX: Int, cellY: Int, table: Table) extends Cell {
  private def next: Option[Cell] = table.getCell(cellX, cellY)
  override def toString: String = {
    this.next match {
      case None => "outOfRange"
      case Some(cell) =>
        if (cell.isInstanceOf[ReferenceCell] && cell.asInstanceOf[ReferenceCell].next.contains(this)) "cyclic" else cell.toString
    }
  }
}
