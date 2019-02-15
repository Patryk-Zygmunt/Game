package com.example.cqrs.commandbus;

import com.example.cqrs.command.handler.AddUserHandler;
import com.example.cqrs.command.handler.CommandHandler;
import com.example.cqrs.commands.AddCommand;
import com.example.cqrs.commands.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommandBus {
    private ApplicationContext context;

    @Autowired
    public CommandBus(ApplicationContext context) {
        this.context = context;
    }

    private Map<Class<? extends Command>, Class<? extends CommandHandler>> providerMap = new HashMap<>()
    {{
        put(AddCommand.class, AddUserHandler.class);
    }};

    public <T extends Command> void execute(T command){
       CommandHandler<T> handler=  (CommandHandler<T>) context.getBean(providerMap.get(command.getClass()));
       handler.handle(command);
    }


}
