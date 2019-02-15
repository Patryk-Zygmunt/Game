package com.example.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Controller
public class GameController {
    List<List<Integer>> board = new ArrayList<>(new ArrayList<>());

    {
        clear();
    }

    @Autowired
    GameService gameService;
   private void clear() {
        this.board.clear();
        IntStream.range(0,3).forEach(nn->board.add(new ArrayList<>()));
        board.forEach(l->IntStream.generate(()->0).limit(3).forEach(l::add));
    }

    @MessageMapping("/play")
    @SendTo("/topic/board")
    public ResponseEntity<Map<String, List<List<Integer>>>> greeting(Field field) throws Exception {
        if(gameService.moveIsRight(field,board))
       board.get(field.getY()).set(field.getX(),field.getValue());
       return ResponseEntity.ok(Collections.singletonMap("board",board));
    }

    @MessageMapping("/clear")
    @SendTo("/topic/board")
    public ResponseEntity<Map<String, List<List<Integer>>>> clearBoard() throws Exception {
        clear();
        return ResponseEntity.ok(Collections.singletonMap("board",board));
    }




}




