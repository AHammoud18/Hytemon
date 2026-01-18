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
/*     */ 
/*     */ 
/*     */ public final class BsonInt32
/*     */   extends BsonNumber
/*     */   implements Comparable<BsonInt32>
/*     */ {
/*     */   private final int value;
/*     */   
/*     */   public BsonInt32(int value) {
/*  36 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(BsonInt32 o) {
/*  41 */     return (this.value < o.value) ? -1 : ((this.value == o.value) ? 0 : 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonType getBsonType() {
/*  46 */     return BsonType.INT32;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValue() {
/*  55 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public int intValue() {
/*  60 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public long longValue() {
/*  65 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public Decimal128 decimal128Value() {
/*  70 */     return new Decimal128(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public double doubleValue() {
/*  75 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  80 */     if (this == o) {
/*  81 */       return true;
/*     */     }
/*  83 */     if (o == null || getClass() != o.getClass()) {
/*  84 */       return false;
/*     */     }
/*     */     
/*  87 */     BsonInt32 bsonInt32 = (BsonInt32)o;
/*     */     
/*  89 */     if (this.value != bsonInt32.value) {
/*  90 */       return false;
/*     */     }
/*     */     
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  98 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 103 */     return "BsonInt32{value=" + this.value + '}';
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonInt32.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */