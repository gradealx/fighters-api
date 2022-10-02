package com.example.fightersapi.service;

import com.example.fightersapi.model.Battle;
import com.example.fightersapi.model.Fighter;
import com.example.fightersapi.repository.BattleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class BattleService {

    private final BattleRepository battleRepository;
    private final FighterService fighterService;

    @Autowired
    public BattleService(BattleRepository battleRepository, FighterService fighterService) {
        this.battleRepository = battleRepository;
        this.fighterService = fighterService;
    }

    public List<Battle> findAll() {
        return battleRepository.findAll();
    }


    /**
     * Simulates a battle.
     * Each of the passed Fighters participates in one battle.
     * The winner is randomly determined (influenced by the fighters experience)
     * The battle is then persisted.
     *
     * @param clientFighters Set of the Fighters that participate in the battle.
     */
    public void simulateBattle(Set<Fighter> clientFighters) {
        for (Fighter fighter : clientFighters) {
            Battle battle = new Battle(fighter.getId(), new Date(), winner(fighter));
            battleRepository.insert(battle);
        }
    }


    /**
     * Randomly decides if the Fighter wins or loses.
     * If (random value between 0 and 10) * experience of the fighter > 250, the fighter wins.
     *
     * @param fighter
     * @return boolean that states if the Fighter won or not.
     */
    public boolean winner(Fighter fighter) {
        if ((int) ((Math.random() * (10 - 0)) + 0) * fighter.getExperience() > 250) {
            fighterService.updateExperience(fighter, true);
            return true;
        }
        fighterService.updateExperience(fighter, false);
        return false;
    }
}
