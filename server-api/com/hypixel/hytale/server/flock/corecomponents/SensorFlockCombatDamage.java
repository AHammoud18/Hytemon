/*    */ package com.hypixel.hytale.server.flock.corecomponents;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.flock.Flock;
/*    */ import com.hypixel.hytale.server.flock.FlockPlugin;
/*    */ import com.hypixel.hytale.server.flock.corecomponents.builders.BuilderSensorFlockCombatDamage;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.EntityPositionProvider;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import com.hypixel.hytale.server.npc.util.DamageData;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorFlockCombatDamage extends SensorBase {
/* 18 */   protected final EntityPositionProvider positionProvider = new EntityPositionProvider(); protected final boolean leaderOnly;
/*    */   
/*    */   public SensorFlockCombatDamage(@Nonnull BuilderSensorFlockCombatDamage builder) {
/* 21 */     super((BuilderSensorBase)builder);
/* 22 */     this.leaderOnly = builder.isLeaderOnly();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 27 */     if (!super.matches(ref, role, dt, store)) {
/* 28 */       this.positionProvider.clear();
/* 29 */       return false;
/*    */     } 
/*    */     
/* 32 */     Flock flock = FlockPlugin.getFlock((ComponentAccessor)store, ref);
/* 33 */     if (flock == null) return false;
/*    */     
/* 35 */     DamageData damageData = this.leaderOnly ? flock.getLeaderDamageData() : flock.getDamageData();
/* 36 */     Ref<EntityStore> entity = damageData.getMostDamagingAttacker();
/* 37 */     return (this.positionProvider.setTarget(entity, (ComponentAccessor)store) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 42 */     return (InfoProvider)this.positionProvider;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\flock\corecomponents\SensorFlockCombatDamage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */