package dev.Fjc.skills;

import dev.Fjc.skills.command.GetMaterialsCommand;
import dev.Fjc.skills.command.Reload;
import dev.Fjc.skills.command.score.AddScoreCommand;
import dev.Fjc.skills.listeners.BuildingListener;
import dev.Fjc.skills.listeners.GuardListener;
import dev.Fjc.skills.listeners.MiningListener;
import dev.Fjc.skills.listeners.ServerListener;
import dev.Fjc.skills.player.AttributeManager;
import dev.Fjc.skills.skill.Guard;
import dev.Fjc.skills.skill.Mining;
import dev.Fjc.skills.storage.YMLDataStorage;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class Skills extends JavaPlugin {

    private static Skills instance;

    private final YMLDataStorage storage = new YMLDataStorage(this);
    private final AttributeManager attributeManager = new AttributeManager(this);

    @Override
    public void onEnable() {
        instance = this;
        Mining.factory(this);
        Guard.factory(this);

        try {
            storage.buildFiles();
        } catch (IOException e) {
            this.getLogger().severe("Something went horribly wrong while attempting to build the files.");
            this.getLogger().severe(e.getLocalizedMessage());
        }
        storage.loadDefaults();

        setListener(new MiningListener(this));
        setListener(new ServerListener(this));
        setListener(new GuardListener(this));
        setListener(new BuildingListener(this));

        setExecutor("getmaterials", new GetMaterialsCommand(this));
        setExecutor("skills-reload", new Reload(this));
        setExecutor("addscore", new AddScoreCommand(this));

    }

    @Override
    public void onDisable() {
        instance = null;
    }

    @NotNull
    public YMLDataStorage getStorage() {
        return storage;
    }

    @NotNull
    public AttributeManager getAttributeManager() {
        return attributeManager;
    }

    private void setListener(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
    private void setExecutor(String command, CommandExecutor executor) {
        this.getServer().getPluginCommand(command).setExecutor(executor);
    }

    public static Skills getInstance() {
        return instance;
    }
}
