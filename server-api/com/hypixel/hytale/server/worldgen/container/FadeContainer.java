/*    */ package com.hypixel.hytale.server.worldgen.container;
/*    */ 
/*    */ import com.hypixel.hytale.server.worldgen.zone.ZoneGeneratorResult;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FadeContainer
/*    */ {
/*    */   public static final double NO_FADE_HEIGHTMAP = -InfinityD;
/*    */   protected final double maskFadeStart;
/*    */   protected final double maskFadeLength;
/*    */   protected final double maskFadeSum;
/*    */   protected final double terrainFadeStart;
/*    */   protected final double terrainFadeLength;
/*    */   protected final double terrainFadeSum;
/*    */   protected final double fadeHeightmap;
/*    */   
/*    */   public FadeContainer(double maskFadeStart, double maskFadeLength, double terrainFadeStart, double terrainFadeLength, double fadeHeightmap) {
/* 23 */     this.maskFadeStart = maskFadeStart;
/* 24 */     this.maskFadeLength = maskFadeLength;
/* 25 */     this.maskFadeSum = maskFadeStart + maskFadeLength;
/* 26 */     this.terrainFadeStart = terrainFadeStart;
/* 27 */     this.terrainFadeLength = terrainFadeLength;
/* 28 */     this.terrainFadeSum = terrainFadeStart + terrainFadeLength;
/* 29 */     this.fadeHeightmap = fadeHeightmap;
/*    */   }
/*    */   
/*    */   public double getMaskFadeStart() {
/* 33 */     return this.maskFadeStart;
/*    */   }
/*    */   
/*    */   public double getMaskFadeLength() {
/* 37 */     return this.maskFadeLength;
/*    */   }
/*    */   
/*    */   public double getMaskFadeSum() {
/* 41 */     return this.maskFadeSum;
/*    */   }
/*    */   
/*    */   public double getHeightFadeStart() {
/* 45 */     return this.terrainFadeStart;
/*    */   }
/*    */   
/*    */   public double getHeightFadeLength() {
/* 49 */     return this.terrainFadeLength;
/*    */   }
/*    */   
/*    */   public double getHeightFadeSum() {
/* 53 */     return this.terrainFadeSum;
/*    */   }
/*    */   
/*    */   public double getFadeHeightmap() {
/* 57 */     return this.fadeHeightmap;
/*    */   }
/*    */   
/*    */   public double getMaskFactor(@Nonnull ZoneGeneratorResult result) {
/* 61 */     return getFactor(result, this.maskFadeStart, this.maskFadeLength);
/*    */   }
/*    */   
/*    */   public double getTerrainFactor(@Nonnull ZoneGeneratorResult result) {
/* 65 */     return getFactor(result, this.terrainFadeStart, this.terrainFadeLength);
/*    */   }
/*    */   
/*    */   public double getFactor(@Nonnull ZoneGeneratorResult result, double distanceFromBorder, double gradientWidth) {
/* 69 */     return 1.0D - limit((result.getBorderDistance() - distanceFromBorder) / gradientWidth);
/*    */   }
/*    */   
/*    */   public boolean shouldFade() {
/* 73 */     return (this.fadeHeightmap != Double.NEGATIVE_INFINITY);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 79 */     return "FadeContainer{maskFadeStart=" + this.maskFadeStart + ", maskFadeLength=" + this.maskFadeLength + ", maskFadeSum=" + this.maskFadeSum + ", terrainFadeStart=" + this.terrainFadeStart + ", terrainFadeLength=" + this.terrainFadeLength + ", terrainFadeSum=" + this.terrainFadeSum + ", fadeHeightmap=" + this.fadeHeightmap + "}";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static double limit(double d) {
/* 91 */     if (d < 0.0D) return 0.0D; 
/* 92 */     if (d > 1.0D) return 1.0D; 
/* 93 */     return d;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\container\FadeContainer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */