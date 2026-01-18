/*    */ package com.hypixel.hytale.builtin.path.commands;
/*    */ 
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
/*    */ public class WorldPathSaveCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 17 */   private static final Message MESSAGE_UNIVERSE_WORLD_PATH_CONFIG_SAVED = Message.translation("server.universe.worldpath.configSaved");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldPathSaveCommand() {
/* 23 */     super("save", "server.commands.worldpath.save.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 28 */     world.getWorldPathConfig().save(world);
/* 29 */     context.sendMessage(MESSAGE_UNIVERSE_WORLD_PATH_CONFIG_SAVED);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\commands\WorldPathSaveCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */