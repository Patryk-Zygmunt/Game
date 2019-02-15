package com.example.game;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {




    public boolean moveIsRight(Field field, List<List<Integer>> board) {
        if(field.getX() < 0 || field.getX() > 2 ) return  false;
        if(board.get(field.getX()).get(field.getY())!=0) return false;
        return true;
    }

    public boolean checkWin(Field field, List<List<Integer>> board){
        return  false;
    }
}
