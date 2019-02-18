import game.*;
import tree.Tree;

public class Game {

    Tree tree;

   public int nextMove(Board board, GameValue val){
     // tree = new Tree(board,board.generatePossibleStates(val));
    tree = Tree.generateTree(new TicToeBoard(),3, OX.O, OX.X,0,new EvaluationOX());

    return 1;
   }





}
