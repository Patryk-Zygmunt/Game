package com.example.game.service;

import com.example.game.model.Field;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service("gomoku")
public class GomokuService extends GameService {


    private static final int gomoku_X =15, gomoku_Y=15;
    private static final int range =gomoku_X*gomoku_Y;

    public Map<String,Object> play(String player)  {
        return super.play(player,"gomoku");
    }

    private static final String GAME_URL = "http://localhost:8000/gomoku/";


    @Override
    public List<Integer> getBoardJedis(String gameId){
        return super.getBoardJedis(gameId,range);
    }

    public void newGame(String gameId){
        jedis.set(gameId + "#round",String.valueOf(1));
        List<Integer> newBoard = new ArrayList<>();
        IntStream.range(0,range).forEach(i->newBoard.add(0));
        saveBoardJedis(newBoard,gameId);
    }

    public boolean moveIsPossible(Field field, String gameID) {
        if(mapTo1Dim(field)<0 || mapTo1Dim(field) >= range) return  false;
        if(getBoardJedis(gameID).get(mapTo1Dim(field)) != 0) return false;
        return isPlayerRound(gameID, field);
    }

    public int checkWin(String gameId){
        return super.checkWin(gameId, GAME_URL + "win");
    }

    public void makeAIMove(String gameId, Field field){
        super.makeAIMove(gameId,field,GAME_URL + "move");
    }

    public int mapTo1Dim(Field field){
        return field.getY()* gomoku_X+field.getX();

    }




}
