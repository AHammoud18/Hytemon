/*    */ package com.hypixel.hytale.server.core.universe.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.system.DelayedSystem;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldConfig;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldConfigSaveSystem
/*    */   extends DelayedSystem<EntityStore>
/*    */ {
/*    */   public WorldConfigSaveSystem() {
/* 21 */     super(10.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void delayedTick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/* 26 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 27 */     saveWorldConfigAndResources(world).join();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static CompletableFuture<Void> saveWorldConfigAndResources(@Nonnull World world) {
/* 38 */     WorldConfig worldConfig = world.getWorldConfig();
/*    */     
/* 40 */     if (worldConfig.isSavingConfig() && worldConfig.consumeHasChanged()) {
/* 41 */       return CompletableFuture.allOf((CompletableFuture<?>[])new CompletableFuture[] { world
/* 42 */             .getChunkStore().getStore().saveAllResources(), world
/* 43 */             .getEntityStore().getStore().saveAllResources(), 
/* 44 */             Universe.get().getWorldConfigProvider().save(world.getSavePath(), world.getWorldConfig(), world) });
/*    */     }
/*    */     
/* 47 */     return CompletableFuture.allOf((CompletableFuture<?>[])new CompletableFuture[] { world
/* 48 */           .getChunkStore().getStore().saveAllResources(), world
/* 49 */           .getEntityStore().getStore().saveAllResources() });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\system\WorldConfigSaveSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */