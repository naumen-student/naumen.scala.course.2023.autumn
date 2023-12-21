trait Cell

class EmptyCell extends Cell {
  override def toString: String = "empty"
}

class NumberCell(number: Int) extends Cell {
  override def toString: String = number.toString

}

class StringCell(name: String) extends Cell {
  override def toString: String = name
}

class ReferenceCell(ix: Int, iy: Int, table: Table) extends Cell {
  override def toString: String = toStringImp()

  private def toStringImp(cellsHistory: Set[ReferenceCell] = Set.empty[ReferenceCell]): String = table.getCell(ix, iy).map {
    case refCell: ReferenceCell => {
      if (cellsHistory.contains(refCell)) "cyclic" else {
        refCell.toStringImp(cellsHistory ++ Set(refCell))
      }
    }
    case cell: Cell => cell.toString
  }.getOrElse("outOfRange")
}