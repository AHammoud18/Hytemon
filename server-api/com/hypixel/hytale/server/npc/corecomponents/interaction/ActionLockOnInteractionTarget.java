/*    */ package com.hypixel.hytale.server.npc.corecomponents.interaction;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.interaction.builders.BuilderActionLockOnInteractionTarget;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionLockOnInteractionTarget extends ActionBase {
/*    */   protected final int targetSlot;
/*    */   
/*    */   public ActionLockOnInteractionTarget(@Nonnull BuilderActionLockOnInteractionTarget builderActionBase, @Nonnull BuilderSupport support) {
/* 18 */     super((BuilderActionBase)builderActionBase);
/* 19 */     this.targetSlot = builderActionBase.getTargetSlot(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 24 */     return (super.canExecute(ref, role, sensorInfo, dt, store) && role.getStateSupport().getInteractionIterationTarget() != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 29 */     super.execute(ref, role, sensorInfo, dt, store);
/* 30 */     Ref<EntityStore> target = role.getStateSupport().getInteractionIterationTarget();
/* 31 */     role.getMarkedEntitySupport().setMarkedEntity(this.targetSlot, target);
/* 32 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\interaction\ActionLockOnInteractionTarget.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */