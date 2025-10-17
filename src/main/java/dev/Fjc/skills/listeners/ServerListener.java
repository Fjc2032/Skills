package dev.Fjc.skills.listeners;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.skill.subskills.Spelunker;
import dev.Fjc.skills.storage.YMLDataStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.jetbrains.annotations.NotNull;

public class ServerListener implements Listener {
    YMLDataStorage storage;
    static double score;

    Spelunker spelunker;

    public ServerListener(@NotNull Skills plugin) {
        this.storage = plugin.getStorage();
        this.spelunker = new Spelunker(plugin);
    }

    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            score = storage.getMiningScore(player);
            storage.setMiningScore(player, Double.isNaN(score) ? 0 : score);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        score = storage.getMiningScore(player);
        storage.setMiningScore(player, Double.isNaN(score) ? 0 : score);
        spelunker.setMiningSpeed(player);
    }
}
