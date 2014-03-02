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

/**
 *
 * @author ferrago
 */
public class Fireball extends ClassSkill implements SkillShot, Listener
{
    public static final String NAME = "Fireball";
    public JavaPlugin plugin;
    private final Map<Snowball, Long> fireballs = new LinkedHashMap(100); 
    private static final long serialVersionUID = 4329526013158603250L;
    public double damage;
    public int level;
    public ArrayList<Player> throwers;
    public ArrayList<Location> explosionLoc;
    
    public Fireball(JavaPlugin plugin)
    {
        super(NAME, SkillType.SKILL_SHOT, Material.FIRE, 1);
        this.plugin = plugin;
        throwers = new ArrayList();
        explosionLoc = new ArrayList();
        description.add("fill in, in yml");
        setAttribute(SkillAttribute.LEVEL, 1, 0);
        setAttribute("Velocity", 3, 0);
        setAttribute("Fire-Ticks", 100, 0);
        setAttribute("Damage", 3, 1);
        setAttribute("Block-Explode", 0, 0);
    }

    @Override
    public boolean cast(Player playerIn, int level) 
    {
    Player player = playerIn;
    throwers.add(player);
    Snowball fireball = (Snowball)player.launchProjectile(Snowball.class);
    fireball.setFireTicks(100);
    this.fireballs.put(fireball, Long.valueOf(System.currentTimeMillis()));
    double mult = getAttribute("Velocity", level);
    damage = getAttribute("Damage", level);
    this.level = level;
    fireball.setVelocity(fireball.getVelocity().multiply(2));
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
      if ((!(projectile instanceof Snowball)) || (!Fireball.this.fireballs.containsKey(projectile))) {
        return;
      }
      Fireball.this.fireballs.remove(projectile);
      LivingEntity entity = (LivingEntity)subEvent.getEntity();
      Entity dmger = ((Snowball)projectile).getShooter();


      if ((dmger instanceof Player)) 
      {
        Player player = (Player)dmger;
        PlayerSkills pskills = new PlayerSkills((SkillAPI)plugin, player.getDisplayName());
        entity.setFireTicks((int)getAttribute("Fire-Ticks", pskills.getLevel()));
        if (event.getEntity() instanceof Player)
        {
            Player target = (Player)event.getEntity();
            target.damage(damage);
            target.sendMessage(player.getDisplayName() + "hit you for " + damage + " damage with a fireball!");
            player.sendMessage("You hit " + target.getDisplayName() + "for " + damage + " damage with a fireball!");
            event.setCancelled(true);
        }
        else if (event.getEntity() instanceof Creature)
        {
            Creature target = (Creature)event.getEntity();
            target.damage(damage);
            player.sendMessage("You hit " + target.getCustomName() + " for " + damage + " damage with a fireball!");
            fireballs.clear();
            event.setCancelled(true);
        }
            
      }
    }
    
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event)
    {
        if (event.getEntityType() == EntityType.SNOWBALL && (throwers.contains((Player)event.getEntity().getShooter())) && level == api.getSkill("Fireball").getMaxLevel())
        {
            event.getEntity().getWorld().createExplosion(event.getEntity().getLocation().getX(), event.getEntity().getLocation().getY(), event.getEntity().getLocation().getZ(), 2, false, false);
            throwers.remove((Player)event.getEntity().getShooter());
        }
    }
    
    @EventHandler
    public void onExplosion(EntityExplodeEvent event)
    {
        if (explosionLoc.contains(event.getLocation()))
        {
            event.setCancelled(true);
        }
    }
    
    
    
}
