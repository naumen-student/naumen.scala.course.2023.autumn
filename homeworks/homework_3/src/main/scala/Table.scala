class Table(width: Int, height: Int) {

  private val cells: Array[Cell] = Array.fill[Cell](width * height)(new EmptyCell)

  private def Array2DCell(ix: Int, iy: Int): Int = iy * width + ix

  private def possibleValues(ix: Int, iy: Int): Boolean = ix >= 0 && iy >= 0 & ix <= width && iy <= height

  def getCell(ix: Int, iy: Int): Option[Cell] = if (possibleValues(ix, iy)) Some(cells(Array2DCell(ix, iy))) else None

  def setCell(ix: Int, iy: Int, cell: Cell): Unit = if (possibleValues(ix, iy)) cells(Array2DCell(ix, iy)) = cell
}
