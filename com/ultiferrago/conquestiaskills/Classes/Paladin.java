package com.ultiferrago.conquestiaskills.Classes;

import com.sucy.skill.api.ClassAttribute;
import com.sucy.skill.api.CustomClass;
import org.bukkit.ChatColor;


/**
 * Fill in later, too lazy.
 */
public class Paladin extends CustomClass {

    public static final String NAME = "Paladin";

    /**
     * Constructor
     */
    public Paladin() {

        // null for 2nd parameter means this is a starting class
        // params Name, preReq class, Display name tyle, profess level, max level
        super(NAME, "Knight", ChatColor.RED + NAME, 50, 50);

        // Class attributes
        setAttribute(ClassAttribute.HEALTH, 110, 2);
        setAttribute(ClassAttribute.MANA, 195, 4);
        
        addSkills(

        );
    }
}