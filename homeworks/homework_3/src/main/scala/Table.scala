class Table(var width: Int, var height: Int) {
  private val cells: Array[Array[Cell]] = Array.fill(width)(Array.fill(height)(new EmptyCell()))

  def getCell(ix: Int, iy: Int): Option[Cell] = {
    if (!isDimensionsValid(ix, iy))
      return None
    Some(cells(ix)(iy))
  }

  def setCell(ix: Int, iy: Int, cell: Cell): Unit = {
    if (!isDimensionsValid(ix, iy))
      return
    cells(ix)(iy) = cell
  }

  private def isDimensionsValid(x: Int, y: Int): Boolean = {
    !(x < 0 || y < 0 || x >= width || y >= height)
  }
}