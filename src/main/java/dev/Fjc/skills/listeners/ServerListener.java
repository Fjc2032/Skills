package dev.Fjc.skills.listeners;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.skill.Building;
import dev.Fjc.skills.skill.Mining;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class ServerListener implements Listener {

    // Main skills
    protected Building building;

    public ServerListener(@NotNull Skills plugin) {
        this.building = new Building(plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        recalc(player);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        recalc(player);
    }

    protected void recalc(Player player) {
        building.setBreakSpeed(player);
        Mining.spelunker.setMiningSpeed(player);
    }
}
