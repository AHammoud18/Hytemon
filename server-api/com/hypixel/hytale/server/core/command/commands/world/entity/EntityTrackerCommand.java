/*    */ package com.hypixel.hytale.server.core.command.commands.world.entity;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class EntityTrackerCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 23 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*    */   @Nonnull
/* 25 */   private static final Message MESSAGE_COMMANDS_ENTITY_TRACKER_NO_VIEWER_COMPONENT = Message.translation("server.commands.entity.tracker.noViewerComponent");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 31 */   private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.entity.tracker.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityTrackerCommand() {
/* 37 */     super("tracker", "server.commands.entity.tracker.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 42 */     PlayerRef playerRef = (PlayerRef)this.playerArg.get(context);
/* 43 */     Ref<EntityStore> ref = playerRef.getReference();
/* 44 */     if (ref == null || !ref.isValid()) {
/* 45 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*    */       
/*    */       return;
/*    */     } 
/* 49 */     EntityTrackerSystems.EntityViewer entityViewerComponent = (EntityTrackerSystems.EntityViewer)store.getComponent(ref, EntityTrackerSystems.EntityViewer.getComponentType());
/* 50 */     if (entityViewerComponent == null) {
/* 51 */       context.sendMessage(MESSAGE_COMMANDS_ENTITY_TRACKER_NO_VIEWER_COMPONENT);
/*    */       
/*    */       return;
/*    */     } 
/* 55 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 56 */     if (playerComponent == null) {
/* 57 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*    */       
/*    */       return;
/*    */     } 
/* 61 */     context.sendMessage(Message.translation("server.commands.entityTracker.summary")
/* 62 */         .param("visibleCount", entityViewerComponent.visible.size())
/* 63 */         .param("lodExcludedCount", entityViewerComponent.lodExcludedCount)
/* 64 */         .param("hiddenCount", entityViewerComponent.hiddenCount)
/* 65 */         .param("totalCount", entityViewerComponent.visible.size() + entityViewerComponent.lodExcludedCount + entityViewerComponent.hiddenCount)
/* 66 */         .param("worldTotalCount", store.getEntityCount())
/* 67 */         .param("viewRadius", playerComponent.getViewRadius())
/* 68 */         .param("viewRadiusBlocks", entityViewerComponent.viewRadiusBlocks));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\EntityTrackerCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */