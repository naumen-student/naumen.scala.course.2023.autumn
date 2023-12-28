class ReferenceCell(x: Int, y: Int, table: Table) extends Cell {
  private var used = false

  override def toString(): String = {
    val cell = table.getCell(x, y)
    val res = cell match {
      case None => "outOfRange"
      case Some(x: Cell) if used => "cyclic"
      case Some(x: Cell) => {
        used = true
        x.toString
      }
    }
    used = false
    return res
  }
}