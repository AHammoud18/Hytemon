/*    */ package com.hypixel.hytale.builtin.hytalegenerator.plugin;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.chunkgenerator.ChunkRequest;
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.server.core.universe.world.spawn.ISpawnProvider;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGen;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldgen.WorldGenTimingsCollector;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.function.LongPredicate;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class Handle
/*    */   implements IWorldGen {
/*    */   @Nonnull
/*    */   private final HytaleGenerator plugin;
/*    */   @Nonnull
/*    */   private final ChunkRequest.GeneratorProfile profile;
/*    */   
/*    */   public Handle(@Nonnull HytaleGenerator plugin, @Nonnull ChunkRequest.GeneratorProfile profile) {
/* 22 */     this.plugin = plugin;
/* 23 */     this.profile = profile;
/*    */   }
/*    */ 
/*    */   
/*    */   public CompletableFuture<GeneratedChunk> generate(int seed, long index, int x, int z, LongPredicate stillNeeded) {
/* 28 */     ChunkRequest.Arguments arguments = new ChunkRequest.Arguments(seed, index, x, z, stillNeeded);
/* 29 */     this.profile.setSeed(seed);
/* 30 */     ChunkRequest request = new ChunkRequest(this.profile, arguments);
/*    */     
/* 32 */     return this.plugin.submitChunkRequest(request);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ChunkRequest.GeneratorProfile getProfile() {
/* 37 */     return this.profile;
/*    */   }
/*    */ 
/*    */   
/*    */   public Transform[] getSpawnPoints(int seed) {
/* 42 */     return new Transform[] { this.profile.spawnPosition().clone() };
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ISpawnProvider getDefaultSpawnProvider(int seed) {
/* 48 */     return super.getDefaultSpawnProvider(seed);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public WorldGenTimingsCollector getTimings() {
/* 54 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\plugin\Handle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */