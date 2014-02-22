/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ultiferrago.conquestiaskills.Skills;

import com.sucy.party.Parties;
import com.sucy.skill.api.skill.ClassSkill;
import com.sucy.skill.api.skill.SkillShot;
import com.sucy.skill.api.skill.SkillType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author ferrago
 */
public class Fortitude extends ClassSkill implements SkillShot {

    public static final String NAME = "Fortitude";
    private JavaPlugin plugin;
    
    public Fortitude(JavaPlugin plugin)
    {
     super(NAME, SkillType.SELF, Material.BED, 2); 
     description.add("Recall's your marked location");
    }
    @Override
    public boolean cast(Player player, int level) {
        Parties partyPlugin = (Parties) plugin.getServer().getPluginManager().getPlugin("Parties");
        //Parties partyPlugin = 
        partyPlugin.getParty(player);
        return false;
    }
    
}
