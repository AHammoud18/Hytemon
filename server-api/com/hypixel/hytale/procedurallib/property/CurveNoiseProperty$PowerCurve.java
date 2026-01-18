/*    */ package com.hypixel.hytale.procedurallib.property;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import java.util.function.DoubleUnaryOperator;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PowerCurve
/*    */   implements DoubleUnaryOperator
/*    */ {
/*    */   protected static final double MAX = 10.0D;
/*    */   protected final double a;
/*    */   protected final double b;
/*    */   
/*    */   public PowerCurve(double a, double b) {
/* 45 */     this.a = MathUtil.clamp(a, 0.0D, 10.0D);
/* 46 */     this.b = MathUtil.clamp(b, -this.a, 10.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public double applyAsDouble(double operand) {
/* 51 */     operand = 1.0D - operand;
/* 52 */     return 1.0D - Math.pow(operand, this.a + this.b * operand);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 58 */     return "PowerCurve{a=" + this.a + ", b=" + this.b + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\property\CurveNoiseProperty$PowerCurve.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */