package com.example.cqrs;

import com.example.cqrs.commandbus.CommandBus;
import com.example.cqrs.commands.AddCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {


    @Autowired
    CommandBus commandBus;




    @GetMapping("/")
    public String test(){

        System.out.println("Start");
        AddCommand addCommand = new AddCommand();
        addCommand.setId(12);
        addCommand.setName("name");
        commandBus.execute(addCommand);
        return "ok";

    }
}
