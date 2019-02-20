package evaluation;

import board.Board;
import board.GameValue;

@FunctionalInterface
public interface EvaluationFunction<T extends Board> {
    int evaluate(T board, GameValue v, GameValue minimizeVal);
}
