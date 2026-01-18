/*    */ package com.hypixel.hytale.server.npc.corecomponents.world;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorReadPosition;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.PositionProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorReadPosition
/*    */   extends SensorBase
/*    */ {
/*    */   protected final int slot;
/*    */   protected final boolean useMarkedTarget;
/*    */   protected final double minRange;
/*    */   protected final double range;
/* 24 */   protected final PositionProvider positionProvider = new PositionProvider();
/*    */   
/*    */   public SensorReadPosition(@Nonnull BuilderSensorReadPosition builder, @Nonnull BuilderSupport support) {
/* 27 */     super((BuilderSensorBase)builder);
/* 28 */     this.slot = builder.getSlot(support);
/* 29 */     this.useMarkedTarget = builder.isUseMarkedTarget(support);
/* 30 */     this.minRange = builder.getMinRange(support);
/* 31 */     this.range = builder.getRange(support);
/*    */   }
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/*    */     Vector3d position;
/* 36 */     if (!super.matches(ref, role, dt, store)) {
/* 37 */       this.positionProvider.clear();
/* 38 */       return false;
/*    */     } 
/*    */ 
/*    */     
/* 42 */     if (this.useMarkedTarget) {
/* 43 */       Ref<EntityStore> entityRef = role.getMarkedEntitySupport().getMarkedEntityRef(this.slot);
/* 44 */       if (entityRef == null) {
/* 45 */         this.positionProvider.clear();
/* 46 */         return false;
/*    */       } 
/*    */       
/* 49 */       TransformComponent entityTransformComponent = (TransformComponent)store.getComponent(entityRef, TransformComponent.getComponentType());
/* 50 */       assert entityTransformComponent != null;
/*    */       
/* 52 */       position = entityTransformComponent.getPosition();
/*    */     } else {
/* 54 */       position = role.getMarkedEntitySupport().getStoredPosition(this.slot);
/*    */     } 
/*    */     
/* 57 */     if (position.equals(Vector3d.MIN)) {
/* 58 */       this.positionProvider.clear();
/* 59 */       return false;
/*    */     } 
/*    */     
/* 62 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 63 */     assert transformComponent != null;
/*    */     
/* 65 */     double dist2 = transformComponent.getPosition().distanceSquaredTo(position);
/* 66 */     if (dist2 > this.range * this.range || dist2 < this.minRange * this.minRange) {
/* 67 */       this.positionProvider.clear();
/* 68 */       return false;
/*    */     } 
/*    */     
/* 71 */     this.positionProvider.setTarget(position);
/* 72 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 77 */     return (InfoProvider)this.positionProvider;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\world\SensorReadPosition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */