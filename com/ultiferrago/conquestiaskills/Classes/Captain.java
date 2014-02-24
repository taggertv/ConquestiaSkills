package com.ultiferrago.conquestiaskills.Classes;

import com.sucy.skill.api.ClassAttribute;
import com.sucy.skill.api.CustomClass;
import org.bukkit.ChatColor;


/**
 * Fill in later, too lazy.
 */
public class Captain extends CustomClass {

    public static final String NAME = "Captain";

    /**
     * Constructor
     */
    public Captain() {

        // null for 2nd parameter means this is a starting class
        // params Name, preReq class, Display name tyle, profess level, max level
        super(NAME, "Guard", ChatColor.RED + NAME, 40, 40);

        // Class attributes
        setAttribute(ClassAttribute.HEALTH, 110, 1);
        setAttribute(ClassAttribute.MANA, 155, 2);
        
        addSkills(

        );
    }
}