/*    */ package com.hypixel.hytale.server.spawning.controllers;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*    */ import com.hypixel.hytale.server.spawning.jobs.SpawnJob;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.ArrayDeque;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SpawnController<T extends SpawnJob>
/*    */ {
/*    */   protected World world;
/*    */   protected boolean unspawnable;
/*    */   protected double expectedNPCs;
/*    */   protected int actualNPCs;
/* 27 */   protected final List<T> activeJobs = (List<T>)new ObjectArrayList();
/* 28 */   protected final ArrayDeque<T> idleJobs = new ArrayDeque<>();
/*    */   
/*    */   protected final int baseMaxActiveJobs;
/*    */   
/*    */   protected boolean debugSpawnFrozen;
/*    */   
/*    */   public SpawnController(World world) {
/* 35 */     this.world = world;
/* 36 */     this.expectedNPCs = 0.0D;
/* 37 */     this.actualNPCs = 0;
/* 38 */     this.unspawnable = false;
/* 39 */     this.baseMaxActiveJobs = SpawningPlugin.get().getMaxActiveJobs();
/*    */   }
/*    */   
/*    */   public World getWorld() {
/* 43 */     return this.world;
/*    */   }
/*    */   
/*    */   public boolean isUnspawnable() {
/* 47 */     return this.unspawnable;
/*    */   }
/*    */   
/*    */   public boolean isDebugSpawnFrozen() {
/* 51 */     return this.debugSpawnFrozen;
/*    */   }
/*    */   
/*    */   public int getActualNPCs() {
/* 55 */     return this.actualNPCs;
/*    */   }
/*    */   
/*    */   public double getExpectedNPCs() {
/* 59 */     return this.expectedNPCs;
/*    */   }
/*    */   
/*    */   public int getActiveJobCount() {
/* 63 */     return this.activeJobs.size();
/*    */   }
/*    */   
/*    */   public int getMaxActiveJobs() {
/* 67 */     return this.baseMaxActiveJobs;
/*    */   }
/*    */   
/*    */   public T getSpawnJob(int index) {
/* 71 */     return this.activeJobs.get(index);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public List<T> getActiveJobs() {
/* 76 */     return this.activeJobs;
/*    */   }
/*    */   
/*    */   public void addIdleJob(@Nonnull T job) {
/* 80 */     this.idleJobs.push(job);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public abstract T createRandomSpawnJob(ComponentAccessor<EntityStore> paramComponentAccessor);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\controllers\SpawnController.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */