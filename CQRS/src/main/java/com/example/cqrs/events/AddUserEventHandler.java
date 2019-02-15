package com.example.cqrs.events;

import com.example.cqrs.UserEntity;
import com.example.cqrs.commands.AddCommand;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class AddUserEventHandler implements EventHandler<AddCommand> {



    @Async
    @EventListener
    public void handle(AddCommand command){
        System.out.println("persist Add");
    }


}
