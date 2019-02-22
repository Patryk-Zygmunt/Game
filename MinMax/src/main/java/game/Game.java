package game;

import board.*;
import evaluation.EvaluationFunction;
import tree.Tree;

public abstract class  Game {



   protected int nextMove(Board board, GameValue maximizedVal, GameValue minimizedVal, int depthMax, EvaluationFunction fun){
       System.out.println("WAITING");
    Tree tree = Tree.generateTree(board,depthMax, maximizedVal, minimizedVal,0,fun);
       System.out.println("response");
    return tree.getMaximizedState();
   }

     public abstract int nextMove(Board board, GameValue maximizedVal, GameValue minimizedVal);

   public int checkWin(Board board, GameValue maximizedVal, GameValue minimizedVal, EvaluationFunction fun){
       int val  = fun.evaluate(board,maximizedVal,minimizedVal);
       if(val==Integer.MAX_VALUE) return maximizedVal.getVal();
       if(val==Integer.MIN_VALUE) return minimizedVal.getVal();
       if(board.endGame()) return -1;
       return 0;
   }



}
