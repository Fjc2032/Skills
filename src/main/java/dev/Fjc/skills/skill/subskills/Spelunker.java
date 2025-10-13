package dev.Fjc.skills.skill.subskills;

import dev.Fjc.skills.skill.Mining;
import org.bukkit.World;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class Spelunker extends Mining {

    public void setMiningSpeed(Player player, AttributeModifier modifier) {

    }

    public void oreBonus(Player player, BlockBreakEvent event) {
        final Block block = event.getBlock();
        World world = block.getWorld();
        if (canGetXP(event)) world.dropItemNaturally(block.getLocation(), ItemStack.of(block.getType(), (int) getMiningscore()));
    }

}
