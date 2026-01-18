/*     */ package com.hypixel.hytale.server.core.universe.world.worldgen.provider;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedBlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedBlockStateChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedEntityChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGen;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.WorldGenTimingsCollector;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.LongPredicate;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ class FlatWorldGen
/*     */   implements IWorldGen
/*     */ {
/*     */   private final FlatWorldGenProvider.Layer[] layers;
/*     */   private final int tintId;
/*     */   
/*     */   public FlatWorldGen(FlatWorldGenProvider.Layer[] layers, int tintId) {
/* 108 */     this.layers = layers;
/* 109 */     this.tintId = tintId;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public WorldGenTimingsCollector getTimings() {
/* 115 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Transform[] getSpawnPoints(int seed) {
/* 121 */     return new Transform[] { new Transform(0.0D, 81.0D, 0.0D) };
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CompletableFuture<GeneratedChunk> generate(int seed, long index, int cx, int cz, LongPredicate stillNeeded) {
/* 127 */     GeneratedBlockChunk generatedBlockChunk = new GeneratedBlockChunk(index, cx, cz);
/*     */     
/* 129 */     for (int x = 0; x < 32; x++) {
/* 130 */       for (int z = 0; z < 32; z++) {
/* 131 */         generatedBlockChunk.setTint(x, z, this.tintId);
/*     */       }
/*     */     } 
/*     */     
/* 135 */     for (FlatWorldGenProvider.Layer layer : this.layers) {
/* 136 */       for (int i = 0; i < 32; i++) {
/* 137 */         for (int z = 0; z < 32; z++) {
/* 138 */           for (int y = layer.from; y < layer.to; y++) {
/* 139 */             generatedBlockChunk.setBlock(i, y, z, layer.blockId, 0, 0);
/* 140 */             generatedBlockChunk.setEnvironment(i, y, z, layer.environmentId);
/*     */           } 
/* 142 */           generatedBlockChunk.setTint(i, z, this.tintId);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 147 */     return CompletableFuture.completedFuture(new GeneratedChunk(generatedBlockChunk, new GeneratedBlockStateChunk(), new GeneratedEntityChunk(), GeneratedChunk.makeSections()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldgen\provider\FlatWorldGenProvider$FlatWorldGen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */