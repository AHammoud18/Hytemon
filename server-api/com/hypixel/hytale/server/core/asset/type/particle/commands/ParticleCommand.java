/*    */ package com.hypixel.hytale.server.core.asset.type.particle.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParticleCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public ParticleCommand() {
/* 14 */     super("particle", "server.commands.particle.desc");
/* 15 */     addSubCommand((AbstractCommand)new ParticleSpawnCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\particle\commands\ParticleCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */