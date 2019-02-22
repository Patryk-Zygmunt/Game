package com.example.game;

import com.example.game.model.XOResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class GameController {



    @Autowired
    GameOXService gameOXService;
    @Autowired
    private SimpMessagingTemplate template;



    @MessageMapping("/move/ai/{gameId}")
    //@SendTo("/topic/ai")
    public void moveAi(@DestinationVariable String gameId,Field field)  {
     if(!gameOXService.moveIsPossible(field,gameId)) throw new BadMoveException();
        gameOXService.makeMove(gameId,field);
        int winPlayer = gameOXService.checkWin(gameId);
       XOResponse xoPlayer =  new XOResponse(gameOXService.getBoardJedis(gameId),winPlayer);
        template.convertAndSend("/topic/ai/" + gameId, ResponseEntity.ok(xoPlayer));

       if(winPlayer==0) {
           Executors.newSingleThreadExecutor().submit(() -> {
               gameOXService.makeAIMove(gameId, field);
               int winAI = gameOXService.checkWin(gameId);
               template.convertAndSend("/topic/ai/" + gameId, ResponseEntity.ok(new XOResponse(gameOXService.getBoardJedis(gameId), winAI)));
           });
       }
    }

    @MessageMapping("/move/{gameId}")
    //@SendTo("/topic/board/{gameId}")
    public void move(@DestinationVariable String gameId, @RequestBody Field field) {
        if(!gameOXService.moveIsPossible(field,gameId)) throw new BadMoveException();
            gameOXService.makeMove(gameId,field);
           int win = gameOXService.checkWin(gameId);
        template.convertAndSend("/topic/board/" + gameId,ResponseEntity.ok(new XOResponse(gameOXService.getBoardJedis(gameId),win)));
    }

    @MessageMapping("/play")
    @SendTo("/topic/play")
    public Map<String,Object> play(Map<String,Object> data) {
        if((Boolean) data.get("ai")) return gameOXService.playWithAi();
        return gameOXService.play((String)data.get("name"));

    }

//    @MessageMapping("/clear")
//    @SendTo("/topic/board")
//    public ResponseEntity<XOResponse> clearBoard() throws Exception {
//        board.clear();
//        jedis.ltrim(gameId,0,8);
//        IntStream.range(0,9).forEach(i->board.add(0));
//        saveBoardJedis(board,gameId);
//
//        return ResponseEntity.ok(XOResponse.empty());
//    }

}




