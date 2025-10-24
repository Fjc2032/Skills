package dev.Fjc.skills.listeners;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.skill.Building;
import dev.Fjc.skills.skill.subskills.Spelunker;
import dev.Fjc.skills.storage.YMLDataStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class ServerListener implements Listener {
    YMLDataStorage storage;

    // Main skills
    protected Building building;

    // Sub skills
    protected Spelunker spelunker;

    public ServerListener(@NotNull Skills plugin) {
        this.storage = plugin.getStorage();
        this.spelunker = new Spelunker(plugin);
        this.building = new Building(plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        spelunker.setMiningSpeed(player);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        spelunker.setMiningSpeed(player);
    }

    protected void recalc(Player player) {
        building.setBreakSpeed(player);
        spelunker.setMiningSpeed(player);
    }
}
