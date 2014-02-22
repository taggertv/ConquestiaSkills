package com.ultiferrago.conquestiaskills.Classes;

import com.sucy.skill.api.ClassAttribute;
import com.sucy.skill.api.CustomClass;
import org.bukkit.ChatColor;


/**
 * Fill in later, too lazy.
 */
public class Ranger extends CustomClass {

    public static final String NAME = "Ranger";

    /**
     * Constructor
     */
    public Ranger() {

        // null for 2nd parameter means this is a starting class
        // params Name, preReq class, Display name tyle, profess level, max level
        super(NAME, "Trapper", ChatColor.RED + NAME, 50, 50);

        // Class attributes
        setAttribute(ClassAttribute.HEALTH, 75, 1.5);
        setAttribute(ClassAttribute.MANA, 195, 3);
        
        addSkills(

        );
    }
}