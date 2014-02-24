package com.ultiferrago.conquestiaskills.Classes;

import com.sucy.skill.api.ClassAttribute;
import com.sucy.skill.api.CustomClass;
import org.bukkit.ChatColor;


/**
 * Fill in later, too lazy.
 */
public class Knight extends CustomClass {

    public static final String NAME = "Knight";

    /**
     * Constructor
     */
    public Knight() {

        // null for 2nd parameter means this is a starting class
        // params Name, preReq class, Display name tyle, profess level, max level
        super(NAME, "Soldier", ChatColor.RED + NAME, 40, 40);

        // Class attributes
        setAttribute(ClassAttribute.HEALTH, 45, 1);
        setAttribute(ClassAttribute.MANA, 75, 3);
        
        addSkills(

        );
    }
}