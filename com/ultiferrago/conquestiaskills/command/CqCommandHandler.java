/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ultiferrago.conquestiaskills.command;

import com.sucy.skill.command.CommandHandler;
import com.ultiferrago.conquestiaskills.command.admin.CmdBonusXp;
import com.ultiferrago.conquestiaskills.command.normal.CmdShowBonusTime;
import org.bukkit.plugin.Plugin;

public class CqCommandHandler extends CommandHandler {

    public CqCommandHandler(Plugin plugin) {

        // This sets up your usage so that it shows "My Commands - Command Usage (Page 1/1)"
        // The last string is what your command is. This would be for "/command"
        super(plugin, "Conquestia Commands", "cq");
    }

    @Override
    public void registerCommands() {
      registerCommand("bonus", new CmdBonusXp());
      registerCommand("time", new CmdShowBonusTime());
    }
}
