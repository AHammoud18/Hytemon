/*    */ package com.hypixel.hytale.procedurallib.property;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.random.ICoordinateRandomizer;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DistortedNoiseProperty
/*    */   implements NoiseProperty
/*    */ {
/*    */   protected final NoiseProperty noiseProperty;
/*    */   protected final ICoordinateRandomizer randomizer;
/*    */   
/*    */   public DistortedNoiseProperty(NoiseProperty noiseProperty, ICoordinateRandomizer randomizer) {
/* 16 */     this.noiseProperty = noiseProperty;
/* 17 */     this.randomizer = randomizer;
/*    */   }
/*    */   
/*    */   public NoiseProperty getNoiseProperty() {
/* 21 */     return this.noiseProperty;
/*    */   }
/*    */   
/*    */   public ICoordinateRandomizer getRandomizer() {
/* 25 */     return this.randomizer;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y) {
/* 30 */     return this.noiseProperty.get(seed, this.randomizer
/* 31 */         .randomDoubleX(seed, x, y), this.randomizer
/* 32 */         .randomDoubleY(seed, x, y));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y, double z) {
/* 38 */     return this.noiseProperty.get(seed, this.randomizer
/* 39 */         .randomDoubleX(seed, x, y, z), this.randomizer
/* 40 */         .randomDoubleY(seed, x, y, z), this.randomizer
/* 41 */         .randomDoubleZ(seed, x, y, z));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 48 */     return "DistortedNoiseProperty{noiseProperty=" + String.valueOf(this.noiseProperty) + ", randomizer=" + String.valueOf(this.randomizer) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\property\DistortedNoiseProperty.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */