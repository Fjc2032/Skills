package dev.Fjc.skills.skill.subskills;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.skill.Mining;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Excavator extends Mining {


    public Excavator(@NotNull Skills plugin) {
        super(plugin);
    }

    public void vein(BlockBreakEvent event) {
        if (canGetXP(event)) {
            for (Block block : veinBlocks(event.getBlock())) block.breakNaturally();
        }
    }

    private List<Block> veinBlocks(Block block) {
        List<Block> objects = new ArrayList<>(16);
        for (Block block1 : surroundingBlocks(block)) {
            if (block1.getType() == block.getType()) objects.add(block1);
            for (Block block2 : surroundingBlocks(block1)) {
                if (block2.getType() == block1.getType()) objects.add(block2);
            }
        }

        return objects;
    }
}
