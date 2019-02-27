import board.{GameValue, GomokuVal, TicToeBoard}
import evaluation.EvaluationFunction

import scala.collection.immutable.Stream.Empty
import board.GomokuVal._


object EvaluationGomoku extends EvaluationFunction[GomokuBoard]{

  def findVertical(array: Array[GomokuVal],maxV: GameValue):Int = {
    var splited : Array[Array[GomokuVal]]= array grouped 15 toArray
   var spt:Array[Array[(Boolean,Boolean)]] = splited.map{ arr=>arr.zipWithIndex.map{ case (v,index)=> isPossibleToPutFive(v,arr,maxV,index)}}
    var (e,w)= spt.map(v=>v.unzip).map{case (em,win)=>(theLOngestTrue(em), theLOngestTrue(win))}.unzip
    if(w.max==0) e.count(p=>p>5)-10
    else math.pow(w.max,5).toInt
  }
  private def isPossibleToPutFive(actVal:GomokuVal,arr:Array[GomokuVal],maxVal: GameValue,index:Int):(Boolean,Boolean)={
    actVal match{
      case EMPTY=> (true,false)
      case `maxVal` =>
        val (l,r) = arr.toList.splitAt(index)
        val (cl,cr) = (l.takeRight(4).count(v => v == EMPTY || v == maxVal) == 4,r.take(4).count(v => v == EMPTY || v == maxVal) == 4)
        (true,cl || cr)
      case _ => (false,false)
    }
  }
  private def theLOngestTrue(arr: Array[Boolean]): Int ={
    var theLongest = 0
    var counter= 0
    arr.foreach(v=>{
      if(v) {
        counter +=1
      }
      else{
        theLongest = Math.max( counter,theLongest)
        counter = 0
      }
    })
    Math.max( counter,theLongest)
  }

  def findHorizontal(array: Array[GomokuVal],v: GameValue):Int = 0

  override def evaluate(board: GomokuBoard, maxVal: GameValue, minimizeVal: GameValue) = {
    val arr = board._board
    var vertical = (findVertical(arr,maxVal),findVertical(arr,minimizeVal))
    var horizontal = (findHorizontal(arr,maxVal),findHorizontal(arr,minimizeVal))

    vertical._1 - vertical._2
  }
}


