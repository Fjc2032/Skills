package dev.Fjc.skills.skill.subskills;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.skill.Mining;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Represents the Explosive Technician skill, under the main skill Mining
 * <p></p>
 * Abilities: <br>
 * TNT Tactics Ability - right click with a pickaxe in hand to blow up an area. <br>
 * Demolition Fury Ability - Blasts through multiple blocks at once (for a very brief time period).
 */
public class ExplosivesTech extends Mining {

    private final Skills plugin;

    public ExplosivesTech(@NotNull Skills plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    /**
     * Creates an explosion at the given location.
     * @param event The event that determines the location of the TNT.
     * @apiNote The TNT will explode immediately.
     */
    public void createExplosion(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        final World world = player.getWorld();
        final Location target = event.getPlayer().getEyeLocation().distance(player.getLocation()) <= 8
                ? event.getPlayer().getEyeLocation()
                : null;

        if (event.getAction() != Action.RIGHT_CLICK_AIR || event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (target == null) return;
        if (!pickaxes.contains(player.getInventory().getItemInMainHand().getType())) return;
        world.spawn(target, TNTPrimed.class);
    }

    /**
     * Activates a demolition ability. Works kinda like the Detonate enchant but on a smaller timer, so the server shouldn't die this time.
     * @param event The event called when blocks are being broken.
     * @param callback Future callback that returns the amount of blocks destroyed when the ability ends.
     */
    public void demolitionFury(BlockBreakEvent event, Consumer<Integer> callback) {
        Block initialBlock = event.getBlock();
        Player player = event.getPlayer();
        var lambdaContext = new Object() {
            int destroyedBlocks = 0;
        };

        ScheduledTask task = player.getScheduler().runAtFixedRate(this.plugin, run -> {
            for (Block block : surroundingBlocks(initialBlock)) {
                if (!block.isEmpty()) {
                    block.breakNaturally();
                    lambdaContext.destroyedBlocks++;
                }
            }
        }, null, 0L, 500L);

        player.getScheduler().runDelayed(this.plugin, run -> {
            if (task != null) task.cancel();
            callback.accept(lambdaContext.destroyedBlocks);
        }, null, 501L);
    }
}
