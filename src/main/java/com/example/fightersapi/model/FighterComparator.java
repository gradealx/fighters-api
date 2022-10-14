package com.example.fightersapi.model;

import java.util.Comparator;

/**
 * Compares the Fighters by their experience property.
 * Fighter with higher experience is preferred.
 */
public class FighterComparator implements Comparator<Fighter> {
    @Override
    public int compare(Fighter f1, Fighter f2) {
        return f2.getExperience().compareTo(f1.getExperience());
    }
}
