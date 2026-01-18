/*     */ package com.hypixel.hytale.server.core.universe.world.worldgen.provider;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.core.asset.util.ColorParseUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedBlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedBlockStateChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedEntityChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGen;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldgen.WorldGenLoadException;
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
/*     */ public class VoidWorldGen
/*     */   implements IWorldGen
/*     */ {
/*     */   private final int tintId;
/*     */   private final int environmentId;
/*     */   
/*     */   public VoidWorldGen() {
/*  78 */     this.tintId = 0;
/*  79 */     this.environmentId = 0;
/*     */   }
/*     */   
/*     */   public VoidWorldGen(@Nullable Color tint, @Nullable String environment) throws WorldGenLoadException {
/*  83 */     int tintId = (tint == null) ? 0 : ColorParseUtil.colorToARGBInt(tint);
/*  84 */     this.tintId = tintId;
/*     */     
/*  86 */     String key = (environment != null) ? environment : "Default";
/*  87 */     int index = Environment.getAssetMap().getIndex(key);
/*  88 */     if (index == Integer.MIN_VALUE) throw new WorldGenLoadException("Unknown key! " + key); 
/*  89 */     this.environmentId = index;
/*     */   }
/*     */   
/*     */   public VoidWorldGen(int tintId, int environmentId) {
/*  93 */     this.tintId = tintId;
/*  94 */     this.environmentId = environmentId;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public WorldGenTimingsCollector getTimings() {
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Transform[] getSpawnPoints(int seed) {
/* 106 */     return new Transform[] { new Transform(0.0D, 1.0D, 0.0D) };
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CompletableFuture<GeneratedChunk> generate(int seed, long index, int cx, int cz, LongPredicate stillNeeded) {
/* 112 */     GeneratedBlockChunk generatedBlockChunk = new GeneratedBlockChunk(index, cx, cz);
/*     */     
/* 114 */     for (int x = 0; x < 32; x++) {
/* 115 */       for (int z = 0; z < 32; z++) {
/* 116 */         if (this.environmentId != 0) generatedBlockChunk.setEnvironmentColumn(x, z, this.environmentId); 
/* 117 */         generatedBlockChunk.setTint(x, z, this.tintId);
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     return CompletableFuture.completedFuture(new GeneratedChunk(generatedBlockChunk, new GeneratedBlockStateChunk(), new GeneratedEntityChunk(), GeneratedChunk.makeSections()));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldgen\provider\VoidWorldGenProvider$VoidWorldGen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */