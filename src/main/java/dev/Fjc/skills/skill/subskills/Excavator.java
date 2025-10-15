package dev.Fjc.skills.skill.subskills;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.skill.Mining;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an Excavator skill, under the main Mining skill
 * <p>
 * Abilities: <br>
 * Vein Miner - Destroy a vein of ores/selected block <br>
 * Blitz - Blast a large line streak in one direction (initial 3x1 radius)
 */
public class Excavator extends Mining {


    public Excavator(@NotNull Skills plugin) {
        super(plugin);
    }

    public void vein(BlockBreakEvent event) {
        if (canGetXP(event)) {
            for (Block block : veinBlocks(event.getBlock())) {
                if (!pickaxes.contains(event.getPlayer().getInventory().getItemInMainHand().getType())) continue;
                block.breakNaturally();
            }
        }
    }

    public void blitz(BlockBreakEvent event) {
        for (Block block : blitzBlocks(event.getBlock())) block.breakNaturally();
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

    private List<Block> blitzBlocks(Block block) {
        World world = block.getWorld();
        double yPrime = block.getLocation().getY() - 1;
        double yDoublePrime = block.getLocation().getY() + 1;

        List<Block> objects = new ArrayList<>(Byte.MAX_VALUE);

        Location first = new Location(world, block.getX(), yPrime, block.getZ());
        Block block1 = first.getBlock();
        objects.add(block1);

        Location second = new Location(world, block.getX(), yDoublePrime, block.getZ());
        Block block2 = second.getBlock();
        objects.add(block2);

        for (int i = 0; i < 24; i++) {
            Block block3 = new Location(world, block.getX() + i, yPrime, block.getZ()).getBlock();
            objects.add(block3);

            Block block4 = new Location(world, block.getX(), yDoublePrime, block.getZ() + i).getBlock();
            objects.add(block4);
        }
        return objects.stream()
                .filter(Objects::nonNull)
                .toList();
    }
}
