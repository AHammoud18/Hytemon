/*    */ package com.hypixel.hytale.math.range;
/*    */ 
/*    */ import com.hypixel.hytale.math.codec.IntRangeArrayCodec;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IntRange
/*    */ {
/* 12 */   public static final IntRangeArrayCodec CODEC = new IntRangeArrayCodec();
/*    */   private int inclusiveMin;
/*    */   private int inclusiveMax;
/*    */   private int range;
/*    */   
/*    */   public IntRange() {
/* 18 */     this(0, 0);
/*    */   }
/*    */   
/*    */   public IntRange(int inclusiveMin, int inclusiveMax) {
/* 22 */     this.inclusiveMin = inclusiveMin;
/* 23 */     this.inclusiveMax = inclusiveMax;
/* 24 */     this.range = inclusiveMax - inclusiveMin + 1;
/*    */   }
/*    */   
/*    */   public int getInclusiveMin() {
/* 28 */     return this.inclusiveMin;
/*    */   }
/*    */   
/*    */   public int getInclusiveMax() {
/* 32 */     return this.inclusiveMax;
/*    */   }
/*    */   
/*    */   public void setInclusiveMin(int inclusiveMin) {
/* 36 */     this.inclusiveMin = inclusiveMin;
/* 37 */     this.range = this.inclusiveMax - inclusiveMin + 1;
/*    */   }
/*    */   
/*    */   public void setInclusiveMax(int inclusiveMax) {
/* 41 */     this.inclusiveMax = inclusiveMax;
/* 42 */     this.range = inclusiveMax - this.inclusiveMin + 1;
/*    */   }
/*    */   
/*    */   public int getInt(float factor) {
/* 46 */     int value = this.inclusiveMin + MathUtil.fastFloor(this.range * factor);
/* 47 */     return Integer.min(this.inclusiveMax, value);
/*    */   }
/*    */   
/*    */   public int getInt(double factor) {
/* 51 */     int value = this.inclusiveMin + MathUtil.floor(this.range * factor);
/* 52 */     return Integer.min(this.inclusiveMax, value);
/*    */   }
/*    */   
/*    */   public boolean includes(int value) {
/* 56 */     return (value >= this.inclusiveMin && value <= this.inclusiveMax);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 61 */     if (this == o) return true; 
/* 62 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 64 */     IntRange intRange = (IntRange)o;
/*    */     
/* 66 */     if (this.inclusiveMin != intRange.inclusiveMin) return false; 
/* 67 */     return (this.inclusiveMax == intRange.inclusiveMax);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 72 */     int result = this.inclusiveMin;
/* 73 */     result = 31 * result + this.inclusiveMax;
/* 74 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 80 */     return "IntRange{inclusiveMin=" + this.inclusiveMin + ", inclusiveMax=" + this.inclusiveMax + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\range\IntRange.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */