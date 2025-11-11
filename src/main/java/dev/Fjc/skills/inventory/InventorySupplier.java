package dev.Fjc.skills.inventory;

import dev.Fjc.skills.enums.SkillSet;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents an abstract supplier that fills an inventory with elements.
 */
public interface InventorySupplier {

    Map<ItemStack, SkillSet> heads = new ConcurrentHashMap<>();

    @Nullable Inventory create(Player owner);

    @NotNull ItemStack buildHead();

    @NotNull ItemStack buildToolOnLevel(int index);

    default void addItems(Inventory inventory, Map<ItemStack, Integer> items) {
        for (ItemStack item : items.keySet()) {
            inventory.setItem(items.get(item), item);
        }
    }

    default Component text(String value) {
        return Component.text(value);
    }

}
