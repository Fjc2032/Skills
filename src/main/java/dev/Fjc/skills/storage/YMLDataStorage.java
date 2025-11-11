package dev.Fjc.skills.storage;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.enums.SkillSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.entry;

public class YMLDataStorage {
    private final Skills plugin;

    private final File config;
    private final YamlConfiguration settings;

    private final File dataFile;
    private final YamlConfiguration dataCluster;

    private final File blockScoreFile;
    private final YamlConfiguration blockScoreCluster;

    private final List<File> files;

    public YMLDataStorage(Skills plugin) {
        this.plugin = plugin;

        this.config = new File(plugin.getDataFolder(), "config.yml");
        this.settings = YamlConfiguration.loadConfiguration(this.config);

        this.dataFile = new File(plugin.getDataFolder(), "data.yml");
        this.dataCluster = YamlConfiguration.loadConfiguration(this.dataFile);

        this.blockScoreFile = new File(plugin.getDataFolder(), "blockscores.yml");
        this.blockScoreCluster = YamlConfiguration.loadConfiguration(this.blockScoreFile);

        this.files = List.of(
                config,
                dataFile,
                blockScoreFile
        );
    }

    public void loadDefaults() {
        settings.addDefault("isEnabled", true);

        //
        Map<String, Double> blockValues = new LinkedHashMap<>(40);
        blockValues.put(Material.STONE.name(), 0.5);
        blockValues.put(Material.COBBLESTONE.name(), 0.5);
        blockValues.put(Material.IRON_ORE.name(), 1.75);
        blockValues.put(Material.GOLD_ORE.name(), 4.25);
        blockValues.put(Material.DIAMOND_ORE.name(), 8.0);
        blockValues.put(Material.ANCIENT_DEBRIS.name(), 12.0);
        blockScoreCluster.addDefault("mining-blocks", blockValues);

        settings.options().copyDefaults(true);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void buildFiles() throws IOException {
        for (File file : this.files) {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                this.plugin.saveResource(file.getName(), false);
            }
        }
    }

    public void reload() {
        try {
            dataCluster.load(dataFile);
            blockScoreCluster.load(blockScoreFile);
            settings.load(config);
        } catch (IOException | InvalidConfigurationException e) {
            this.plugin.getLogger().warning("Something went wrong while attempting to reload the files!");
            e.printStackTrace();
        }
    }

    @Deprecated
    public List<Material> getValidMiningMaterials() {
        List<Material> materials = new ArrayList<>();

        for (Map<?, ?> map : blockScoreCluster.getMapList("mining-blocks")) {
            for (Object object : map.keySet()) {
                try {
                    Material material = Material.valueOf(object.toString().toUpperCase());
                    materials.add(material);
                } catch (IllegalArgumentException e) {
                    this.plugin.getLogger().warning("An object in the material map is invalid!");
                    return materials.stream()
                            .filter(Objects::nonNull)
                            .toList();
                }
            }
        }

        return materials;
    }

    @Deprecated
    public @NotNull Map<Material, Double> getMaterialScores() {
        Map<Material, Double> map = blockScoreCluster.getMapList("mining-blocks").stream()
                .flatMap(obj -> obj.entrySet().stream())
                .flatMap(entry -> {
                    try {
                        Material material = Material.valueOf(
                                entry.getKey().toString().toUpperCase()
                        );
                        double value = Double.parseDouble(entry.getValue().toString());
                        return Stream.of(entry(material, value));
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

        //If something goes wrong with the data stream above, just load this instead
        Map<Material, Double> fallback = Map.ofEntries(
                entry(Material.STONE, 0.5),
                entry(Material.COBBLESTONE, 0.5),
                entry(Material.IRON_ORE, 2.0),
                entry(Material.GOLD_ORE, 4.25),
                entry(Material.DIAMOND_ORE, 8.0),
                entry(Material.ANCIENT_DEBRIS, 12.0)
        );
        return map.containsKey(null) || map.containsValue(null) || map.isEmpty() ? fallback : map;
    }


    /**
     * Returns a copy of the map contained in the blockdata file.
     * @return A map of materials and scores.
     */
    public @NotNull Map<Material, Double> blockMap() {
        ConfigurationSection section = blockScoreCluster.getConfigurationSection("mining-blocks");
        Map<Material, Double> setter = new LinkedHashMap<>(40);
        if (section != null) {
            Map<String, Object> map = section.getValues(false);

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Material material = Material.matchMaterial(entry.getKey());
                double score = Double.parseDouble(entry.getValue().toString());

                if (material != null) setter.put(material, score);
            }
        }
        Map<Material, Double> fallback = Map.ofEntries(
                entry(Material.STONE, 0.5),
                entry(Material.COBBLESTONE, 0.5),
                entry(Material.IRON_ORE, 2.0),
                entry(Material.GOLD_ORE, 4.25),
                entry(Material.DIAMOND_ORE, 8.0),
                entry(Material.ANCIENT_DEBRIS, 12.0)
        );
        return setter.containsKey(null) ? Map.copyOf(setter) : fallback;
    }

    /**
     * The offset will be the average of all skill scores
     * If the offset reaches a certain threshold, skill scores will be penalized
     * For now, 50 is being used as a dummy threshold. It'll be changed later.
     * @param player The player whose score is being affected
     * @return The offset, or 0 if the requirement is not met.
     */
    public double getOffset(Player player) {
        double value = 0;
        for (SkillSet skill : SkillSet.values()) {
            value += getScore(player, skill);
        }

        return value > 50 ? value : 0;
    }

    /**
     * A standalone method that updates the storage file with the appropriate skill.
     * Will automatically create a new path if one doesn't already exist for the player.
     * @param player The player being targeted
     * @param skill The skill being targeted
     * @param data The amount to increment by
     */
    public void incrementSkillScore(Player player, SkillSet skill, double data) {
        String path = skill.getSkill() + "." + player.getUniqueId();
        double current = dataCluster.getDouble(path, 0);

        dataCluster.set(path, current + data);
        save(dataCluster, dataFile);
    }

    /**
     * Gets the score associated with this skill
     * @param player The player
     * @param skill The skill
     * @return The score, as a double
     */
    public double getScore(Player player, SkillSet skill) {
        String path = skill.getSkill() + "." + player.getUniqueId();
        return dataCluster.getDouble(path, 0);
    }

    /**
     * Gets the total score of all accumulated skills of the player.
     * @param player The player
     * @return The total score, as a double
     */
    public double getTotalScore(Player player) {
        double score = 0;
        for (SkillSet skill : SkillSet.values()) {
            String path = skill.getSkill() + "." + player.getUniqueId();
            score += dataCluster.getDouble(path, 0);
        }

        return score;
    }

    private void save(YamlConfiguration yaml, File file) {
        try {
            yaml.save(file);
        } catch (IOException | IllegalArgumentException exception) {
            this.plugin.getLogger().warning("Something went wrong while attempting to save the file!");
            exception.printStackTrace();
        }
    }

    public void autosave(long frequency) {
        BukkitScheduler scheduler = Bukkit.getScheduler();
    }

}
