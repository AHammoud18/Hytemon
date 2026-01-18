/*    */ package com.hypixel.hytale.server.npc.instructions;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import com.hypixel.hytale.server.npc.util.ComponentInfo;
/*    */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponent;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class NullSensor
/*    */   implements Sensor
/*    */ {
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 17 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 22 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean processDelay(float dt) {
/* 27 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void clearOnce() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void setOnce() {}
/*    */ 
/*    */   
/*    */   public boolean isTriggered() {
/* 40 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void getInfo(Role role, ComponentInfo holder) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void setContext(IAnnotatedComponent parent, int index) {}
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public IAnnotatedComponent getParent() {
/* 54 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getIndex() {
/* 59 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\instructions\NullSensor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */