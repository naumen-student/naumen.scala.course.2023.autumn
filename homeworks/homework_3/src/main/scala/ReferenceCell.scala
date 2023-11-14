import scala.annotation.tailrec

class ReferenceCell(ix: Int, iy: Int, table: Table) extends Cell {
  override def toString: String = {
    innerToString(Set.empty)
  }

  @tailrec
  private def innerToString(acc: Set[ReferenceCell]): String = {
    if (acc.contains(this))
      return "cyclic"

    table.getCell(ix, iy) match {
      case Some(cell: ReferenceCell) => cell.innerToString(acc + this)
      case Some(x) => x.toString
      case None => "outOfRange"
    }
  }
}
