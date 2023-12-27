import scala.annotation.tailrec

sealed trait Cell

class EmptyCell() extends Cell {
  override def toString: String = "empty"
}

class NumberCell(number: Int) extends Cell {
  override def toString: String = number.toString
}

class StringCell(str: String) extends Cell {
  override def toString: String = str
}

class ReferenceCell(val ix: Int, val iy: Int, table: Table) extends Cell {
  @tailrec
  private def toStringImpl(acc: Seq[ReferenceCell] = Seq.empty): String = {
    table.getCell(ix, iy) match {
      case Some(value) => value match {
        case v: ReferenceCell => if (!acc.contains(v))
          v.toStringImpl(acc :+ v)
        else
          "cyclic"
        case v => v.toString
      }
      case None => "outOfRange"
    }
  }

  override def toString: String = toStringImpl()
}