/*    */ package com.hypixel.hytale.procedurallib.property;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.NoiseFunction;
/*    */ import com.hypixel.hytale.procedurallib.logic.GeneralNoise;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SingleNoiseProperty
/*    */   implements NoiseProperty
/*    */ {
/*    */   protected final int seedOffset;
/*    */   protected final NoiseFunction function;
/*    */   
/*    */   public SingleNoiseProperty(NoiseFunction function) {
/* 20 */     this(0, function);
/*    */   }
/*    */   
/*    */   public SingleNoiseProperty(int seedOffset, NoiseFunction function) {
/* 24 */     this.seedOffset = seedOffset;
/* 25 */     this.function = function;
/*    */   }
/*    */   
/*    */   public int getSeedOffset() {
/* 29 */     return this.seedOffset;
/*    */   }
/*    */   
/*    */   public NoiseFunction getFunction() {
/* 33 */     return this.function;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y) {
/* 38 */     return GeneralNoise.limit(this.function.get(seed, seed + this.seedOffset, x, y) * 0.5D + 0.5D);
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y, double z) {
/* 43 */     return GeneralNoise.limit(this.function.get(seed, seed + this.seedOffset, x, y, z) * 0.5D + 0.5D);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 49 */     return "SingleNoiseProperty{seedOffset=" + this.seedOffset + ", function=" + String.valueOf(this.function) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\property\SingleNoiseProperty.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */