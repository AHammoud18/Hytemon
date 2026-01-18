/*    */ package com.hypixel.hytale.server.npc.corecomponents.movement;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderActionOverrideAltitude;
/*    */ import com.hypixel.hytale.server.npc.movement.controllers.MotionControllerFly;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionOverrideAltitude
/*    */   extends ActionBase
/*    */ {
/*    */   private final double[] desiredRange;
/*    */   
/*    */   public ActionOverrideAltitude(@Nonnull BuilderActionOverrideAltitude builder, @Nonnull BuilderSupport support) {
/* 35 */     super((BuilderActionBase)builder);
/* 36 */     this.desiredRange = builder.getDesiredAltitudeRange(support);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 42 */     return (super.canExecute(ref, role, sensorInfo, dt, store) && "Fly".equals(role.getActiveMotionController().getType()));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 47 */     super.execute(ref, role, sensorInfo, dt, store);
/* 48 */     ((MotionControllerFly)role.getActiveMotionController()).setDesiredAltitudeOverride(this.desiredRange);
/* 49 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\movement\ActionOverrideAltitude.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */