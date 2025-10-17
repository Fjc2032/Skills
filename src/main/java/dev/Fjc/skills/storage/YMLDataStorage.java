package dev.Fjc.skills.storage;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.enums.SkillSet;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class YMLDataStorage {
    private final Skills plugin;

    private final File dataFile;
    private final YamlConfiguration dataCluster;

    private final File blockScoreFile;
    private final YamlConfiguration blockScoreCluster;

    private static List<File> files;

    public YMLDataStorage(Skills plugin) {
        this.plugin = plugin;

        this.dataFile = new File(plugin.getDataFolder(), "data.yml");
        this.dataCluster = YamlConfiguration.loadConfiguration(this.dataFile);

        this.blockScoreFile = new File(plugin.getDataFolder(), "blockscores.yml");
        this.blockScoreCluster = YamlConfiguration.loadConfiguration(this.blockScoreFile);

        files = List.of(
                dataFile,
                blockScoreFile
        );
    }



    public void loadDefaults() {
        FileConfiguration configuration = this.plugin.getConfig();
        configuration.addDefault("isEnabled", true);

        //
        blockScoreCluster.addDefault("mining-blocks", new HashMap<>());
    }

    @SuppressWarnings("all")
    public void buildFiles() throws IOException {
        for (File file : files) {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                this.plugin.saveResource(file.getName(), false);
            }
        }
    }

    public void reload() {
        for (File file : files) {
            YamlConfiguration.loadConfiguration(file);
        }
    }

    //Mining - start
    public void addMiningData(Player player, Map<Map<UUID, SkillSet>, Double> data) {
        String path = SkillSet.MINING.getSkill() + player.getUniqueId();

        for (double value : data.values()) {
            dataCluster.set(path, value);
        }
    }

    public List<Material> getValidMiningMaterials() {
        List<Material> materials = new ArrayList<>();

        for (Map<?, ?> map : blockScoreCluster.getMapList("mining-blocks")) {
            for (Object object : map.keySet()) {
                try {
                    Material material = Material.valueOf(object.toString().toUpperCase());
                    materials.add(material);
                } catch (IllegalArgumentException e) {
                    this.plugin.getLogger().warning("An object in the material map is invalid!");
                }
            }
        }

        return materials;
    }

    public @NotNull Map<Material, Double> getMaterialScores() {
        return blockScoreCluster.getMapList("mining-blocks").stream()
                .flatMap(obj -> obj.entrySet().stream())
                .flatMap(entry -> {
                    try {
                        Material material = Material.valueOf(
                                entry.getKey().toString().toUpperCase()
                        );
                        double value = ((Number) entry.getValue()).doubleValue();
                        return Stream.of(Map.entry(material, value));
                    } catch (IllegalArgumentException exception) {
                        this.plugin.getLogger().warning("An invalid material was caught in the data stream!");
                        return Stream.empty();
                    }
                })
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Double::sum
                ));
    }

    public double getMiningScore(Player player) {
        return dataCluster.getDouble(SkillSet.MINING.getSkill() + player.getUniqueId(), 0);
    }
    public void setMiningScore(Player player, double score) {
        String path = SkillSet.MINING.getSkill() + player.getUniqueId();
        this.dataCluster.set(path, score);
    }
    //Mining - end

    //Building - start
    public void addBuildData(Player player, Map<Map<UUID, SkillSet>, Double> data) {
        String path = SkillSet.BUILDING.getSkill() + player.getUniqueId();

        for (double value : data.values()) dataCluster.set(path, value);
    }

    public double getBuildScore(Player player) {
        return dataCluster.getDouble(SkillSet.BUILDING.getSkill() + player.getUniqueId(), 0);
    }
    public void setBuildScore(Player player, double score) {
        String path = SkillSet.BUILDING.getSkill() + player.getUniqueId();
        this.dataCluster.set(path, score);
    }

    //Building - end

    /**
     * The offset will be the average of all skill scores
     * If the offset reaches a certain threshold, skill scores will be penalized
     * For now, 50 is being used as a dummy threshold. It'll be changed later.
     * @param player The player whose score is being affected
     * @return The offset, or 0 if the requirement is not met.
     */
    public double getOffset(Player player) {
        return ((getMiningScore(player) - getBuildScore(player)) / 2) > 50
                ? getMiningScore(player) - getBuildScore(player)
                : 0;
    }

}
