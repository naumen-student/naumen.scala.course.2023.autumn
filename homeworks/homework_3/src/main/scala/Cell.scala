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