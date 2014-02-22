package com.ultiferrago.conquestiaskills.Classes;

import com.sucy.skill.api.ClassAttribute;
import com.sucy.skill.api.CustomClass;
import org.bukkit.ChatColor;


/**
 * Fill in later, too lazy.
 */
public class Commander extends CustomClass {

    public static final String NAME = "Commander";

    /**
     * Constructor
     */
    public Commander() {

        // null for 2nd parameter means this is a starting class
        // params Name, preReq class, Display name tyle, profess level, max level
        super(NAME, "Bannerman", ChatColor.RED + NAME, 50, 50);

        // Class attributes
        setAttribute(ClassAttribute.HEALTH, 85, 2);
        setAttribute(ClassAttribute.MANA, 155, 4);
        
        addSkills(

        );
    }
}