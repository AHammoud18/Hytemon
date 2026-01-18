/*    */ package com.hypixel.hytale.procedurallib.supplier;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.HashUtil;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DoubleRangeCoordinateHashSupplier
/*    */   implements IDoubleCoordinateHashSupplier
/*    */ {
/*    */   protected final IDoubleRange range;
/*    */   
/*    */   public DoubleRangeCoordinateHashSupplier(IDoubleRange range) {
/* 15 */     this.range = range;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, int x, int y, long hash) {
/* 20 */     return this.range.getValue(HashUtil.random(seed, x, y, hash));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 26 */     return "DoubleRangeCoordinateHashSupplier{range=" + String.valueOf(this.range) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\supplier\DoubleRangeCoordinateHashSupplier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */