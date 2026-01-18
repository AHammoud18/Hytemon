/*     */ package com.hypixel.hytale.server.core.universe.world.storage.provider;
/*     */ 
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.IChunkLoader;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import java.io.IOException;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Function;
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
/*     */ public class MigrationChunkLoader
/*     */   implements IChunkLoader
/*     */ {
/*     */   @Nonnull
/*     */   private final IChunkLoader[] loaders;
/*     */   
/*     */   public MigrationChunkLoader(@Nonnull IChunkLoader... loaders) {
/* 127 */     this.loaders = loaders;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 132 */     IOException exception = null;
/* 133 */     for (IChunkLoader loader : this.loaders) {
/*     */       try {
/* 135 */         loader.close();
/* 136 */       } catch (Exception e) {
/* 137 */         if (exception == null) exception = new IOException("Failed to close one or more loaders!"); 
/* 138 */         exception.addSuppressed(e);
/*     */       } 
/*     */     } 
/* 141 */     if (exception != null) throw exception;
/*     */   
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public CompletableFuture<Holder<ChunkStore>> loadHolder(int x, int z) {
/* 147 */     CompletableFuture<Holder<ChunkStore>> future = this.loaders[0].loadHolder(x, z);
/* 148 */     for (int i = 1; i < this.loaders.length; i++) {
/*     */       
/* 150 */       IChunkLoader loader = this.loaders[i];
/* 151 */       CompletableFuture<Holder<ChunkStore>> previous = future;
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
/* 167 */       future = previous.handle((worldChunk, throwable) -> (throwable != null) ? loader.loadHolder(x, z).exceptionally(()) : ((worldChunk == null) ? loader.loadHolder(x, z) : previous)).thenCompose(Function.identity());
/*     */     } 
/* 169 */     return future;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public LongSet getIndexes() throws IOException {
/* 175 */     LongOpenHashSet indexes = new LongOpenHashSet();
/* 176 */     for (IChunkLoader loader : this.loaders) {
/* 177 */       indexes.addAll((LongCollection)loader.getIndexes());
/*     */     }
/* 179 */     return (LongSet)indexes;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\provider\MigrationChunkStorageProvider$MigrationChunkLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */