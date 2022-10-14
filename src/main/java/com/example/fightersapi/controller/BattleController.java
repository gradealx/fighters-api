package com.example.fightersapi.controller;

import com.example.fightersapi.model.*;
import com.example.fightersapi.service.BattleService;
import com.example.fightersapi.service.FighterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
public class BattleController {

    private final BattleService battleService;
    private final FighterService fighterService;

    @Autowired
    public BattleController(BattleService battleService, FighterService fighterService) {
        this.battleService = battleService;
        this.fighterService = fighterService;
    }

    /**
     * This endpoint receives the teams of the client.
     * A list of fighterIds is passed to the endpoint. These are the ids of the fighters that are 'sent' to a battle.
     * At least 5 fighterIds must be passed to this endpoint. If the number of passed fighterIds is lower, a ResponseStatusException is thrown.
     * The function checks if the fighters that are passed are valid. If less than 5 fighters are valid, it throws a ResponseStatusException.
     * If more than 5 fighterIds are passed, the fighters are sorted by their experience, and the most experienced fighters are used for the battle.
     *
     * @param fighterIds list of fighterIds that are 'sent' to the battle.
     * @throws ResponseStatusException if validation of fighters fails.
     */
    @PostMapping("/battles")
    public void receiveFightersForBattle(@RequestBody List<String> fighterIds) {

        if (fighterIds.size() < 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least 5 fighters need to be provided");
        }

        // get valid fighters
        Set<Fighter> clientFighters = new HashSet<>();

        for (String s : fighterIds) {
            Fighter fetchedfighter = fighterService.findById(s);
            if (!fetchedfighter.isInjured()) {
                clientFighters.add(fetchedfighter);
            }
        }

        if (clientFighters.size() < 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Too many of the provided fighters are out of order");
        }

        //order fighters by experience
        if (clientFighters.size() > 5) {
            clientFighters = getBestFighters(clientFighters);
        }

        // simulation of battles
        battleService.simulateBattle(clientFighters);
    }


    /**
     * @return List of all previous battles.
     */
    @GetMapping("/battles")
    public List<Battle> getBattles() {
        return battleService.findAll();
    }


    /**
     * Performs a calculation to determine the current ranking.
     * Fighters are ordered by number of total wins.
     *
     * @return LeaderBoard with current state.
     */
    @GetMapping("/leaderboard")
    public List<Leader> getLeaderboard() {
        LeaderCalculator leaderCalculator = new LeaderCalculator(battleService.findAll());
        LeaderBoard leaderBoard = new LeaderBoard(leaderCalculator, fighterService);
        return leaderBoard.getLeaders();
    }


    /**
     * Orders the fighters by experience.
     *
     * @param clientFighters the fighters that should be ordered.
     * @return Set of the 5 fighters with most experience.
     */
    public Set<Fighter> getBestFighters(Set<Fighter> clientFighters) {
        ArrayList<Fighter> strongestFighters = new ArrayList<>(clientFighters);
        Collections.sort(strongestFighters, new FighterComparator());
        return new HashSet<>(strongestFighters.subList(0, 5));
    }
}
