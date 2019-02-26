package com.example.game.service;

import com.example.game.model.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.stream.Collectors;

@Service
public abstract class GameService {
    @Autowired
    Jedis jedis;


    protected Map<String,Object> play(String player, String game)  {
        String waiting = jedis.lpop("waiting1"+game);
        var res = new HashMap<String, Object>();
        if (waiting == null) {
            jedis.lpush("waiting1" + game, player);
            res.put("gameId", "wait");
            res.put("side", 1);
            return res;
        } else {
            String uuid = UUID.randomUUID().toString();
            jedis.lpush("games", uuid);
            newGame(uuid);
            res.put("gameId", uuid);
            res.put("side", 2);
            return res;
        }
    }

    public Map<String,Object> playWithAi()  {
             var res = new HashMap<String, Object>();
            String uuid = UUID.randomUUID().toString();
            jedis.lpush("games", uuid);
            newGame(uuid);
            res.put("gameId", uuid);
            res.put("side", 1);
            return res;
        }

    public abstract void newGame(String gameId);
    public abstract List<Integer>  getBoardJedis(String gameId);

    public void saveBoardJedis(List<Integer> board, String gameId){
        board.forEach(v->jedis.lpush(gameId,v.toString()));
    }

    protected List<Integer>  getBoardJedis(String gameId, int end){
        return jedis.lrange(gameId,0,end).stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    public void makeMove(String gameId, Field field){
        jedis.set(gameId + "#round",String.valueOf(field.getOppositeValue()));
        jedis.lset(gameId,mapTo1Dim(field),String.valueOf(field.getValue()));
    }
    protected boolean isPlayerRound(String gameId, Field field){
        return whichSideMove(gameId) == field.getValue();
    }

    public int whichSideMove(String gameId){
        return Integer.parseInt(jedis.get(gameId + "#round"));
    }

    public abstract boolean moveIsPossible(Field field, String gameID);


    public void makeOppositeMove( String gameId,int x, Field field){
        jedis.set(gameId + "#round",String.valueOf(field.getValue()));
        jedis.lset(gameId,x,String.valueOf(field.getOppositeValue()));
    }


    public  Integer[] flat( List<List<Integer>> board){
        return board.stream().flatMap(Collection::stream).toArray(Integer[]::new);
    }


    public abstract  int mapTo1Dim(Field field);

    public int checkWin(String gameId, String url){
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> req = new HashMap<>();
        req.put("board", getBoardJedis(gameId));
        ResponseEntity<String> response
                = restTemplate.postForEntity(url, req, String.class);
        return Integer.parseInt(response.getBody());
    }




    public void makeAIMove(String gameId, Field field, String url) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> req = new HashMap<>();
        req.put("board", getBoardJedis(gameId));
        req.put("player1", field.getOppositeValue());
        req.put("player2", field.getValue());
        ResponseEntity<String> response
                = restTemplate.postForEntity(url, req, String.class);
        int v = Integer.parseInt(response.getBody());
        makeOppositeMove(gameId,v,field);
    }

    public abstract int checkWin(String gameId);

    public abstract void makeAIMove(String gameId, Field field);

    public abstract Map<String, Object> play(String name);
}
