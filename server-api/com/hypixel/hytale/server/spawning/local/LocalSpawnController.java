/*    */ package com.hypixel.hytale.server.spawning.local;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocalSpawnController
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   private double timeToNextRunSeconds;
/*    */   
/*    */   public static ComponentType<EntityStore, LocalSpawnController> getComponentType() {
/* 20 */     return SpawningPlugin.get().getLocalSpawnControllerComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public LocalSpawnController() {
/* 26 */     this.timeToNextRunSeconds = SpawningPlugin.get().getLocalSpawnControllerJoinDelay();
/*    */   }
/*    */   
/*    */   public void setTimeToNextRunSeconds(double seconds) {
/* 30 */     this.timeToNextRunSeconds = seconds;
/*    */   }
/*    */   
/*    */   public boolean tickTimeToNextRunSeconds(float dt) {
/* 34 */     return ((this.timeToNextRunSeconds -= dt) <= 0.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 41 */     LocalSpawnController controller = new LocalSpawnController();
/* 42 */     controller.timeToNextRunSeconds = this.timeToNextRunSeconds;
/* 43 */     return controller;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 49 */     return "LocalSpawnController{timeToNextRunSeconds=" + this.timeToNextRunSeconds + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\local\LocalSpawnController.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */