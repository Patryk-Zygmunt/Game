package model;

import board.OX;

import java.util.List;

public class XOState {
    private List<OX> board;
    private OX player1;
    private OX player2;

    public List<OX> getBoard() {
        return board;
    }

    public void setBoard(List<OX> board) {
        this.board = board;
    }

    public OX getPlayer1() {
        return player1;
    }

    public void setPlayer1(OX player1) {
        this.player1 = player1;
    }

    public OX getPlayer2() {
        return player2;
    }

    public void setPlayer2(OX player2) {
        this.player2 = player2;
    }
}
