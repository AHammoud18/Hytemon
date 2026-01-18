/*    */ package com.hypixel.hytale.server.flock.corecomponents;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.entity.group.EntityGroup;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.flock.FlockMembership;
/*    */ import com.hypixel.hytale.server.flock.corecomponents.builders.BuilderSensorFlockLeader;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.EntityPositionProvider;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorFlockLeader extends SensorBase {
/* 17 */   protected final EntityPositionProvider positionProvider = new EntityPositionProvider();
/*    */   
/*    */   public SensorFlockLeader(@Nonnull BuilderSensorFlockLeader builder) {
/* 20 */     super((BuilderSensorBase)builder);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 25 */     if (!super.matches(ref, role, dt, store)) {
/* 26 */       this.positionProvider.clear();
/* 27 */       return false;
/*    */     } 
/*    */     
/* 30 */     FlockMembership membership = (FlockMembership)store.getComponent(ref, FlockMembership.getComponentType());
/* 31 */     if (membership == null) {
/* 32 */       this.positionProvider.clear();
/* 33 */       return false;
/*    */     } 
/*    */     
/* 36 */     EntityGroup group = null;
/* 37 */     Ref<EntityStore> flockReference = membership.getFlockRef();
/* 38 */     if (flockReference != null && flockReference.isValid()) {
/* 39 */       group = (EntityGroup)store.getComponent(flockReference, EntityGroup.getComponentType());
/*    */     }
/*    */     
/* 42 */     if (group == null) {
/* 43 */       this.positionProvider.clear();
/* 44 */       return false;
/*    */     } 
/*    */     
/* 47 */     return (this.positionProvider.setTarget(group.getLeaderRef(), (ComponentAccessor)store) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 52 */     return (InfoProvider)this.positionProvider;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\flock\corecomponents\SensorFlockLeader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */