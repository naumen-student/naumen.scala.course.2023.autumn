import scala.collection.mutable.ArrayBuffer

class Table(weight: Int, height: Int) {

  private val table = {
    val array = new ArrayBuffer[Cell]
    for {
      _ <- 0 until weight
      _ <- 0 until height
    } array += EmptyCell()
    array
  }

  def getCell(ix: Int, iy: Int): Option[Cell] = {
    table.lift(ix + iy * weight)
  }

  def setCell(ix: Int, iy: Int, cell: Cell): Unit =
    table(ix + iy * weight) = cell

}
