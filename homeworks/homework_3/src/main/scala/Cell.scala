trait Cell
class EmptyCell extends Cell {
  override def toString: String = "empty"
}

class NumberCell(n: Int) extends Cell {
  override def toString: String = n.toString
}

class StringCell(str: String) extends Cell {
  override def toString: String = str
}

class ReferenceCell(val ix: Int, val iy: Int, table: Table) extends Cell
{
  override def toString: String = table.getCell(ix, iy).map {
    case ref: ReferenceCell =>
      table
        .getCell(ref.ix, ref.iy)
        .filter(_ != this).map(_.toString)
        .getOrElse("cyclic")
    case cell: Cell => cell.toString
  }.getOrElse("outOfRange")
}