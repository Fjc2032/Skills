package dev.Fjc.skills.skill.subskills;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.skill.Mining;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Represents an Excavator skill, under the main Mining skill
 * <p>
 * Abilities: <br>
 * Vein Miner - Destroy a vein of ores/selected block <br>
 * Blitz - Blast a large line streak in one direction (initial 3x1 radius)
 */
public class Excavator extends Mining {

    /**
     * A hard limit that the vein miner skill may NEVER pass. <br>
     * This should prevent the server from crashing.
     */
    protected static final int HARD_VEIN_LIMIT = 48;

    public Excavator(@NotNull Skills plugin) {
        super(plugin);
    }

    public void vein(BlockBreakEvent event) {
        if (canGetXP(event)) {
            for (Block block : veinBlocks(event.getBlock())) {
                if (!pickaxes.contains(event.getPlayer().getInventory().getItemInMainHand().getType())) continue;
                block.breakNaturally(event.getPlayer().getInventory().getItemInMainHand());
            }
        }
    }

    public void blitz(BlockBreakEvent event) {
        for (Block block : blitzBlocks(event.getBlock())) block.breakNaturally();
    }

    /**
     * Gets a list of blocks that are neighbours to each other and are of the same type.
     * @param block The initial block
     * @return A list of blocks derived from the initial block.
     */
    private List<Block> veinBlocks(Block block) {
        List<Block> objects = new ArrayList<>();
        Queue<Block> queue = new LinkedList<>();

        Material type = block.getType();
        objects.add(block);
        queue.add(block);

        while (!queue.isEmpty() && objects.size() <= HARD_VEIN_LIMIT) {
            Block current = queue.poll();

            //Check all derives and add them
            for (Block db : surroundingBlocks(current)) {
                if (!objects.contains(db) && db.getType() == type) {
                    objects.add(db);
                    queue.add(db);
                }
            }
        }

        return objects;
    }

    /**
     * Gets a list of blitzed blocks.
     * @param block The initial block
     * @return A list of blitzed blocks.
     */
    private List<Block> blitzBlocks(@NotNull Block block) {
        World world = block.getWorld();
        double yPrime = block.getLocation().getY() - 1;
        double yDoublePrime = block.getLocation().getY() + 1;

        List<Block> objects = new ArrayList<>();

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
