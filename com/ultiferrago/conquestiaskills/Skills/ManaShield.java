/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ultiferrago.conquestiaskills.Skills;

import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.PlayerSkills;
import com.sucy.skill.api.skill.ClassSkill;
import com.sucy.skill.api.skill.SkillType;
import com.sucy.skill.api.skill.TargetSkill;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 *
 * @author ferrago
 */
public class ManaShield extends ClassSkill implements TargetSkill, Listener
{
    public final static String NAME = "Mana Shield";
    public HashMap shielded = new HashMap<Player,Long>();
    public HashMap shielders = new HashMap<Player,Player>();
    public SkillAPI plugin;
    public int LEVEL;
    
    public ManaShield(SkillAPI plugin)
    {
        super(NAME, SkillType.TARGET, Material.DIAMOND_CHESTPLATE, 1);
        setAttribute("Shield-Duration", 60, 0);
        setAttribute("Shield-Percent", 80, 2);
        setAttribute("Mana-Drain-Percent", 100, -5);
        this.plugin = plugin;
        
   
        
    }

    @Override
    public boolean cast(Player player, LivingEntity target, int level, boolean ally) {
        if ((target instanceof Player) && ally)
        {
           LEVEL = level;
           shielded.put(target, System.currentTimeMillis());
           shielders.put(target, player);
           Player targetPlayer = (Player)target;
           if ((Player)target == player)
           {
               player.sendMessage(ChatColor.GREEN + "You use your mana to shield yourself");
           }
           else
           {
                targetPlayer.sendMessage(ChatColor.RED + player.getName() + ChatColor.RESET + ChatColor.GREEN + " is temporarily shielding you");
           }
           return true;
        }
        else
        {
            return false;
        }
    }
    
    @EventHandler()
    public void onEntityDamage(EntityDamageByEntityEvent event)
    {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player && shielded.containsKey((Player)event.getEntity()) && (System.currentTimeMillis() - (long)shielded.get((Player)event.getEntity()) < (getAttribute("Shield-Duration", LEVEL) * 1000)))
        {
            Player shielder = (Player)shielders.get((Player)event.getEntity());
            Player shieldedPlayer = (Player)event.getEntity();
            PlayerSkills shielderSkills = plugin.getPlayer(shielder.getName());
            if (shielderSkills.getMana() < event.getDamage())
            {
                shielders.remove(shielder);
                shielded.remove(shieldedPlayer);
                if (shielded.equals(shielder))
                {
                    shielder.sendMessage(ChatColor.DARK_RED + "You are out of mana, and your shield has faded!");
                }
                else
                {
                    shielder.sendMessage(ChatColor.DARK_RED + "Your shielder is out of mana, and your shield has faded!");
                }
            }
            else
            {
                if (LEVEL == plugin.getSkill("Mana Shield").getMaxLevel())
                {
                    event.getEntity().getWorld().playEffect(event.getEntity().getLocation(), Effect.MOBSPAWNER_FLAMES, 2);
                }
                if (event.getDamager() instanceof Player)
                {
                    Player damager = (Player)event.getDamager();
                    damager.sendMessage(ChatColor.RED + "" + (int)(event.getDamage() * (getAttribute("Shield-Percent", LEVEL)/100.00)) + ChatColor.BLUE + " damage was absorbed by" + ChatColor.AQUA +  "Mana Shield");
                    if (shielder.equals(shieldedPlayer))
                    {
                        shielder.sendMessage(ChatColor.BLUE + "Your " + ChatColor.AQUA + "Mana Shield" + ChatColor.BLUE + "absorbed " + ChatColor.RED + "" + (int)((event.getDamage() * getAttribute("Shield-Percent", LEVEL))/100.00) + ChatColor.BLUE + " damage at the cost of " + ChatColor.AQUA + "" + (int)(event.getDamage() * (getAttribute("Mana-Drain-Percent", LEVEL)/100.00)) + " mana");
               
                    }
                    else
                    {
                        shielder.sendMessage(ChatColor.BLUE + "You used " + ChatColor.AQUA + "" + (int)(event.getDamage() * (getAttribute("Mana-Drain-Percent", LEVEL)/100.00)) + " mana" + ChatColor.BLUE + " to absorb" + ChatColor.RED + "" + (int)((event.getDamage() * getAttribute("Shield-Percent", LEVEL))/100.00) + " damage " + ChatColor.BLUE + "for " + ChatColor.AQUA + shieldedPlayer.getName());
                        shieldedPlayer.sendMessage(ChatColor.AQUA + "Mana Shield" + ChatColor.BLUE + " absorbed " + ChatColor.RED + "" + (int)((event.getDamage() * getAttribute("Shield-Percent", LEVEL))/100.00) + " damage");
                    }
                }
                event.setDamage(event.getDamage() * ((double)getAttribute("Shield-Percent", LEVEL)/100.00));
                shielderSkills.gainMana(0 - (int)(event.getDamage() * (getAttribute("Mana-Drain-Percent", LEVEL)/100)));

            }
            
        }
        else if ((event.getEntity() instanceof Player) && shielded.containsKey((Player)event.getEntity()) &&  (System.currentTimeMillis() - (long)shielded.get((Player)event.getEntity()) > (getAttribute("Shield-Duration", LEVEL) * 1000)))
        {
            shielded.remove((Player)event.getEntity());
            shielders.remove((Player)shielders.get((Player)event.getEntity()));
            Player message1 = (Player)(event.getEntity());
            Player message2 = (Player)shielders.get((Player)event.getEntity());
            message1.sendMessage(ChatColor.BLUE + "Mana shield has faded.");
            message2.sendMessage(ChatColor.BLUE + "Mana shield has faded.");
        }
    }
    
    @EventHandler()
    public void onEntityDeath(EntityDeathEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            if (shielded.containsKey((Player)event.getEntity()) || shielders.containsKey((Player)event.getEntity()))
            {
                if (shielders.get((Player)event.getEntity()).equals((Player)event.getEntity()))
                {
                    shielded.remove((Player)event.getEntity());
                    shielders.remove((Player)event.getEntity());
                }
                else if (shielders.containsKey((Player)event.getEntity()))
                {
                    Player shielderGuy = (Player)shielders.get((Player)event.getEntity());
                    Player dead = (Player)event.getEntity();
                    shielderGuy.sendMessage(ChatColor.BLUE + "Your shield has faded from " + dead.getName());
                }
                else
                {
                    for (Object shieldedGuy : shielders.keySet())
                    {
                        if (shielders.get((Player)shieldedGuy).equals((Player)event.getEntity()))
                        {
                            Player shieldedDude = (Player)shieldedGuy;
                            shieldedDude.sendMessage(ChatColor.BLUE + "Your shield has faded.");
                        }
                    }
                     
                    
                }
            }
        }
    }
    

    
}
