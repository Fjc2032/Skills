package dev.Fjc.skills.player;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.skill.Guard;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Represents all main Skills.
 */
public interface SkillController {

    private static Skills getPlugin() {
        return JavaPlugin.getPlugin(Skills.class);
    }

    Guard guard = new Guard(getPlugin());

}
