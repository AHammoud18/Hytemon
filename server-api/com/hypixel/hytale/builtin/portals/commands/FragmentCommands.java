/*   */ package com.hypixel.hytale.builtin.portals.commands;
/*   */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*   */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*   */ 
/*   */ public class FragmentCommands extends AbstractCommandCollection {
/*   */   public FragmentCommands() {
/* 7 */     super("fragment", "server.commands.fragment.desc");
/* 8 */     addSubCommand((AbstractCommand)new TimerFragmentCommand());
/*   */   }
/*   */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\commands\FragmentCommands.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */