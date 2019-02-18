package game;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TicToeBoard extends Board<OX> {
    private  OX[] board = {OX.EMPTY, OX.EMPTY, OX.EMPTY,OX.EMPTY, OX.EMPTY, OX.EMPTY,OX.EMPTY, OX.EMPTY, OX.EMPTY};

    public TicToeBoard() {
    }

    public OX[] getBoard() {
        return board;
    }

    private TicToeBoard(OX[] board) {
        this.board = board;
    }

    @Override
    public Board newState(int x,  OX v) {
        this.board[x] = v;
        return new TicToeBoard(this.board);
    }

    @Override
    public OX getFieldValue(int x) {
        return this.board[x];
    }

   public boolean isPossibleMove(int x,  OX v){
        return board[x] == OX.EMPTY;
    }

    @Override
    public List<Board> generatePossibleStates(OX v) {
        return IntStream.range(0, board.length).filter(i->isPossibleMove(i,OX.EMPTY))
                .mapToObj(index->newState(index,v)).collect(Collectors.toList());
    }
}
