package dev.Fjc.skills.skill.subskills;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.skill.Mining;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the Spelunker skill, under the main skill Mining.
 * <p>
 * Abilities: <br>
 * Mining Speed - Mining speed increases as the score of this skill increases. Stacks with base mining skill. <br>
 * Fortune Bonus - Gain additional drops from mining blocks that provide XP. Usually ores. <br>
 * XP Boost - Increases the amount of base XP from blocks that provide XP.
 */
public class Spelunker extends Mining {

    public Spelunker(@NotNull Skills plugin) {
        super(plugin);
    }

    /**
     * Mining Speed Ability (passive) <br>
     * Applies additional mining efficiency to all tools.
     * @param player The player to apply this to
     * @param modifier The amount to adjust by
     */
    public void setMiningSpeed(Player player, AttributeModifier modifier) {
        player.getAttribute(Attribute.MINING_EFFICIENCY).addModifier(modifier);
    }

    /**
     * Fortune Bonus Ability (passive) <br>
     * Gives additional drops from certain blocks
     * @param event The event handling this ability
     */
    public void oreBonus(BlockBreakEvent event) {
        final Block block = event.getBlock();
        final Player player = event.getPlayer();
        World world = block.getWorld();

        if (!block.getBlockData().isPreferredTool(player.getInventory().getItemInMainHand())) return;
        if (canGetXP(event)) world.dropItemNaturally(block.getLocation(), ItemStack.of(block.getType(), (int) getMiningscore(player)));
    }

    /**
     * XP Boost Ability (passive) <br>
     * Increase base XP gain from certain blocks
     * @param event The event handling this ability
     */
    public void xpBoost(BlockBreakEvent event) {
        Player player = event.getPlayer();
        int initial = event.getExpToDrop();

        if (canGetXP(event)) event.setExpToDrop(initial + (int) (getMiningscore(player) / 10));
    }

}
