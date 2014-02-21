package com.ultiferrago.conquestiaskills.command.admin;

import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.CustomClass;
import com.sucy.skill.api.PlayerSkills;
import com.sucy.skill.command.ICommand;
import com.sucy.skill.api.event.PlayerExperienceGainEvent;
import com.sucy.skill.command.CommandHandler;
import com.sucy.skill.command.ICommand;
import com.sucy.skill.command.SenderType;
import com.ultiferrago.conquestiaskills.ConquestiaSkills;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class CmdBonusXp implements ICommand
{
    ConquestiaSkills cq;
    long time;
    double factor;
  public void execute(CommandHandler handler, Plugin plugin, CommandSender sender, String[] args)
  {
    time = 0;
    factor = 0;
    cq = (ConquestiaSkills)plugin;
    ConquestiaSkills CqSkills = (ConquestiaSkills)plugin;
    int amount;
    if (args.length >= 2)
    {
      try {
      String timeString = args[0];
      long timeTemp = Long.parseLong(timeString) * 3600 * 1000;
      String factorString = args[1];
      double factorTemp = Double.parseDouble(factorString);
      time = timeTemp;
      factor = factorTemp;
      
      }
      catch (Exception ex)
      {
      }

      if (factor <= 0 || time <= 0) {
        String error = ChatColor.RED + "Error, bad arguments";
        sender.sendMessage(error);
      }
      else
      {
        cq.doubleXp(System.currentTimeMillis(),this.time, this.factor);
      }
    }
    else
    {
      handler.displayUsage(sender);
    }
  }

  public String getPermissionNode()
  {
    return "conquestiaskills.admin";
  }

  public String getArgsString(Plugin plugin)
  {
    return "<time> <factor>";
  }

  public String getDescription(Plugin plugin)
  {
    return "Provides the server with bonus xp for a given time period";
  }

  public SenderType getSenderType()
  {
    return SenderType.ANYONE;
  }
}
