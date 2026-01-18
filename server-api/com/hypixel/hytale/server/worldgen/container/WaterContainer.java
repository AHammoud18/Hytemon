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
/*    */ public class WaterContainer
/*    */ {
/*    */   public static final int NO_WATER_AT_COORDINATED = -2147483648;
/*    */   private final Entry[] entries;
/*    */   
/*    */   public static boolean isValidWaterHeight(int height) {
/* 17 */     return (height > 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public WaterContainer(Entry[] entries) {
/* 23 */     this.entries = entries;
/*    */   }
/*    */   
/*    */   public boolean hasEntries() {
/* 27 */     return (this.entries.length != 0);
/*    */   }
/*    */   
/*    */   public Entry[] getEntries() {
/* 31 */     return this.entries;
/*    */   }
/*    */   
/*    */   public int getMaxHeight(int seed, int x, int z) {
/* 35 */     int totalMax = Integer.MIN_VALUE;
/* 36 */     for (Entry entry : this.entries) {
/* 37 */       int min = entry.getMin(seed, x, z);
/* 38 */       int max = entry.getMax(seed, x, z);
/* 39 */       if (min <= max && 
/* 40 */         max > totalMax) totalMax = max; 
/*    */     } 
/* 42 */     if (totalMax < 0) return Integer.MIN_VALUE; 
/* 43 */     return totalMax;
/*    */   }
/*    */   
/*    */   public static class Entry {
/* 47 */     public static final Entry[] EMPTY_ARRAY = new Entry[0];
/*    */     
/*    */     private final int block;
/*    */     
/*    */     private final int fluid;
/*    */     
/*    */     private final IDoubleCoordinateSupplier min;
/*    */     
/*    */     private final IDoubleCoordinateSupplier max;
/*    */     private final ICoordinateCondition mask;
/*    */     
/*    */     public Entry(int block, int fluid, IDoubleCoordinateSupplier min, IDoubleCoordinateSupplier max, ICoordinateCondition mask) {
/* 59 */       this.block = block;
/* 60 */       this.fluid = fluid;
/* 61 */       this.min = min;
/* 62 */       this.max = max;
/* 63 */       this.mask = mask;
/*    */     }
/*    */     
/*    */     public int getBlock() {
/* 67 */       return this.block;
/*    */     }
/*    */     
/*    */     public int getFluid() {
/* 71 */       return this.fluid;
/*    */     }
/*    */     
/*    */     public int getMax(int seed, int x, int z) {
/* 75 */       return MathUtil.floor(this.max.get(seed, x, z));
/*    */     }
/*    */     
/*    */     public int getMin(int seed, int x, int z) {
/* 79 */       return MathUtil.floor(this.min.get(seed, x, z));
/*    */     }
/*    */     
/*    */     public boolean shouldPopulate(int seed, int x, int z) {
/* 83 */       return this.mask.eval(seed, x, z);
/*    */     }
/*    */     
/*    */     public IDoubleCoordinateSupplier getMax() {
/* 87 */       return this.max;
/*    */     }
/*    */     
/*    */     public IDoubleCoordinateSupplier getMin() {
/* 91 */       return this.min;
/*    */     }
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public String toString() {
/* 97 */       return "Entry{, fluid=" + this.fluid + ", min=" + String.valueOf(this.min) + ", max=" + String.valueOf(this.max) + ", mask=" + String.valueOf(this.mask) + "}";
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\container\WaterContainer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */