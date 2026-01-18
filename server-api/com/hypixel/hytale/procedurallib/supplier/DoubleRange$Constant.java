/*    */ package com.hypixel.hytale.procedurallib.supplier;
/*    */ 
/*    */ import java.util.Random;
/*    */ import java.util.function.DoubleSupplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Constant
/*    */   implements IDoubleRange
/*    */ {
/*    */   protected final double result;
/*    */   
/*    */   public Constant(double result) {
/* 20 */     this.result = result;
/*    */   }
/*    */   
/*    */   public double getResult() {
/* 24 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getValue(double v) {
/* 29 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getValue(DoubleSupplier supplier) {
/* 34 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getValue(Random random) {
/* 39 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getValue(int seed, double x, double y, IDoubleCoordinateSupplier2d supplier) {
/* 44 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getValue(int seed, double x, double y, double z, IDoubleCoordinateSupplier3d supplier) {
/* 49 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 55 */     return "DoubleRange.Constant{result=" + this.result + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\supplier\DoubleRange$Constant.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */