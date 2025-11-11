package dev.Fjc.skills.inventory;

import dev.Fjc.skills.inventory.skills.MiningInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * A standalone, abstract class that initializes all inventory objects for use.
 */
public abstract class InventoryLoader extends BaseInventory {

    private static Player player;

    private static MiningInventory miningInventory;

    public void initialize(Player player) {
        InventoryLoader.player = player;
        miningInventory = new MiningInventory(player);
    }

    public static void loadAllInventories() {
        createBaseInventory();

        miningInventory.create(player);
    }

    public static void close() {
    }
}
