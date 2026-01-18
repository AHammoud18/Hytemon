/*    */ package com.hypixel.hytale.server.worldgen.cave.shape.distorted;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.NoiseFunction;
/*    */ import com.hypixel.hytale.procedurallib.logic.ConstantNoise;
/*    */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*    */ import com.hypixel.hytale.procedurallib.property.SingleNoiseProperty;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ShapeDistortion {
/* 10 */   private static final NoiseProperty DEFAULT_NOISE = (NoiseProperty)new SingleNoiseProperty((NoiseFunction)new ConstantNoise(1.0D));
/* 11 */   public static final ShapeDistortion DEFAULT = new ShapeDistortion(DEFAULT_NOISE, DEFAULT_NOISE, DEFAULT_NOISE);
/*    */   
/*    */   private final NoiseProperty widthNoise;
/*    */   private final NoiseProperty floorNoise;
/*    */   private final NoiseProperty ceilingNoise;
/*    */   
/*    */   public ShapeDistortion(NoiseProperty widthNoise, NoiseProperty floorNoise, NoiseProperty ceilingNoise) {
/* 18 */     this.widthNoise = widthNoise;
/* 19 */     this.floorNoise = floorNoise;
/* 20 */     this.ceilingNoise = ceilingNoise;
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
/*    */   public double getWidthFactor(int seed, double x, double z) {
/* 32 */     return this.widthNoise.get(seed, x, z);
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
/*    */   public double getFloorFactor(int seed, double x, double z) {
/* 44 */     return this.floorNoise.get(seed, x, z);
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
/*    */   public double getCeilingFactor(int seed, double x, double z) {
/* 56 */     return this.ceilingNoise.get(seed, x, z);
/*    */   }
/*    */   
/*    */   public static ShapeDistortion of(@Nullable NoiseProperty widthNoise, @Nullable NoiseProperty floorNoise, @Nullable NoiseProperty ceilingNoise) {
/* 60 */     widthNoise = (widthNoise == null) ? DEFAULT_NOISE : widthNoise;
/* 61 */     floorNoise = (floorNoise == null) ? DEFAULT_NOISE : floorNoise;
/* 62 */     ceilingNoise = (ceilingNoise == null) ? DEFAULT_NOISE : ceilingNoise;
/*    */     
/* 64 */     if (widthNoise == DEFAULT_NOISE && floorNoise == DEFAULT_NOISE && ceilingNoise == DEFAULT_NOISE) {
/* 65 */       return DEFAULT;
/*    */     }
/*    */     
/* 68 */     return new ShapeDistortion(widthNoise, floorNoise, ceilingNoise);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\cave\shape\distorted\ShapeDistortion.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */