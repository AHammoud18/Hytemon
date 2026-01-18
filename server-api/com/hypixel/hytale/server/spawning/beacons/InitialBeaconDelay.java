/*    */ package com.hypixel.hytale.server.spawning.beacons;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.math.random.RandomExtra;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class InitialBeaconDelay
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   private double loadTimeSpawnDelay;
/*    */   
/*    */   public static ComponentType<EntityStore, InitialBeaconDelay> getComponentType() {
/* 17 */     return SpawningPlugin.get().getInitialBeaconDelayComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLoadTimeSpawnDelay(double loadTimeSpawnDelay) {
/* 23 */     this.loadTimeSpawnDelay = loadTimeSpawnDelay;
/*    */   }
/*    */   
/*    */   public boolean tickLoadTimeSpawnDelay(float dt) {
/* 27 */     if (this.loadTimeSpawnDelay <= 0.0D) return true; 
/* 28 */     return ((this.loadTimeSpawnDelay -= dt) <= 0.0D);
/*    */   }
/*    */   
/*    */   public void setupInitialSpawnDelay(@Nonnull double[] initialSpawnDelay) {
/* 32 */     this.loadTimeSpawnDelay = RandomExtra.randomRange(initialSpawnDelay[0], initialSpawnDelay[1]);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 39 */     InitialBeaconDelay delay = new InitialBeaconDelay();
/* 40 */     delay.setLoadTimeSpawnDelay(this.loadTimeSpawnDelay);
/* 41 */     return delay;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\beacons\InitialBeaconDelay.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */