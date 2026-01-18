/*    */ package com.hypixel.hytale.server.worldgen.cache;
/*    */ 
/*    */ import com.hypixel.hytale.server.worldgen.biome.Biome;
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
/*    */ public class BiomeCountResult
/*    */ {
/*    */   @Nonnull
/*    */   public final Biome biome;
/*    */   public double heightThresholdContext;
/*    */   public double heightNoise;
/*    */   public int count;
/*    */   
/*    */   public BiomeCountResult(@Nonnull Biome biome, double heightThresholdContext, double heightNoise) {
/* 85 */     this.biome = biome;
/* 86 */     this.heightThresholdContext = heightThresholdContext;
/* 87 */     this.heightNoise = heightNoise;
/* 88 */     this.count = 1;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 94 */     return "BiomeCountResult{biome=" + String.valueOf(this.biome) + ", heightThresholdContext=" + this.heightThresholdContext + ", heightNoise=" + this.heightNoise + ", count=" + this.count + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cache\InterpolatedBiomeCountList$BiomeCountResult.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */