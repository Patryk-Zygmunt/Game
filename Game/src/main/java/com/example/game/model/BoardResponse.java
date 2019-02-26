package com.example.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class BoardResponse {
    List<List<Integer>> board = new ArrayList<>(new ArrayList<>());
 private int win=0;
 @JsonIgnore
private int BOARD_RANGE =9, BOARD_X =3,BOARD_Y=3;
 private int move=0;

    public BoardResponse(List<Integer> board) {
    this.clear();
    IntStream.range(0,BOARD_RANGE).forEach(index->{
            this.board.get(mapTo2DY(index)).set(mapTo2DX(index),board.get(index));
        });
    }

    private BoardResponse() {
    }

    public BoardResponse(List<Integer> board, int win) {
        this.clear();
        IntStream.range(0,BOARD_RANGE).forEach(index->{
            this.board.get(mapTo2DY(index)).set(mapTo2DX(index),board.get(index));
        });
        this.win= win;
    }

    public BoardResponse(List<Integer> board, int win, int move) {
            this(board,win);
            this.move = move;
    }

    public BoardResponse(List<Integer> board, int win, int move, int x, int y) {
        this(board,win);
        this.move = move;
        this.BOARD_X =x;
        this.BOARD_Y =y;
        this.BOARD_RANGE =x*y-1;
    }

    public BoardResponse(List<Integer> board, int win, int move, int x) {
        this.win= win;
        this.move = move;
        this.BOARD_X =x;
        this.BOARD_Y =x;
        this.BOARD_RANGE =x*x;
        this.clear();
        IntStream.range(0,BOARD_RANGE).forEach(index->{
            this.board.get(mapTo2DY(index)).set(mapTo2DX(index),board.get(index));
        });

    }

    private  void clear( ) {
        board.clear();
        IntStream.range(0,BOARD_Y).forEach(nn->board.add(new ArrayList<>()));
        board.forEach(l->IntStream.generate(()->0).limit(BOARD_X).forEach(l::add));
    }

    public static BoardResponse empty(){
        BoardResponse xo = new BoardResponse();
        xo.clear();
        return xo;
    }

    private  int mapTo2DY( int v){
        return  v/BOARD_Y;
    }

    private   int mapTo2DX( int v){
        return v % BOARD_X;
    }

    public List<List<Integer>> getBoard() {
        return board;
    }

    public void setBoard(List<List<Integer>> board) {
        this.board = board;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }
}
