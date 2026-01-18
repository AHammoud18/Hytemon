/*     */ package com.hypixel.hytale.server.core.universe.world.storage.component;
/*     */ 
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.RootDependency;
/*     */ import com.hypixel.hytale.component.system.tick.RunWhenPausedSystem;
/*     */ import com.hypixel.hytale.component.system.tick.TickingSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkFlag;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.IChunkSaver;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.ForkJoinPool;
/*     */ import java.util.function.Function;
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
/*     */ public class Ticking
/*     */   extends TickingSystem<ChunkStore>
/*     */   implements RunWhenPausedSystem<ChunkStore>
/*     */ {
/*     */   @Nonnull
/*     */   public Set<Dependency<ChunkStore>> getDependencies() {
/* 139 */     return RootDependency.lastSet();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int systemIndex, @Nonnull Store<ChunkStore> store) {
/* 144 */     ChunkSavingSystems.Data data = (ChunkSavingSystems.Data)store.getResource(ChunkStore.SAVE_RESOURCE);
/* 145 */     if (!data.isSaving || !((ChunkStore)store.getExternalData()).getWorld().getWorldConfig().canSaveChunks()) {
/*     */       return;
/*     */     }
/* 148 */     data.chunkSavingFutures.removeIf(CompletableFuture::isDone);
/*     */ 
/*     */     
/* 151 */     if (data.checkTimer(dt))
/*     */     {
/* 153 */       store.forEachChunk(ChunkSavingSystems.QUERY, ChunkSavingSystems::tryQueueSync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     World world = ((ChunkStore)store.getExternalData()).getWorld();
/* 160 */     IChunkSaver saver = ((ChunkStore)store.getExternalData()).getSaver();
/*     */     
/* 162 */     int parallelSaves = ForkJoinPool.commonPool().getParallelism();
/* 163 */     for (int i = 0; i < parallelSaves; i++) {
/* 164 */       Ref<ChunkStore> reference = data.poll();
/* 165 */       if (reference == null)
/*     */         break; 
/* 167 */       if (!reference.isValid()) {
/* 168 */         ChunkSavingSystems.LOGGER.at(Level.FINEST).log("Chunk reference in queue is for a chunk that has been removed!");
/*     */         
/*     */         return;
/*     */       } 
/* 172 */       WorldChunk chunk = (WorldChunk)store.getComponent(reference, ChunkSavingSystems.WORLD_CHUNK_COMPONENT_TYPE);
/* 173 */       chunk.setSaving(true);
/*     */       
/* 175 */       Holder<ChunkStore> holder = store.copySerializableEntity(reference);
/*     */       
/* 177 */       data.toSaveTotal.getAndIncrement();
/* 178 */       data.chunkSavingFutures.add(CompletableFuture.supplyAsync(() -> saver.saveHolder(chunk.getX(), chunk.getZ(), holder))
/* 179 */           .thenCompose(Function.identity())
/* 180 */           .whenCompleteAsync((aVoid, throwable) -> {
/*     */               if (throwable != null) {
/*     */                 ((HytaleLogger.Api)ChunkSavingSystems.LOGGER.at(Level.SEVERE).withCause(throwable)).log("Failed to save chunk (%d, %d):", chunk.getX(), chunk.getZ());
/*     */               } else {
/*     */                 chunk.setFlag(ChunkFlag.ON_DISK, true);
/*     */                 ChunkSavingSystems.LOGGER.at(Level.FINEST).log("Finished saving chunk (%d, %d)", chunk.getX(), chunk.getZ());
/*     */               } 
/*     */               chunk.consumeNeedsSaving();
/*     */               chunk.setSaving(false);
/*     */             }(Executor)world));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\component\ChunkSavingSystems$Ticking.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */