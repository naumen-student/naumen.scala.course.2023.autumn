import Table.Cyclic
import Table.OutOfRange
import Table.SomeCell

sealed trait Cell { self => 

    override def toString(): String = self match {
      case EmptyCell => "empty"
      case NumberCell(number) => number.toString()
      case StringCell(str) => str
      case ReferenceCell(row, column, table) => 
        table.getCellValue(row, column) match {
          case SomeCell(cell) => cell.toString()
          case Cyclic => "cyclic"
          case OutOfRange => "outOfRange"
        }
    }
  }


  case object EmptyCell extends Cell
  case class NumberCell(number: Int) extends Cell
  case class StringCell(str: String) extends Cell
  case class ReferenceCell (row: Int, column: Int, table: Table) extends Cell
