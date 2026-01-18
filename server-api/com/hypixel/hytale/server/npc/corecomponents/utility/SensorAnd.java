/*    */ package com.hypixel.hytale.server.npc.corecomponents.utility;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderSensorAnd;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderSensorMany;
/*    */ import com.hypixel.hytale.server.npc.instructions.Sensor;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.role.support.DebugSupport;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.WrappedInfoProvider;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class SensorAnd
/*    */   extends SensorMany
/*    */ {
/*    */   public SensorAnd(@Nonnull BuilderSensorAnd builder, @Nonnull BuilderSupport support, @Nonnull List<Sensor> sensors) {
/* 21 */     super((BuilderSensorMany)builder, support, sensors);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 26 */     this.infoProvider.clearPositionMatch();
/*    */     
/* 28 */     DebugSupport debugSupport = role.getDebugSupport();
/* 29 */     int length = this.sensors.length;
/* 30 */     if (!super.matches(ref, role, dt, store) || length == 0) {
/* 31 */       if (this.autoUnlockTargetSlot >= 0) role.getMarkedEntitySupport().clearMarkedEntity(this.autoUnlockTargetSlot); 
/* 32 */       if (debugSupport.isTraceSensorFails()) debugSupport.setLastFailingSensor((Sensor)this); 
/* 33 */       return false;
/*    */     } 
/*    */     
/* 36 */     for (Sensor s : this.sensors) {
/* 37 */       if (!s.matches(ref, role, dt, store)) {
/* 38 */         if (this.autoUnlockTargetSlot >= 0) role.getMarkedEntitySupport().clearMarkedEntity(this.autoUnlockTargetSlot); 
/* 39 */         this.infoProvider.clearPositionMatch();
/* 40 */         if (debugSupport.isTraceSensorFails()) debugSupport.setLastFailingSensor(s); 
/* 41 */         return false;
/*    */       } 
/*    */       
/* 44 */       if (!this.infoProvider.hasPosition() && s.getSensorInfo() != null && s.getSensorInfo().hasPosition()) {
/* 45 */         this.infoProvider.setPositionMatch(s.getSensorInfo().getPositionProvider());
/*    */       }
/*    */     } 
/*    */     
/* 49 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected WrappedInfoProvider createInfoProvider() {
/* 55 */     return new WrappedInfoProvider(this.sensors);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponent\\utility\SensorAnd.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */