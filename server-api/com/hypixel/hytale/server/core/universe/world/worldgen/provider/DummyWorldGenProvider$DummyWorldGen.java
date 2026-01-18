/*    */ package com.hypixel.hytale.server.core.universe.world.worldgen.provider;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedBlockChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedBlockStateChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldgen.GeneratedEntityChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldgen.IWorldGen;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldgen.WorldGenTimingsCollector;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.function.LongPredicate;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class DummyWorldGen
/*    */   implements IWorldGen
/*    */ {
/*    */   @Nullable
/*    */   public WorldGenTimingsCollector getTimings() {
/* 56 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Transform[] getSpawnPoints(int seed) {
/* 62 */     return new Transform[] { new Transform(0.0D, 1.0D, 0.0D) };
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CompletableFuture<GeneratedChunk> generate(int seed, long index, int cx, int cz, LongPredicate stillNeeded) {
/* 68 */     GeneratedBlockChunk chunk = new GeneratedBlockChunk(index, cx, cz);
/*    */     
/* 70 */     for (int x = 0; x < 32; x++) {
/* 71 */       for (int z = 0; z < 32; z++) {
/* 72 */         chunk.setBlock(x, 0, z, 1, 0, 0);
/*    */       }
/*    */     } 
/*    */     
/* 76 */     return CompletableFuture.completedFuture(new GeneratedChunk(chunk, new GeneratedBlockStateChunk(), new GeneratedEntityChunk(), GeneratedChunk.makeSections()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldgen\provider\DummyWorldGenProvider$DummyWorldGen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */