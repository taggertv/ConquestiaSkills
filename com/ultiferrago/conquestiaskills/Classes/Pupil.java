package com.ultiferrago.conquestiaskills.Classes;

import com.sucy.skill.api.ClassAttribute;
import com.sucy.skill.api.CustomClass;
import com.ultiferrago.conquestiaskills.Skills.Fireball;
import com.ultiferrago.conquestiaskills.Skills.ManaShield;
import com.ultiferrago.conquestiaskills.Skills.Pray;
import org.bukkit.ChatColor;


/**
 * Fill in later, too lazy.
 */
public class Pupil extends CustomClass {

    public static final String NAME = "Pupil";

    /**
     * Constructor
     */
    public Pupil() {

        // null for 2nd parameter means this is a starting class
        // params Name, preReq class, Display name tyle, profess level, max level
        super(NAME, null, ChatColor.RED + NAME, 15, 15);

        // Class attributes
        setAttribute(ClassAttribute.HEALTH, 10, 0.5);
        setAttribute(ClassAttribute.MANA, 50, 2);
        
        addSkills(
                Fireball.NAME,
                Pray.NAME,
                ManaShield.NAME
        );
    }
}