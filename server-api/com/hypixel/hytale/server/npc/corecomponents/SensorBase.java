/*    */ package com.hypixel.hytale.server.npc.corecomponents;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.instructions.Sensor;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class SensorBase
/*    */   extends AnnotatedComponentBase implements Sensor {
/*    */   protected final boolean once;
/*    */   protected boolean triggered;
/*    */   
/*    */   public SensorBase(@Nonnull BuilderSensorBase builderSensorBase) {
/* 17 */     this.once = builderSensorBase.getOnce();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 22 */     return (!this.once || !this.triggered);
/*    */   }
/*    */ 
/*    */   
/*    */   public void clearOnce() {
/* 27 */     this.triggered = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setOnce() {
/* 32 */     this.triggered = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isTriggered() {
/* 37 */     return this.triggered;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean processDelay(float dt) {
/* 42 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\SensorBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */