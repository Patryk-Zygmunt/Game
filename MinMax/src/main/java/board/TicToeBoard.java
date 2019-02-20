package board;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TicToeBoard extends Board<OX> {
    private  OX[] board = {OX.EMPTY, OX.EMPTY, OX.EMPTY,OX.EMPTY, OX.EMPTY, OX.EMPTY,OX.EMPTY, OX.EMPTY, OX.EMPTY};
    private Integer move=-1;

    public TicToeBoard() {
    }

    public OX[] getBoard() {
        return board;
    }

    public TicToeBoard(OX[] board) {
        this.board = board;
    }

    @Override
    public Board newState(int x,  OX v) {
       TicToeBoard newBoard = new TicToeBoard(Arrays.copyOf(this.board,9));
        newBoard.getBoard()[x] = v;
        newBoard.move = x;
        return newBoard;
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

    public int getMove() {
        return move;
    }
}
