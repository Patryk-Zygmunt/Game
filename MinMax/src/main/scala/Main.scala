import board.{GameValue, GomokuVal}
import board.GomokuVal._

object HelloWorld {
  def main(array: Array[String]): Unit = {
  var a =  Array.fill(10){(EMPTY,true)};
    //a(3)= WHITE
    a.unzip
    ///findVertical(a,WHITE)
  }



def findVertical(array: Array[GomokuVal],maxV: GameValue):Int = {
  var splited : Array[Array[GomokuVal]]= array grouped 5 toArray
  var t:Array[Int] = splited.map{ arr=>{
    arr
    arr.zipWithIndex.span{ case (v,index)=> {

      print(isPossibleToPutFive(v,arr,maxV,index))
      isPossibleToPutFive(v,arr,maxV,index)}}
  2}}
  t.max
}
private def isPossibleToPutFive(actVal:GomokuVal,arr:Array[GomokuVal],maxVal: GameValue,index:Int):Boolean= {
  print(index)
  actVal match {
    case EMPTY => false
    case `maxVal` =>
      val (l, r) = arr.toList.splitAt(index)
      val (cl, cr) = (l.takeRight(4).count(v => v == EMPTY || v == maxVal) == 4, r.take(4).count(v => v == EMPTY || v == maxVal) == 4)
      cl || cr
    case _ => false
  }
}
}