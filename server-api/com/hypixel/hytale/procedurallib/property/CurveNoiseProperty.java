/*    */ package com.hypixel.hytale.procedurallib.property;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import java.util.function.DoubleUnaryOperator;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class CurveNoiseProperty
/*    */   implements NoiseProperty {
/*    */   protected final NoiseProperty noise;
/*    */   protected final DoubleUnaryOperator function;
/*    */   
/*    */   public CurveNoiseProperty(NoiseProperty noise, DoubleUnaryOperator function) {
/* 13 */     this.noise = noise;
/* 14 */     this.function = function;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y) {
/* 19 */     double value = this.noise.get(seed, x, y);
/* 20 */     return this.function.applyAsDouble(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y, double z) {
/* 25 */     double value = this.noise.get(seed, x, y, z);
/* 26 */     return this.function.applyAsDouble(value);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 32 */     return "CurveNoiseProperty{noise=" + String.valueOf(this.noise) + ", function=" + String.valueOf(this.function) + "}";
/*    */   }
/*    */ 
/*    */   
/*    */   public static class PowerCurve
/*    */     implements DoubleUnaryOperator
/*    */   {
/*    */     protected static final double MAX = 10.0D;
/*    */     
/*    */     protected final double a;
/*    */     protected final double b;
/*    */     
/*    */     public PowerCurve(double a, double b) {
/* 45 */       this.a = MathUtil.clamp(a, 0.0D, 10.0D);
/* 46 */       this.b = MathUtil.clamp(b, -this.a, 10.0D);
/*    */     }
/*    */ 
/*    */     
/*    */     public double applyAsDouble(double operand) {
/* 51 */       operand = 1.0D - operand;
/* 52 */       return 1.0D - Math.pow(operand, this.a + this.b * operand);
/*    */     }
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public String toString() {
/* 58 */       return "PowerCurve{a=" + this.a + ", b=" + this.b + "}";
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\property\CurveNoiseProperty.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */