/*     */ package org.bson;
/*     */ 
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
/*     */ public final class BsonInt64
/*     */   extends BsonNumber
/*     */   implements Comparable<BsonInt64>
/*     */ {
/*     */   private final long value;
/*     */   
/*     */   public BsonInt64(long value) {
/*  34 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(BsonInt64 o) {
/*  39 */     return (this.value < o.value) ? -1 : ((this.value == o.value) ? 0 : 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonType getBsonType() {
/*  44 */     return BsonType.INT64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getValue() {
/*  54 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public int intValue() {
/*  59 */     return (int)this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public long longValue() {
/*  64 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public double doubleValue() {
/*  69 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public Decimal128 decimal128Value() {
/*  74 */     return new Decimal128(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  79 */     if (this == o) {
/*  80 */       return true;
/*     */     }
/*  82 */     if (o == null || getClass() != o.getClass()) {
/*  83 */       return false;
/*     */     }
/*     */     
/*  86 */     BsonInt64 bsonInt64 = (BsonInt64)o;
/*     */     
/*  88 */     if (this.value != bsonInt64.value) {
/*  89 */       return false;
/*     */     }
/*     */     
/*  92 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  97 */     return (int)(this.value ^ this.value >>> 32L);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 102 */     return "BsonInt64{value=" + this.value + '}';
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonInt64.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */