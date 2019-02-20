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




}
