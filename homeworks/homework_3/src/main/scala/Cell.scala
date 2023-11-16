import scala.collection.mutable

trait Cell {
  def toString: String
}

class EmptyCell extends Cell {
  override def toString: String = "empty"
}

class NumberCell(number: Int) extends Cell {
  override def toString: String = number.toString
}

class StringCell(text: String) extends Cell {
  override def toString: String = text
}

class ReferenceCell(ix: Int, iy: Int, table: Table) extends Cell {
  override def toString: String = toStringWithVisited(mutable.Set.empty)

  private def toStringWithVisited(visited: mutable.Set[ReferenceCell]): String = {
    if (visited.contains(this)) {
      "cyclic"
    } else {
      visited += this
      val index = ix + iy * table.width
      if (ix < 0 || ix >= table.width || iy < 0 || iy >= table.height) {
        "outOfRange"
      } else {
        table.cells(index) match {
          case ref: ReferenceCell => ref.toStringWithVisited(visited)
          case cell => cell.toString
        }
      }
    }
  }
}