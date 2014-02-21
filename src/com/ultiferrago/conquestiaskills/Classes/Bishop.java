package com.ultiferrago.conquestiaskills.Classes;

import com.sucy.skill.api.ClassAttribute;
import com.sucy.skill.api.CustomClass;
import org.bukkit.ChatColor;


/**
 * Fill in later, too lazy.
 */
public class Bishop extends CustomClass {

    public static final String NAME = "Bishop";

    /**
     * Constructor
     */
    public Bishop() {

        // null for 2nd parameter means this is a starting class
        // params Name, preReq class, Display name tyle, profess level, max level
        super(NAME, "Priest", ChatColor.RED + NAME, 50, 50);

        // Class attributes
        setAttribute(ClassAttribute.HEALTH, 75, 1);
        setAttribute(ClassAttribute.MANA, 220, 4);
        
        addSkills(

        );
    }
}