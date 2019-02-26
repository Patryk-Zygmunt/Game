import board.{GameValue, GomokuVal, TicToeBoard}
import evaluation.EvaluationFunction

import scala.collection.immutable.Stream.Empty
import board.GomokuVal._


object EvaluationGomoku extends EvaluationFunction[GomokuBoard]{

  def findVertical(array: Array[GomokuVal],v: GameValue):Int = {
    var splited : Array[Array[GomokuVal]]= array grouped 15 toArray
   var t:Array[Int] = splited.zipWithIndex.map{ case (arr,i)=>arr.span(_==v)._1.length}
    t.max
  }

  def findHorizontal(array: Array[GomokuVal],v: GameValue):Int = 0

  override def evaluate(board: GomokuBoard, maxVal: GameValue, minimizeVal: GameValue) = {
    val arr = board._board
    var vertical = (findVertical(arr,maxVal),findVertical(arr,minimizeVal))
    var horizontal = (findHorizontal(arr,maxVal),findHorizontal(arr,minimizeVal))

    vertical._1 - vertical._2
  }
}


