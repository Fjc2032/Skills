package dev.Fjc.skills.skill.subskills;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.skill.Mining;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

public class Excavator extends Mining {


    public Excavator(@NotNull Skills plugin) {
        super(plugin);
    }

    public void vein(BlockBreakEvent event) {

    }
}
