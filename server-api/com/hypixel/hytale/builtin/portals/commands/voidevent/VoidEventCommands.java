/*   */ package com.hypixel.hytale.builtin.portals.commands.voidevent;
/*   */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*   */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*   */ 
/*   */ public class VoidEventCommands extends AbstractCommandCollection {
/*   */   public VoidEventCommands() {
/* 7 */     super("voidevent", "server.commands.voidevent.desc");
/* 8 */     addSubCommand((AbstractCommand)new StartVoidEventCommand());
/*   */   }
/*   */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\commands\voidevent\VoidEventCommands.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */