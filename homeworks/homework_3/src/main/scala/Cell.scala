import scala.annotation.tailrec
import scala.collection.mutable

trait Cell {}

class EmptyCell() extends Cell {
  override def toString: String = "empty"
}

class NumberCell(value: Number) extends Cell {
  override def toString: String = value.toString
}

class StringCell(value: String) extends Cell {
  override def toString: String = value
}

class ReferenceCell(val ix: Int, val iy: Int, table: Table) extends Cell {
  override def toString: String = recSearch(this, mutable.HashSet(this))

  @tailrec
  private def recSearch(cell: ReferenceCell, visitedCells: mutable.HashSet[ReferenceCell]): String = {
    table.getCell(cell.ix, cell.iy) match {
      case None => "outOfBounds"
      case Some(value: ReferenceCell) =>
        if (visitedCells.contains(value)) "cyclic" else recSearch(value, visitedCells += value)
      case Some(value) => value.toString
    }
  }
}
