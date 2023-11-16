trait Cell { }

class EmptyCell extends Cell {
  override def toString: String = {
    "empty"
  }
}

class NumberCell(var value: Int) extends Cell {
  override def toString: String = {
    value.toString
  }
}

class StringCell(var value: String) extends Cell {
  override def toString: String = {
    value
  }
}

class ReferenceCell(var x: Int, var y: Int, var table: Table) extends Cell {
  override def toString: String = {
    table.getCell(x, y).map {
      case ref: ReferenceCell =>
        ref.table
          .getCell(ref.x, ref.y)
          .filter(_ != this)
          .map(_.toString)
          .getOrElse("cyclic")

      case cell: Cell => cell.toString
    }.getOrElse("outOfRange")
  }
}