package dev.Fjc.skills.player;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.storage.YMLDataStorage;
import org.reflections.Reflections;

import java.util.Set;

/**
 * Represents all skills.
 */
public class SkillController {

    static YMLDataStorage storage;

    public SkillController(Skills plugin) {
        storage = plugin.getStorage();
    }

    public Set<Class<? extends SkillController>> getSubSkills() {
        return new Reflections("dev.Fjc.skills.player").getSubTypesOf(SkillController.class);
    }
}
