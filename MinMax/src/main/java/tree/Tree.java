package tree;

import game.Board;
import game.EvaluationFunction;
import game.GameValue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Tree {
    private List<Tree> states = new ArrayList<>();
    private Board board;
    private int depth;
    private int rate;

    public Tree(List<Tree> states) {
        this.states = states;
    }

    public Tree(Board board, List<Board<GameValue>> states) {
        this.states = states.stream().map(Tree::new).collect(Collectors.toList());
        this.board = board;
    }

    public Tree(Board board) {
        this.board = board;
    }

    public static Tree generateTree(Board board, int depthMax, GameValue val1, GameValue val2, int depth, EvaluationFunction fun){
        Tree tree = new Tree(board);
        tree.depth = depth;
        final var whichMove = depth % 2 == 0 ;
        final Comparator<Tree> comparator = whichMove ? ((t1, t2)->t1.rate - t2.rate) :(t1, t2)->t2.rate - t1.rate;
       final var newDepth =  ++depth;
      if(depth < depthMax) {
          tree.states = (List<Tree>) board.generatePossibleStates(whichMove ? val1 : val2).stream()
                  .map(b -> generateTree((Board) b, depthMax, val1, val2, newDepth, fun)).collect(Collectors.toList());
          tree.rate = tree.states.stream().max(comparator).get().rate;
      }
      else {
          tree.rate = fun.evaluate(board, val1);
          tree.states = new LinkedList<>();
      }
      return  tree;
    }


    public List<Tree> getStates() {
        return states;
    }

    public void setStates(List<Tree> states) {
        this.states = states;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return "("+depth+":"+rate+")"
                + states.stream().map(Tree::toString).reduce( "",String::concat) +  (states.size()>0 ?System.lineSeparator() :"");

    }
}
