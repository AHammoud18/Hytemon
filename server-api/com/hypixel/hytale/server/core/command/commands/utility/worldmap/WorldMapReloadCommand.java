/*    */ package com.hypixel.hytale.server.core.command.commands.utility.worldmap;
/*    */ 
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldmap.IWorldMap;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapLoadException;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class WorldMapReloadCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 20 */   private static final Message MESSAGE_COMMANDS_WORLD_MAP_CLEAR_IMAGES = Message.translation("server.commands.worldmap.clearimages");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldMapReloadCommand() {
/* 26 */     super("reload", "server.commands.worldmap.reload.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*    */     try {
/* 32 */       IWorldMap worldMap = world.getWorldConfig().getWorldMapProvider().getGenerator(world);
/* 33 */       world.getWorldMapManager().setGenerator(worldMap);
/*    */       
/* 35 */       context.sendMessage(MESSAGE_COMMANDS_WORLD_MAP_CLEAR_IMAGES);
/* 36 */     } catch (WorldMapLoadException e) {
/* 37 */       HytaleLogger.getLogger().at(Level.SEVERE).log("Failed to reload world map for world " + world.getName(), e);
/* 38 */       context.sendMessage(Message.translation("server.commands.worldmap.reloadFailed")
/* 39 */           .param("error", e.getMessage()));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\worldmap\WorldMapReloadCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */