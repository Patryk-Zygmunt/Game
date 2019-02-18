package game;

@FunctionalInterface
public interface EvaluationFunction<T extends Board> {
    int evaluate(T board, GameValue v);
}
