package dev.Fjc.skills.storage;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.enums.SkillSet;
import dev.Fjc.skills.player.PlayerManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Map;
import java.util.UUID;

public class YMLDataStorage extends YamlConfiguration {
    private Skills plugin;
    private File file;

    private PlayerManager manager;

    public YMLDataStorage(Skills plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "data.yml");
        this.manager = new PlayerManager(plugin);
    }

    public void loadDefaults() {
    }

    public void addPlayerData(Player player, Map<Map<UUID, SkillSet>, Double> data) {
        this.set(player.getName(), PlayerManager.getPlayerMap().get(player.getUniqueId()));
        this.set(player.getName(), "bro idfk");
    }
}
