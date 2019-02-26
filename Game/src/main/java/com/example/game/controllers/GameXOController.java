package com.example.game.controllers;

import com.example.game.BadMoveException;
import com.example.game.model.Field;
import com.example.game.model.BoardResponse;
import com.example.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.concurrent.Executors;

@Controller
public class GameXOController {



    @Autowired
    GameService gameOXService;
    @Autowired
    private SimpMessagingTemplate template;
    private final String game="/xo";



    @MessageMapping(game+"/move/ai/{gameId}")
    //@SendTo("/topic/ai")
    public void moveAi(@DestinationVariable String gameId, Field field)  {
     if(!gameOXService.moveIsPossible(field,gameId)) throw new BadMoveException();
        gameOXService.makeMove(gameId,field);
        int winPlayer = gameOXService.checkWin(gameId);
       BoardResponse xoPlayer =  new BoardResponse(gameOXService.getBoardJedis(gameId),winPlayer, gameOXService.whichSideMove(gameId));
        template.convertAndSend("/topic"+ game+"/ai/" + gameId, ResponseEntity.ok(xoPlayer));

       if(winPlayer==0) {
           Executors.newSingleThreadExecutor().submit(() -> {
               gameOXService.makeAIMove(gameId, field);
               int winAI = gameOXService.checkWin(gameId);
               template.convertAndSend("/topic"+ game+"/ai/" + gameId,
                       ResponseEntity.ok(new BoardResponse(gameOXService.getBoardJedis(gameId), winAI, gameOXService.whichSideMove(gameId))));
           });
       }
    }

    @MessageMapping(game+"/move/{gameId}")
    //@SendTo("/topic/board/{gameId}")
    public void move(@DestinationVariable String gameId, @RequestBody Field field) {
        if(!gameOXService.moveIsPossible(field,gameId)) throw new BadMoveException();
            gameOXService.makeMove(gameId,field);
           int win = gameOXService.checkWin(gameId);
        template.convertAndSend("/topic"+ game+"/board/" + gameId,
                ResponseEntity.ok(new BoardResponse(gameOXService.getBoardJedis(gameId),win, gameOXService.whichSideMove(gameId))));
    }

    @MessageMapping(game+"/play")
    @SendTo("/topic"+game+"/play")
    public Map<String,Object> play(Map<String,Object> data) {
        if((Boolean) data.get("ai")) return gameOXService.playWithAi();
        return gameOXService.play((String)data.get("name"));

    }
}




