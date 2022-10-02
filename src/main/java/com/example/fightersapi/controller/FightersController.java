package com.example.fightersapi.controller;

import com.example.fightersapi.model.Fighter;
import com.example.fightersapi.service.FighterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class FightersController {

    private final FighterService fighterService;

    @Autowired
    public FightersController(FighterService fighterService) {
        this.fighterService = fighterService;
    }


    /**
     * @return a List of all available fighters
     */
    @GetMapping("/fighters")
    public List<Fighter> findAll() {
        return fighterService.findAll();
    }


    /**
     * @param fighterId the Id of the fighter that is requested.
     * @return the Fighter object that is requested.
     * @throws ResponseStatusException if the Id is not in the database.
     */
    @GetMapping("/fighters/{fighterId}")
    public Fighter findById(@PathVariable String fighterId) {
        return fighterService.findById(fighterId);
    }
}
