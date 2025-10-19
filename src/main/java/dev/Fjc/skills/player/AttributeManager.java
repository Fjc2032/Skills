package dev.Fjc.skills.player;

import dev.Fjc.skills.Skills;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AttributeManager {

    private final Skills plugin;

    public AttributeManager(@NotNull Skills plugin) {
        this.plugin = plugin;
    }

    public AttributeModifier buildModifier(String key, double value, AttributeModifier.Operation operation) {
        NamespacedKey namespacedKey = new NamespacedKey(this.plugin, key);
        return new AttributeModifier(namespacedKey, value, operation);
    }

    public void addAttributeModifier(Player player, Attribute attribute, AttributeModifier modifier) {
        if (attribute == null) return;
        var instance = player.getAttribute(attribute);

        if (instance == null) return;
        instance.getModifiers().stream()
                .filter(obj -> obj.getName().equals(modifier.getName()))
                .forEach(instance::removeModifier);

        player.getAttribute(attribute).addModifier(modifier);
    }

    public void removeModifier(Player player, Attribute attribute, AttributeModifier modifier) {
        player.getAttribute(attribute).removeModifier(modifier);
    }
}
