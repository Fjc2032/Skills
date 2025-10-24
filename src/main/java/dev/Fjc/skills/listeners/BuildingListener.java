package dev.Fjc.skills.listeners;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.skill.Building;
import org.bukkit.event.Listener;

public class BuildingListener extends Building implements Listener {

    private final Skills plugin;

    public BuildingListener(Skills plugin) {
        super(plugin);
        this.plugin = plugin;
    }
}
