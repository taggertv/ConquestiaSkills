package com.ultiferrago.conquestiaskills.command.normal;

import com.sucy.skill.api.PlayerSkills;
import com.sucy.skill.command.CommandHandler;
import com.sucy.skill.command.ICommand;
import com.sucy.skill.command.SenderType;
import com.ultiferrago.conquestiaskills.ConquestiaSkills;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class CmdShowBonusTime implements ICommand
{
  
  public void execute(CommandHandler handler, Plugin plugin, CommandSender sender, String[] args)
  {
    ConquestiaSkills cq = (ConquestiaSkills)plugin;
    ConquestiaSkills CqSkills = (ConquestiaSkills)plugin;
    if (CqSkills.getDoubleXpTimeLeft() == " no ")
    {
        sender.sendMessage("There is no bonus experience time right now. Donate for bonus xp time at http://conquestiarevolution.com/shop");
    }
    else
    {
        sender.sendMessage("There is" + CqSkills.getDoubleXpTimeLeft() + "hours left of " + cq.getBonusXpFactor() + "x experience");
    }    
   }

  

  public String getPermissionNode()
  {
    return "conquestiaskills.normal";
  }

  public String getArgsString(Plugin plugin)
  {
    return "";
  }

  public String getDescription(Plugin plugin)
  {
    return "Shows amount of bonus time left.";
  }

  public SenderType getSenderType()
  {
    return SenderType.ANYONE;
  }
}
