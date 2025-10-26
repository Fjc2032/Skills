package dev.Fjc.skills.hunger.recipes;

import dev.Fjc.skills.hunger.RecipeImplementation;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SkillBoostItem implements RecipeImplementation {

    private static ItemStack booster = new ItemStack(Material.NETHER_STAR, 1);

    @Override
    public void setRecipe() {

    }

    @Override
    public ItemStack buildItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("&lSkill Booster"));
        item.setItemMeta(meta);

        return item;
    }
}
