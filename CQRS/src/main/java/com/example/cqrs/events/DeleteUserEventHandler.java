package com.example.cqrs.events;

import com.example.cqrs.commands.AddCommand;
import com.example.cqrs.commands.Command;
import com.example.cqrs.commands.DeleteUserCommand;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserEventHandler implements EventHandler<DeleteUserCommand> {

        @Async
        @EventListener
        public void handle(DeleteUserCommand command){
            System.out.println("Delete");
        }


    }

