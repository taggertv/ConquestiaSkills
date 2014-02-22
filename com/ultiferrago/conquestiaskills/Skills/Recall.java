package com.ultiferrago.conquestiaskills.Skills;

import com.sucy.skill.api.skill.ClassSkill;
import com.sucy.skill.api.skill.SkillAttribute;
import com.sucy.skill.api.skill.SkillShot;
import com.sucy.skill.api.skill.SkillType;
import com.ultiferrago.conquestiaskills.Config.Config;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
/**
 * Dashes forward
 */
public class Recall extends ClassSkill implements SkillShot {

    public static final String NAME = "Recall";
    private JavaPlugin plugin;

    /**
     * Constructor
     *
     * @param plugin plugin reference
     */
    public Recall(JavaPlugin plugin) {
        super(NAME, SkillType.SELF, Material.BED, 2);
        this.plugin = plugin;
        description.add("Recall's your marked location");
        
        setAttribute(SkillAttribute.COOLDOWN, 20, -3);
        setAttribute(SkillAttribute.COST, 2, 0);
        setAttribute(SkillAttribute.LEVEL, 10, 2);
        setAttribute(SkillAttribute.MANA, 5, 0);
        setAttribute("delay", 10, -3);
        setAttribute("reagent-cost", 5, 0);
        
        
        
    }

    /**
     * Need to change
     */
    @Override
    public boolean cast(Player player, int level) {
       
        if (player.getInventory().contains(Material.REDSTONE, (int) getAttribute("reagent-cost", level))) {
            int cost = (int) getAttribute("reagent-cost", level);
            ItemStack reagentCost = new ItemStack(Material.REDSTONE, cost);
            player.getInventory().removeItem(reagentCost);
                    
            
        }
        else {
            player.sendMessage(ChatColor.DARK_RED + "You require redstone to open the aether.");
            return false;
        }
        Config markConfig = new Config(this.plugin, "Marks" + File.separator + player.getName());
        Location castLoc = player.getLocation();
        try {
            player.sendMessage(ChatColor.DARK_PURPLE + "Recall is warming up!");
            TimeUnit.SECONDS.sleep((int)getAttribute("delay", level));
            if (!(castLoc == player.getLocation()))
            {
                player.sendMessage("Do not move while casting recall!");
                return false;
            }
            
        } catch (InterruptedException ex) {
            plugin.getLogger().log(Level.SEVERE, "Error waiting 10 seconds for recall!");
            
            return false;
        }
        
        
        if (!markConfig.getConfig().isSet("x") || !markConfig.getConfig().isSet("y") || !markConfig.getConfig().isSet("z")) {
            player.sendMessage(ChatColor.RED + "Error! No mark location set!");
            return false;
        }
        Location loc = player.getLocation();
        loc.setX((double)markConfig.getConfig().get("x"));
        loc.setY((double)markConfig.getConfig().get("y"));
        loc.setZ((double)markConfig.getConfig().get("z"));
        player.teleport(loc);
        //plugin.getServer().getWorld(name); //to add multiworld support
        
        return true;
    }
}