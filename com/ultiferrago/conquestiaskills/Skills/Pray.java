/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ultiferrago.conquestiaskills.Skills;

import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.PlayerSkills;
import com.sucy.skill.api.skill.ClassSkill;
import com.sucy.skill.api.skill.SkillShot;
import com.sucy.skill.api.skill.SkillType;
import com.sucy.skill.api.skill.TargetSkill;
import com.sucy.skill.api.util.Protection;
import com.sucy.skill.api.util.TargetHelper;
import com.ultiferrago.conquestiaskills.util.FireworkEffectPlayer;
import java.lang.reflect.Method;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.FireworkEffect.Type;
/**
 *
 * @author ferrago
 */
public class Pray extends ClassSkill implements SkillShot 
{
    public static final String NAME = "Pray";
    private static SkillAPI plugin;
    public Pray(SkillAPI plugin)
    {
        super(NAME, SkillType.TARGET, Material.BOOK, 1);
        description.add("fill in, in yml");
        this.plugin = plugin;
        setAttribute("Heal-Amount", 10, 1);
        setAttribute("Heal-Percent", 5, 1);
        setAttribute("Range", 15, 2);
        
    }
    
    @Override
    public boolean cast(Player player, int level)
    {
       if (TargetHelper.getLivingTarget(player, getAttribute("Range", level)) == null)
       {
           if (player.getHealth() == player.getMaxHealth())
           {
               player.sendMessage(ChatColor.GREEN + "Heal" + ChatColor.DARK_RED + " failed! You have full health!");
               return false;
           }
           else
           {
               PlayerSkills pskills = api.getPlayer(player.getName());
               double percent = (double)((getAttribute("Heal-Percent", level)/100.00)); 
               int healPercent = (int)Math.ceil(percent * player.getMaxHealth());
               int healConstant = (int)getAttribute("Heal-Amount", level);
               int healAmount = healPercent + healConstant;
               pskills.heal(player, healAmount, NAME);
               player.sendMessage(ChatColor.GREEN + "You healed yourself for " + healAmount + ".");
               if (api.getSkill("Pray").getMaxLevel() == level)
               {
                FireworkEffectPlayer effectPlayer = new FireworkEffectPlayer();
                FireworkEffect fe = FireworkEffect.builder().withColor(Color.GREEN).with(Type.BALL).build();
                boolean worked = false;
                while (!worked)
                {
                 try
                 {
                     effectPlayer.playFirework(player.getWorld(), player.getLocation(), fe);
                     worked = true;
                 }
                 catch (Exception e)
                 {
                     worked = false;
                 }    
                }
               }
               return true;
           }
       }
       else
       {
           if (((TargetHelper.getLivingTarget(player, getAttribute("Range", level)) instanceof Player) && Protection.isAlly(player, TargetHelper.getLivingTarget(player, getAttribute("Range", level)))))
           {
               Player target = (Player)TargetHelper.getLivingTarget(player, getAttribute("Range", level));
               PlayerSkills targetSkills = api.getPlayer(target.getName());
               int healAmount = (int)(player.getMaxHealth() * (double)((getAttribute("Heal-Percent", level)/100.00)) + getAttribute("Heal-Amount", level));
               if (target.getMaxHealth() != target.getHealth())
               {
                    targetSkills.heal(player, healAmount, "Pray");
                    target.sendMessage(player.getName() + " healed you for " + healAmount);
                    if (api.getSkill("Pray").getMaxLevel() == level)
                    {
                        boolean worked = false;
                        FireworkEffectPlayer effectPlayer = new FireworkEffectPlayer();
                        FireworkEffect fe = FireworkEffect.builder().withColor(Color.GREEN).with(Type.BALL).build();
                        while (!worked)
                        {
                            try
                            {
                                effectPlayer.playFirework(target.getWorld(), target.getLocation(), fe);
                                worked = true;
                            }
                            catch (Exception e)
                            {
                                worked = false;
                            }
                        }
                    }
               }
               else
               {
                   player.sendMessage(ChatColor.GREEN + "Heal " + ChatColor.DARK_RED + "failed, target has full health");
               }
               return true;
           }
           else if (player.getMaxHealth() != player.getHealth())
           {
               PlayerSkills pskills = api.getPlayer(player.getName());
               pskills.heal(player, (player.getMaxHealth() * (getAttribute("Heal-Percent", level)/100.00)) + getAttribute("Heal-Amount", level), NAME);
               int healAmount = (int)(player.getMaxHealth() * (double)((getAttribute("Heal-Percent", level)/100.00)) + getAttribute("Heal-Amount", level));
               player.sendMessage(ChatColor.GREEN + "You healed yourself for " + healAmount + ".");
               if (api.getSkill("Pray").getMaxLevel() == level)
               {
                   boolean worked = false;
                   FireworkEffectPlayer effectPlayer = new FireworkEffectPlayer();
                   FireworkEffect fe = FireworkEffect.builder().withColor(Color.GREEN).with(Type.BALL).build();
                   while (!worked)
                   {
                     try
                     {
                        effectPlayer.playFirework(player.getWorld(), player.getLocation(), fe);
                        worked = true;
                     }
                     catch (Exception e)
                     {
                        worked = false;
                     }
                   }
               }
               return true;
           }
           else
           {
             player.sendMessage(ChatColor.GREEN + "Heal" + ChatColor.DARK_RED + " failed! You have full health!");
             return false;
           }
       }
    }
   
    
}
