package dev.Fjc.skills.inventory.skills;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.enums.SkillSet;
import dev.Fjc.skills.inventory.BaseInventory;
import dev.Fjc.skills.inventory.InventorySupplier;
import dev.Fjc.skills.storage.YMLDataStorage;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MiningInventory extends BaseInventory implements InventorySupplier {

    private static final Skills plugin = Skills.getInstance();

    private static final Map<ItemStack, Integer> items = new ConcurrentHashMap<>();

    private static final ItemStack[] objects = new ItemStack[18];

    private static ItemStack head;

    private final Player player;

    public static Inventory target;

    public MiningInventory(Player player) {
        this.player = player;
    }

    @Override
    public @Nullable Inventory create(Player player) {

        target = Bukkit.createInventory(player, 18, Component.text("Mining - Main Skill"));


        target.setItem(5, buildHead());
        objects[0] = new ItemStack(Material.PAPER, 1);
        objects[1] = new ItemStack(Material.PAPER, 2);

        applyAllMetas();
        items.put(objects[0], 10);
        addItems(target, items);


        return target;
    }

    public void applyAllMetas() {
        ItemMeta zero = objects[0].getItemMeta();
        zero.displayName(Component.text("Leveling up"));

        zero.lore(
                List.of(
                        text(""),
                        text("Most common blocks that can be mined with a pickaxe\ncount towards the mining score.")
        ));

        objects[0].setItemMeta(zero);
    }

    public @NotNull ItemStack buildHead() {
        YMLDataStorage storage = plugin.getStorage();
        double score = storage.getScore(player, SkillSet.MINING);
        int level = score < 1000 ? 1 : (int) (score/1000);
        head = buildToolOnLevel(level);

        return head;
    }

    public @NotNull ItemStack buildToolOnLevel(int index) {
        Map<Integer, Material> map = new ConcurrentHashMap<>();
        map.put(1, Material.WOODEN_PICKAXE);
        map.put(2, Material.STONE_PICKAXE);
        map.put(3, Material.GOLDEN_PICKAXE);
        map.put(4, Material.IRON_PICKAXE);
        map.put(5, Material.DIAMOND_PICKAXE);
        map.put(6, Material.NETHERITE_PICKAXE);

        if (index > 6) {
            ItemStack newItem = new ItemStack(Material.NETHERITE_PICKAXE);
            ItemMeta meta = newItem.getItemMeta();
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            newItem.setItemMeta(meta);

            return newItem;
        }

        return ItemStack.of(map.get(index));
    }


}
