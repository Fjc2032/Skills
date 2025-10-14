package dev.Fjc.skills.listeners;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.enums.SkillSet;
import dev.Fjc.skills.player.PlayerManager;
import dev.Fjc.skills.skill.Mining;
import dev.Fjc.skills.storage.YMLDataStorage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

import static java.util.Map.entry;

public class MiningListener extends Mining implements Listener {

    YMLDataStorage storage;

    public MiningListener(@NotNull Skills plugin) {
        super(plugin);
        this.storage = plugin.getStorage();
    }

    /**
     * A map that will store data of all players.
     * UUID is the player's UUID, and SkillSet is an enum.
     * I might have to change this up later though.
     */
    private Map<Map<UUID, SkillSet>, Double> skillSetMap = PlayerManager.skillMap();

    /**
     * A map containing every block and its score, as a double.
     * Will be configurable later.
     * <p>
     * The idea is to have the score increase when certain blocks are mined.
     */
    private Map<Material, Double> blockScore = Map.ofEntries(
            entry(Material.COBBLESTONE, 0.25),
            entry(Material.STONE, 0.25),
            entry(Material.COAL_ORE, 1.0),
            entry(Material.IRON_ORE, 2.0),
            entry(Material.GOLD_ORE, 4.0),
            entry(Material.DIAMOND_ORE, 8.5)
    );

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (blockScore.containsKey(block.getType())) {
            //The stuff here will add a "score" to the player's mining index. After a certain
            //point, the specialization will improve. At least that's the theory.
            //Obviously, there's nothing here yet since it's all theoretical.
            addScore(player, blockScore.get(block.getType()));
            storage.addMiningData(player, skillSetMap);
        }
    }
}
