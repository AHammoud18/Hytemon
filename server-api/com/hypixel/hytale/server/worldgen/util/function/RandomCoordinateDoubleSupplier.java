/*    */ package com.hypixel.hytale.server.worldgen.util.function;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.HashUtil;
/*    */ import com.hypixel.hytale.procedurallib.supplier.IDoubleRange;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RandomCoordinateDoubleSupplier
/*    */   implements ICoordinateDoubleSupplier
/*    */ {
/*    */   protected final IDoubleRange range;
/*    */   
/*    */   public RandomCoordinateDoubleSupplier(IDoubleRange range) {
/* 14 */     this.range = range;
/*    */   }
/*    */   
/*    */   public IDoubleRange getRange() {
/* 18 */     return this.range;
/*    */   }
/*    */ 
/*    */   
/*    */   public double apply(int seed, int x, int y) {
/* 23 */     return this.range.getValue(HashUtil.random(seed, x, y));
/*    */   }
/*    */ 
/*    */   
/*    */   public double apply(int seed, int x, int y, int z) {
/* 28 */     return this.range.getValue(HashUtil.random(seed, x, y, z));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\function\RandomCoordinateDoubleSupplier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */