package com.ultiferrago.conquestiaskills.Classes;

import com.sucy.skill.api.ClassAttribute;
import com.sucy.skill.api.CustomClass;
import org.bukkit.ChatColor;


/**
 * Fill in later, too lazy.
 */
public class Sage extends CustomClass {

    public static final String NAME = "Sage";

    /**
     * Constructor
     */
    public Sage() {

        // null for 2nd parameter means this is a starting class
        // params Name, preReq class, Display name tyle, profess level, max level
        super(NAME, "Acolyte", ChatColor.RED + NAME, 50, 50);

        // Class attributes
        setAttribute(ClassAttribute.HEALTH, 60, 1);
        setAttribute(ClassAttribute.MANA, 260, 5);
        
        addSkills(

        );
    }
}