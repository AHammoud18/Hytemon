/*    */ package com.hypixel.hytale.server.spawning.commands;
/*    */ 
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class DumpAll
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 82 */   private static final Message MESSAGE_COMMANDS_SPAWNING_SUPPRESSION_DUMP_DUMPED = Message.translation("server.commands.spawning.suppression.dump.dumped");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DumpAll() {
/* 88 */     super("dumpall", "server.commands.spawning.suppression.dump.all.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 93 */     Universe.get().getWorlds().values().forEach(w -> ((HytaleLogger.Api)SpawningPlugin.get().getLogger().atInfo()).log(SpawnSuppressionCommand.dumpWorld(w)));
/* 94 */     context.sendMessage(MESSAGE_COMMANDS_SPAWNING_SUPPRESSION_DUMP_DUMPED);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\commands\SpawnSuppressionCommand$DumpAll.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */