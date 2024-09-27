import scala.annotation.tailrec
import scala.collection.mutable

abstract class Cell{
  def toString: String
}
case class EmptyCell() extends Cell{
  override def toString: String = "empty"
}
case class NumberCell(value:Int) extends Cell{
  override def toString: String = value.toString
}
case class StringCell(value:String) extends Cell{
  override def toString: String = value
}
case class ReferenceCell(posX:Int, posY:Int, table:Table) extends Cell{
  override def toString:String = {
    @tailrec
    def rec(acc:List[Cell]=Nil, cur:Cell=this):String = {
      cur match {
        case ReferenceCell(posX, posY, table) =>
          table.getCell(posX, posY) match {
            case Some(value) =>
              if(acc.contains(value)) "cyclic"
              else rec(value::acc, value)

            case None => "outOfRange"
          }


        case _ => cur.toString
      }
    }
    rec()
  }

}

