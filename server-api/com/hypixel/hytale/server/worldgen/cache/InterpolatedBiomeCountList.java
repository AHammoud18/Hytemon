/*    */ package com.hypixel.hytale.server.worldgen.cache;
/*    */ 
/*    */ import com.hypixel.hytale.metrics.metric.AverageCollector;
/*    */ import com.hypixel.hytale.server.worldgen.biome.Biome;
/*    */ import com.hypixel.hytale.server.worldgen.chunk.ZoneBiomeResult;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*    */ import it.unimi.dsi.fastutil.ints.IntList;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class InterpolatedBiomeCountList
/*    */ {
/*    */   @Nonnull
/* 26 */   private final Int2ObjectMap<BiomeCountResult> results = (Int2ObjectMap<BiomeCountResult>)new Int2ObjectOpenHashMap(); @Nonnull
/* 27 */   private final IntList biomeIds = (IntList)new IntArrayList();
/*    */   private Biome center;
/*    */   
/*    */   public BiomeCountResult get(@Nonnull Biome biome) {
/* 31 */     return get(biome.getId());
/*    */   }
/*    */   
/*    */   public BiomeCountResult get(int index) {
/* 35 */     return (BiomeCountResult)this.results.get(index);
/*    */   }
/*    */   
/*    */   public void setCenter(@Nonnull ZoneBiomeResult result) {
/* 39 */     Biome biome = result.getBiome();
/* 40 */     this.center = biome;
/* 41 */     this.biomeIds.add(biome.getId());
/* 42 */     this.results.put(biome.getId(), new BiomeCountResult(biome, result.heightThresholdContext, result.heightmapNoise));
/*    */   }
/*    */   
/*    */   public void add(@Nonnull ZoneBiomeResult result, int distance2) {
/* 46 */     Biome biome = result.getBiome();
/* 47 */     int biomeId = biome.getId();
/*    */ 
/*    */ 
/*    */     
/* 51 */     if (this.center.getInterpolation().getBiomeRadius2(biomeId) < distance2)
/*    */       return; 
/* 53 */     BiomeCountResult r = get(biomeId);
/* 54 */     if (r == null) {
/* 55 */       this.biomeIds.add(biomeId);
/* 56 */       this.results.put(biomeId, new BiomeCountResult(biome, result.heightThresholdContext, result.heightmapNoise));
/*    */     } else {
/* 58 */       r.heightNoise = AverageCollector.add(r.heightNoise, result.heightmapNoise, r.count);
/* 59 */       r.count++;
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public IntList getBiomeIds() {
/* 65 */     return this.biomeIds;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 71 */     return "InterpolatedBiomeCountList{results=" + String.valueOf(this.results) + ", biomeIds=" + String.valueOf(this.biomeIds) + "}";
/*    */   }
/*    */ 
/*    */   
/*    */   public static class BiomeCountResult
/*    */   {
/*    */     @Nonnull
/*    */     public final Biome biome;
/*    */     
/*    */     public double heightThresholdContext;
/*    */     public double heightNoise;
/*    */     public int count;
/*    */     
/*    */     public BiomeCountResult(@Nonnull Biome biome, double heightThresholdContext, double heightNoise) {
/* 85 */       this.biome = biome;
/* 86 */       this.heightThresholdContext = heightThresholdContext;
/* 87 */       this.heightNoise = heightNoise;
/* 88 */       this.count = 1;
/*    */     }
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public String toString() {
/* 94 */       return "BiomeCountResult{biome=" + String.valueOf(this.biome) + ", heightThresholdContext=" + this.heightThresholdContext + ", heightNoise=" + this.heightNoise + ", count=" + this.count + "}";
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cache\InterpolatedBiomeCountList.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */