/*    */ package com.hypixel.hytale.server.worldgen.util;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.NoiseFunction;
/*    */ import com.hypixel.hytale.procedurallib.logic.ConstantNoise;
/*    */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*    */ import com.hypixel.hytale.procedurallib.property.SingleNoiseProperty;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ConstantNoiseProperty
/*    */ {
/* 12 */   private static final ConstantNoise DEFAULT_ZERO_NOISE = new ConstantNoise(0.0D);
/* 13 */   public static final NoiseProperty DEFAULT_ZERO = (NoiseProperty)new SingleNoiseProperty(0, (NoiseFunction)DEFAULT_ZERO_NOISE);
/* 14 */   private static final ConstantNoise DEFAULT_ONE_NOISE = new ConstantNoise(1.0D);
/* 15 */   public static final NoiseProperty DEFAULT_ONE = (NoiseProperty)new SingleNoiseProperty(0, (NoiseFunction)DEFAULT_ONE_NOISE);
/*    */   
/*    */   private ConstantNoiseProperty() {
/* 18 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\ConstantNoiseProperty.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */