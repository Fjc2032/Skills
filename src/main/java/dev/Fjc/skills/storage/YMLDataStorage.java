package dev.Fjc.skills.storage;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.enums.SkillSet;
import dev.Fjc.skills.player.PlayerManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class YMLDataStorage {
    private Skills plugin;
    private File file;
    private YamlConfiguration configuration;

    private PlayerManager manager;

    public YMLDataStorage(Skills plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "data.yml");
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
        this.manager = new PlayerManager(plugin);
    }

    public void loadDefaults() {
        configuration.addDefault("isEnabled", true);
    }

    //Mining - start
    public void addMiningData(Player player, Map<Map<UUID, SkillSet>, Double> data) {
        String path = SkillSet.MINING.getSkill() + player.getUniqueId();

        for (double value : data.values()) {
            configuration.set(path, value);
        }
    }

    public double getMiningScore(Player player) {
        return configuration.getDouble(SkillSet.MINING.getSkill() + player.getUniqueId(), 0);
    }
    public void setMiningScore(Player player, double score) {
        String path = SkillSet.MINING.getSkill() + player.getUniqueId();
        this.configuration.set(path, score);
    }
    //Mining - end


}
