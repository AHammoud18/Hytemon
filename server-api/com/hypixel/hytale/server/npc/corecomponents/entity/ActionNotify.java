/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.components.messaging.BeaconSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderActionNotify;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionNotify extends ActionBase {
/*    */   protected final String message;
/*    */   protected final double expirationTime;
/*    */   protected final int usedTargetSlot;
/*    */   
/*    */   public ActionNotify(@Nonnull BuilderActionNotify builderActionBase, @Nonnull BuilderSupport support) {
/* 21 */     super((BuilderActionBase)builderActionBase);
/* 22 */     this.message = builderActionBase.getMessage(support);
/* 23 */     this.expirationTime = builderActionBase.getExpirationTime();
/* 24 */     this.usedTargetSlot = builderActionBase.getUsedTargetSlot(support);
/*    */   }
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/*    */     Ref<EntityStore> targetRef;
/* 29 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */ 
/*    */     
/* 32 */     if (this.usedTargetSlot >= 0) {
/* 33 */       targetRef = role.getMarkedEntitySupport().getMarkedEntityRef(this.usedTargetSlot);
/*    */     } else {
/* 35 */       targetRef = sensorInfo.getPositionProvider().getTarget();
/*    */     } 
/*    */     
/* 38 */     if (targetRef != null) {
/* 39 */       BeaconSupport beaconSupport = (BeaconSupport)store.getComponent(targetRef, BeaconSupport.getComponentType());
/* 40 */       if (beaconSupport != null) beaconSupport.postMessage(this.message, ref, this.expirationTime);
/*    */     
/*    */     } 
/* 43 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\ActionNotify.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */