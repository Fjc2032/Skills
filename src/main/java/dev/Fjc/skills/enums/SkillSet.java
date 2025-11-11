package dev.Fjc.skills.enums;

import org.jetbrains.annotations.Nullable;

/**
 * An iterable enum of all main Skills
 */
public enum SkillSet {

    MINING("mining"),
    BUILDING("building"),
    GUARD("guard"),
    CRAFTSMAN;

    private final String skill;

    SkillSet() {
        this.skill = null;
    }

    SkillSet(String skill) {
        this.skill = skill;
    }

    public String getSkill() {
        return skill;
    }

    public static @Nullable SkillSet parseSkill(String name) {
        for (SkillSet skill : SkillSet.values()) {
            return name.toUpperCase().equals(skill.name()) ? skill : null;
        }
        return null;
    }
}
