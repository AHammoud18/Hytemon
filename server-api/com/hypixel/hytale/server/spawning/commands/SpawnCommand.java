/*    */ package com.hypixel.hytale.server.spawning.commands;
/*    */ 
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldConfig;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpawnCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public SpawnCommand() {
/* 22 */     super("spawning", "server.commands.spawning.desc");
/* 23 */     addAliases(new String[] { "sp" });
/* 24 */     addSubCommand((AbstractCommand)new EnableCommand());
/* 25 */     addSubCommand((AbstractCommand)new DisableCommand());
/* 26 */     addSubCommand((AbstractCommand)new SpawnBeaconsCommand());
/* 27 */     addSubCommand((AbstractCommand)new SpawnMarkersCommand());
/* 28 */     addSubCommand((AbstractCommand)new SpawnPopulateCommand());
/* 29 */     addSubCommand((AbstractCommand)new SpawnStatsCommand());
/* 30 */     addSubCommand((AbstractCommand)new SpawnSuppressionCommand());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static class EnableCommand
/*    */     extends AbstractWorldCommand
/*    */   {
/*    */     public EnableCommand() {
/* 42 */       super("enable", "server.commands.spawning.enable.desc");
/*    */     }
/*    */ 
/*    */     
/*    */     protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 47 */       WorldConfig worldConfig = world.getWorldConfig();
/* 48 */       worldConfig.setSpawningNPC(true);
/* 49 */       worldConfig.markChanged();
/* 50 */       context.sendMessage(Message.translation("server.commands.spawning.enabled")
/* 51 */           .param("worldName", world.getName()));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static class DisableCommand
/*    */     extends AbstractWorldCommand
/*    */   {
/*    */     public DisableCommand() {
/* 64 */       super("disable", "server.commands.spawning.disable.desc");
/*    */     }
/*    */ 
/*    */     
/*    */     protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 69 */       WorldConfig worldConfig = world.getWorldConfig();
/* 70 */       worldConfig.setSpawningNPC(false);
/* 71 */       worldConfig.markChanged();
/* 72 */       context.sendMessage(Message.translation("server.commands.spawning.disabled")
/* 73 */           .param("worldName", world.getName()));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\commands\SpawnCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */