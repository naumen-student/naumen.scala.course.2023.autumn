trait Cell {
  def toString(): String
}

class EmptyCell extends Cell {
  override def toString = "empty"
}

class NumberCell(n: Int) extends Cell {
  override def toString = n.toString
}

class StringCell(str: String) extends Cell {
  override def toString = str
}

class ReferenceCell(ix: Int, iy: Int, table: Table) extends Cell {
  var cell = table.getCell(ix, iy)
  override def toString: String = {
    cell = table.getCell(ix, iy)
    cell match {
      case None => "outOfRange"
      case Some(x: ReferenceCell) =>
        if (x.cell.contains(this)) "cyclic" else x.toString
      case Some(x: Cell) => x.toString
    }
  }
}