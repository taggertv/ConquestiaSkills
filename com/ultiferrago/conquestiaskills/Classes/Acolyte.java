package com.ultiferrago.conquestiaskills.Classes;

import com.sucy.skill.api.ClassAttribute;
import com.sucy.skill.api.CustomClass;
import org.bukkit.ChatColor;


/**
 * Fill in later, too lazy.
 */
public class Acolyte extends CustomClass {

    public static final String NAME = "Acolyte";

    /**
     * Constructor
     */
    public Acolyte() {

        // null for 2nd parameter means this is a starting class
        // params Name, preReq class, Display name tyle, profess level, max level
        super(NAME, "Pupil", ChatColor.RED + NAME, 40, 40);

        // Class attributes
        setAttribute(ClassAttribute.HEALTH, 20, 1);
        setAttribute(ClassAttribute.MANA, 100, 4);
        
        addSkills(

        );
    }
}