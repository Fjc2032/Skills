package dev.Fjc.skills.hunger;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.storage.YMLDataStorage;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.FoodProperties;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public abstract class HungerManagement {

    public static void modifyHungerDecayRate(Skills plugin, Player player, int decay) {
        YMLDataStorage storage = plugin.getStorage();
        int currentFood = player.getFoodLevel();
        long total = (long) storage.getTotalScore(player) / 100;
        player.getScheduler().runDelayed(plugin, run -> player.setFoodLevel(currentFood - decay), null, 100L + total);
    }

    public static void setExhaustionFromTask(Event event, Player player, float exhaustion) {
        float current = player.getExhaustion();
        if (event.callEvent()) player.setExhaustion(current + exhaustion);
    }

    @SuppressWarnings("UnstableApiUsage")
    public static ItemStack createNewFoodItem(ItemStack item, boolean canEat, int nutrition) {
        FoodProperties foodBuilder = FoodProperties.food()
                .canAlwaysEat(canEat)
                .nutrition(nutrition)
                .saturation(0)
                .build();

        item.setData(DataComponentTypes.FOOD, foodBuilder);
        return item;
    }
}
