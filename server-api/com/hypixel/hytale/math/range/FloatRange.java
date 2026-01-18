/*    */ package com.hypixel.hytale.math.range;
/*    */ 
/*    */ import com.hypixel.hytale.math.codec.FloatRangeArrayCodec;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FloatRange
/*    */ {
/* 13 */   public static final FloatRangeArrayCodec CODEC = new FloatRangeArrayCodec();
/*    */   private float inclusiveMin;
/*    */   private float inclusiveMax;
/*    */   private float range;
/*    */   
/*    */   public FloatRange() {
/* 19 */     this(0.0F, 0.0F);
/*    */   }
/*    */   
/*    */   public FloatRange(float inclusiveMin, float inclusiveMax) {
/* 23 */     this.inclusiveMin = inclusiveMin;
/* 24 */     this.inclusiveMax = inclusiveMax;
/* 25 */     this.range = inclusiveMax - inclusiveMin + 1.0F;
/*    */   }
/*    */   
/*    */   public float getInclusiveMin() {
/* 29 */     return this.inclusiveMin;
/*    */   }
/*    */   
/*    */   public float getInclusiveMax() {
/* 33 */     return this.inclusiveMax;
/*    */   }
/*    */   
/*    */   public void setInclusiveMin(float inclusiveMin) {
/* 37 */     this.inclusiveMin = inclusiveMin;
/* 38 */     this.range = this.inclusiveMax - inclusiveMin + 1.0F;
/*    */   }
/*    */   
/*    */   public void setInclusiveMax(float inclusiveMax) {
/* 42 */     this.inclusiveMax = inclusiveMax;
/* 43 */     this.range = inclusiveMax - this.inclusiveMin + 1.0F;
/*    */   }
/*    */   
/*    */   public float getFloat(float factor) {
/* 47 */     float value = this.inclusiveMin + this.range * factor;
/* 48 */     return Float.min(this.inclusiveMax, value);
/*    */   }
/*    */   
/*    */   public float getFloat(double factor) {
/* 52 */     float value = this.inclusiveMin + (float)(this.range * factor);
/* 53 */     return Float.min(this.inclusiveMax, value);
/*    */   }
/*    */   
/*    */   public boolean includes(float value) {
/* 57 */     return (value >= this.inclusiveMin && value <= this.inclusiveMax);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 62 */     if (this == o) return true; 
/* 63 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 65 */     FloatRange that = (FloatRange)o;
/*    */     
/* 67 */     if (Float.compare(that.inclusiveMin, this.inclusiveMin) != 0) return false; 
/* 68 */     if (Float.compare(that.inclusiveMax, this.inclusiveMax) != 0) return false; 
/* 69 */     return (Float.compare(that.range, this.range) == 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 74 */     int result = (this.inclusiveMin != 0.0F) ? Float.floatToIntBits(this.inclusiveMin) : 0;
/* 75 */     result = 31 * result + ((this.inclusiveMax != 0.0F) ? Float.floatToIntBits(this.inclusiveMax) : 0);
/* 76 */     result = 31 * result + ((this.range != 0.0F) ? Float.floatToIntBits(this.range) : 0);
/* 77 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 83 */     return "FloatRange{inclusiveMin=" + this.inclusiveMin + ", inclusiveMax=" + this.inclusiveMax + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\range\FloatRange.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */