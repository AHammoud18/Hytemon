/*     */ package org.bson;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import org.bson.types.Decimal128;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BsonDouble
/*     */   extends BsonNumber
/*     */   implements Comparable<BsonDouble>
/*     */ {
/*     */   private final double value;
/*     */   
/*     */   public BsonDouble(double value) {
/*  38 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(BsonDouble o) {
/*  43 */     return Double.compare(this.value, o.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonType getBsonType() {
/*  48 */     return BsonType.DOUBLE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getValue() {
/*  57 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public int intValue() {
/*  62 */     return (int)this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public long longValue() {
/*  67 */     return (long)this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public Decimal128 decimal128Value() {
/*  72 */     if (Double.isNaN(this.value)) {
/*  73 */       return Decimal128.NaN;
/*     */     }
/*  75 */     if (Double.isInfinite(this.value)) {
/*  76 */       return (this.value > 0.0D) ? Decimal128.POSITIVE_INFINITY : Decimal128.NEGATIVE_INFINITY;
/*     */     }
/*     */     
/*  79 */     return new Decimal128(new BigDecimal(this.value));
/*     */   }
/*     */ 
/*     */   
/*     */   public double doubleValue() {
/*  84 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  89 */     if (this == o) {
/*  90 */       return true;
/*     */     }
/*  92 */     if (o == null || getClass() != o.getClass()) {
/*  93 */       return false;
/*     */     }
/*     */     
/*  96 */     BsonDouble that = (BsonDouble)o;
/*     */     
/*  98 */     if (Double.compare(that.value, this.value) != 0) {
/*  99 */       return false;
/*     */     }
/*     */     
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 107 */     long temp = Double.doubleToLongBits(this.value);
/* 108 */     return (int)(temp ^ temp >>> 32L);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 113 */     return "BsonDouble{value=" + this.value + '}';
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonDouble.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */