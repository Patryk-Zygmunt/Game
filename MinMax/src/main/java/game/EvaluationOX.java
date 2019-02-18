package game;

import java.util.Arrays;

public class EvaluationOX implements EvaluationFunction<TicToeBoard> {
    @Override
    public int evaluate(TicToeBoard board, GameValue aVal) {
        var val = (OX)aVal;
       return (int) Arrays.asList(board.getBoard()).stream().filter(v->v==val).count();

    }
}
