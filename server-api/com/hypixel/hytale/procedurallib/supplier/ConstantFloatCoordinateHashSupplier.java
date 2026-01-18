/*    */ package com.hypixel.hytale.procedurallib.supplier;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConstantFloatCoordinateHashSupplier
/*    */   implements IFloatCoordinateHashSupplier
/*    */ {
/* 10 */   public static final ConstantFloatCoordinateHashSupplier ZERO = new ConstantFloatCoordinateHashSupplier(0.0F);
/* 11 */   public static final ConstantFloatCoordinateHashSupplier ONE = new ConstantFloatCoordinateHashSupplier(1.0F);
/*    */   
/*    */   protected final float result;
/*    */   
/*    */   public ConstantFloatCoordinateHashSupplier(float result) {
/* 16 */     this.result = result;
/*    */   }
/*    */   
/*    */   public float getResult() {
/* 20 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public float get(int seed, double x, double y, long hash) {
/* 25 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 31 */     return "ConstantFloatCoordinateHashSupplier{result=" + this.result + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\supplier\ConstantFloatCoordinateHashSupplier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */