package model;

import board.GomokuVal;

import java.util.List;

public class GomokuState {

    private List<GomokuVal> board;
    private GomokuVal player1;
    private GomokuVal player2;

    public List<GomokuVal> getBoard() {
        return board;
    }

    public void setBoard(List<GomokuVal> board) {
        this.board = board;
    }

    public GomokuVal getPlayer1() {
        return player1;
    }

    public void setPlayer1(GomokuVal player1) {
        this.player1 = player1;
    }

    public GomokuVal getPlayer2() {
        return player2;
    }

    public void setPlayer2(GomokuVal player2) {
        this.player2 = player2;
    }
    
}
