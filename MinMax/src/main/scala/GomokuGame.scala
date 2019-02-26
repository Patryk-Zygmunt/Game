
import board.{Board, GameValue, GomokuVal, TicToeBoard}
import evaluation.EvaluationFunction
import game.Game


class GomokuGame extends Game {
  override def nextMove(board: Board[_ <: GameValue], maximizedVal: GameValue, minimizedVal: GameValue): Int =
    super.nextMove(board, maximizedVal, minimizedVal, 3, EvaluationGomoku)



  override def checkWin(board: Board[_ <: GameValue], maximizedVal: GameValue, minimizedVal: GameValue): Int =
    super.checkWin(board, maximizedVal, minimizedVal,  EvaluationGomoku)
}
