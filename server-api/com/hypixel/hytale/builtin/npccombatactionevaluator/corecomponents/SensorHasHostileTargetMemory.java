/*    */ package com.hypixel.hytale.builtin.npccombatactionevaluator.corecomponents;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.npccombatactionevaluator.corecomponents.builders.BuilderSensorHasHostileTargetMemory;
/*    */ import com.hypixel.hytale.builtin.npccombatactionevaluator.memory.TargetMemory;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorHasHostileTargetMemory extends SensorBase {
/* 16 */   private static final ComponentType<EntityStore, TargetMemory> TARGET_MEMORY = TargetMemory.getComponentType();
/*    */   
/*    */   public SensorHasHostileTargetMemory(@Nonnull BuilderSensorHasHostileTargetMemory builder) {
/* 19 */     super((BuilderSensorBase)builder);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 24 */     if (!super.matches(ref, role, dt, store)) return false;
/*    */     
/* 26 */     TargetMemory targetMemory = (TargetMemory)ref.getStore().getComponent(ref, TARGET_MEMORY);
/* 27 */     return (targetMemory != null && !targetMemory.getKnownHostiles().isEmpty());
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 32 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\npccombatactionevaluator\corecomponents\SensorHasHostileTargetMemory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */