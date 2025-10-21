package dev.Fjc.skills.skill;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.enums.SkillSet;
import dev.Fjc.skills.player.AttributeManager;
import dev.Fjc.skills.player.SkillController;
import dev.Fjc.skills.storage.YMLDataStorage;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Represents the main Guard skill.
 */
public class Guard implements SkillController {

    private final YMLDataStorage storage;

    private final AttributeManager attributeManager;

    /**
     * Defense reduces the amount of damage taken from blunt hits. <br>
     * For example, with a defense of 0.07 (7%), you take up to 7% less damage from a blunt hit. <br>
     * Keep in mind that the guaranteed damage reduction is not the cap, only that it COULD be the cap. <br>
     * Also, any extra defense (values over 100%) get reimbursed as an attribute.
     */
    static double defense;

    public Guard(Skills plugin) {
        this.storage = plugin.getStorage();
        this.attributeManager = plugin.getAttributeManager();
    }

    public void reduceDamageTaken(Event event) {
        if (!(event instanceof EntityDamageEvent damageEvent)) return;
        if (!(damageEvent.getEntity() instanceof Player player)) return;

        double score = storage.getScore(player, SkillSet.GUARD);
        double value = score >= 1000 ? 1 + (score / 100) : 0;

        if (value <= 0) return;
        double damage = damageEvent.getDamage();
        double newDamage = damage - value;
        if (newDamage <= 0) newDamage = 1;
        damageEvent.setDamage(newDamage);
    }

    /**
     * Calculates the defense value based on the player's Guard skill score.
     * @param pluginBase The plugin to reference, since this is a static method.
     * @param player The player
     * @return The defense percentage, as a double
     */
    public static double getDefense(Skills pluginBase, Player player) {
        YMLDataStorage storage1 = new YMLDataStorage(pluginBase);
        double score = storage1.getScore(player, SkillSet.GUARD);
        if (score == 0) return 0;
        defense = score / 1000;
        return defense;
    }

    public static void setDefense(double defense) {
        Guard.defense = defense;
    }

    public boolean setExtraDefense(Skills plugin, Player player) {
        double getDefense = getDefense(plugin, player);
        if (getDefense > 1) {
            NamespacedKey key = new NamespacedKey(plugin, "building-guard-defense-extra");
            AttributeModifier modifier = new AttributeModifier(key, getDefense - 1, AttributeModifier.Operation.ADD_NUMBER);
            attributeManager.addAttributeModifier(player, Attribute.MAX_ABSORPTION, modifier);
        }

        return getDefense > 1;
    }
}
