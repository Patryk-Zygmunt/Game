import java.util

import board.{Board, GameValue, GomokuVal}

class GomokuBoard extends Board[GomokuVal]{
  override def newState(x: Int, value: GomokuVal): Board[_ <: GameValue] = ???

  override def getFieldValue(x: Int): GomokuVal = ???

  override def isPossibleMove(x: Int, v: GomokuVal): Boolean = ???

  override def generatePossibleStates(v: GomokuVal): util.List[Board[_ <: GameValue]] = ???

  override def getMove: Int = ???

  override def endGame(): Boolean = ???
}
