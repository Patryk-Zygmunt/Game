package com.example.cqrs.events;

import com.example.cqrs.commands.Command;

public interface EventHandler<T extends Command> {
    void handle(T command);
}
