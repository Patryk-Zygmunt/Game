package com.example.game.redis;

import com.example.game.model.Field;
import org.springframework.data.repository.CrudRepository;


public interface GameRepository extends CrudRepository<Field,String> {
}
