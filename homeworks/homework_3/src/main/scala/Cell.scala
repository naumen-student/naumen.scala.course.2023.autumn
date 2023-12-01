trait Cell

class EmptyCell extends Cell {
  override def toString: String = "empty"
}

class NumberCell(number: Int) extends Cell {
  override def toString: String = number.toString
}

class StringCell(str: String) extends Cell{
  override def toString: String = str
}

class ReferenceCell(ix:Int, iy:Int, table:Table) extends Cell{

  override def toString: String = toStringImpl()

  private def toStringImpl(cellsHistory:Set[ReferenceCell]=Set.empty[ReferenceCell]): String = table.getCell(ix, iy).map{
    case refCell: ReferenceCell =>
      if (cellsHistory.contains(refCell)) "cyclic" else {
        refCell.toStringImpl(cellsHistory ++ Set(refCell))
      }
    case cell: Cell =>cell.toString
  }.getOrElse("outofRange")

}