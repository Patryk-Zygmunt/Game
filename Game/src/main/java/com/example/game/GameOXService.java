package com.example.game;

import com.example.game.model.XOResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class GameOXService {
    @Autowired
    Jedis jedis;




    public Map<String,Object> play(String player)  {
        String waiting = jedis.lpop("waiting1");
        var res = new HashMap<String, Object>();
        if (waiting == null) {
            jedis.lpush("waiting1", player);
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




    public void newGame(String gameId){
        jedis.set(gameId + "#round",String.valueOf(1));
        List<Integer> newBoard = new ArrayList<>();
        IntStream.range(0,9).forEach(i->newBoard.add(0));
        saveBoardJedis(newBoard,gameId);
    }

    public void saveBoardJedis(List<Integer> board, String gameId){
        board.forEach(v->jedis.lpush(gameId,v.toString()));
    }

    public List<Integer>  getBoardJedis(String gameId){
        return jedis.lrange(gameId,0,8).stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    public void makeMove( String gameId,Field field){
        jedis.set(gameId + "#round",String.valueOf(field.getOppositeValue()));
        jedis.lset(gameId,field.mapTo1Dim(),String.valueOf(field.getValue()));
    }
    protected boolean isPlayerRound(String gameId, Field field){
        return Integer.parseInt(jedis.get(gameId + "#round")) == field.getValue();
    }

    public void makeOppositeMove( String gameId,int x, Field field){
        jedis.set(gameId + "#round",String.valueOf(field.getValue()));
        jedis.lset(gameId,x,String.valueOf(field.getOppositeValue()));
    }


    public  Integer[] flat( List<List<Integer>> board){
        return board.stream().flatMap(Collection::stream).toArray(Integer[]::new);
    }

    public boolean moveIsPossible(Field field,String gameID) {
        if(field.mapTo1Dim()<0 || field.mapTo1Dim() > 8) return  false;
        if(getBoardJedis(gameID).get(field.mapTo1Dim()) != 0) return false;
        return isPlayerRound(gameID, field);
    }

    public int checkWin(String gameId){
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> req = new HashMap<>();
        req.put("board", getBoardJedis(gameId));
        ResponseEntity<String> response
                = restTemplate.postForEntity("http://localhost:8000/xo/win", req, String.class);
        return Integer.parseInt(response.getBody());
    }




    public void makeAIMove(String gameId, Field field) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> req = new HashMap<>();
        req.put("board", getBoardJedis(gameId));
        req.put("player1", field.getOppositeValue());
        req.put("player2", field.getValue());
        ResponseEntity<String> response
                = restTemplate.postForEntity("http://localhost:8000/xo/move", req, String.class);
        int v = Integer.parseInt(response.getBody());
        makeOppositeMove(gameId,v,field);
    }
}
