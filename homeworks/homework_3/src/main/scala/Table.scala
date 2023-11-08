class Table(height: Int, width: Int) {
  private val cells: Array[Cell] = Array.fill[Cell](height * width)(new EmptyCell)
  private def checkRange(index: Int): Boolean = 0 <= index && index < cells.length
  def getCell(ix: Int, iy: Int): Option[Cell] = {
    val cellNumber = Some(ix + iy * width)
    cellNumber.filter(checkRange).map(cell => cells(cell))
  }
  def setCell(ix: Int, iy: Int, cellValue: Cell): Unit = {
    val cellNumber = Some(ix + iy * width)
    cellNumber.filter(checkRange).map(cell => cells(cell) = cellValue)
  }
}