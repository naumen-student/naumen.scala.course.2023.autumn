import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.*

class Test extends AnyFeatureSpec {
  Feature("tests") {
    Scenario("create table") {
      val table = Table.simpleMake(3, 3)
      for (i <- 0 until 9) {
        assert(table.getCell(i / 3, i % 3).map(_.toString) == Some("empty"))
      }
      assert(table.getCell(0, -1).map(_.toString) == None)
      assert(table.getCell(-1, 0).map(_.toString) == None)
      assert(table.getCell(9, 8).map(_.toString) == None)
      assert(table.getCell(8, 9).map(_.toString) == None)
    }
    Scenario("numberCell") {
      val table = Table.simpleMake(2, 2)
      val cellInt00 = cells.Cell.NumberCell(5)
      val cellInt11 = cells.Cell.NumberCell(2147483647)
      table(Position(0, 0)) = cellInt00
      table(Position(1, 1)) = cellInt11
      assert(table.getCell(0, 0).map(_.toString) == Some("5"))
      assert(table.getCell(0, 1).map(_.toString) == Some("empty"))
      assert(table.getCell(1, 0).map(_.toString) == Some("empty"))
      assert(table.getCell(1, 1).map(_.toString) == Some("2147483647"))
    }
    Scenario("referenceCell") {
      val table = Table.simpleMake(3, 3)
      /*ix = 0*/
      val cellStr00 = cells.Cell.StringCell("00")
      val cellRef01 = cells.Cell.ReferenceCell(0, 2, table)
      val cellRef02 = cells.Cell.ReferenceCell(0, 1, table)
      /*ix = 1*/
      val cellInt10 = cells.Cell.NumberCell(10)
      val cellInt11 = cells.Cell.NumberCell(11)
      val cellRef12 = cells.Cell.ReferenceCell(0, 0, table)
      /*ix = 2*/
      val cellEmpty20 = cells.Cell.EmptyCell
      val cellRef21 = cells.Cell.ReferenceCell(1, 1, table)
      val cellRef22 = cells.Cell.ReferenceCell(2, 1, table)
      table(Position(0, 0)) = cellStr00
      table(Position(0, 1)) = cellRef01
      table(Position(0, 2)) = cellRef02
      table(Position(1, 0)) = cellInt10
      table(Position(1, 1)) = cellInt11
      table(Position(1, 2)) = cellRef12
      table(Position(2, 0)) = cellEmpty20
      table(Position(2, 1)) = cellRef21
      table(Position(2, 2)) = cellRef22
      for (i <- 0 until 9) {
        val value = table.getCell(i / 3, i % 3).get.toString
        i match {
          case 0 => assert(value == "00")
          case 1 => assert(value == "cyclic")
          case 2 => assert(value == "cyclic")
          case 3 => assert(value == "10")
          case 4 => assert(value == "11")
          case 5 => assert(value == "00")
          case 6 => assert(value == "empty")
          case 7 => assert(value == "11")
          case 8 => assert(value == "11")
          case _ => assert(false)
        }
      }
    }
  }
}
