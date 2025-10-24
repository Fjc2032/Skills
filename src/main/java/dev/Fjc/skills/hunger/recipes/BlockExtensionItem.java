package dev.Fjc.skills.hunger.recipes;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.enums.SkillSet;
import dev.Fjc.skills.player.AttributeManager;
import dev.Fjc.skills.storage.YMLDataStorage;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A standalone class that represents a custom item. <br>
 * Intended for the Building skill, it will extend the block interaction range for the player who equips it.
 */
public class BlockExtensionItem {

    private static final Skills plugin = Skills.getInstance();

    private static final ItemStack extender = new ItemStack(Material.BLAZE_ROD, 1);

    private static Player player;

    private static final NamespacedKey key = new NamespacedKey(plugin, "building-item-extender");

    public BlockExtensionItem() {
        player = null;
    }

    public BlockExtensionItem(Player player) {
        BlockExtensionItem.player = player;
    }

    private static void setProperties() {
        ItemMeta meta = extender.getItemMeta();
        meta.displayName(Component.text("Block Extender"));
        extender.setItemMeta(meta);
        if (player != null) {
            YMLDataStorage storage = plugin.getStorage();
            AttributeManager attributeManager = plugin.getAttributeManager();

            double score = storage.getScore(player, SkillSet.BUILDING);
            double value = score >= 5000 ? 1 + (score / 300) : 0;
            if (value != 0) {
                AttributeModifier modifier = new AttributeModifier(key, value, AttributeModifier.Operation.ADD_NUMBER);
                attributeManager.addAttributeModifier(player, Attribute.BLOCK_INTERACTION_RANGE, modifier);
            }
        }
    }

    private static void setRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(key, extender)
                .setIngredient('A', Material.ENCHANTED_BOOK)
                .setIngredient('B', Material.DIAMOND)
                .setIngredient('C', Material.BLAZE_ROD)
                .shape("AAA", "ACA", "ABA");
        Bukkit.addRecipe(recipe, true);
    }

    public static ItemStack getItem() {
        setProperties();
        setRecipe();
        return extender;
    }

    public static void execute() {
        if (player != null) setProperties();
    }

}
