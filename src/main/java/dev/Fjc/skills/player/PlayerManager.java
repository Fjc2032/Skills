package dev.Fjc.skills.player;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.enums.SkillSet;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {

    public PlayerManager(@NotNull Skills plugin) {
        this.plugin = plugin;
    }
    private final Skills plugin;

    private static Map<UUID, SkillSet> playerMap = new ConcurrentHashMap<>();

    private static Map<Map<UUID, SkillSet>, Double> skillLevel = new ConcurrentHashMap<>(Short.MAX_VALUE);

    public static Map<Map<UUID, SkillSet>, Double> skillMap() {
        return skillLevel;
    }
}
