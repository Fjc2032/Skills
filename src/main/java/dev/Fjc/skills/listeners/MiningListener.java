package dev.Fjc.skills.listeners;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.enums.SkillSet;
import dev.Fjc.skills.player.PlayerManager;
import dev.Fjc.skills.skill.Mining;
import dev.Fjc.skills.skill.subskills.Excavator;
import dev.Fjc.skills.skill.subskills.ExplosivesTech;
import dev.Fjc.skills.skill.subskills.Spelunker;
import dev.Fjc.skills.storage.YMLDataStorage;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public class MiningListener extends Mining implements Listener {

    Spelunker spelunker;
    ExplosivesTech explosivesTech;
    Excavator excavator;

    YMLDataStorage storage;
    PlayerManager manager;

    Skills plugin;

    static double score;

    static int blocksBroken = 0;

    public MiningListener(@NotNull Skills plugin) {
        super(plugin);
        this.plugin = plugin;
        this.storage = plugin.getStorage();

        this.excavator = new Excavator(plugin);
        this.explosivesTech = new ExplosivesTech(plugin);
        this.spelunker = new Spelunker(plugin);

    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        this.manager = new PlayerManager(this.plugin, player);
        Map<Map<UUID, SkillSet>, Double> skillSetMap = this.manager.skillMap();

        double blockScore = storage.getMaterialScores().getOrDefault(block.getType(), 0.0);

        if (blockScore > 0) {
            storage.addMiningData(player, blockScore);
            addScore(player, storage.getMaterialScores().get(block.getType()));

            player.sendMessage("You mined " + block.getType() + " for " + storage.getMaterialScores().get(block.getType()) + " points.");
        }
    }

    @EventHandler
    public void passives(BlockBreakEvent event) {
        score = storage.getMiningScore(event.getPlayer());

        spelunker.oreBonus(event);
        spelunker.xpBoost(event);
    }

    @EventHandler
    public void demolition(BlockBreakEvent event) {
        Player player = event.getPlayer();

        blocksBroken++;
        if (blocksBroken >= 100) {
            explosivesTech.demolitionFury(event, val -> {
                player.sendMessage("You destroyed " + val + " blocks while demolition fury was active.");
                blocksBroken = -100;
            });
        }

    }

    @EventHandler
    public void abilities(PlayerInteractEvent event) {
        explosivesTech.createExplosion(event);
    }
}
