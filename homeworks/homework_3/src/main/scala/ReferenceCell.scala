class ReferenceCell(ix: Int, iy: Int, table: Table) extends Cell {
  private var isBeingEvaluated = false

  override def toString: String = {
    if (isBeingEvaluated) {
      "cyclic"
    } else {
      isBeingEvaluated = true
      val result = table.getCell(ix, iy) match {
        case Some(cell) => cell.toString
        case None => "outOfRange"
      }
      isBeingEvaluated = false
      result
    }
  }
}