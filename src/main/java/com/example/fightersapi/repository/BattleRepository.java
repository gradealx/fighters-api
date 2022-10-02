package com.example.fightersapi.repository;

import com.example.fightersapi.model.Battle;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BattleRepository extends MongoRepository<Battle, String> {
}
