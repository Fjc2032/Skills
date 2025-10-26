package dev.Fjc.skills.hunger;

import org.bukkit.inventory.ItemStack;

public interface RecipeImplementation {

    /**
     * Sets the recipe for the item on which this is implemented on, and then registers it to the
     * Bukkit crafting manager for use.
     */
     void setRecipe();

     default ItemStack buildItem(ItemStack item) {
         return item;
     }

    /**
     * Sets the targeted item as a consumable.
     * @param item The targeted item
     * @param nutrition The amount of nutrition. Can be zero.
     * @param ignoreHunger Whether to allow consumption even when not hungry.
     */
     default ItemStack consumable(ItemStack item, int nutrition, boolean ignoreHunger) {
         return HungerManagement.createNewFoodItem(item, ignoreHunger, nutrition);
     }
}
