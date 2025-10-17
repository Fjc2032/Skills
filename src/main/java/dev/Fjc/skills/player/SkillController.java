package dev.Fjc.skills.player;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.storage.YMLDataStorage;

/**
 * Represents all skills.
 */
public class SkillController {

    static YMLDataStorage storage;

    public SkillController(Skills plugin) {
        storage = plugin.getStorage();
    }
}
