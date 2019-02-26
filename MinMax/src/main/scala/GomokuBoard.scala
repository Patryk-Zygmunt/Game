import java.util
import scala.collection.JavaConverters._
import board.{Board, GameValue, GomokuVal}
import board.GomokuVal._

class GomokuBoard(board: Array[GomokuVal],move: Int) extends Board[GomokuVal]{

  def this(_board: util.List[GomokuVal]) = this(_board.asScala.toArray ,-1)
  def _board: Array[GomokuVal] = board

  override def newState(x: Int, value: GomokuVal): Board[_ <: GameValue] = {
    val  tmpArr =  board map identity
    tmpArr(x)=value
    new GomokuBoard(tmpArr,x)
  }



  override def getFieldValue(x: Int): GomokuVal = board(x)

  override def isPossibleMove(x: Int, v: GomokuVal): Boolean = board(x) == EMPTY

  override def generatePossibleStates(v: GomokuVal): util.List[Board[_ <: GameValue]] = _generatePossbleStates(v).toList.asJava

  private def _generatePossbleStates(v: GomokuVal):Array[Board[_ <: GameValue]]= {
    print((1 to board.length).toArray.deep.mkString("\n"))
    board.indices.toArray filter (i => isPossibleMove(i, GomokuVal.EMPTY)) map (i => newState(i, v))
  }


  override def getMove: Int = move

  override def endGame(): Boolean = ! (board contains EMPTY)
}
