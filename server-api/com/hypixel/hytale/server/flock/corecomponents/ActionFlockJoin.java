/*    */ package com.hypixel.hytale.server.flock.corecomponents;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.flock.FlockMembership;
/*    */ import com.hypixel.hytale.server.flock.FlockMembershipSystems;
/*    */ import com.hypixel.hytale.server.flock.FlockPlugin;
/*    */ import com.hypixel.hytale.server.flock.corecomponents.builders.BuilderActionFlockJoin;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ActionFlockJoin
/*    */   extends ActionBase {
/*    */   public ActionFlockJoin(@Nonnull BuilderActionFlockJoin builderActionFlockJoin) {
/* 21 */     super((BuilderActionBase)builderActionFlockJoin);
/* 22 */     this.forceJoin = builderActionFlockJoin.isForceJoin();
/*    */   }
/*    */   
/*    */   protected final boolean forceJoin;
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 28 */     return (super.canExecute(ref, role, sensorInfo, dt, store) && sensorInfo != null && sensorInfo.hasPosition());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 33 */     super.execute(ref, role, sensorInfo, dt, store);
/* 34 */     Ref<EntityStore> target = (sensorInfo != null && sensorInfo.hasPosition()) ? sensorInfo.getPositionProvider().getTarget() : null;
/* 35 */     if (target == null) return false;
/*    */     
/* 37 */     FlockMembership targetMembership = (FlockMembership)target.getStore().getComponent(target, FlockMembership.getComponentType());
/* 38 */     Ref<EntityStore> targetFlockReference = (targetMembership != null) ? targetMembership.getFlockRef() : null;
/*    */     
/* 40 */     Ref<EntityStore> flockReference = FlockPlugin.getFlockReference(ref, (ComponentAccessor)store);
/* 41 */     if (flockReference != null && targetFlockReference != null) return true;
/*    */     
/* 43 */     if (flockReference != null) {
/* 44 */       FlockMembershipSystems.join(target, flockReference, store);
/* 45 */     } else if (targetFlockReference != null) {
/* 46 */       FlockMembershipSystems.join(ref, targetFlockReference, store);
/*    */     } else {
/* 48 */       flockReference = FlockPlugin.createFlock(store, role);
/* 49 */       if (role.isCanLeadFlock()) {
/* 50 */         FlockMembershipSystems.join(ref, flockReference, store);
/* 51 */         FlockMembershipSystems.join(target, flockReference, store);
/*    */       } else {
/* 53 */         FlockMembershipSystems.join(target, flockReference, store);
/* 54 */         FlockMembershipSystems.join(ref, flockReference, store);
/*    */       } 
/*    */     } 
/* 57 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\flock\corecomponents\ActionFlockJoin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */