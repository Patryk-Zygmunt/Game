package com.example.cqrs.command.handler;

import com.example.cqrs.UserEntity;
import com.example.cqrs.UserRepository;
import com.example.cqrs.commands.AddCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class AddUserHandler implements CommandHandler<AddCommand>{
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserRepository userRepository;


    @Autowired
    public AddUserHandler(ApplicationEventPublisher applicationEventPublisher, UserRepository userRepository) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.userRepository = userRepository;
    }


    @Override
    public void handle(AddCommand command) {
        System.out.println("Walidacja: "+command.getName());
        userRepository.save(new UserEntity(command.getId(),command.getName()));
        applicationEventPublisher.publishEvent(command);
    }
}
