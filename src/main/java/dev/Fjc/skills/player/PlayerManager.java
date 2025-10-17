package dev.Fjc.skills.player;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.enums.SkillSet;
import dev.Fjc.skills.storage.YMLDataStorage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {

    public PlayerManager(@NotNull Skills plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.storage = plugin.getStorage();
    }
    private final Skills plugin;

    private Player player;

    private YMLDataStorage storage;

    private static Map<UUID, SkillSet> playerMap = new ConcurrentHashMap<>();

    private final Map<Map<UUID, SkillSet>, Double> skillScore = new ConcurrentHashMap<>(Short.MAX_VALUE);

    public Map<Map<UUID, SkillSet>, Double> skillMap() {
        return this.skillScore;
    }
}
