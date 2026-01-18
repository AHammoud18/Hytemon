/*    */ package com.hypixel.hytale.procedurallib.supplier;
/*    */ 
/*    */ import java.util.Random;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Constant
/*    */   implements IFloatRange
/*    */ {
/*    */   protected final float result;
/*    */   
/*    */   public Constant(float result) {
/* 18 */     this.result = result;
/*    */   }
/*    */   
/*    */   public float getResult() {
/* 22 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getValue(float v) {
/* 27 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getValue(FloatSupplier supplier) {
/* 32 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getValue(Random random) {
/* 37 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getValue(int seed, double x, double y, IDoubleCoordinateSupplier2d supplier) {
/* 42 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getValue(int seed, double x, double y, double z, IDoubleCoordinateSupplier3d supplier) {
/* 47 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 53 */     return "FloatRange.Constant{result=" + this.result + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\supplier\FloatRange$Constant.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */