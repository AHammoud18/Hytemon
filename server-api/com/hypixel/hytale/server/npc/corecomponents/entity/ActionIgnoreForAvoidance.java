/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderActionIgnoreForAvoidance;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionIgnoreForAvoidance extends ActionBase {
/*    */   private final int targetSlot;
/*    */   
/*    */   public ActionIgnoreForAvoidance(@Nonnull BuilderActionIgnoreForAvoidance builder, @Nonnull BuilderSupport support) {
/* 18 */     super((BuilderActionBase)builder);
/* 19 */     this.targetSlot = builder.getTargetSlot(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 24 */     role.getMarkedEntitySupport().setTargetSlotToIgnoreForAvoidance(this.targetSlot);
/* 25 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\ActionIgnoreForAvoidance.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */