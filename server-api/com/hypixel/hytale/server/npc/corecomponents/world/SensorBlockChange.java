/*    */ package com.hypixel.hytale.server.npc.corecomponents.world;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.blackboard.view.event.block.BlockEventType;
/*    */ import com.hypixel.hytale.server.npc.components.messaging.NPCBlockEventSupport;
/*    */ import com.hypixel.hytale.server.npc.components.messaging.PlayerBlockEventSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorBlockChange;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorBlockChange extends SensorEvent {
/*    */   public SensorBlockChange(@Nonnull BuilderSensorBlockChange builder, @Nonnull BuilderSupport support) {
/* 16 */     super((BuilderSensorEvent)builder, support);
/*    */     
/* 18 */     BlockEventType type = builder.getEventType(support);
/* 19 */     int blockSet = builder.getBlockSet(support);
/*    */     
/* 21 */     switch (this.searchType) { case PlayerFirst:
/*    */       case NpcFirst:
/* 23 */         this.playerEventMessageSlot = support.getBlockEventSlot(type, blockSet, this.range, true);
/* 24 */         this.npcEventMessageSlot = support.getBlockEventSlot(type, blockSet, this.range, false);
/*    */         return;
/*    */       case PlayerOnly:
/* 27 */         this.playerEventMessageSlot = support.getBlockEventSlot(type, blockSet, this.range, true);
/* 28 */         this.npcEventMessageSlot = -1;
/*    */         return;
/*    */       case NpcOnly:
/* 31 */         this.playerEventMessageSlot = -1;
/* 32 */         this.npcEventMessageSlot = support.getBlockEventSlot(type, blockSet, this.range, false);
/*    */         return; }
/*    */     
/* 35 */     this.playerEventMessageSlot = -1;
/* 36 */     this.npcEventMessageSlot = -1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected Ref<EntityStore> getPlayerTarget(@Nonnull Ref<EntityStore> parent, @Nonnull Store<EntityStore> store) {
/* 44 */     PlayerBlockEventSupport blockEventSupportComponent = (PlayerBlockEventSupport)store.getComponent(parent, PlayerBlockEventSupport.getComponentType());
/* 45 */     assert blockEventSupportComponent != null;
/*    */     
/* 47 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(parent, TRANSFORM_COMPONENT_TYPE);
/* 48 */     assert transformComponent != null;
/*    */     
/* 50 */     Vector3d position = transformComponent.getPosition();
/* 51 */     if (!blockEventSupportComponent.hasMatchingMessage(this.playerEventMessageSlot, position, this.range)) {
/* 52 */       return null;
/*    */     }
/* 54 */     return blockEventSupportComponent.pollMessage(this.playerEventMessageSlot);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected Ref<EntityStore> getNpcTarget(@Nonnull Ref<EntityStore> parent, @Nonnull Store<EntityStore> store) {
/* 60 */     NPCBlockEventSupport blockEventSupportComponent = (NPCBlockEventSupport)store.getComponent(parent, NPCBlockEventSupport.getComponentType());
/* 61 */     assert blockEventSupportComponent != null;
/*    */     
/* 63 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(parent, TRANSFORM_COMPONENT_TYPE);
/* 64 */     assert transformComponent != null;
/*    */     
/* 66 */     Vector3d position = transformComponent.getPosition();
/* 67 */     if (!blockEventSupportComponent.hasMatchingMessage(this.npcEventMessageSlot, position, this.range)) {
/* 68 */       return null;
/*    */     }
/* 70 */     return blockEventSupportComponent.pollMessage(this.npcEventMessageSlot);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\world\SensorBlockChange.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */