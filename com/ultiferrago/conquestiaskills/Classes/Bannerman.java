package com.ultiferrago.conquestiaskills.Classes;

import com.sucy.skill.api.ClassAttribute;
import com.sucy.skill.api.CustomClass;
import org.bukkit.ChatColor;


/**
 * Fill in later, too lazy.
 */
public class Bannerman extends CustomClass {

    public static final String NAME = "Bannerman";

    /**
     * Constructor
     */
    public Bannerman() {

        // null for 2nd parameter means this is a starting class
        // params Name, preReq class, Display name tyle, profess level, max level
        super(NAME, "Soldier", ChatColor.RED + NAME, 40, 40);

        // Class attributes
        setAttribute(ClassAttribute.HEALTH, 45, 1);
        setAttribute(ClassAttribute.MANA, 75, 2);
        
        addSkills(

        );
    }
}