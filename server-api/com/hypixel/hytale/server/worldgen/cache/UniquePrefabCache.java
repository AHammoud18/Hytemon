/*    */ package com.hypixel.hytale.server.worldgen.cache;
/*    */ 
/*    */ import com.hypixel.hytale.server.worldgen.container.UniquePrefabContainer;
/*    */ import com.hypixel.hytale.server.worldgen.util.cache.SizedTimeoutCache;
/*    */ import java.util.Objects;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UniquePrefabCache
/*    */ {
/*    */   @Nonnull
/*    */   protected final SizedTimeoutCache<Integer, UniquePrefabContainer.UniquePrefabEntry[]> cache;
/*    */   
/*    */   public UniquePrefabCache(@Nonnull UniquePrefabFunction function, int maxSize, long expireAfterSeconds) {
/* 19 */     Objects.requireNonNull(function); this.cache = new SizedTimeoutCache(expireAfterSeconds, TimeUnit.SECONDS, maxSize, function::get, null);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public UniquePrefabContainer.UniquePrefabEntry[] get(int seed) {
/*    */     try {
/* 25 */       return (UniquePrefabContainer.UniquePrefabEntry[])this.cache.get(Integer.valueOf(seed));
/* 26 */     } catch (Exception e) {
/* 27 */       throw new Error("Failed to receive UniquePrefabEntry for " + seed, e);
/*    */     } 
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface UniquePrefabFunction {
/*    */     UniquePrefabContainer.UniquePrefabEntry[] get(int param1Int);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cache\UniquePrefabCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */