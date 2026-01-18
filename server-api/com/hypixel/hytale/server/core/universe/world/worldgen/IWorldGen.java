/*    */ package com.hypixel.hytale.server.core.universe.world.worldgen;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.server.core.universe.world.spawn.FitToHeightMapSpawnProvider;
/*    */ import com.hypixel.hytale.server.core.universe.world.spawn.ISpawnProvider;
/*    */ import com.hypixel.hytale.server.core.universe.world.spawn.IndividualSpawnProvider;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.function.LongPredicate;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public interface IWorldGen
/*    */ {
/*    */   @Nullable
/*    */   WorldGenTimingsCollector getTimings();
/*    */   
/*    */   CompletableFuture<GeneratedChunk> generate(int paramInt1, long paramLong, int paramInt2, int paramInt3, LongPredicate paramLongPredicate);
/*    */   
/*    */   @Deprecated
/*    */   Transform[] getSpawnPoints(int paramInt);
/*    */   
/*    */   @Nonnull
/*    */   default ISpawnProvider getDefaultSpawnProvider(int seed) {
/* 24 */     return (ISpawnProvider)new FitToHeightMapSpawnProvider((ISpawnProvider)new IndividualSpawnProvider(getSpawnPoints(seed)));
/*    */   }
/*    */   
/*    */   default void shutdown() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldgen\IWorldGen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */