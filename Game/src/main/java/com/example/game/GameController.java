package com.example.game;

import com.example.game.redis.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class GameController {
    List<List<Integer>> board = new ArrayList<>(new ArrayList<>());

    {
        clear();
    }

    @Autowired
    GameService gameService;

    @Autowired
    Jedis jedis;
   private void clear() {
        this.board.clear();
        IntStream.range(0,3).forEach(nn->board.add(new ArrayList<>()));
        board.forEach(l->IntStream.generate(()->0).limit(3).forEach(l::add));
    }

    @MessageMapping("/move/ai")
    @SendTo("/topic/ai")
    public ResponseEntity<Map<String, List<List<Integer>>>> move(Field field) throws Exception {
        if(!gameService.moveIsRight(field,board)) throw new BadMoveException();
       board.get(field.getY()).set(field.getX(),field.getValue());
        RestTemplate restTemplate = new RestTemplate();
Map<String,Object> req = new HashMap<>();
                req.put("board",gameService.flat(board));
                req.put("player1",field.getOppositeValue());
                req.put("player2",field.getValue());
        System.out.println(board);
        ResponseEntity<String> response
                = restTemplate.postForEntity("http://localhost:8000/xo/move",req, String.class);
       int v = Integer.parseInt(response.getBody());
        System.err.println(response);
        board.get(gameService.mapTo2DY(v)).set(gameService.mapTo2DX(v),field.getOppositeValue());
        return ResponseEntity.ok(Collections.singletonMap("board",board));
    }


    @MessageMapping("/move")
    @SendTo("/topic/board")
    public ResponseEntity<Map<String, List<List<Integer>>>> moveAi(Field field) throws Exception {
        if(gameService.moveIsRight(field,board))
            board.get(field.getY()).set(field.getX(),field.getValue());
        return ResponseEntity.ok(Collections.singletonMap("board",board));
    }

    @MessageMapping("/play")
    @SendTo("/topic/play")
    public Map<String,Object> play() throws Exception {
        String waiting =jedis.lpop("waiting1");
        var res= new HashMap<String,Object>();
    if(waiting==null){
        jedis.lpush("waiting1","player");
        res.put("gameId","wait");
        res.put("side",1);
        return res;
    }else{
        String uuid = UUID.randomUUID().toString();
        jedis.lpush("games",uuid);
        res.put("gameId",uuid);
        res.put("side",2);
        return res;
    }
    }

    @MessageMapping("/clear")
    @SendTo("/topic/board")
    public ResponseEntity<Map<String, List<List<Integer>>>> clearBoard() throws Exception {
        clear();
        return ResponseEntity.ok(Collections.singletonMap("board",board));
    }

//
//    @GetMapping("/g/{id}")
//    public ResponseEntity geee(@PathVariable  String id){
//       jedis.lpush(id,board.get(0).stream().map().collect(Collectors.toList()))
//      return new ResponseEntity( jedis.set(id,new Field()), HttpStatus.ACCEPTED);
//    }

    @GetMapping("/f/{id}")
    public ResponseEntity geees(@PathVariable  String id){
        return new ResponseEntity( jedis.get(id), HttpStatus.ACCEPTED);
    }




}




