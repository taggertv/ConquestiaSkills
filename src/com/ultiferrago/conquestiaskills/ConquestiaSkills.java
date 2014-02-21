/*
 * 
 * and open the template in the editor.
 */
package com.ultiferrago.conquestiaskills;

import com.garbagemule.MobArena.events.ArenaPlayerJoinEvent;
import com.garbagemule.MobArena.events.ArenaPlayerLeaveEvent;
import com.garbagemule.MobArena.events.NewWaveEvent;
import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.SkillPlugin;
import com.sucy.skill.api.event.PlayerExperienceGainEvent;
import com.sucy.skill.api.util.TextSizer;
import com.ultiferrago.conquestiaskills.Classes.Acolyte;
import com.ultiferrago.conquestiaskills.Classes.Assassin;
import com.ultiferrago.conquestiaskills.Classes.Bannerman;
import com.ultiferrago.conquestiaskills.Classes.Berserker;
import com.ultiferrago.conquestiaskills.Classes.Bishop;
import com.ultiferrago.conquestiaskills.Classes.Commander;
import com.ultiferrago.conquestiaskills.Classes.Druid;
import com.ultiferrago.conquestiaskills.Classes.Hunter;
import com.ultiferrago.conquestiaskills.Classes.Knight;
import com.ultiferrago.conquestiaskills.Classes.Mercenary;
import com.ultiferrago.conquestiaskills.Classes.Paladin;
import com.ultiferrago.conquestiaskills.Classes.Priest;
import com.ultiferrago.conquestiaskills.Classes.Pupil;
import com.ultiferrago.conquestiaskills.Classes.Ranger;
import com.ultiferrago.conquestiaskills.Classes.Renegade;
import com.ultiferrago.conquestiaskills.Classes.Rogue;
import com.ultiferrago.conquestiaskills.Classes.Sage;
import com.ultiferrago.conquestiaskills.Classes.Shaman;
import com.ultiferrago.conquestiaskills.Classes.Soldier;
import com.ultiferrago.conquestiaskills.Classes.Tester;
import com.ultiferrago.conquestiaskills.Classes.Trapper;
import com.ultiferrago.conquestiaskills.Classes.Woodsman;
import com.ultiferrago.conquestiaskills.Skills.Mark;
import com.ultiferrago.conquestiaskills.Skills.Whirlwind;
import com.ultiferrago.conquestiaskills.Skills.Recall;
import com.ultiferrago.conquestiaskills.Skills.ThunderStorm;
import com.ultiferrago.conquestiaskills.Config.Config;
import com.ultiferrago.conquestiaskills.command.CqCommandHandler;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;


/**
 *
 * @author ferrago
 */

public class ConquestiaSkills extends JavaPlugin implements SkillPlugin, Listener {
    private Config languageConfig;
    private boolean doubleXp = false;
    private double factor;
    private long timeLength;
    private long savedTime;
    private ArrayList<Player> playersInArena;
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        new CqCommandHandler(this);
        factor = 1;
        timeLength = 0;
        savedTime = 0;
        this.languageConfig = new Config(this, "language");
        this.languageConfig.saveDefaultConfig();
        reloadConfig();
        Config PlayerConfig = new Config(this, "players"); //unused as of now.
        Config markConfig = new Config(this, "Marks" + File.separator);
    }
    public JavaPlugin getPlugin() {
        return this;
    }
    public void doubleXp(long currentTimeIn, long timeLengthIn, double factorIn)
    {
        this.factor = factorIn;
        this.savedTime = currentTimeIn;
        this.timeLength = timeLengthIn;
    }
    public String getDoubleXpTimeLeft()
    {
        if ((System.currentTimeMillis() - savedTime) > timeLength)
        {
            return " no ";
        }
        else
        {
            return " " + ((((timeLength - (System.currentTimeMillis() - savedTime))/1000)/60)/60) + " ";
        }
    }
    public double getBonusXpFactor()
    {
        return factor;
    }
    @Override
    public void registerSkills(SkillAPI api) {
        api.addSkills(
           new Whirlwind(),
           new Mark(getPlugin()),
           new Recall(getPlugin()),
           new ThunderStorm()
                );
        
    }

    @Override
    public void registerClasses(SkillAPI api) {
        api.addClasses(
                //Admin Only Classes
                new Tester(),
                //Melee Classes
                new Soldier(), //Melee Base
                new Mercenary(), //Melee Dps 1
                new Berserker(), //Melee Dps 2
                new Bannerman(), //Melee Support 1
                new Commander(), //Melee Support 2
                new Knight(), //Melee Tank 1
                new Paladin(), //Melee Tank 2
                //Ranged Classes
                new Hunter(), //Range Base
                new Rogue(), //Range Dps 1
                new Assassin(), //Range Dps 2
                new Trapper(), //Range Sup 1
                new Ranger(), //Range Sup 2
                new Woodsman(), //Range Tank 1
                new Renegade(), //Range Tank 2
                //Magic Classes
                new Pupil(), // Magic Base
                new Acolyte(), //Magic Dps 1
                new Sage(), //Magic Dps 2
                new Priest(), //Magic Support 1
                new Bishop(), //Magic Support 2
                new Shaman(), //Magic Tank 1
                new Druid() //Magic Tank 2
                );
    }
    //Method from SkillsAPI
    public String getMessage(String path, boolean applyFilters) {
    String message = this.languageConfig.getConfig().getString(path);

    if (message == null) {
      return message;
    }

    if (applyFilters) {
      message = applyFilters(message);
    }

    return message;
  }
  //Method from SkillsAPI 
  public String applyFilters(String message) {
    message = message.replaceAll("&([0-9a-fl-orA-FL-OR])", "§$1");

    message = message.replace("{break}", TextSizer.createLine("", "", "-"));

    message = filterSizer(message, true);
    message = filterSizer(message, false);

    return message;
  }
  //Method from SkillsAPI
  private String filterSizer(String message, boolean front)
  {
    Pattern regex = Pattern.compile(new StringBuilder().append("\\{expand").append(front ? "Front" : "Back").append("\\(([0-9]+),(.+)\\)\\}").toString());
    Matcher match = regex.matcher(message);
    int size = message.length();
    while (match.find()) {
      int length = Integer.parseInt(match.group(1));
      String string = match.group(2);
      message = new StringBuilder().append(message.substring(0, match.start() + message.length() - size)).append(TextSizer.measureString(string) > length - 2 ? string : TextSizer.expand(string, length, front)).append(message.substring(match.end())).toString();
    }

    return message;
  }
  @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=false)
    public void onExpChange(PlayerExperienceGainEvent event)
    {
        if (!event.isCommandExp())
        {
             if ((System.currentTimeMillis() - savedTime) < timeLength) {
                event.setExp((int)(event.getExp()*this.factor));
            }
            if (playersInArena.contains(event.getPlayerData().getPlayer()))
            {
                event.setExp((int)(event.getExp()*0.25));
            }
            event.getPlayerData().getPlayer().sendMessage("You gained " + event.getExp() + " xp");
        }
    }
  
  @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=false)
  public void onPlayerJoin(PlayerJoinEvent event) throws InterruptedException
  {
    if (((System.currentTimeMillis() - savedTime) < timeLength))
    {
        event.getPlayer().sendMessage(ChatColor.RED +"ATTENTION! " + ChatColor.BLUE + "There is a bonus xp time event happening now! Use /cq time to check remaining time!");
    }
  }
  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=false)
  public void onPlayerJoinArena(ArenaPlayerJoinEvent event)
  {
      playersInArena.add(event.getPlayer());
  }
  @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=false)
  public void onPlayerLeaveArena(ArenaPlayerLeaveEvent event)
  {
      playersInArena.remove(event.getPlayer());
  }
}





