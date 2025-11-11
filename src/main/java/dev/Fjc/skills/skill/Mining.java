package dev.Fjc.skills.skill;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.enums.SubSkillSet;
import dev.Fjc.skills.player.SkillController;
import dev.Fjc.skills.skill.subskills.Excavator;
import dev.Fjc.skills.skill.subskills.ExplosivesTech;
import dev.Fjc.skills.skill.subskills.Spelunker;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents the main Mining skill.
 */
public class Mining implements SkillController {

    //Local constructors. These will be passed onto listeners for subskill use.
    public static Spelunker spelunker;
    public static ExplosivesTech explosivesTech;
    public static Excavator excavator;

    public Mining(@NotNull Skills plugin) {

    }

    public static void factory(Skills plugin) {
        spelunker = new Spelunker(plugin);
        explosivesTech = new ExplosivesTech(plugin);
        excavator = new Excavator(plugin);
    }

    @Override
    public boolean enableCooldown(UUID uuid, SubSkillSet priority) {
        return false;
    }

    protected boolean canGetXP(BlockBreakEvent event) {
        return event.getExpToDrop() > 0;
    }

    /**
     * Gets the surrounding blocks of the block specified.
     * @param block The block
     * @return A list of all blocks that match the conditions
     */
    protected List<Block> surroundingBlocks(Block block) {
        World world = block.getWorld();
        Location location = block.getLocation();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        List<Location> objects = List.of(
                new Location(world, x -1, y, z),
                new Location(world, x, y - 1, z),
                new Location(world, x, y, z -1),
                new Location(world, x + 1, y, z),
                new Location(world, x, y + 1, z),
                new Location(world, x, y, z + 1)
        );
        List<Block> blocks = new ArrayList<>(6);

        for (Location targets : objects) {
            Block block1 = targets.getBlock();
            blocks.add(block1);
        }

        return blocks;
    }

    /**
     * Unmodifiable list of all pickaxes
     */
    protected List<Material> pickaxes = List.of(
            Material.WOODEN_PICKAXE,
            Material.STONE_PICKAXE,
            Material.IRON_PICKAXE,
            Material.GOLDEN_PICKAXE,
            Material.DIAMOND_PICKAXE,
            Material.NETHERITE_PICKAXE
    );
}
