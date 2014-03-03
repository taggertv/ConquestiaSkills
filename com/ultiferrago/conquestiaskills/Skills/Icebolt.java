/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ultiferrago.conquestiaskills.Skills;

import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.PlayerSkills;
import com.sucy.skill.api.skill.ClassSkill;
import com.sucy.skill.api.skill.SkillAttribute;
import com.sucy.skill.api.skill.SkillShot;
import com.sucy.skill.api.skill.SkillType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author ferrago
 */
public class Icebolt extends ClassSkill implements SkillShot, Listener
{
    public static final String NAME = "Icebolt";
    public JavaPlugin plugin;
    private final Map<Snowball, Long> snowballs = new LinkedHashMap(100); 
    private static final long serialVersionUID = 4329526013158603250L;
    public double damage;
    public int level;
    public ArrayList<Player> throwers;
    
    public Icebolt(JavaPlugin plugin)
    {
        super(NAME, SkillType.SKILL_SHOT, Material.SNOW_BALL, 1);
        this.plugin = plugin;
        throwers = new ArrayList();
        description.add("fill in, in yml");
        setAttribute("Velocity", 3, 0);
        setAttribute("Damage", 3, 1);
        setAttribute("Slow-Duration", 10, 0);
        setAttribute("Speed-Mult", 2, 0);
        setAttribute("Ambient", 1, 0);
        setAttribute(SkillAttribute.MANA, 10, 2);
        setAttribute(SkillAttribute.COOLDOWN, 0, 0);
        setAttribute(SkillAttribute.LEVEL, 0 , 0);
    }

    @Override
    public boolean cast(Player playerIn, int level) 
    {
    Player player = playerIn;
    throwers.add(player);
    Snowball snowball = (Snowball)player.launchProjectile(Snowball.class);
    snowball.setFireTicks(100);
    this.snowballs.put(snowball, Long.valueOf(System.currentTimeMillis()));
    double mult = getAttribute("Velocity", level);
    damage = getAttribute("Damage", level);
    this.level = level;
    snowball.setVelocity(snowball.getVelocity().multiply(2));
    return true;
    }
    
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
      if ((event.isCancelled()) || (!(event instanceof EntityDamageByEntityEvent)) || (!(event.getEntity() instanceof LivingEntity))) {
        return;
      }
      EntityDamageByEntityEvent subEvent = (EntityDamageByEntityEvent)event;
      if ((event.getCause().equals(DamageCause.ENTITY_EXPLOSION)) && event.getEntity() == null)
      {
          event.setCancelled(true);
      }


      Entity projectile = subEvent.getDamager();
      if ((!(projectile instanceof Snowball)) || (!Icebolt.this.snowballs.containsKey(projectile))) {
        return;
      }
      Icebolt.this.snowballs.remove(projectile);
      LivingEntity entity = (LivingEntity)subEvent.getEntity();
      Entity dmger = ((Snowball)projectile).getShooter();


      if ((dmger instanceof Player)) 
      {
        Player player = (Player)dmger;
        PlayerSkills pskills = new PlayerSkills((SkillAPI)plugin, player.getDisplayName());
        if (event.getEntity() instanceof Player)
        {
            Player target = (Player)event.getEntity();
            target.damage(damage);
            int duration = (int)(getAttribute("Slow-Duration", level) * 1000);
            int mult = (int)getAttribute("Speed-Mult", level);
            boolean ambient = getAttribute("Ambient", level) == 1;
            PotionEffect pe = new PotionEffect(PotionEffectType.SLOW, duration, mult, ambient);
            target.addPotionEffect(pe);
            target.sendMessage(player.getDisplayName() + "hit you for " + damage + " damage with an icebolt, and are slowed for " + duration + " seconds!");
            player.sendMessage("You hit " + target.getDisplayName() + "for " + damage + " damage with an icebolt! Slowing them for " + duration + " seconds.");
            event.setCancelled(true);
        }
        else if (event.getEntity() instanceof Creature)
        {
            Creature target = (Creature)event.getEntity();
            target.damage(damage);
            int duration = (int)(getAttribute("Slow-Duration", level) * 1000);
            int mult = (int)getAttribute("Speed-Mult", level);
            boolean ambient = getAttribute("Ambient", level) == 1;
            PotionEffect pe = new PotionEffect(PotionEffectType.SLOW, duration, mult, ambient);
            target.addPotionEffect(pe);
            player.sendMessage("You hit " + target.getCustomName() + " for " + damage + " damage with an icebolt!");
            snowballs.clear();
            event.setCancelled(true);
        }
            
      }
    }
    
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event)
    {
        if (event.getEntityType() == EntityType.SNOWBALL && (throwers.contains((Player)event.getEntity().getShooter())) && level == api.getSkill("Fireball").getMaxLevel())
        {
            throwers.remove((Player)event.getEntity().getShooter());
        }
    }
    
    
    
    
}
