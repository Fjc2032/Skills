package dev.Fjc.skills.enums;

public enum SkillSet {

    MINING("mining"),
    BUILDING("building"),
    GUARD,
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
}
