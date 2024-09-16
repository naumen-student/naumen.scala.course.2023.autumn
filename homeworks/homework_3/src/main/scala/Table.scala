class Table(width: Int, height: Int){

  private val Cells: Array[Cell] =Array.fill[Cell](width*height)(new EmptyCell)

  private def inRange(ix: Int, iy:Int):  Option[Boolean] = Some(ix < width && ix > -1 && iy < height && iy > -1).filter(_ == true)

  def getCell(ix: Int, iy: Int): Option[Cell] = //{
  //if (ix < width && ix > -1 && iy < height && iy > -1) {
  //    Some(cells(ix + iy * width))
  //  } else {
  //     None
  //    }
    inRange(ix, iy).map(_ => cells(ix + iy * width))
  //    }
  def setCell(ix: Int, iy: Int, cell: Cell): Unit = inRange(ix, iy).foreach(_ => cells(ix + iy * width) = cell: Cell)

}