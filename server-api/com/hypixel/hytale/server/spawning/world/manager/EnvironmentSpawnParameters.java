/*    */ package com.hypixel.hytale.server.spawning.world.manager;
/*    */ 
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnvironmentSpawnParameters
/*    */ {
/*    */   private double density;
/* 14 */   private final Set<WorldSpawnWrapper> spawnWrappers = ConcurrentHashMap.newKeySet();
/*    */   
/*    */   public EnvironmentSpawnParameters(double density) {
/* 17 */     this.density = density;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Set<WorldSpawnWrapper> getSpawnWrappers() {
/* 22 */     return this.spawnWrappers;
/*    */   }
/*    */   
/*    */   public double getSpawnDensity() {
/* 26 */     return this.density;
/*    */   }
/*    */   
/*    */   public void setDensity(double density) {
/* 30 */     this.density = density;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\world\manager\EnvironmentSpawnParameters.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */