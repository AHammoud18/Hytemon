/*    */ package com.hypixel.hytale.server.flock.corecomponents;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.flock.FlockPlugin;
/*    */ import com.hypixel.hytale.server.flock.corecomponents.builders.BuilderActionFlockSetTarget;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ActionFlockSetTarget extends ActionBase {
/*    */   protected final boolean clear;
/*    */   protected final String targetSlot;
/*    */   
/*    */   public ActionFlockSetTarget(@Nonnull BuilderActionFlockSetTarget builderActionFlockSetTarget, @Nonnull BuilderSupport support) {
/* 21 */     super((BuilderActionBase)builderActionFlockSetTarget);
/* 22 */     this.clear = builderActionFlockSetTarget.isClear();
/* 23 */     this.targetSlot = builderActionFlockSetTarget.getTargetSlot(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 28 */     if (!super.canExecute(ref, role, sensorInfo, dt, store) || !FlockPlugin.isFlockMember(ref, store)) {
/* 29 */       return false;
/*    */     }
/*    */     
/* 32 */     if (this.clear) {
/* 33 */       return true;
/*    */     }
/*    */     
/* 36 */     Ref<EntityStore> target = (sensorInfo != null && sensorInfo.hasPosition()) ? sensorInfo.getPositionProvider().getTarget() : null;
/* 37 */     return (target != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 42 */     super.execute(ref, role, sensorInfo, dt, store);
/* 43 */     if (this.clear) {
/* 44 */       role.getMarkedEntitySupport().flockSetTarget(this.targetSlot, null, store);
/* 45 */       return true;
/*    */     } 
/*    */     
/* 48 */     Ref<EntityStore> targetRef = sensorInfo.getPositionProvider().getTarget();
/* 49 */     role.getMarkedEntitySupport().flockSetTarget(this.targetSlot, targetRef, store);
/* 50 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\flock\corecomponents\ActionFlockSetTarget.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */