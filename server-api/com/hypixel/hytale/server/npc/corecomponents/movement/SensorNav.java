/*    */ package com.hypixel.hytale.server.npc.corecomponents.movement;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderSensorNav;
/*    */ import com.hypixel.hytale.server.npc.movement.NavState;
/*    */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorNav
/*    */   extends SensorBase {
/*    */   protected final EnumSet<NavState> navStates;
/*    */   protected final double throttleDuration;
/*    */   protected final double targetDeltaSquared;
/*    */   
/*    */   public SensorNav(@Nonnull BuilderSensorNav builderSensorNav, @Nonnull BuilderSupport builderSupport) {
/* 24 */     super((BuilderSensorBase)builderSensorNav);
/*    */     
/* 26 */     this.navStates = builderSensorNav.getNavStates(builderSupport);
/* 27 */     this.throttleDuration = builderSensorNav.getThrottleDuration(builderSupport);
/*    */     
/* 29 */     double targetDelta = builderSensorNav.getTargetDelta(builderSupport);
/* 30 */     this.targetDeltaSquared = targetDelta * targetDelta;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 35 */     if (!super.matches(ref, role, dt, store)) return false;
/*    */     
/* 37 */     MotionController motionController = role.getActiveMotionController();
/*    */     
/* 39 */     return ((this.throttleDuration == 0.0D || motionController.getThrottleDuration() >= this.throttleDuration) && (this.targetDeltaSquared == 0.0D || motionController
/* 40 */       .getTargetDeltaSquared() >= this.targetDeltaSquared) && (this.navStates
/* 41 */       .isEmpty() || this.navStates.contains(motionController.getNavState())));
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 46 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\movement\SensorNav.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */