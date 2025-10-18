package dev.Fjc.skills;

import dev.Fjc.skills.listeners.MiningListener;
import dev.Fjc.skills.listeners.ServerListener;
import dev.Fjc.skills.storage.YMLDataStorage;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class Skills extends JavaPlugin {

    private final YMLDataStorage storage = new YMLDataStorage(this);

    @Override
    public void onEnable() {
        try {
            storage.buildFiles();
        } catch (IOException e) {
            this.getLogger().severe("Something went horribly wrong while attempting to build the files.");
            this.getLogger().severe(e.getLocalizedMessage());
        }
        storage.loadDefaults();

        setListener(new MiningListener(this));
        setListener(new ServerListener(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @NotNull
    public YMLDataStorage getStorage() {
        return storage;
    }

    private void setListener(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
}
