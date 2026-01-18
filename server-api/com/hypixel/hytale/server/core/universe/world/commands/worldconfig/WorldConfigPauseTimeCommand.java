/*    */ package com.hypixel.hytale.server.core.universe.world.commands.worldconfig;
/*    */ 
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldConfig;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldConfigPauseTimeCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   public WorldConfigPauseTimeCommand() {
/* 23 */     super("pausetime", "server.commands.pausetime.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 28 */     pauseTime(context.sender(), world, store);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void pauseTime(@Nonnull CommandSender commandSender, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 39 */     WorldTimeResource worldTimeResource = (WorldTimeResource)store.getResource(WorldTimeResource.getResourceType());
/*    */     
/* 41 */     boolean timePause = !world.getWorldConfig().isGameTimePaused();
/*    */     
/* 43 */     WorldConfig worldConfig = world.getWorldConfig();
/* 44 */     worldConfig.setGameTimePaused(timePause);
/* 45 */     worldConfig.markChanged();
/*    */     
/* 47 */     Message timePausedMessage = Message.translation(timePause ? "server.general.paused" : "server.general.resumed");
/* 48 */     commandSender.sendMessage(Message.translation("server.commands.pausetime.timeInfo")
/* 49 */         .param("msg", timePausedMessage)
/* 50 */         .param("worldName", world.getName())
/* 51 */         .param("time", worldTimeResource.getGameTime().toString()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\worldconfig\WorldConfigPauseTimeCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */