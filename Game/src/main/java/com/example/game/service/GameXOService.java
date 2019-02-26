package com.example.game.service;

import com.example.game.model.Field;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

@Primary
@Service
public class GameXOService extends  GameService {
    public Map<String,Object> play(String player)  {
            return super.play(player,"xo");
    }

    private static final String GAME_URL = "http://localhost:8000/xo/";


    @Override
    public List<Integer> getBoardJedis(String gameId){
        return super.getBoardJedis(gameId,8);
    }

    public void newGame(String gameId){
        jedis.set(gameId + "#round",String.valueOf(1));
        List<Integer> newBoard = new ArrayList<>();
        IntStream.range(0,9).forEach(i->newBoard.add(0));
        saveBoardJedis(newBoard,gameId);
    }

    public boolean moveIsPossible(Field field, String gameID) {
        if(mapTo1Dim(field)<0 || mapTo1Dim(field) > 8) return  false;
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
        return field.getY()* 3 +field.getX();

    }



}
