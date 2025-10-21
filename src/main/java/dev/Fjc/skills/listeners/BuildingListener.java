package dev.Fjc.skills.listeners;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.skill.Building;
import dev.Fjc.skills.skill.Guard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class BuildingListener extends Building implements Listener {

    private final Skills plugin;

    public BuildingListener(Skills plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void event(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        double current = player.getAbsorptionAmount();
        double value = Guard.getDefense(plugin, player);

        guard.setExtraDefense(plugin, player);
    }
}
