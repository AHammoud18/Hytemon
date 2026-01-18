/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.asset.type.attitude.Attitude;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderActionOverrideAttitude;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ActionOverrideAttitude extends ActionBase {
/*    */   protected final Attitude attitude;
/*    */   protected final double duration;
/*    */   
/*    */   public ActionOverrideAttitude(@Nonnull BuilderActionOverrideAttitude builder, @Nonnull BuilderSupport support) {
/* 21 */     super((BuilderActionBase)builder);
/* 22 */     this.attitude = builder.getAttitude(support);
/* 23 */     this.duration = builder.getDuration(support);
/* 24 */     support.requireAttitudeOverrideMemory();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 30 */     return (super.canExecute(ref, role, sensorInfo, dt, store) && sensorInfo != null && sensorInfo.hasPosition());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 35 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 37 */     Ref<EntityStore> target = sensorInfo.getPositionProvider().getTarget();
/* 38 */     if (target == null) return true;
/*    */     
/* 40 */     role.getWorldSupport().overrideAttitude(target, this.attitude, this.duration);
/* 41 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\ActionOverrideAttitude.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */