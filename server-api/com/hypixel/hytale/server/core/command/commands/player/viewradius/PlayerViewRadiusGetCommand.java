/*    */ package com.hypixel.hytale.server.core.command.commands.player.viewradius;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerViewRadiusGetCommand
/*    */   extends AbstractTargetPlayerCommand
/*    */ {
/*    */   public PlayerViewRadiusGetCommand() {
/* 26 */     super("get", "server.commands.player.viewradius.get.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 31 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 32 */     assert playerComponent != null;
/*    */     
/* 34 */     EntityTrackerSystems.EntityViewer entityViewerComponent = (EntityTrackerSystems.EntityViewer)store.getComponent(ref, EntityTrackerSystems.EntityViewer.getComponentType());
/* 35 */     assert entityViewerComponent != null;
/*    */     
/* 37 */     int viewRadiusChunks = playerComponent.getViewRadius();
/* 38 */     int clientViewRadiusChunks = playerComponent.getClientViewRadius();
/* 39 */     int viewRadiusBlocks = entityViewerComponent.viewRadiusBlocks;
/*    */     
/* 41 */     context.sendMessage(Message.translation("server.commands.player.viewradius.info")
/* 42 */         .param("radius", viewRadiusChunks)
/* 43 */         .param("clientRadius", clientViewRadiusChunks)
/* 44 */         .param("radiusBlocks", viewRadiusBlocks));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\viewradius\PlayerViewRadiusGetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */