/*    */ package com.hypixel.hytale.server.core.command.commands.world.entity;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.EntityWrappedArg;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class EntityRemoveCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 28 */   private static final Message MESSAGE_GENERAL_NO_ENTITY_IN_VIEW = Message.translation("server.general.noEntityInView");
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 33 */   private final EntityWrappedArg entityArg = (EntityWrappedArg)
/* 34 */     withOptionalArg("entity", "server.commands.entity.remove.entity.desc", ArgTypes.ENTITY_ID);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 40 */   private final FlagArg othersFlag = withFlagArg("others", "server.commands.entity.remove.others.desc");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityRemoveCommand() {
/* 46 */     super("remove", "server.commands.entity.remove.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 51 */     Ref<EntityStore> entityRef = this.entityArg.get((ComponentAccessor)store, context);
/* 52 */     if (entityRef == null || !entityRef.isValid()) {
/* 53 */       context.sendMessage(MESSAGE_GENERAL_NO_ENTITY_IN_VIEW);
/*    */       
/*    */       return;
/*    */     } 
/* 57 */     if (this.othersFlag.provided(context)) {
/* 58 */       store.forEachEntityParallel((index, archetypeChunk, commandBuffer) -> {
/*    */             if (archetypeChunk.getArchetype().contains(Player.getComponentType())) {
/*    */               return;
/*    */             }
/*    */             
/*    */             if (archetypeChunk.getReferenceTo(index).equals(entityRef)) {
/*    */               return;
/*    */             }
/*    */             commandBuffer.removeEntity(archetypeChunk.getReferenceTo(index), RemoveReason.REMOVE);
/*    */           });
/*    */     } else {
/* 69 */       removeEntity(context.senderAsPlayerRef(), entityRef, (ComponentAccessor<EntityStore>)store);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void removeEntity(@Nullable Ref<EntityStore> playerRef, @Nonnull Ref<EntityStore> entityReference, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 77 */     if (playerRef != null && playerRef.isValid()) {
/* 78 */       EntityTrackerSystems.EntityViewer entityViewer = (EntityTrackerSystems.EntityViewer)componentAccessor.getComponent(playerRef, EntityTrackerSystems.EntityViewer.getComponentType());
/* 79 */       if (entityViewer != null && !entityViewer.visible.contains(entityReference)) {
/* 80 */         PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(playerRef, PlayerRef.getComponentType());
/* 81 */         assert playerRefComponent != null;
/*    */         
/* 83 */         playerRefComponent.sendMessage(Message.translation("server.general.entity.remove.unableToRemove")
/* 84 */             .param("id", entityReference.getIndex()));
/*    */         return;
/*    */       } 
/*    */     } 
/* 88 */     componentAccessor.removeEntity(entityReference, EntityStore.REGISTRY.newHolder(), RemoveReason.REMOVE);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\EntityRemoveCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */