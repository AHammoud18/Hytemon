/*    */ package com.hypixel.hytale.server.npc.corecomponents.movement;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderSensorMotionController;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorMotionController extends SensorBase {
/*    */   protected final String motionControllerName;
/*    */   
/*    */   public SensorMotionController(@Nonnull BuilderSensorMotionController builderSensorMotionController) {
/* 17 */     super((BuilderSensorBase)builderSensorMotionController);
/* 18 */     this.motionControllerName = builderSensorMotionController.getMotionControllerName();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 23 */     return (super.matches(ref, role, dt, store) && this.motionControllerName.equalsIgnoreCase(role.getActiveMotionController().getType()));
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 28 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\movement\SensorMotionController.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */