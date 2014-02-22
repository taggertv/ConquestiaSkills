package com.ultiferrago.conquestiaskills.Skills;
import com.sucy.skill.api.*;
import com.sucy.skill.api.util.*;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import com.sucy.skill.api.skill.ClassSkill;
import com.sucy.skill.api.skill.SkillAttribute;
import com.sucy.skill.api.skill.SkillShot;
import com.sucy.skill.api.skill.SkillType;

import java.util.List;

/**
 * Damages and slightly knocks back all nearby enemies
 */
public class Whirlwind extends ClassSkill implements SkillShot {

    public static final String NAME = "Whirlwind";
    private static final String
        SPEED = "Speed",
        DAMAGE = "Damage",
        RADIUS = "Radius";

    //private final SkillPack plugin;

    /**
     * Constructor
     *
     * @param plugin plugin reference
     */
    public Whirlwind() {
        super(NAME, SkillType.SKILL_SHOT_AOE, Material.STRING, 3);
        //this.pl = plugin;

        description.add("Damages and slightly");
        description.add("knocks back all nearby");
        description.add("enemies");

        setAttribute(SkillAttribute.COOLDOWN, 15, 0);
        setAttribute(SkillAttribute.COST, 3, 0);
        setAttribute(SkillAttribute.LEVEL, 15, 2);
        setAttribute(SkillAttribute.MANA, 20, 0);
        setAttribute(SPEED, 1, 0);
        setAttribute(DAMAGE, 3, 1);
        setAttribute(RADIUS, 4, 1);
    }

    /**
     * Damages and knocks back all nearby enemies
     *
     * @param player player casting the skill
     * @param i      skill level
     * @return       true if hit at least one enemy, false otherwise
     */
    @Override
    public boolean cast(Player player, int level) {

        int radius = (int) getAttribute(RADIUS, level);
        List<Entity> list = player.getNearbyEntities(radius, radius, radius);

        int damage = (int) getAttribute(DAMAGE, level);
        int speed = (int) getAttribute(SPEED, level);
        boolean worked = false;

        // Damage each living entity in the radius
        for (Entity entity : list) {
            if (entity instanceof LivingEntity) {
                LivingEntity target = (LivingEntity)entity;

                // Make sure the target can be attacked
                if (target instanceof Player && !Protection.canAttack(player, (Player)target)) {
                    continue;
                }

                target.damage(damage, player);
                Vector velocity = target.getLocation().subtract(player.getLocation()).toVector();
                velocity.multiply(speed / velocity.length());
                velocity.setY(velocity.getY() / 5 + 0.5);
                target.setVelocity(velocity);
                worked = true;
            }
        }

        return worked;
    }
}