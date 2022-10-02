package com.example.fightersapi.model;

import lombok.Data;

import java.util.*;

/**
 * Datastructures to represent the ranking of the fighters.
 * Maps the number of wins of a fighter to the corresponding place in the ranking.
 */
@Data
public class LeaderCalculator {

    private Map<String, Integer> fighterNumberWinsMap = new HashMap<>();
    private Map<Integer, Integer> winsPlaceMap = new HashMap<>();


    /**
     * Creates the LeaderCalculator.
     * fighterNumberWinsMap: maps the fighterId to the number of wins of the fighter.
     * winsPlaceMap: maps each possible number of wins to a certain place in the ranking.
     *
     * @param battles all battles that are persisted in the database.
     */
    public LeaderCalculator(List<Battle> battles) {

        // construct fighterNumberWinsMap: if fighterId does not yet exist create a new key in the map with the fighterId. Adjust number of wins.
        for (Battle battle : battles) {
            if (!fighterNumberWinsMap.containsKey(battle.getFighterId())) {
                fighterNumberWinsMap.put(battle.getFighterId(), battle.isWinner() ? 1 : 0);
            } else if (battle.isWinner()) {
                Integer previousWins = fighterNumberWinsMap.get(battle.getFighterId());
                fighterNumberWinsMap.put(battle.getFighterId(), previousWins + 1);
            }
        }

        // construct numberWinsList: a list of Integers that state the number of wins of each fighter, sorted from highest to lowest e.g. 3, 3, 2, 2, 1, 1, 1, 1, ...
        List<Integer> numberWinsList = new ArrayList<>(fighterNumberWinsMap.values());
        Collections.sort(numberWinsList, Collections.reverseOrder());

        // construct winsPlaceMap: iterate over numberWinsList and map the highest number of wins to place 1, second highest to place 2 ect.
        for (int i = 0; i < numberWinsList.size(); i++) {
            if (!winsPlaceMap.containsKey(numberWinsList.get(i))) {
                winsPlaceMap.put(numberWinsList.get(i), i + 1);
            }
        }
    }
}
