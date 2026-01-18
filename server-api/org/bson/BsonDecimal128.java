/*     */ package org.bson;
/*     */ 
/*     */ import org.bson.assertions.Assertions;
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
/*     */ public final class BsonDecimal128
/*     */   extends BsonNumber
/*     */ {
/*     */   private final Decimal128 value;
/*     */   
/*     */   public BsonDecimal128(Decimal128 value) {
/*  37 */     Assertions.notNull("value", value);
/*  38 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonType getBsonType() {
/*  43 */     return BsonType.DECIMAL128;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decimal128 getValue() {
/*  52 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  57 */     if (this == o) {
/*  58 */       return true;
/*     */     }
/*  60 */     if (o == null || getClass() != o.getClass()) {
/*  61 */       return false;
/*     */     }
/*     */     
/*  64 */     BsonDecimal128 that = (BsonDecimal128)o;
/*     */     
/*  66 */     if (!this.value.equals(that.value)) {
/*  67 */       return false;
/*     */     }
/*     */     
/*  70 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  75 */     return this.value.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  80 */     return "BsonDecimal128{value=" + this.value + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int intValue() {
/*  87 */     return this.value.bigDecimalValue().intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public long longValue() {
/*  92 */     return this.value.bigDecimalValue().longValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public double doubleValue() {
/*  97 */     return this.value.bigDecimalValue().doubleValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public Decimal128 decimal128Value() {
/* 102 */     return this.value;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonDecimal128.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */