/*    */ package com.hypixel.hytale.server.worldgen.biome;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.condition.IDoubleThreshold;
/*    */ import com.hypixel.hytale.procedurallib.condition.IIntCondition;
/*    */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*    */ import com.hypixel.hytale.server.worldgen.zone.ZoneGeneratorResult;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomBiomeGenerator
/*    */ {
/*    */   protected final NoiseProperty noiseProperty;
/*    */   protected final IDoubleThreshold threshold;
/*    */   protected final IIntCondition biomeMask;
/*    */   protected final int priority;
/*    */   
/*    */   public CustomBiomeGenerator(NoiseProperty noiseProperty, IDoubleThreshold threshold, IIntCondition biomeMask, int priority) {
/* 24 */     this.noiseProperty = noiseProperty;
/* 25 */     this.threshold = threshold;
/* 26 */     this.biomeMask = biomeMask;
/* 27 */     this.priority = priority;
/*    */   }
/*    */   
/*    */   public boolean shouldGenerateAt(int seed, double x, double z, @Nonnull ZoneGeneratorResult zoneResult, @Nonnull Biome customBiome) {
/* 31 */     double noise = this.noiseProperty.get(seed, x, z);
/* 32 */     if (zoneResult.getBorderDistance() < customBiome.getFadeContainer().getMaskFadeSum()) {
/* 33 */       double factor = customBiome.getFadeContainer().getMaskFactor(zoneResult);
/* 34 */       return isThreshold(noise, factor);
/*    */     } 
/* 36 */     return isThreshold(noise);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isThreshold(double d) {
/* 41 */     return this.threshold.eval(d);
/*    */   }
/*    */   
/*    */   public boolean isThreshold(double d, double factor) {
/* 45 */     return (factor >= 1.0E-5D && this.threshold.eval(d, factor));
/*    */   }
/*    */   
/*    */   public boolean isValidParentBiome(int index) {
/* 49 */     return this.biomeMask.eval(index);
/*    */   }
/*    */   
/*    */   public int getPriority() {
/* 53 */     return this.priority;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 59 */     return "CustomBiomeGenerator{noiseProperty=" + String.valueOf(this.noiseProperty) + ", threshold=" + String.valueOf(this.threshold) + ", biomeMask=" + String.valueOf(this.biomeMask) + ", priority=" + this.priority + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\biome\CustomBiomeGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */