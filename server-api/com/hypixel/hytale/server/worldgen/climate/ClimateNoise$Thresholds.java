/*     */ package com.hypixel.hytale.server.worldgen.climate;
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
/*     */ public class Thresholds
/*     */ {
/*     */   public static final double LAND_DEFAULT = 0.5D;
/*     */   public static final double ISLAND_DEFAULT = 0.8D;
/*     */   public static final double BEACH_SIZE_DEFAULT = 0.05D;
/*     */   public static final double SHALLOW_OCEAN_SIZE_DEFAULT = 0.15D;
/*     */   public final double land;
/*     */   public final double island;
/*     */   public final double beachSize;
/*     */   public final double shallowOceanSize;
/*     */   public final transient double landShoreInner;
/*     */   public final transient double islandShoreInner;
/*     */   public final transient double landShallowOceanOuter;
/*     */   public final transient double islandShallowOceanOuter;
/*     */   
/*     */   public Thresholds(double land, double island, double beach, double shore) {
/* 125 */     this.land = land;
/* 126 */     this.island = island;
/* 127 */     this.beachSize = beach;
/* 128 */     this.shallowOceanSize = shore;
/*     */     
/* 130 */     this.landShoreInner = land - beach;
/* 131 */     this.islandShoreInner = island + beach;
/*     */     
/* 133 */     this.landShallowOceanOuter = land + shore;
/* 134 */     this.islandShallowOceanOuter = island - shore * 0.5D;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\climate\ClimateNoise$Thresholds.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */