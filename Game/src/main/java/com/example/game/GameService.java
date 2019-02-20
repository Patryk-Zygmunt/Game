package com.example.game;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {


    public  Integer[] flat( List<List<Integer>> board){
        List<Integer> l = board.stream().flatMap(Collection::stream).collect(Collectors.toList());
        return l.toArray(new Integer[0]);
    }

    public  int mapTo2DX( int v){
      return v % 3;
    }
    public  int mapTo2DY( int v){

        if(v<3) return 0;
        if(v<6) return 1;
         return 2;
    }


    public boolean moveIsRight(Field field, List<List<Integer>> board) {
        if(field.getX() < 0 || field.getX() > 2 ) return  false;
        if(board.get(field.getY()).get(field.getX())!=0) return false;
        return true;
    }

    public boolean checkWin(Field field, List<List<Integer>> board){
        return  false;
    }
}
