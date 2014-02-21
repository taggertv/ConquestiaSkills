package com.ultiferrago.conquestiaskills.Classes;

import com.sucy.skill.api.ClassAttribute;
import com.sucy.skill.api.CustomClass;
import com.ultiferrago.conquestiaskills.Skills.Mark;
import com.ultiferrago.conquestiaskills.Skills.Recall;
import com.ultiferrago.conquestiaskills.Skills.ThunderStorm;
import com.ultiferrago.conquestiaskills.Skills.Whirlwind;
import org.bukkit.ChatColor;


/**
 * Melee bulky fighter
 */
public class Tester extends CustomClass {

    public static final String NAME = "Tester";

    /**
     * Constructor
     */
    public Tester() {

        // null for 2nd parameter means this is a starting class
        super(NAME, null, ChatColor.RED + NAME, 40, 40);

        // Class attributes
        setAttribute(ClassAttribute.HEALTH, 20, 2);
        setAttribute(ClassAttribute.MANA, 15, 1);

        // Default skills for a fighter
        // (Note: the x.NAME parameters are just the 
        // name strings of the skills that I have
        // a public static field for)
        addSkills(
                Mark.NAME,
                Recall.NAME,
                ThunderStorm.NAME,
                Whirlwind.NAME
        );
    }
}