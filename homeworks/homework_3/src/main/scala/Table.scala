import cells.*

trait Table{
  def width: Int
  def height: Int
  def getCell(p: Position): Option[Cell[?]]
  def getCell(x: Int, y: Int): Option[Cell[?]]
  def update(p: Position, cell: Cell[?]): Unit
}

object Table{
  def simpleMake(_width: Int, _height: Int) = new Table {
    private var table = Map.empty[Position, Cell[?]]
    private def isWithin(p: Position): Boolean = !(p.x < 0 || p.x > width || p.y < 0 || p.y > height)
    
    override val width: Int = _width
    override val height: Int = _height

    override def getCell(x: Int, y: Int): Option[Cell[?]] = getCell(Position(x,y))
    
    override def getCell(p: Position): Option[Cell[?]] =
      if(!isWithin(p)) Option.empty
      else {
        table.get(p).map {
          case empty@ Cell.EmptyCell => empty
          case int@ Cell.NumberCell(_) => int
          case str@ Cell.StringCell(_) => str
          case ref@ Cell.ReferenceCell(_, _, _) => ref
        }.orElse(Some(Cell.EmptyCell))
      }

    override def update(p: Position, cell: Cell[?]): Unit = if(isWithin(p)) table = table.updated(p, cell)
      
  }
}
case class Position(x: Int, y: Int)