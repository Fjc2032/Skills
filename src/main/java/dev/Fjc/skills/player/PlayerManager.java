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

    private static Map<Map<UUID, SkillSet>, Double> skillLevel = new ConcurrentHashMap<>();

    public static double miningScore;
    public static double buildingScore;
    public static double guardScore;

    public PlayerManager getManager() {
        return PlayerManager.this;
    }

    public static Map<UUID, SkillSet> getPlayerMap() {
        return playerMap;
    }

    public static double getMiningScore() {
        return miningScore;
    }

    public void setMiningScore(double score) {
        miningScore = score;
    }
}
