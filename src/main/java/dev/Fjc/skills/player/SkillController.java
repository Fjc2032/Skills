package dev.Fjc.skills.player;

import dev.Fjc.skills.enums.SubSkillSet;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A simple interface that represents all Skills.
 */
public interface SkillController {

    Map<UUID, Long> cooldown = new ConcurrentHashMap<>();
    Map<UUID, SubSkillSet> prio = new LinkedHashMap<>();

    default void setCooldown(UUID uuid, long duration, SubSkillSet priority) {
        if (!enableCooldown(uuid, priority)) return;
        long time = System.currentTimeMillis() + duration;
        cooldown.put(uuid, time);
        prio.putIfAbsent(uuid, priority);
    }

    default long getRemainingCooldown(UUID uuid, SubSkillSet priority) {
        if (!enableCooldown(uuid, priority)) return 0;
        if (cooldown.containsKey(uuid) && prio.get(uuid) == priority) {
            long remaining = cooldown.get(uuid) - System.currentTimeMillis();
            return Math.max(0, remaining);
        }

        return 0;
    }

    default void removeCooldown(UUID uuid) {
        cooldown.remove(uuid);
        prio.remove(uuid);
    }

    boolean enableCooldown(UUID uuid, SubSkillSet priority);

}
