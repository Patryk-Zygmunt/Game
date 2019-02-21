package com.example.game.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class XOResponse {
    List<List<Integer>> board = new ArrayList<>(new ArrayList<>());


    public XOResponse(List<Integer> board) {
    this.clear();
    IntStream.range(0,9).forEach(index->{
            this.board.get(mapTo2DY(index)).set(mapTo2DX(index),board.get(index));
        });
    }

    private XOResponse() {
    }

    private  void clear( ) {
        board.clear();
        IntStream.range(0,3).forEach(nn->board.add(new ArrayList<>()));
        board.forEach(l->IntStream.generate(()->0).limit(3).forEach(l::add));
    }

    public static XOResponse empty(){
        XOResponse xo = new XOResponse();
        xo.clear();
        return xo;
    }

    private  int mapTo2DY( int v){
        if(v<3) return 0;
        if(v<6) return 1;
        return 2;
    }

    private   int mapTo2DX( int v){
        return v % 3;
    }

    public List<List<Integer>> getBoard() {
        return board;
    }

    public void setBoard(List<List<Integer>> board) {
        this.board = board;
    }
}
