package tree;

import board.Board;
import board.TicToeBoard;
import evaluation.EvaluationFunction;
import board.GameValue;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tree {
    private List<Tree> states = new ArrayList<>();
    private Board board;
    private int depth;
    private int rate=0;

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

    public static Tree generateTree(Board board, int depthMax, GameValue maximizeVal, GameValue minimizeVal, int depth, EvaluationFunction fun){
        Tree tree = new Tree(board);
        tree.depth = depth;
        final var whichMove = depth % 2 ==0 ;
           //debilu final Comparator<Tree> comparator = whichMove ? ((t1, t2)->t1.rate - t2.rate) :(t1, t2)->t2.rate - t1.rate;
         // final Comparator<Tree> comparator = whichMove ? (Comparator.comparingInt(t1 -> t1.rate)) : (Comparator.comparingInt(t1 -> t1.rate));
        final var newDepth =  ++depth;
        final int rate = fun.evaluate(board, maximizeVal,minimizeVal);
       if(depth == depthMax || rate == Integer.MAX_VALUE  || rate == Integer.MIN_VALUE) {
            tree.rate = rate;
            tree.states = new LinkedList<>();
        }
      else{
          tree.states = (List<Tree>) board.generatePossibleStates(whichMove ? maximizeVal : minimizeVal).stream()
                  .map(b -> generateTree((Board) b, depthMax, maximizeVal, minimizeVal, newDepth, fun)).collect(Collectors.toList());

          tree.rate = whichMove ? tree.states.stream().max(Comparator.comparingInt(t1 -> t1.rate)).map(t->t.rate).orElse(111111)
                  : tree.states.stream().min(Comparator.comparingInt(t1 -> t1.rate)).map(t->t.rate).orElse(111111);
           //System.out.println("TREE:" + tree.depth +" r="+tree.rate+ " , "+ tree.states);

       }
      return  tree;
    }

    public int getMaximizedState(){
            return states.stream().max(Comparator.comparingInt(t -> t.rate)).map(t->t.getBoard().getMove()).orElse(board.getMove());
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
        return "("+depth+":"+rate+")";

    }

    public static void main(String[] args) {

        final Comparator<Tree> comparator =  ((t1, t2)->t1.rate - t2.rate);

        var tr2 = new Tree(new TicToeBoard());
        tr2.rate=2;
        var tr5 = new Tree(new TicToeBoard());
        tr5.rate = 5;
        var tr3 = new Tree(new TicToeBoard());
        tr3.rate = 3;



                System.out.print(Stream.of(tr2,tr5,tr3,tr3,tr3).max(comparator).map(i->i.rate).orElse(12));
    }


}
