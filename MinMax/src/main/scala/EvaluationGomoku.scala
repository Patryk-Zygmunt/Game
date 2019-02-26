import board.{GameValue, TicToeBoard}
import evaluation.EvaluationFunction

class EvaluationGomoku extends EvaluationFunction[TicToeBoard]{
  override def evaluate(board: TicToeBoard, maxVal: GameValue, minVal: GameValue): Int = ???
}
