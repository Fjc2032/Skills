package dev.Fjc.skills.listeners;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.enums.SkillSet;
import dev.Fjc.skills.player.PlayerManager;
import dev.Fjc.skills.skill.Mining;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

import static java.util.Map.entry;

public class MiningListener extends Mining implements Listener {

    public MiningListener(@NotNull Skills plugin) {
        this.plugin = plugin;
    }

    private final Skills plugin;

    /**
     * A map that will store data of all players.
     * UUID is the player's UUID, and SkillSet is an enum.
     * I might have to change this up later though.
     */
    private Map<UUID, SkillSet> skillSetMap = PlayerManager.getPlayerMap();

    /**
     * A map containing every block and its score, as a double.
     * Will be configurable later.
     * <p>
     * The idea is to have the score increase when certain blocks are mined.
     */
    private Map<Material, Double> blockScore = Map.ofEntries(
            entry(Material.COBBLESTONE, 1d),
            entry(Material.STONE, 1d),
            entry(Material.COAL_ORE, 2d),
            entry(Material.IRON_ORE, 2d),
            entry(Material.GOLD_ORE, 4d)
    );

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (skillSetMap.containsKey(player.getUniqueId())) {
            //The stuff here will add a "score" to the player's mining index. After a certain
            //point, the specialization will improve. At least that's the theory.
            //Obviously, there's nothing here yet since it's all theoretical.
        }
    }
}
