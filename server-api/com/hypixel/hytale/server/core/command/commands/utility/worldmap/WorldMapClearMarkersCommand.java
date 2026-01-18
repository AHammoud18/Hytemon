/*    */ package com.hypixel.hytale.server.core.command.commands.utility.worldmap;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerWorldData;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class WorldMapClearMarkersCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 20 */   private static final Message MESSAGE_COMMANDS_WORLD_MAP_MARKERS_CLEARED = Message.translation("server.commands.worldmap.markersCleared");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldMapClearMarkersCommand() {
/* 26 */     super("clearmarkers", "server.commands.worldmap.clearmarkers.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 31 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 32 */     assert playerComponent != null;
/*    */     
/* 34 */     PlayerWorldData perWorldData = playerComponent.getPlayerConfigData().getPerWorldData(world.getName());
/* 35 */     perWorldData.setWorldMapMarkers(null);
/*    */     
/* 37 */     context.sendMessage(MESSAGE_COMMANDS_WORLD_MAP_MARKERS_CLEARED);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\worldmap\WorldMapClearMarkersCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */