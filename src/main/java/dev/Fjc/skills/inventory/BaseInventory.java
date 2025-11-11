package dev.Fjc.skills.inventory;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Represents the Main Menu.
 */
public class BaseInventory implements InventorySupplier {

    public static Inventory createBaseInventory() {
        return Bukkit.createInventory(null, 27, "Main Selection");
    }

    @Override
    public @Nullable Inventory create(Player owner) {
        return createBaseInventory();
    }

    @Override
    public @NotNull ItemStack buildHead() {
        return ItemStack.empty();
    }

    @Override
    public @NotNull ItemStack buildToolOnLevel(int index) {
        return ItemStack.empty();
    }

    @Override
    public void addItems(Inventory inventory, Map<ItemStack, Integer> items) {
        InventorySupplier.super.addItems(inventory, items);
    }

    @Override
    public Component text(String value) {
        return InventorySupplier.super.text(value);
    }
}
