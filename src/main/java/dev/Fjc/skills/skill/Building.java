package dev.Fjc.skills.skill;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.enums.SkillSet;
import dev.Fjc.skills.enums.SubSkillSet;
import dev.Fjc.skills.player.AttributeManager;
import dev.Fjc.skills.player.SkillController;
import dev.Fjc.skills.storage.YMLDataStorage;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Represents the main Building skill.
 */
public class Building implements SkillController {

    private final Skills plugin;

    private final YMLDataStorage storage;

    private final AttributeManager attributeManager;

    public Building(Skills plugin) {
        this.plugin = plugin;
        this.storage = plugin.getStorage();
        this.attributeManager = plugin.getAttributeManager();
    }

    @Override
    public boolean enableCooldown(UUID uuid, SubSkillSet priority) {
        return false;
    }

    /**
     * After 100 score, increase the player's total block break speed.
     * @param player The player
     */
    public void setBreakSpeed(Player player) {
        double score = storage.getScore(player, SkillSet.BUILDING);
        double value = score >= 1000 ? 1 + (score/1000) : 0;

        if (value <= 0) return;
        NamespacedKey key = new NamespacedKey(plugin, "building-break-speed-boost");
        AttributeModifier modifier = new AttributeModifier(key, value, AttributeModifier.Operation.ADD_NUMBER);
        attributeManager.addAttributeModifier(player, Attribute.BLOCK_BREAK_SPEED, modifier);
    }

}
