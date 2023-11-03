object cells {
  sealed trait Cell[+T]

  object Cell {
    case object EmptyCell extends Cell[Nothing] {
      override def toString: String = "empty"
    }

    case class NumberCell(i: Int) extends Cell[Int] {
      override def toString: String = s"$i"
    }

    case class StringCell(s: String) extends Cell[String] {
      override def toString: String = s
    }

    case class ReferenceCell[T](x: Int, y: Int, t: Table) extends Cell[Cell[T]] {
      self =>
      override def toString: String = {
        if (x < 0 || x > t.width || y < 0 || y > t.height) "outOfRange"
        else {
          if (
            t.getCell(x, y).fold(false) {
              case EmptyCell | NumberCell(_) | StringCell(_) => false
              case ReferenceCell(_x, _y, _t) => _t.getCell(_x, _y).exists(_ eq this)
            }
          ) "cyclic"
          else t.getCell(x, y).get.toString
        }
      }
    }
  }
}