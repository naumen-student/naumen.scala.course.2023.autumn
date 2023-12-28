import scala.util.Try

class Table(width: Int, height: Int) {

  private val storage: Array[Cell] = Array.fill[Cell](width * height)(new EmptyCell)
  def getCell(ix: Int, iy: Int): Option[Cell] = Try(storage(ix + iy * width)).toOption

  def setCell(ix: Int, iy: Int, cell: Cell): Unit = storage.update(ix + iy * width, cell)
}