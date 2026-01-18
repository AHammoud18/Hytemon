/*    */ package com.hypixel.hytale.server.npc.corecomponents.world;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.blackboard.view.event.entity.EntityEventType;
/*    */ import com.hypixel.hytale.server.npc.components.messaging.PlayerEntityEventSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorEntityEvent;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorEvent;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class SensorEntityEvent extends SensorEvent {
/*    */   public SensorEntityEvent(@Nonnull BuilderSensorEntityEvent builder, @Nonnull BuilderSupport support) {
/* 18 */     super((BuilderSensorEvent)builder, support);
/*    */     
/* 20 */     this.flockOnly = builder.isFlockOnly(support);
/*    */     
/* 22 */     EntityEventType type = builder.getEventType(support);
/* 23 */     int npcGroup = builder.getNPCGroup(support);
/*    */     
/* 25 */     switch (this.searchType) { case PlayerFirst:
/*    */       case NpcFirst:
/* 27 */         this.playerEventMessageSlot = support.getEntityEventSlot(type, npcGroup, this.range, true);
/* 28 */         this.npcEventMessageSlot = support.getEntityEventSlot(type, npcGroup, this.range, false);
/*    */         return;
/*    */       case PlayerOnly:
/* 31 */         this.playerEventMessageSlot = support.getEntityEventSlot(type, npcGroup, this.range, true);
/* 32 */         this.npcEventMessageSlot = -1;
/*    */         return;
/*    */       case NpcOnly:
/* 35 */         this.playerEventMessageSlot = -1;
/* 36 */         this.npcEventMessageSlot = support.getEntityEventSlot(type, npcGroup, this.range, false);
/*    */         return; }
/*    */     
/* 39 */     this.playerEventMessageSlot = -1;
/* 40 */     this.npcEventMessageSlot = -1;
/*    */   }
/*    */ 
/*    */   
/*    */   private final boolean flockOnly;
/*    */   
/*    */   @Nullable
/*    */   protected Ref<EntityStore> getPlayerTarget(@Nonnull Ref<EntityStore> parent, @Nonnull Store<EntityStore> store) {
/* 48 */     PlayerEntityEventSupport entityEventSupportComponent = (PlayerEntityEventSupport)store.getComponent(parent, PlayerEntityEventSupport.getComponentType());
/* 49 */     assert entityEventSupportComponent != null;
/*    */     
/* 51 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(parent, TRANSFORM_COMPONENT_TYPE);
/* 52 */     assert transformComponent != null;
/*    */     
/* 54 */     Vector3d position = transformComponent.getPosition();
/* 55 */     if (!entityEventSupportComponent.hasFlockMatchingMessage(this.playerEventMessageSlot, position, this.range, this.flockOnly)) {
/* 56 */       return null;
/*    */     }
/* 58 */     return entityEventSupportComponent.pollMessage(this.playerEventMessageSlot);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected Ref<EntityStore> getNpcTarget(@Nonnull Ref<EntityStore> parent, @Nonnull Store<EntityStore> store) {
/* 64 */     PlayerEntityEventSupport entityEventSupportComponent = (PlayerEntityEventSupport)store.getComponent(parent, PlayerEntityEventSupport.getComponentType());
/* 65 */     assert entityEventSupportComponent != null;
/*    */     
/* 67 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(parent, TRANSFORM_COMPONENT_TYPE);
/* 68 */     assert transformComponent != null;
/*    */     
/* 70 */     Vector3d position = transformComponent.getPosition();
/* 71 */     if (!entityEventSupportComponent.hasFlockMatchingMessage(this.npcEventMessageSlot, position, this.range, this.flockOnly)) {
/* 72 */       return null;
/*    */     }
/* 74 */     return entityEventSupportComponent.pollMessage(this.npcEventMessageSlot);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\world\SensorEntityEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */