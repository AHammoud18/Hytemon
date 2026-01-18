/*    */ package com.hypixel.hytale.server.npc.corecomponents.utility;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderSensorMany;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderSensorOr;
/*    */ import com.hypixel.hytale.server.npc.instructions.Sensor;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.role.support.DebugSupport;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.WrappedInfoProvider;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class SensorOr
/*    */   extends SensorMany
/*    */ {
/*    */   public SensorOr(@Nonnull BuilderSensorOr builder, @Nonnull BuilderSupport support, @Nonnull List<Sensor> sensors) {
/* 21 */     super((BuilderSensorMany)builder, support, sensors);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 26 */     this.infoProvider.clearMatches();
/* 27 */     this.infoProvider.clearPositionMatch();
/* 28 */     DebugSupport debugSupport = role.getDebugSupport();
/* 29 */     if (!super.matches(ref, role, dt, store)) {
/* 30 */       if (this.autoUnlockTargetSlot >= 0) role.getMarkedEntitySupport().clearMarkedEntity(this.autoUnlockTargetSlot); 
/* 31 */       if (debugSupport.isTraceSensorFails()) debugSupport.setLastFailingSensor((Sensor)this); 
/* 32 */       return false;
/*    */     } 
/*    */     
/* 35 */     Sensor match = null;
/* 36 */     if (super.matches(ref, role, dt, store)) {
/* 37 */       for (Sensor s : this.sensors) {
/* 38 */         if (s.matches(ref, role, dt, store)) {
/* 39 */           if (match == null) {
/* 40 */             match = s;
/*    */           }
/* 42 */           this.infoProvider.addMatch(s);
/* 43 */           if (!this.infoProvider.hasPosition() && s.getSensorInfo() != null && s.getSensorInfo().hasPosition()) {
/* 44 */             this.infoProvider.setPositionMatch(s.getSensorInfo().getPositionProvider());
/*    */           }
/*    */         } 
/*    */       } 
/*    */     }
/*    */     
/* 50 */     if (match == null) {
/* 51 */       if (this.autoUnlockTargetSlot >= 0) role.getMarkedEntitySupport().clearMarkedEntity(this.autoUnlockTargetSlot); 
/* 52 */       if (debugSupport.isTraceSensorFails()) debugSupport.setLastFailingSensor((Sensor)this); 
/* 53 */       return false;
/*    */     } 
/* 55 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected WrappedInfoProvider createInfoProvider() {
/* 61 */     return new WrappedInfoProvider();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponent\\utility\SensorOr.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */