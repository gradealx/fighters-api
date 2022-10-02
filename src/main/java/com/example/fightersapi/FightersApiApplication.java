package com.example.fightersapi;

import com.example.fightersapi.model.Fighter;
import com.example.fightersapi.repository.FighterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class FightersApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FightersApiApplication.class, args);
    }


    /**
     * Fills the database with sample values.
     */
    @Bean
    CommandLineRunner runner(FighterRepository fighterRepository) {
        return args -> {
            fighterRepository.deleteAll();

            Fighter fighter1 = new Fighter("a", "Joe", 5, false);
            fighterRepository.insert(fighter1);

            Fighter fighter2 = new Fighter("b", "Max", 10, false);
            fighterRepository.insert(fighter2);

            Fighter fighter3 = new Fighter("c", "Jack", 20, true);
            fighterRepository.insert(fighter3);

            Fighter fighter4 = new Fighter("d", "Alex", 5, false);
            fighterRepository.insert(fighter4);

            Fighter fighter5 = new Fighter("e", "Tim", 100, false);
            fighterRepository.insert(fighter5);

            Fighter fighter6 = new Fighter("f", "Tom", 60, false);
            fighterRepository.insert(fighter6);

            Fighter fighter7 = new Fighter("g", "Carl", 30, false);
            fighterRepository.insert(fighter7);


        };
    }
}
