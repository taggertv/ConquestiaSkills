package com.ultiferrago.conquestiaskills.Classes;

import com.sucy.skill.api.ClassAttribute;
import com.sucy.skill.api.CustomClass;
import org.bukkit.ChatColor;


/**
 * Fill in later, too lazy.
 */
public class Hunter extends CustomClass {

    public static final String NAME = "Hunter";

    /**
     * Constructor
     */
    public Hunter() {

        // null for 2nd parameter means this is a starting class
        // params Name, preReq class, Display name tyle, profess level, max level
        super(NAME, null, ChatColor.RED + NAME, 15, 15);

        // Class attributes
        setAttribute(ClassAttribute.HEALTH, 10, 1);
        setAttribute(ClassAttribute.MANA, 50, 1);
        
        addSkills(

        );
    }
}