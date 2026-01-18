/*    */ package com.hypixel.hytale.server.npc.corecomponents.world;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderActionSetLeashPosition;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionSetLeashPosition
/*    */   extends ActionBase
/*    */ {
/*    */   protected final boolean toTarget;
/*    */   protected final boolean toCurrent;
/*    */   
/*    */   public ActionSetLeashPosition(@Nonnull BuilderActionSetLeashPosition builderActionSetLeashPosition) {
/* 38 */     super((BuilderActionBase)builderActionSetLeashPosition);
/* 39 */     this.toCurrent = builderActionSetLeashPosition.isToCurrent();
/* 40 */     this.toTarget = builderActionSetLeashPosition.isToTarget();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 45 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 47 */     if (this.toCurrent) {
/* 48 */       setLeashPosition(ref, ref, (ComponentAccessor<EntityStore>)store);
/* 49 */     } else if (this.toTarget && 
/* 50 */       sensorInfo != null) {
/* 51 */       Ref<EntityStore> targetRef = sensorInfo.hasPosition() ? sensorInfo.getPositionProvider().getTarget() : null;
/* 52 */       if (targetRef != null) {
/* 53 */         setLeashPosition(ref, targetRef, (ComponentAccessor<EntityStore>)store);
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 58 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected static void setLeashPosition(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 69 */     NPCEntity selfNpcComponent = (NPCEntity)componentAccessor.getComponent(ref, NPCEntity.getComponentType());
/* 70 */     assert selfNpcComponent != null;
/*    */     
/* 72 */     TransformComponent entityTransformComponent = (TransformComponent)componentAccessor.getComponent(targetRef, TransformComponent.getComponentType());
/* 73 */     assert entityTransformComponent != null;
/*    */     
/* 75 */     Vector3f entityBodyRotation = entityTransformComponent.getRotation();
/* 76 */     selfNpcComponent.getLeashPoint().assign(entityTransformComponent.getPosition());
/* 77 */     selfNpcComponent.setLeashPitch(entityBodyRotation.getPitch());
/* 78 */     selfNpcComponent.setLeashHeading(entityBodyRotation.getYaw());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\world\ActionSetLeashPosition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */