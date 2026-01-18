/*    */ package com.hypixel.hytale.server.core.universe.world.commands.worldconfig;
/*    */ 
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Constants;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldPauseCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 18 */   private static final Message MESSAGE_COMMANDS_PAUSE_TOO_MANY_PLAYERS = Message.translation("server.commands.pause.tooManyPlayers");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldPauseCommand() {
/* 24 */     super("pause", "server.commands.pause.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 29 */     if (world.getPlayerCount() != 1 || !Constants.SINGLEPLAYER) {
/* 30 */       context.sendMessage(MESSAGE_COMMANDS_PAUSE_TOO_MANY_PLAYERS);
/*    */       
/*    */       return;
/*    */     } 
/* 34 */     world.setPaused(!world.isPaused());
/* 35 */     context.sendMessage(Message.translation("server.commands.pause.updated")
/* 36 */         .param("state", Message.translation(world.isPaused() ? "server.commands.pause.paused" : "server.commands.pause.unpaused")));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\worldconfig\WorldPauseCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */