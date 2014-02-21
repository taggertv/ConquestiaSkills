package com.ultiferrago.conquestiaskills.Classes;

import com.sucy.skill.api.ClassAttribute;
import com.sucy.skill.api.CustomClass;
import org.bukkit.ChatColor;


/**
 * Fill in later, too lazy.
 */
public class Trapper extends CustomClass {

    public static final String NAME = "Trapper";

    /**
     * Constructor
     */
    public Trapper() {

        // null for 2nd parameter means this is a starting class
        // params Name, preReq class, Display name tyle, profess level, max level
        super(NAME, "Hunter", ChatColor.RED + NAME, 40, 40);

        // Class attributes
        setAttribute(ClassAttribute.HEALTH, 35, 1);
        setAttribute(ClassAttribute.MANA, 75, 3);
        
        addSkills(

        );
    }
}