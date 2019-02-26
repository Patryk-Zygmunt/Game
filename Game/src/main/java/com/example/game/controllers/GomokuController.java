package com.example.game.controllers;

import com.example.game.BadMoveException;
import com.example.game.model.Field;
import com.example.game.model.BoardResponse;
import com.example.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.concurrent.Executors;
//TODO parametrized Controlller synchoronize przy waiting
@Controller
public class GomokuController {

    @Autowired
    @Qualifier("gomoku")
    GameService gomokuService;
    @Autowired
    private SimpMessagingTemplate template;
    private final String game="/gomoku";
    private final int BOARD=15;




    @MessageMapping(game+"/move/ai/{gameId}")
    //@SendTo("/topic/ai")
    public void moveAi(@DestinationVariable String gameId, Field field)  {
        if(!gomokuService.moveIsPossible(field,gameId)) throw new BadMoveException();
        gomokuService.makeMove(gameId,field);
        int winPlayer = gomokuService.checkWin(gameId);
        BoardResponse xoPlayer =  new BoardResponse(gomokuService.getBoardJedis(gameId),winPlayer, gomokuService.whichSideMove(gameId),BOARD);
        template.convertAndSend("/topic"+ game+"/ai/" + gameId, ResponseEntity.ok(xoPlayer));

        if(winPlayer==0) {
            Executors.newSingleThreadExecutor().submit(() -> {
                gomokuService.makeAIMove(gameId, field);
                int winAI = gomokuService.checkWin(gameId);
                template.convertAndSend("/topic"+ game+"/ai/" + gameId,
                        ResponseEntity.ok(new BoardResponse(gomokuService.getBoardJedis(gameId), winAI, gomokuService.whichSideMove(gameId),BOARD)));
            });
        }
    }

    @MessageMapping(game+"/move/{gameId}")
    //@SendTo("/topic/board/{gameId}")
    public void move(@DestinationVariable String gameId, @RequestBody Field field) {
        if(!gomokuService.moveIsPossible(field,gameId)) throw new BadMoveException();
        gomokuService.makeMove(gameId,field);
        int win = gomokuService.checkWin(gameId);
        template.convertAndSend("/topic"+ game+"/board/" + gameId,
                ResponseEntity.ok(new BoardResponse(gomokuService.getBoardJedis(gameId),win, gomokuService.whichSideMove(gameId),BOARD)));
    }

    @MessageMapping(game+"/play")
    @SendTo("/topic"+game+"/play")
    public Map<String,Object> play(Map<String,Object> data) {
        if((Boolean) data.get("ai")) return gomokuService.playWithAi();
        return gomokuService.play((String)data.get("name"));

    }
}
