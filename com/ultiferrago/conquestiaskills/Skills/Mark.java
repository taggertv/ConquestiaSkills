package com.ultiferrago.conquestiaskills.Skills;

import com.sucy.skill.api.skill.ClassSkill;
import com.sucy.skill.api.skill.SkillAttribute;
import com.sucy.skill.api.skill.SkillShot;
import com.sucy.skill.api.skill.SkillType;
import com.ultiferrago.conquestiaskills.Config.Config;
import com.ultiferrago.conquestiaskills.ConquestiaSkills;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Dashes forward
 */
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
public class Mark extends ClassSkill implements SkillShot {
    public static final String NAME = "Mark";
    public JavaPlugin plugin;
    

    /**
     * Constructor
     *
     * @param plugin plugin reference
     */
    public Mark(JavaPlugin plugin) {
        super(NAME, SkillType.SELF, Material.BED, 1);
        this.plugin = plugin;

        description.add("fill in, in yml");

        setAttribute(SkillAttribute.COOLDOWN, 20, -3);
        setAttribute(SkillAttribute.COST, 2, 0);
        setAttribute(SkillAttribute.LEVEL, 10, 2);
        setAttribute(SkillAttribute.MANA, 5, 0);
        setAttribute("reagent-cost",2, -1);
        
    }

    /**
     * Dashes forward
     *
     * @param player player casting the skill
     * @param i      skill level
     * @return       true
     */
    @Override
    public boolean cast(Player player, int level) {
       if (player.getInventory().contains(Material.GOLD_INGOT, (int) getAttribute("reagent-cost", level))) {
            int cost = (int) getAttribute("reagent-cost", level);
            ItemStack reagentCost = new ItemStack(Material.GOLD_INGOT, cost);
            player.getInventory().remove(reagentCost);
       }
       else {
            player.sendMessage(ChatColor.DARK_RED + "You require " + (int) getAttribute("reagent-cost", level) + " gold ingots to mark your location.");
            return false;
        }
        Config markConfig = new Config(this.plugin, "Marks" + File.separator + player.getName());
        markConfig.getConfig().set("x", player.getLocation().getX());
        markConfig.getConfig().set("y", player.getLocation().getY());
        markConfig.getConfig().set("z", player.getLocation().getZ());
        markConfig.getConfig().set("world", player.getWorld().toString());
        markConfig.saveConfig();
        player.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + "Successfully marked!" + ChatColor.DARK_GREEN + " x: " + (int)(player.getLocation().getX())  + "   y: " + (int)(player.getLocation().getY()) + "  z: " + (int)(player.getLocation().getZ()));
        return true;
    
    }
}