/*     */ package org.bson;
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
/*     */ public final class BsonTimestamp
/*     */   extends BsonValue
/*     */   implements Comparable<BsonTimestamp>
/*     */ {
/*     */   private final long value;
/*     */   
/*     */   public BsonTimestamp() {
/*  34 */     this.value = 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonTimestamp(long value) {
/*  44 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonTimestamp(int seconds, int increment) {
/*  54 */     this.value = seconds << 32L | increment & 0xFFFFFFFFL;
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonType getBsonType() {
/*  59 */     return BsonType.TIMESTAMP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getValue() {
/*  70 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTime() {
/*  79 */     return (int)(this.value >> 32L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInc() {
/*  88 */     return (int)this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  93 */     return "Timestamp{value=" + 
/*  94 */       getValue() + ", seconds=" + 
/*  95 */       getTime() + ", inc=" + 
/*  96 */       getInc() + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(BsonTimestamp ts) {
/* 102 */     return Long.compareUnsigned(this.value, ts.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 107 */     if (this == o) {
/* 108 */       return true;
/*     */     }
/* 110 */     if (o == null || getClass() != o.getClass()) {
/* 111 */       return false;
/*     */     }
/*     */     
/* 114 */     BsonTimestamp timestamp = (BsonTimestamp)o;
/*     */     
/* 116 */     if (this.value != timestamp.value) {
/* 117 */       return false;
/*     */     }
/*     */     
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 125 */     return (int)(this.value ^ this.value >>> 32L);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonTimestamp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */