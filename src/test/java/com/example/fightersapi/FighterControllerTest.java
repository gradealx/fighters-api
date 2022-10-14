package com.example.fightersapi;

import com.example.fightersapi.controller.FightersController;
import com.example.fightersapi.model.Fighter;
import com.example.fightersapi.repository.BattleRepository;
import com.example.fightersapi.repository.FighterRepository;
import com.example.fightersapi.service.FighterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = FightersApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FighterControllerTest {

    @Autowired
    private FighterRepository fighterRepository;

    @Autowired
    private BattleRepository battleRepository;

    @Autowired
    private FighterService fighterService;

    private FightersController fightersController;

    @BeforeEach
    public void setup() {
        fighterRepository.deleteAll();
        battleRepository.deleteAll();
        fightersController = new FightersController(fighterService);
    }

    @Test
    public void testFindById() {
        String id = "a";
        Fighter fighter1 = new Fighter(id, "Funky Joe", 5, false);
        fighterRepository.insert(fighter1);

        Fighter fetchedFighter = fightersController.findById(id);
        Assertions.assertEquals(fighter1, fetchedFighter);
    }

    @Test
    public void testFindByIdFail() {
        String id = "a";

        // fighter not available
        Assertions.assertThrows(ResponseStatusException.class,
                () -> {
                    fightersController.findById(id);
                });
    }

    @Test
    public void testFindAll() {
        List<Fighter> fighterList = new ArrayList<>();
        Fighter fighter1 = new Fighter("a", "Funky Joe", 5, false);
        Fighter fighter2 = new Fighter("b", "Terminator", 20, false);
        Fighter fighter3 = new Fighter("c", "R2D2", 10, true);
        Fighter fighter4 = new Fighter("d", "WALL-E", 8, false);

        fighterList.add(fighter1);
        fighterList.add(fighter2);
        fighterList.add(fighter3);
        fighterList.add(fighter4);

        for (Fighter fighter : fighterList) {
            fighterRepository.insert(fighter);
        }

        List<Fighter> fetchedFighters = fightersController.findAll();
        Assertions.assertEquals(fighterList, fetchedFighters);
    }
}
