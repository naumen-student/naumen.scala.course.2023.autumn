trait Cell

class EmptyCell extends Cell
{
  override def toString: String = "empty"
}

class NumberCell(value: Int) extends Cell
{
  override def toString: String = value.toString
}

class StringCell(value: String) extends Cell
{
  override def toString: String = value
}

class ReferenceCell(x: Int, y: Int, table: Table) extends Cell
{
  override def toString: String = walkByLinks()

  private def walkByLinks(visitedCells: Set[ReferenceCell] = Set.empty): String = table.getCell(x,y).map{
    case referenceCell: ReferenceCell =>
      if (visitedCells.contains(referenceCell)) "cyclic" else referenceCell.walkByLinks(visitedCells ++ Set(referenceCell))
    case cell: Cell => cell.toString
  }.getOrElse("outOfTableRanges")
}