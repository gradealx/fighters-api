package com.example.fightersapi;

import com.example.fightersapi.controller.BattleController;
import com.example.fightersapi.model.Fighter;
import com.example.fightersapi.repository.BattleRepository;
import com.example.fightersapi.repository.FighterRepository;
import com.example.fightersapi.service.BattleService;
import com.example.fightersapi.service.FighterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest(classes = FightersApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BattleControllerTest {

    @Autowired
    private FighterRepository fighterRepository;

    @Autowired
    private BattleRepository battleRepository;

    @Autowired
    private BattleService battleService;

    @Autowired
    private FighterService fighterService;

    private BattleController battleController;

    @BeforeEach
    public void setup() {
        fighterRepository.deleteAll();
        battleRepository.deleteAll();
        battleController = new BattleController(battleService, fighterService);

        Fighter fighter1 = new Fighter("a", "Funky Joe", 5, false);
        fighterRepository.insert(fighter1);

        Fighter fighter2 = new Fighter("b", "Terminator", 20, false);
        fighterRepository.insert(fighter2);

        Fighter fighter3 = new Fighter("c", "R2D2", 10, true);
        fighterRepository.insert(fighter3);

        Fighter fighter4 = new Fighter("d", "WALL-E", 8, false);
        fighterRepository.insert(fighter4);

        Fighter fighter5 = new Fighter("e", "Optimus", 90, false);
        fighterRepository.insert(fighter5);

        Fighter fighter6 = new Fighter("f", "Omicron", 70, false);
        fighterRepository.insert(fighter6);
    }

    @Test
    public void testReceiveFightersForBattle() {

        // not enough fighters
        Assertions.assertThrows(ResponseStatusException.class,
                () -> {
                    battleController.receiveFightersForBattle(List.of(new String[]{"a", "b", "c", "d"}));
                });

        // not enough valid fighters
        Assertions.assertThrows(ResponseStatusException.class,
                () -> {
                    battleController.receiveFightersForBattle(List.of(new String[]{"a", "b", "c", "d", "e"}));
                });

        // check if battle was successfully simulated. Initially the battle repository should return an empty list. After simulating it should not be empty anymore.
        assert (battleController.getBattles().isEmpty());
        battleController.receiveFightersForBattle(List.of(new String[]{"a", "b", "c", "d", "e", "f"}));
        assert (!battleController.getBattles().isEmpty());
    }

    @Test
    public void testGetBestFighters() {
        Set<Fighter> alFighters = new HashSet<>(fighterRepository.findAll());
        battleController.getBestFighters(alFighters);

        // the Set of the best fighters should not contain the weakest fighter
        assert (!battleController.getBestFighters(alFighters).contains(fighterRepository.findById("a")));
    }

    @Test
    public void testGetLeaderBoard() {
        assert (battleController.getLeaderboard().isEmpty());
        battleController.receiveFightersForBattle(List.of(new String[]{"a", "b", "c", "d", "e", "f"}));
        assert (!battleController.getLeaderboard().isEmpty());
    }
}
