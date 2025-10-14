package dev.Fjc.skills.player;

import dev.Fjc.skills.Skills;
import org.bukkit.entity.Player;

/**
 * Represents all skills.
 */
public class SkillController {

    private Skills plugin;

    private Player player;

    private double totalscore;

    //Getters
    public double getTotalscore() {
        return totalscore;
    }

    //Setters

    public void setTotalscore(double totalscore) {
        this.totalscore = totalscore;
    }
}
