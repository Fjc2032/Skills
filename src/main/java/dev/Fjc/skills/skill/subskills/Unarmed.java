package dev.Fjc.skills.skill.subskills;

import dev.Fjc.skills.Skills;
import dev.Fjc.skills.skill.Guard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class Unarmed extends Guard implements Listener {

    public Unarmed(Skills plugin) {
        super(plugin);
    }

    @EventHandler
    public void onUnarmedAttack(EntityDamageByEntityEvent event) {

    }

    @EventHandler
    public void onAttacked(EntityDamageEvent event) {

    }
}
