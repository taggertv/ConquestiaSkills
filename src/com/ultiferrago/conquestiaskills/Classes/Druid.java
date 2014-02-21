package com.ultiferrago.conquestiaskills.Classes;

import com.sucy.skill.api.ClassAttribute;
import com.sucy.skill.api.CustomClass;
import org.bukkit.ChatColor;


/**
 * Fill in later, too lazy.
 */
public class Druid extends CustomClass {

    public static final String NAME = "Druid";

    /**
     * Constructor
     */
    public Druid() {

        // null for 2nd parameter means this is a starting class
        // params Name, preReq class, Display name tyle, profess level, max level
        super(NAME, "Shaman", ChatColor.RED + NAME, 50, 50);

        // Class attributes
        setAttribute(ClassAttribute.HEALTH, 90, 2);
        setAttribute(ClassAttribute.MANA, 180, 3);
        
        addSkills(

        );
    }
}