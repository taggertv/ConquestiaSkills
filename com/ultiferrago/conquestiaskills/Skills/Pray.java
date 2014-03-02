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
import java.lang.reflect.Method;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

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
        setAttribute("Heal-Amount", 10, 5);
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
               int healAmount = (int)(player.getMaxHealth() * (double)((getAttribute("Heal-Percent", level)/100.00)) + getAttribute("Heal-Amount", level)); 
               pskills.heal(player, healAmount, NAME);
               player.sendMessage(ChatColor.GREEN + "You healed yourself for " + healAmount + ".");
               if (api.getSkill("Pray").getMaxLevel() == level)
               {
                   boolean worked = false;
                   while (!worked)
                   {
                     try
                     {
                        Pray.FireworkEffectPlayer effect = new Pray.FireworkEffectPlayer();
                        FireworkEffect fe = FireworkEffect.builder().with(FireworkEffect.Type.BALL).withColor(Color.GREEN).build();
                        effect.playFirework(player.getWorld(), player.getLocation(), fe);
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
           if (Protection.isAlly(player, TargetHelper.getLivingTarget(player, getAttribute("Range", level))) && (TargetHelper.getLivingTarget(player, getAttribute("Range", level)) instanceof Player))
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
                        try
                        {
                            Pray.FireworkEffectPlayer effect = new Pray.FireworkEffectPlayer();
                            FireworkEffect fe = FireworkEffect.builder().with(FireworkEffect.Type.BALL).withColor(Color.GREEN).build();
                            effect.playFirework(target.getWorld(), target.getLocation(), fe);
                            worked = true;
                        }
                        catch (Exception e)
                        {
                            worked = false;
                        }
                    }
               }
               else
               {
                   player.sendMessage(ChatColor.GREEN + "Heal " + ChatColor.DARK_RED + "failed, target has full health");
               }
               return true;
           }
           else
           {
               PlayerSkills pskills = api.getPlayer(player.getName());
               pskills.heal(player, (player.getMaxHealth() * (getAttribute("Heal-Percent", level)/100.00)) + getAttribute("Heal-Amount", level), NAME);
               int healAmount = (int)(player.getMaxHealth() * (double)((getAttribute("Heal-Percent", level)/100.00)) + getAttribute("Heal-Amount", level));
               player.sendMessage(ChatColor.GREEN + "You healed yourself for " + healAmount + ".");
               if (api.getSkill("Pray").getMaxLevel() == level)
               {
                   boolean worked = false;
                   while (!worked)
                   {
                     try
                     {
                        Pray.FireworkEffectPlayer effect = new Pray.FireworkEffectPlayer();
                        FireworkEffect fe = FireworkEffect.builder().with(FireworkEffect.Type.BALL).withColor(Color.GREEN).build();
                        effect.playFirework(player.getWorld(), player.getLocation(), fe);
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
    }
    private class FireworkEffectPlayer
    {
        private Method world_getHandle = null;
        private Method nms_world_broadcastEntityEffect = null;
        private Method firework_getHandle = null;

    private FireworkEffectPlayer()
    {
    }

    public void playFirework(World world, Location loc, FireworkEffect fe)
      throws Exception
    {
      Firework fw = (Firework)world.spawn(loc, Firework.class);

      Object nms_world = null;
      Object nms_firework = null;

      if (this.world_getHandle == null)
      {
        this.world_getHandle = getMethod(world.getClass(), "getHandle");
        this.firework_getHandle = getMethod(fw.getClass(), "getHandle");
      }

      nms_world = this.world_getHandle.invoke(world, (Object[])null);
      nms_firework = this.firework_getHandle.invoke(fw, (Object[])null);

      if (this.nms_world_broadcastEntityEffect == null)
      {
        this.nms_world_broadcastEntityEffect = getMethod(nms_world.getClass(), "broadcastEntityEffect");
      }

      FireworkMeta data = fw.getFireworkMeta();

      data.clearEffects();

      data.setPower(1);

      data.addEffect(fe);

      fw.setFireworkMeta(data);

      this.nms_world_broadcastEntityEffect.invoke(nms_world, new Object[] { nms_firework, Byte.valueOf("17") });

      fw.remove();
    }

    private Method getMethod(Class<?> cl, String method)
    {
      for (Method m : cl.getMethods()) {
        if (m.getName().equals(method)) {
          return m;
        }
      }
      return null;
    }
  }
    
}
