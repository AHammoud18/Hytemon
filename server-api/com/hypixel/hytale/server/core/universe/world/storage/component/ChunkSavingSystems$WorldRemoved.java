/*     */ package com.hypixel.hytale.server.core.universe.world.storage.component;
/*     */ 
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.system.StoreSystem;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGen;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldRemoved
/*     */   extends StoreSystem<ChunkStore>
/*     */ {
/*     */   @Nonnull
/*  65 */   private final Set<Dependency<ChunkStore>> dependencies = (Set)Set.of(new SystemDependency(Order.AFTER, ChunkStore.ChunkLoaderSaverSetupSystem.class));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<ChunkStore>> getDependencies() {
/*  74 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSystemAddedToStore(@Nonnull Store<ChunkStore> store) {}
/*     */ 
/*     */   
/*     */   public void onSystemRemovedFromStore(@Nonnull Store<ChunkStore> store) {
/*  83 */     World world = ((ChunkStore)store.getExternalData()).getWorld();
/*     */     
/*  85 */     IWorldGen generator = world.getChunkStore().getGenerator();
/*  86 */     if (generator != null) {
/*  87 */       world.getLogger().at(Level.INFO).log("Shutting down chunk generator...");
/*  88 */       generator.shutdown();
/*     */     } 
/*     */     
/*  91 */     if (!world.getWorldConfig().canSaveChunks()) {
/*  92 */       world.getLogger().at(Level.INFO).log("This world has opted to disable chunk saving so it will not be saved on shutdown");
/*     */       
/*     */       return;
/*     */     } 
/*  96 */     world.getLogger().at(Level.INFO).log("Saving Chunks...");
/*  97 */     ChunkSavingSystems.Data data = (ChunkSavingSystems.Data)store.getResource(ChunkStore.SAVE_RESOURCE);
/*     */     
/*  99 */     data.savedCount.set(0);
/* 100 */     data.toSaveTotal.set(0);
/*     */     
/* 102 */     ChunkSavingSystems.saveChunksInWorld(store).join();
/* 103 */     world.getLogger().at(Level.INFO).log("Done Saving Chunks!");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\component\ChunkSavingSystems$WorldRemoved.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */