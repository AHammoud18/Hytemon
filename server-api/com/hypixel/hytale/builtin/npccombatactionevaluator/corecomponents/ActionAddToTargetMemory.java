/*    */ package com.hypixel.hytale.builtin.npccombatactionevaluator.corecomponents;
/*    */ import com.hypixel.hytale.builtin.npccombatactionevaluator.corecomponents.builders.BuilderActionAddToTargetMemory;
/*    */ import com.hypixel.hytale.builtin.npccombatactionevaluator.memory.TargetMemory;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ActionAddToTargetMemory extends ActionBase {
/* 17 */   private static final ComponentType<EntityStore, TargetMemory> TARGET_MEMORY = TargetMemory.getComponentType();
/*    */   
/*    */   public ActionAddToTargetMemory(@Nonnull BuilderActionAddToTargetMemory builder) {
/* 20 */     super((BuilderActionBase)builder);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 25 */     return (super.canExecute(ref, role, sensorInfo, dt, store) && sensorInfo != null && sensorInfo.hasPosition());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 30 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 32 */     TargetMemory targetMemory = (TargetMemory)ref.getStore().getComponent(ref, TARGET_MEMORY);
/* 33 */     if (targetMemory == null) return true;
/*    */     
/* 35 */     Ref<EntityStore> target = sensorInfo.getPositionProvider().getTarget();
/* 36 */     Int2FloatOpenHashMap hostiles = targetMemory.getKnownHostiles();
/* 37 */     if (hostiles.put(target.getIndex(), targetMemory.getRememberFor()) <= 0.0F) {
/* 38 */       targetMemory.getKnownHostilesList().add(target);
/*    */     }
/*    */     
/* 41 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\npccombatactionevaluator\corecomponents\ActionAddToTargetMemory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */