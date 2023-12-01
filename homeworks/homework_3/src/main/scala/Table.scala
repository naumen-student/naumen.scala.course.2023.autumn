import scala.collection.mutable

class Table(val sizeX: Int, val sizeY: Int) {
  private def containsInList(ix: Int, iy: Int): Boolean = ix < sizeX && ix >= 0 && iy < sizeY && iy >= 0

  private def to1d(ix: Int, iy: Int): Option[Int] = {
    if (containsInList(ix, iy)) Some(ix * sizeX + iy)
    else None
  }

  private val innerList: mutable.MutableList[Cell] = mutable.MutableList.fill(sizeX * sizeY) {EmptyCell()}


  def getCell(ix: Int, iy: Int): Option[Cell] = to1d(ix, iy).flatMap(a => Some(innerList(a)))

  def setCell(ix: Int, iy: Int, cell: Cell): Unit = to1d(ix, iy).foreach(a => {
    innerList(a) = cell
  })

}
