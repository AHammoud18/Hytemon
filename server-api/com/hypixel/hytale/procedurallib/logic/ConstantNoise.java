/*    */ package com.hypixel.hytale.procedurallib.logic;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.NoiseFunction;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConstantNoise
/*    */   implements NoiseFunction
/*    */ {
/*    */   protected final double value;
/*    */   
/*    */   public ConstantNoise(double value) {
/* 15 */     this.value = value;
/*    */   }
/*    */   
/*    */   public double getValue() {
/* 19 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, int offsetSeed, double x, double y) {
/* 24 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, int offsetSeed, double x, double y, double z) {
/* 29 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 35 */     return "ConstantNoise{value=" + this.value + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\ConstantNoise.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */