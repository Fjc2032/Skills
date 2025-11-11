package dev.Fjc.skills.listeners;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.enums.SkillSet;
import dev.Fjc.skills.skill.Guard;
import dev.Fjc.skills.storage.YMLDataStorage;
import io.papermc.paper.event.entity.EntityKnockbackEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class GuardListener extends Guard implements Listener {

    private final Skills plugin;

    private final YMLDataStorage storage;

    public GuardListener(Skills plugin) {
        super(plugin);
        this.plugin = plugin;
        this.storage = plugin.getStorage();
    }

    @EventHandler
    public void event(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        double current = player.getAbsorptionAmount();
        double value = getDefense(plugin, player);

        if (value > 0) player.setAbsorptionAmount(current + setExtraDefense(plugin, player));
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        unarmed.unarmedAttack(event);
        unarmed.stun(event);
    }

    @EventHandler
    public void onDefend(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().isRightClick() && player.getInventory().getItemInOffHand().isSimilar(ItemStack.of(Material.SHIELD))) {
            Random random = new Random();
            double rand = random.nextDouble() * 10;
            storage.incrementSkillScore(player, SkillSet.GUARD, 1 + rand);
        }
    }

    @EventHandler
    public void onDefend(EntityKnockbackEvent event) {
        if (!(event.getEntity() instanceof Player defender)) return;
        if (event.getCause() == EntityKnockbackEvent.Cause.SHIELD_BLOCK) {
            Random random = new Random();
            double rand = random.nextDouble() * 10;
            storage.incrementSkillScore(defender, SkillSet.GUARD, 1 + rand);
        }
    }

    @EventHandler
    public void event(EntityDamageEvent event) {
        reduceDamageTaken(event);
    }
}
