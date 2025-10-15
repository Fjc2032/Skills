package dev.Fjc.skills.listeners;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.storage.YMLDataStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.jetbrains.annotations.NotNull;

public class ServerListener implements Listener {
    YMLDataStorage storage;
    static double score;

    public ServerListener(@NotNull Skills plugin) {
        this.storage = new YMLDataStorage(plugin);
    }

    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            score = storage.getMiningScore(player);
            storage.setMiningScore(player, Double.isNaN(score) ? 0 : score);
        }
    }
}
