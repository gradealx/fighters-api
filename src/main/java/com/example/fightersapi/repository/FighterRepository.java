package com.example.fightersapi.repository;

import com.example.fightersapi.model.Fighter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FighterRepository extends MongoRepository<Fighter, String> {
}
