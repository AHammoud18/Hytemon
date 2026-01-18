/*    */ package com.hypixel.hytale.server.worldgen.zone;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ public class ZonePatternGeneratorCache {
/*  9 */   protected final Map<Integer, ZonePatternGenerator> cache = new ConcurrentHashMap<>(); protected final Function<Integer, ZonePatternGenerator> compute;
/*    */   
/*    */   public ZonePatternGeneratorCache(ZonePatternProvider provider) {
/* 12 */     Objects.requireNonNull(provider); this.compute = provider::createGenerator;
/*    */   }
/*    */   
/*    */   public ZonePatternGenerator get(int seed) {
/*    */     try {
/* 17 */       return this.cache.computeIfAbsent(Integer.valueOf(seed), this.compute);
/* 18 */     } catch (Exception e) {
/* 19 */       throw new Error("Failed to receive UniquePrefabEntry for " + seed, e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\zone\ZonePatternGeneratorCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */