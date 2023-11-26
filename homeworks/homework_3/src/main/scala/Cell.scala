sealed trait Cell

case class EmptyCell() extends Cell {
  override def toString: String = "empty"
}
case class NumberCell(number: Int) extends Cell {
  override def toString: String = number.toString
}
case class StringCell(str: String) extends Cell {
  override def toString: String = str
}

case class ReferenceCell(indX: Int, indY: Int, table: Table) extends Cell {
  private def recStr(cell: Option[Cell], visited: Seq[ReferenceCell]): String = cell match {
    case Some(x) => x match {
      case currentCell: ReferenceCell =>
        if (!visited.contains(x)) recStr(table.getCell(currentCell.indX, currentCell.indY), visited :+ currentCell)
        else "cyclic"
      case _ => x.toString
    }
    case None => "outOfRange"
  }

  override def toString: String = recStr(table.getCell(indX, indY), Seq(this))
}