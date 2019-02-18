package game;

public class EvaluationOX implements EvaluationFunction<TicToeBoard> {
    @Override
    public int evaluate(TicToeBoard board, GameValue aVal) {
        var val = (OX)aVal;

        return 0;
    }
}
