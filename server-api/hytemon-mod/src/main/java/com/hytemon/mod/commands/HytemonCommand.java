package com.hytemon.mod.commands;

import com.hytemon.mod.HytemonPlugin;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import javax.annotation.Nonnull;

public class HytemonCommand extends AbstractCommandCollection {
  public HytemonCommand(@Nonnull HytemonPlugin plugin) {
    super("hytemon", "Hytemon mod helper commands");

    addAliases(new String[] { "htm" });
    addSubCommand(new HytemonGiveCommand(plugin));
    addSubCommand(new HytemonCaptureCommand(plugin));
    addSubCommand(new HytemonBattleCommand(plugin));
    addSubCommand(new HytemonSpawnTraderCommand(plugin));
  }
}
