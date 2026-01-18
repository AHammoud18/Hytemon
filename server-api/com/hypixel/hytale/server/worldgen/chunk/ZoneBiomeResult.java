/*    */ package com.hypixel.hytale.server.worldgen.chunk;
/*    */ 
/*    */ import com.hypixel.hytale.server.worldgen.biome.Biome;
/*    */ import com.hypixel.hytale.server.worldgen.zone.ZoneGeneratorResult;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZoneBiomeResult
/*    */ {
/*    */   public ZoneGeneratorResult zoneResult;
/*    */   public Biome biome;
/*    */   public double heightThresholdContext;
/*    */   public double heightmapNoise;
/*    */   
/*    */   public ZoneBiomeResult() {}
/*    */   
/*    */   public ZoneBiomeResult(ZoneGeneratorResult zoneResult, Biome biome, double heightThresholdContext, double heightmapNoise) {
/* 20 */     this.zoneResult = zoneResult;
/* 21 */     this.biome = biome;
/* 22 */     this.heightThresholdContext = heightThresholdContext;
/* 23 */     this.heightmapNoise = heightmapNoise;
/*    */   }
/*    */   
/*    */   public ZoneGeneratorResult getZoneResult() {
/* 27 */     return this.zoneResult;
/*    */   }
/*    */   
/*    */   public void setZoneResult(ZoneGeneratorResult zoneResult) {
/* 31 */     this.zoneResult = zoneResult;
/*    */   }
/*    */   
/*    */   public Biome getBiome() {
/* 35 */     return this.biome;
/*    */   }
/*    */   
/*    */   public void setBiome(Biome biome) {
/* 39 */     this.biome = biome;
/*    */   }
/*    */   
/*    */   public double getHeightThresholdContext() {
/* 43 */     return this.heightThresholdContext;
/*    */   }
/*    */   
/*    */   public void setHeightThresholdContext(double heightThresholdContext) {
/* 47 */     this.heightThresholdContext = heightThresholdContext;
/*    */   }
/*    */   
/*    */   public double getHeightmapNoise() {
/* 51 */     return this.heightmapNoise;
/*    */   }
/*    */   
/*    */   public void setHeightmapNoise(double heightmapNoise) {
/* 55 */     this.heightmapNoise = heightmapNoise;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\chunk\ZoneBiomeResult.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */