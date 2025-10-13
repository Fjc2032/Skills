package dev.Fjc.skills.storage;

import dev.Fjc.skills.Skills;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class YMLDataStorage extends YamlConfiguration {
    private Skills plugin;
    private File file;

    public YMLDataStorage(Skills plugin) {
        this.plugin = plugin;
        this.file = new File(this.plugin.getDataFolder(), "data.yml");
    }
}
