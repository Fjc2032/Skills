package dev.Fjc.skills.skill.subskills;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.enums.SkillSet;
import dev.Fjc.skills.skill.Guard;
import dev.Fjc.skills.storage.YMLDataStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.UseCooldownComponent;

import java.util.Random;

/**
 * Represents the Unarmed skill, under the main skill Guard.
 */
public class Unarmed extends Guard {

    private final YMLDataStorage storage;

    public Unarmed(Skills plugin) {
        super(plugin);
        this.storage = plugin.getStorage();
    }

    /**
     * Increases the attack damage when using fists.
     * @param event The damage event to listen to. Should be EntityDamageByEntityEvent or something that inherits that.
     */
    public void unarmedAttack(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        double score = storage.getScore(player, SkillSet.GUARD);
        double value = 1 + (score / 100);
        double current = event.getDamage();

        if (player.getInventory().getItemInMainHand().isEmpty()) event.setDamage(current + value);
    }

    /**
     * Chance to temporarily disable whatever item the opponent is using, granted the following: <br>
     * Both parties are an instance of Player. <br>
     * The attacker's main hand is empty. <br>
     * The defender's main hand is not empty. <br>
     * The item is valid for vanilla cooldown mechanics.
     * @param event The damage event to listen to. Should be EntityDamageByEntityEvent or something that inherits that.
     */
    @SuppressWarnings("UnstableApiUsage")
    public void stun(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        if (!(event.getEntity() instanceof Player defender)) return;

        if (attacker.getInventory().getItemInMainHand().isEmpty()) {
            if (defender.getInventory().getItemInMainHand().isEmpty()) return;

            ItemStack item = defender.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();

            if (meta == null) return;

            Random random = new Random();
            double rand = random.nextDouble();
            if (rand >= 0.07) return;

            double attackerScore = storage.getScore(attacker, SkillSet.GUARD);
            double defenderScore = storage.getScore(defender, SkillSet.GUARD);
            double debuff = 1 + (attackerScore / 300);
            double counter = 1 + (defenderScore / 100);
            UseCooldownComponent component = meta.getUseCooldown();
            component.setCooldownSeconds((float) (3 + (debuff - counter)));
            meta.setUseCooldown(component);
            item.setItemMeta(meta);
        }
    }
}
