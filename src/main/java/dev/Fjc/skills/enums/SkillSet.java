package dev.Fjc.skills.enums;

public enum SkillSet {

    MINING("mining"),
    BUILDING,
    GUARD,
    CRAFTSMAN;

    private String skill;

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
