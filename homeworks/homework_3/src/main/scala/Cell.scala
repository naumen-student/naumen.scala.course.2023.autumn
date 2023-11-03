import scala.annotation.tailrec

sealed trait Cell

class EmptyCell() extends Cell {
  override def toString: String = "empty"
}

class NumberCell(number: Int) extends Cell {
  override def toString: String = number.toString
}

class StringCell(text: String) extends Cell {
  override def toString: String = text
}

class ReferenceCell(ix: Int, iy: Int, table: Table) extends Cell {
  private var refCell: Cell = _

  override def toString: String = {
    if (refCell == null) {
      refCell = table.getCell(ix, iy).getOrElse(new EmptyCell)
    }

    refCell match {
      case c: ReferenceCell =>
        if (c.isCyclic(this)) {
          "cyclic"
        } else {
          c.toString()
        }
      case _ => refCell.toString
    }
  }

  @tailrec
  private def isCyclic(cell: Cell): Boolean = {
    refCell match {
      case c: ReferenceCell => c == cell || c.isCyclic(cell)
      case _ => false
    }
  }
}


