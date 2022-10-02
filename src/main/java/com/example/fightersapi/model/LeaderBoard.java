package com.example.fightersapi.model;

import com.example.fightersapi.service.FighterService;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class LeaderBoard {
    private final List<Leader> leaders;
    private final FighterService fighterService;

    /**
     * Constructs a LeaderBoard. Has a List of all fighters names and their current place in the ranking.
     *
     * @param leaderCalculator instance that performs the calculation
     * @param fighterService     service to fetch fighters
     */
    public LeaderBoard(LeaderCalculator leaderCalculator, FighterService fighterService) {
        this.fighterService = fighterService;
        this.leaders = new ArrayList<>();

        // iterate over fighterNumberWinsMap, to get the fighter name, and its corresponding place in the ranking
        for (Map.Entry<String, Integer> entry : leaderCalculator.getFighterNumberWinsMap().entrySet()) {
            Leader leader = new Leader(fighterService.findById(entry.getKey()).getName(), leaderCalculator.getWinsPlaceMap().get(entry.getValue()));
            this.leaders.add(leader);
        }
    }
}
