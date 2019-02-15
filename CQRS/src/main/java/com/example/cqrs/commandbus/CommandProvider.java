package com.example.cqrs.commandbus;

import com.example.cqrs.command.handler.CommandHandler;
import org.springframework.context.ApplicationContext;

class CommandProvider<H extends CommandHandler<?>> {

    private final ApplicationContext applicationContext;
    private final Class<H> type;

    CommandProvider(ApplicationContext applicationContext, Class<H> type) {
        this.applicationContext = applicationContext;
        this.type = type;
    }

    public H get() {
        return applicationContext.getBean(type);
    }
}