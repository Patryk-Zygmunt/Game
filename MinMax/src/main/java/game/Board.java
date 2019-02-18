package game;

import java.util.List;

public abstract  class Board<T extends GameValue> {

    public abstract Board newState(int x, T value);
    public abstract T getFieldValue(int x);
    public abstract boolean isPossibleMove(int x,  T v);
    public  abstract  List<Board> generatePossibleStates(T v);

}
