/*    */ package com.hypixel.hytale.builtin.adventure.memories.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.memories.MemoriesPlugin;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MemoriesLevelCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   public MemoriesLevelCommand() {
/* 22 */     super("level", "server.commands.memories.level.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 27 */     int level = MemoriesPlugin.get().getMemoriesLevel(world.getGameplayConfig());
/* 28 */     context.sendMessage(Message.translation("server.commands.memories.level.success")
/* 29 */         .param("level", level));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\memories\commands\MemoriesLevelCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */