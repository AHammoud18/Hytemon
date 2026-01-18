/*    */ package com.hypixel.hytale.procedurallib.supplier;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConstantDoubleCoordinateHashSupplier
/*    */   implements IDoubleCoordinateHashSupplier
/*    */ {
/* 10 */   public static final ConstantDoubleCoordinateHashSupplier ZERO = new ConstantDoubleCoordinateHashSupplier(0.0D);
/* 11 */   public static final ConstantDoubleCoordinateHashSupplier ONE = new ConstantDoubleCoordinateHashSupplier(1.0D);
/*    */   
/*    */   protected final double result;
/*    */   
/*    */   public ConstantDoubleCoordinateHashSupplier(double result) {
/* 16 */     this.result = result;
/*    */   }
/*    */   
/*    */   public double getResult() {
/* 20 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, int x, int y, long hash) {
/* 25 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 31 */     return "ConstantDoubleCoordinateHashSupplier{result=" + this.result + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\supplier\ConstantDoubleCoordinateHashSupplier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */