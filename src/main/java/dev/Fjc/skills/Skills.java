package dev.Fjc.skills;

import dev.Fjc.skills.storage.YMLDataStorage;
import org.bukkit.plugin.java.JavaPlugin;

public final class Skills extends JavaPlugin {

    private final YMLDataStorage storage = new YMLDataStorage(this);

    @Override
    public void onEnable() {
        storage.loadDefaults();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public YMLDataStorage getStorage() {
        return storage;
    }
}
