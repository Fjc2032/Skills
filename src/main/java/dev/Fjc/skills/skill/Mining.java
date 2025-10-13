package dev.Fjc.skills.skill;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.player.SkillController;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public class Mining extends SkillController {

    private Skills plugin;

    private Player player;

    private double miningscore;


    //Getters
    public double getMiningscore() {
        return this.miningscore;
    }

    //Setters
    public void setMiningscore(double score) {
        this.miningscore = score;
    }

    protected boolean canGetXP(BlockBreakEvent event) {
        return event.getExpToDrop() > 0;
    }

}
