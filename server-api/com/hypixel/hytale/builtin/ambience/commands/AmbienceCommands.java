/*    */ package com.hypixel.hytale.builtin.ambience.commands;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ public class AmbienceCommands extends AbstractCommandCollection {
/*    */   public AmbienceCommands() {
/*  7 */     super("ambience", "server.commands.ambience.desc");
/*  8 */     addAliases(new String[] { "ambiance" });
/*  9 */     addAliases(new String[] { "ambient" });
/*    */     
/* 11 */     addSubCommand((AbstractCommand)new AmbienceEmitterCommands());
/* 12 */     addSubCommand((AbstractCommand)new AmbienceSetMusicCommand());
/* 13 */     addSubCommand((AbstractCommand)new AmbienceClearCommand());
/*    */   }
/*    */   
/*    */   public static class AmbienceEmitterCommands extends AbstractCommandCollection {
/*    */     public AmbienceEmitterCommands() {
/* 18 */       super("emitter", "server.commands.ambience.emitter.desc");
/*    */       
/* 20 */       addSubCommand((AbstractCommand)new AmbienceEmitterAddCommand());
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\ambience\commands\AmbienceCommands.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */