package dev.Fjc.skills.skill.subskills;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.enums.SkillSet;
import dev.Fjc.skills.player.AttributeManager;
import dev.Fjc.skills.skill.Mining;
import dev.Fjc.skills.storage.YMLDataStorage;
import org.bukkit.NamespacedKey;
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
 * <p>
 * Unlocked after 5000 score reached.
 */
public class Spelunker extends Mining {

    private final Skills plugin;

    private final YMLDataStorage storage;

    private final AttributeManager attributeManager;

    public Spelunker(@NotNull Skills plugin) {
        super(plugin);
        this.plugin = plugin;
        this.storage = plugin.getStorage();
        this.attributeManager = plugin.getAttributeManager();
    }

    /**
     * Mining Speed Ability (passive) <br>
     * Applies additional mining efficiency to all tools.
     * <p>
     * After 5000 XP, 1 + (score/1000) - 5000
     * @param player The player to apply this to
     */
    public void setMiningSpeed(Player player) {
        double score = storage.getScore(player, SkillSet.MINING);
        double value = Math.min(score >= 5000 ? 1 + (score/1000) : 0, 1023);
        NamespacedKey key = new NamespacedKey(this.plugin, "mining-speed-boost-skill");
        AttributeModifier modifier = new AttributeModifier(key, value, AttributeModifier.Operation.ADD_NUMBER);
        attributeManager.addAttributeModifier(player, Attribute.MINING_EFFICIENCY, modifier);
    }

    /**
     * Fortune Bonus Ability (passive) <br>
     * Gives additional drops from certain blocks
     * <p>
     * After 8000 XP, 1 + (score/1500) - 8000
     * @param event The event handling this ability
     */
    public void oreBonus(BlockBreakEvent event) {
        final Block block = event.getBlock();
        final Player player = event.getPlayer();
        World world = block.getWorld();

        double score = storage.getScore(player, SkillSet.MINING);
        double value = score >= 8000 ? 1 + (score/1500) : 0;

        if (value <= 0) return;
        if (!block.getBlockData().isPreferredTool(player.getInventory().getItemInMainHand())) return;
        if (canGetXP(event)) world.dropItemNaturally(block.getLocation(), ItemStack.of(block.getType(), (int) value)); //todo better rounding, cuz this int cast is dumb
    }

    /**
     * XP Boost Ability (passive) <br>
     * Increase base XP gain from certain blocks
     * <p>
     * After 5000 XP, 1 + (score/100) - 5000
     * @param event The event handling this ability
     */
    public void xpBoost(BlockBreakEvent event) {
        Player player = event.getPlayer();
        int initial = event.getExpToDrop();

        double score = storage.getScore(player, SkillSet.MINING);
        double value = score >= 5000 ? 1 + (score/100) : 0;

        if (value <= 0) return;
        if (canGetXP(event)) event.setExpToDrop(initial + (int) value); //todo same problem here
    }

}
