
import scala.collection.mutable.ArrayOps
import scala.util.Try
import Table._
import scala.annotation.tailrec

class Table private (table: Array[Cell], colls: Int, rows: Int) { self =>

   def getCell(row: Int, column: Int): Option[Cell] =
    Try(table(row + column * rows)).toOption
  

  def getCellValue(row: Int, column: Int): GetCellResult = { 
    
    @tailrec
    def iter(cell: Option[Cell]): GetCellResult = cell match {
      case Some(ReferenceCell(refRow, refColumn, table)) if refRow == row && refColumn == column => Cyclic
      case Some(ReferenceCell(refRow, refColumn, table)) => iter(table.getCell(refRow, refColumn))
      case Some(cell) => SomeCell(cell)
      case _ => OutOfRange
    }

    iter(self.getCell(row, column))
  }

  def setCell(row: Int, column: Int, cell: Cell): Unit = Try(table.update(row + column * rows, cell))
}


object Table {

  def apply(colls: Int, rows: Int): Table = 
    new Table(Array.fill[Cell](colls * rows)(EmptyCell), colls, rows)

  sealed trait GetCellResult
  case object OutOfRange extends GetCellResult
  case object Cyclic extends GetCellResult
  case class SomeCell(cell: Cell) extends GetCellResult
}
