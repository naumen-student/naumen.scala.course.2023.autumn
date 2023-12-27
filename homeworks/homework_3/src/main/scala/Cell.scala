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

class ReferenceCell(x: Int, y:Int, table: Table) extends Cell {
  @scala.annotation.tailrec
  private def chainToString(visited: Set[Cell] = Set.empty[Cell]):String = table.getCell(x, y) match {
    case Some(ref:ReferenceCell) => if (visited.contains(ref)) "cyclic" else ref.chainToString(visited++Set(ref))
    case Some(cell:Cell) => cell.toString
    case None => "OutOfRange"
  }

  override def toString: String = chainToString()
}