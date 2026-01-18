/*    */ package com.hypixel.hytale.server.spawning.controllers;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.spawning.jobs.SpawnJob;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SpawnControllerSystem<J extends SpawnJob, T extends SpawnController<J>>
/*    */   extends EntityTickingSystem<EntityStore>
/*    */ {
/*    */   protected void tickController(@Nonnull T spawnController, @Nonnull Store<EntityStore> store) {
/* 17 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 18 */     if (world.getPlayerCount() == 0 || !world.getWorldConfig().isSpawningNPC() || spawnController
/* 19 */       .isUnspawnable() || world.getChunkStore().getStore().getEntityCount() == 0)
/*    */       return; 
/* 21 */     if (spawnController.getActualNPCs() > spawnController.getExpectedNPCs())
/*    */       return; 
/* 23 */     prepareSpawnJobGeneration(spawnController, (ComponentAccessor<EntityStore>)store);
/* 24 */     createRandomSpawnJobs(spawnController, (ComponentAccessor<EntityStore>)store);
/*    */   }
/*    */   
/*    */   protected void createRandomSpawnJobs(@Nonnull T spawnController, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*    */     do {
/*    */     
/* 30 */     } while (spawnController.getActiveJobCount() < spawnController.getMaxActiveJobs() && 
/* 31 */       spawnController.createRandomSpawnJob(componentAccessor) != null);
/*    */   }
/*    */   
/*    */   protected abstract void prepareSpawnJobGeneration(T paramT, ComponentAccessor<EntityStore> paramComponentAccessor);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\controllers\SpawnControllerSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */