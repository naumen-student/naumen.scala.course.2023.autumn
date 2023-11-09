class Table(width: Int, height: Int) {
  private val array: Array[Cell] = Array.fill(width * height)(new EmptyCell)

  def getCell(ix: Int, iy: Int): Option[Cell] = {
    if (inBounds(ix, iy)) Some(array(index(ix, iy))) else None
  }

  def setCell(ix: Int, iy: Int, cell: Cell): Unit = {
    if (!inBounds(ix, iy)) throw new IndexOutOfBoundsException()
    array(index(ix, iy)) = cell
  }

  private def index(ix: Int, iy: Int): Int = iy * width + ix

  private def inBounds(ix: Int, iy: Int): Boolean = {
    ix >= 0 && ix < width && iy >= 0 && iy < height
  }
}
