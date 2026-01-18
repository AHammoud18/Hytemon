/*    */ package com.hypixel.hytale.server.worldgen.util.function;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConstantCoordinateDoubleSupplier
/*    */   implements ICoordinateDoubleSupplier
/*    */ {
/*  8 */   public static final ConstantCoordinateDoubleSupplier DEFAULT_ZERO = new ConstantCoordinateDoubleSupplier(0.0D);
/*  9 */   public static final ConstantCoordinateDoubleSupplier DEFAULT_ONE = new ConstantCoordinateDoubleSupplier(1.0D);
/*    */   
/*    */   protected final double value;
/*    */   
/*    */   public ConstantCoordinateDoubleSupplier(double value) {
/* 14 */     this.value = value;
/*    */   }
/*    */   
/*    */   public double getValue() {
/* 18 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public double apply(int seed, int x, int y) {
/* 23 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public double apply(int seed, int x, int y, int z) {
/* 28 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\function\ConstantCoordinateDoubleSupplier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */