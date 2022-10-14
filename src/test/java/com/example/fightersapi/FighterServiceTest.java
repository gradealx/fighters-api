package com.example.fightersapi;


import com.example.fightersapi.model.Fighter;
import com.example.fightersapi.repository.FighterRepository;
import com.example.fightersapi.service.FighterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = FightersApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FighterServiceTest {

    @Autowired
    private FighterRepository fighterRepository;

    @Autowired
    private FighterService fighterService;

    @BeforeEach
    public void setup() {
        fighterRepository.deleteAll();
    }

    @Test
    public void testUpdateExperience() {
        int experience = 10;
        Fighter fighter1 = new Fighter("a", "Funky Joe", experience, false);
        fighterRepository.insert(fighter1);

        Assertions.assertEquals(fighterService.findById("a").getExperience(), 10);
        fighterService.updateExperience(fighter1, true);
        Assertions.assertEquals(fighterService.findById("a").getExperience(), 20);
        fighterService.updateExperience(fighter1, false);
        Assertions.assertEquals(fighterService.findById("a").getExperience(), 10);
    }
}
