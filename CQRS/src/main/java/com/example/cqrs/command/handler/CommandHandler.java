package com.example.cqrs.command.handler;

import com.example.cqrs.commands.Command;

public interface CommandHandler<T extends Command> {


 void handle(T command);


}
