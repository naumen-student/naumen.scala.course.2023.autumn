class ReferenceCell(var x: Int, var y: Int, var table: Table) extends Cell {
  override def toString(): String = {
    val cell = table.getCell(x, y)
    val result = if (table.n > x || table.n < 0 || table.m > y || table.m < 0) {
      "wrong range"
    } else if (cell.nonEmpty){
      cell.toString
    } else {
      "error"
    }
    result
  }
}
