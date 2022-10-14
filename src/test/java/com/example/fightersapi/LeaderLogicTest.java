package com.example.fightersapi;

import com.example.fightersapi.model.*;
import com.example.fightersapi.repository.FighterRepository;
import com.example.fightersapi.service.FighterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = FightersApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LeaderLogicTest {

    @Autowired
    private FighterRepository fighterRepository;

    @Autowired
    private FighterService fighterService;

    @BeforeEach
    public void setup() {
        fighterRepository.deleteAll();

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
    public void testLeaderLogic() {
        Battle battle1 = new Battle("a", new Date(), true);
        Battle battle2  = new Battle("a", new Date(), true);
        Battle battle3  = new Battle("b", new Date(), true);
        Battle battle4  = new Battle("c", new Date(), true);
        Battle battle5  = new Battle("d", new Date(), false);

        List<Battle> battles = new ArrayList<>();
        battles.add(battle1);
        battles.add(battle2);
        battles.add(battle3);
        battles.add(battle4);
        battles.add(battle5);

        LeaderCalculator leaderCalculator = new LeaderCalculator(battles);

        LeaderBoard leaderBoard = new LeaderBoard(leaderCalculator, fighterService);

        for (Leader leader : leaderBoard.getLeaders()) {

            // Funky Joe hast 2 wins, so he should be on first place
            if (leader.getName().equals("Funky Joe")) {
                Assertions.assertEquals(leader.getPlace(), 1);
            }

            // Terminator has 1 win, so he should be on second place
            if (leader.getName().equals("Terminator")) {
                Assertions.assertEquals(leader.getPlace(), 2);
            }

            // R2D2 has 1 win, so he should be on second place
            if (leader.getName().equals("R2D2")) {
                Assertions.assertEquals(leader.getPlace(), 2);
            }

            // WALL-E has no wins, so he should be on 4th place
            if (leader.getName().equals("WALL-E")) {
                Assertions.assertEquals(leader.getPlace(), 4);
            }
        }
    }
}
