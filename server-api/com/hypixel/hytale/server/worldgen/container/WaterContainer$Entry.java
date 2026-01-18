/*    */ package com.hypixel.hytale.server.worldgen.container;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*    */ import com.hypixel.hytale.procedurallib.supplier.IDoubleCoordinateSupplier;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Entry
/*    */ {
/* 47 */   public static final Entry[] EMPTY_ARRAY = new Entry[0];
/*    */   
/*    */   private final int block;
/*    */   
/*    */   private final int fluid;
/*    */   
/*    */   private final IDoubleCoordinateSupplier min;
/*    */   
/*    */   private final IDoubleCoordinateSupplier max;
/*    */   private final ICoordinateCondition mask;
/*    */   
/*    */   public Entry(int block, int fluid, IDoubleCoordinateSupplier min, IDoubleCoordinateSupplier max, ICoordinateCondition mask) {
/* 59 */     this.block = block;
/* 60 */     this.fluid = fluid;
/* 61 */     this.min = min;
/* 62 */     this.max = max;
/* 63 */     this.mask = mask;
/*    */   }
/*    */   
/*    */   public int getBlock() {
/* 67 */     return this.block;
/*    */   }
/*    */   
/*    */   public int getFluid() {
/* 71 */     return this.fluid;
/*    */   }
/*    */   
/*    */   public int getMax(int seed, int x, int z) {
/* 75 */     return MathUtil.floor(this.max.get(seed, x, z));
/*    */   }
/*    */   
/*    */   public int getMin(int seed, int x, int z) {
/* 79 */     return MathUtil.floor(this.min.get(seed, x, z));
/*    */   }
/*    */   
/*    */   public boolean shouldPopulate(int seed, int x, int z) {
/* 83 */     return this.mask.eval(seed, x, z);
/*    */   }
/*    */   
/*    */   public IDoubleCoordinateSupplier getMax() {
/* 87 */     return this.max;
/*    */   }
/*    */   
/*    */   public IDoubleCoordinateSupplier getMin() {
/* 91 */     return this.min;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 97 */     return "Entry{, fluid=" + this.fluid + ", min=" + String.valueOf(this.min) + ", max=" + String.valueOf(this.max) + ", mask=" + String.valueOf(this.mask) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\container\WaterContainer$Entry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */