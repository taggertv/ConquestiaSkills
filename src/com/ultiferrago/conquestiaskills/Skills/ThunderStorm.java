package com.ultiferrago.conquestiaskills.Skills;

import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.*;
import com.sucy.skill.api.util.*;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import com.sucy.skill.api.skill.ClassSkill;
import com.sucy.skill.api.skill.SkillAttribute;
import com.sucy.skill.api.skill.SkillShot;
import com.sucy.skill.api.skill.SkillType;
import java.lang.reflect.Method;
import java.util.ArrayList;

import java.util.List;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Damages and slightly knocks back all nearby enemies
 */
public class ThunderStorm extends ClassSkill implements SkillShot {

    public static final String NAME = "Thunderstorm";
    private static final String
        SPEED = "Speed",
        DAMAGE = "Damage",
        RADIUS = "Radius";

    //private final SkillPack plugin;

    /**
     * Constructor
     *
     * @param plugin plugin reference
     */
    public ThunderStorm() {
        super(NAME, SkillType.SKILL_SHOT_AOE, Material.STRING, 3);
        //this.pl = plugin;

        description.add("Need to change + rename skill");

        setAttribute(SkillAttribute.COOLDOWN, 15, 0);
        setAttribute(SkillAttribute.COST, 3, 0);
        setAttribute(SkillAttribute.LEVEL, 15, 2);
        setAttribute(SkillAttribute.MANA, 20, 0);
        setAttribute(SPEED, 1, 0);
        setAttribute(DAMAGE, 3, 1);
        setAttribute(RADIUS, 4, 1);
    }

    /**
     * Damages and knocks back all nearby enemies
     *
     * @param player player casting the skill
     * @param i      skill level
     * @return       true if hit at least one enemy, false otherwise
     */
    @Override
    public boolean cast(Player player, int level) {
    SkillAPI api = this.getAPI();
    PlayerSkills pSkills = new PlayerSkills(api, player.getName());

    int radius = (int) getAttribute(RADIUS, level);
    List<Entity> list = player.getNearbyEntities(radius, radius, radius);
    int damage = (int) getAttribute(DAMAGE, level);
    
    Location playerLocation = player.getLocation();
    
    List<Location> circularOutline = ThunderStorm.circle(player, playerLocation, radius, 1, true, false, 3);
    ThunderStorm.FireworkEffectPlayer boom = new ThunderStorm.FireworkEffectPlayer();
    
    FireworkEffect fe = FireworkEffect.builder().with(FireworkEffect.Type.STAR).withColor(Color.BLUE).withColor(Color.BLACK).withFade(Color.GRAY).build();
    for (Location x : circularOutline) {
      try {
        boom.playFirework(player.getWorld(), x, fe); 
      }
      catch (Exception e) {
          return false;
      }
    }
    for (int i = 0; i < list.size(); i++)
    {
        list.get(i).getWorld().strikeLightning(list.get(i).getLocation());
        list.get(i).getWorld().strikeLightningEffect(list.get(i).getLocation());
    }
    return true;
  }
      public static List<Location> circle (Player player, Location loc, Integer r, Integer h, Boolean hollow, Boolean sphere, int plus_y) {
        List<Location> circleblocks = new ArrayList<Location>();
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        for (int x = cx - r; x <= cx +r; x++)
            for (int z = cz - r; z <= cz +r; z++)
                for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r*r && !(hollow && dist < (r-1)*(r-1))) {
                        Location l = new Location(loc.getWorld(), x, y + plus_y, z);
                        circleblocks.add(l);
                        }
                    }
     
        return circleblocks;
    }

private class FireworkEffectPlayer {

    
    // internal references, performance improvements
    private Method world_getHandle = null;
    private Method nms_world_broadcastEntityEffect = null;
    private Method firework_getHandle = null;
    
    public void playFirework(World world, Location loc, FireworkEffect fe) throws Exception {
        // Bukkity load (CraftFirework)
        Firework fw = (Firework) world.spawn(loc, Firework.class);
        // the net.minecraft.server.World
        Object nms_world = null;
        Object nms_firework = null;
        /*
         * The reflection part, this gives us access to funky ways of messing around with things
         */
        if(world_getHandle == null) {
            // get the methods of the craftbukkit objects
            world_getHandle = getMethod(world.getClass(), "getHandle");
            firework_getHandle = getMethod(fw.getClass(), "getHandle");
        }
        // invoke with no arguments
        nms_world = world_getHandle.invoke(world, (Object[]) null);
        nms_firework = firework_getHandle.invoke(fw, (Object[]) null);
        // null checks are fast, so having this seperate is ok
        if(nms_world_broadcastEntityEffect == null) {
            // get the method of the nms_world
            nms_world_broadcastEntityEffect = getMethod(nms_world.getClass(), "broadcastEntityEffect");
        }
        /*
         * Now we mess with the metadata, allowing nice clean spawning of a pretty firework (look, pretty lights!)
         */
        // metadata load
        FireworkMeta data = (FireworkMeta) fw.getFireworkMeta();
        // clear existing
        data.clearEffects();
        // power of one
        data.setPower(1);
        // add the effect
        data.addEffect(fe);
        // set the meta
        fw.setFireworkMeta(data);
        /*
         * Finally, we broadcast the entity effect then kill our fireworks object
         */
        // invoke with arguments
        nms_world_broadcastEntityEffect.invoke(nms_world, new Object[] {nms_firework, (byte) 17});
        // remove from the game
        fw.remove();
    }
    

    private Method getMethod(Class<?> cl, String method) {
        for(Method m : cl.getMethods()) {
            if(m.getName().equals(method)) {
                return m;
            }
        }
        return null;
    }
}
}
