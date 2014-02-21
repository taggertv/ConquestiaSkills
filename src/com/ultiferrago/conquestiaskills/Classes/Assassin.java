package com.ultiferrago.conquestiaskills.Classes;

import com.sucy.skill.api.ClassAttribute;
import com.sucy.skill.api.CustomClass;
import org.bukkit.ChatColor;


/**
 * Fill in later, too lazy.
 */
public class Assassin extends CustomClass {

    public static final String NAME = "Assassin";

    /**
     * Constructor
     */
    public Assassin() {

        // null for 2nd parameter means this is a starting class
        // params Name, preReq class, Display name tyle, profess level, max level
        super(NAME, "Rogue", ChatColor.RED + NAME, 50, 50);

        // Class attributes
        setAttribute(ClassAttribute.HEALTH, 80, 1);
        setAttribute(ClassAttribute.MANA, 195, 3);
        
        addSkills(

        );
    }
}