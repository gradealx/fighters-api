package com.example.fightersapi.service;

import com.example.fightersapi.model.Fighter;
import com.example.fightersapi.repository.FighterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FighterService {

    private final FighterRepository fighterRepository;

    @Autowired
    public FighterService(FighterRepository fighterRepository) {
        this.fighterRepository = fighterRepository;
    }

    public List<Fighter> findAll() {
        return fighterRepository.findAll();
    }

    public Fighter findById(String id) {
        if (fighterRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fighter not in database");
        }
        return fighterRepository.findById(id).orElseThrow(UnsupportedOperationException::new);
    }

    public void updateExperience(Fighter fighter, boolean winner) {
        if (winner) {
            fighter.setExperience(fighter.getExperience() + 10);
            fighterRepository.save(fighter);
            return;
        }
        fighter.setExperience(fighter.getExperience() - 10);
        fighterRepository.save(fighter);
    }

    public void insert(Fighter fighter) {
        fighterRepository.insert(fighter);
    }
}
