package com.example.fightersapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Battle {
    private String fighterId;
    private Date date;
    private boolean winner;
}
