package com.example.game.redis;

import com.example.game.Field;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface GameRepository extends CrudRepository<Field,String> {
}
