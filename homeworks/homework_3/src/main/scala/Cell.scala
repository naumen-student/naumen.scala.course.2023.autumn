import scala.annotation.tailrec

sealed trait Cell

final class EmptyCell extends Cell {
  override def toString: String = "empty"
}

final class NumberCell(number: Int) extends Cell {
  override def toString: String = number.toString
}

final class StringCell(string: String) extends Cell {
  override def toString: String = string
}

final class ReferenceCell(ix: Int, iy: Int, table: Table) extends Cell {
  @tailrec
  private def dfsToString(used: Set[Cell] = Set.empty[Cell]):String = {
    table.getCell(ix, iy) match {
      case Some(cell: ReferenceCell) => if (used.contains(cell)) "cyclic" else cell.dfsToString(used ++(Set(cell)))
      case Some(cell: Cell) => cell.toString
      case _ => "outOfRange"
    }
  }

  override def toString: String = dfsToString()
}
