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

        if (storage.getMaterialScores().containsKey(block.getType())) {
            /*
            The stuff here will add a "score" to the player's mining index. After a certain
            point, the specialization will improve. At least that's the theory.
            Obviously, there's nothing here yet since it's all theoretical.
            */
            storage.addMiningData(player, skillSetMap);
            addScore(player, storage.getMaterialScores().get(block.getType()));
        }
    }

    @EventHandler
    public void activateAbilities(BlockBreakEvent event) {
        score = storage.getMiningScore(event.getPlayer());

        spelunker.oreBonus(event);
        spelunker.xpBoost(event);
    }
}
