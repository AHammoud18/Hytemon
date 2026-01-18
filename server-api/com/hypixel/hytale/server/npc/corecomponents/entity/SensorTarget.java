/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorWithEntityFilters;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderSensorTarget;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.EntityPositionProvider;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorTarget extends SensorWithEntityFilters {
/* 19 */   protected static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/*    */   
/*    */   protected final int targetSlot;
/*    */   
/*    */   protected final boolean autoUnlockTarget;
/*    */   
/*    */   protected final double range;
/* 26 */   protected final EntityPositionProvider positionProvider = new EntityPositionProvider();
/*    */   
/*    */   public SensorTarget(@Nonnull BuilderSensorTarget builderSensorTarget, @Nonnull BuilderSupport support) {
/* 29 */     super((BuilderSensorBase)builderSensorTarget, builderSensorTarget.getFilters(support, null, ComponentContext.SensorTarget));
/* 30 */     this.targetSlot = builderSensorTarget.getTargetSlot(support);
/* 31 */     this.range = builderSensorTarget.getRange(support);
/* 32 */     this.autoUnlockTarget = builderSensorTarget.getAutoUnlockTarget(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 37 */     if (!super.matches(ref, role, dt, store)) {
/* 38 */       this.positionProvider.clear();
/* 39 */       return false;
/*    */     } 
/*    */     
/* 42 */     Ref<EntityStore> target = role.getMarkedEntitySupport().getMarkedEntityRef(this.targetSlot);
/* 43 */     if (target == null) return false;
/*    */     
/* 45 */     if (!fulfilsRequirements(ref, role, target, store)) {
/* 46 */       if (this.autoUnlockTarget) {
/* 47 */         this.positionProvider.clear();
/* 48 */         role.getMarkedEntitySupport().clearMarkedEntity(this.targetSlot);
/*    */       } 
/* 50 */       return false;
/*    */     } 
/*    */     
/* 53 */     return (this.positionProvider.setTarget(target, (ComponentAccessor)store) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 58 */     return (InfoProvider)this.positionProvider;
/*    */   }
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
/*    */   protected boolean fulfilsRequirements(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull Ref<EntityStore> target, @Nonnull Store<EntityStore> store) {
/* 71 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/* 72 */     if (transformComponent == null) return false;
/*    */     
/* 74 */     Vector3d position = transformComponent.getPosition();
/* 75 */     if (this.range != Double.MAX_VALUE) {
/*    */       
/* 77 */       TransformComponent targetTransformComponent = (TransformComponent)store.getComponent(target, TRANSFORM_COMPONENT_TYPE);
/* 78 */       if (targetTransformComponent == null) return false;
/*    */       
/* 80 */       double squaredDistance = position.distanceSquaredTo(targetTransformComponent.getPosition());
/* 81 */       if (squaredDistance > this.range * this.range) return false;
/*    */     
/*    */     } 
/* 84 */     return matchesFilters(ref, target, role, store);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\SensorTarget.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */