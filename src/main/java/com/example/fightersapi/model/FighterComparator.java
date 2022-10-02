package com.example.fightersapi.model;

import java.util.Comparator;

/**
 * Compares the Robots by their experience property.
 * Robot with higher experience is preferred.
 */
public class FighterComparator implements Comparator<Fighter> {
    @Override
    public int compare(Fighter f1, Fighter f2) {
        return f2.getExperience().compareTo(f1.getExperience());
    }
}
