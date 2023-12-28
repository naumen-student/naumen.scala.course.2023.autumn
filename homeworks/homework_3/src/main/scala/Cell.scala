import scala.annotation.tailrec

sealed trait Cell { self =>
  override def toString: String = self match {
    case EmptyCell => "empty"
    case NumberCell(number) => number.toString
    case StringCell(value) => value
    case refCell: ReferenceCell => refCell.getValue()
  }
}

case object EmptyCell extends Cell
case class NumberCell(number: Int) extends Cell
case class StringCell(value: String) extends Cell
case class ReferenceCell(ix: Int, iy: Int, table: Table) extends Cell { self =>
  def getValue(): String = {
    @tailrec
    def rec(cell: Option[Cell]): String = cell match {
      case Some(cell) => cell match {
        case ReferenceCell(ixOther, iyOther, _) if ix == ixOther && iy == iyOther => "cyclic"
        case ReferenceCell(ixOther, iyOther, table) =>rec(table.getCell(ixOther, iyOther))
        case _ => cell.toString
      }
      case None => "outOfRange"
    }
    rec(table.getCell(ix, iy))
  }
}