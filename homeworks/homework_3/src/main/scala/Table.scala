
class Table(width: Int, height: Int) {
  private val cells: Array[Cell] = Array.fill[Cell](width * height)(new EmptyCell)

  def getCell(ix: Int, iy: Int): Option[Cell] =
    if (bordersCheck(ix, iy)) {
      Some(cells(ix + iy * width))
    } else None

  def setCell(ix: Int, iy: Int, cell: Cell): Unit =
    if (bordersCheck(ix, iy)) {
      cells(ix + iy * width) = cell
    }

  private def bordersCheck(x: Int, y: Int): Boolean = x > -1 && y > -1 && x < width && y < height
}