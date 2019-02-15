package com.example.cqrs.commandbus;

import com.example.cqrs.command.handler.CommandHandler;
import com.example.cqrs.commands.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class Registry {

    private Map<Class<? extends Command>, CommandProvider> providerMap = new HashMap<>();

    @Autowired
    public Registry(ApplicationContext applicationContext) {
        String[] names = applicationContext.getBeanNamesForType(CommandHandler.class);
        for (String name : names) {
         ///   register(applicationContext, name);
        }
    }


}
