/*    */ package org.bson;
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
/*    */ public class BsonDateTime
/*    */   extends BsonValue
/*    */   implements Comparable<BsonDateTime>
/*    */ {
/*    */   private final long value;
/*    */   
/*    */   public BsonDateTime(long value) {
/* 34 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int compareTo(BsonDateTime o) {
/* 39 */     return Long.compare(this.value, o.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public BsonType getBsonType() {
/* 44 */     return BsonType.DATE_TIME;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getValue() {
/* 53 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 58 */     if (this == o) {
/* 59 */       return true;
/*    */     }
/* 61 */     if (o == null || getClass() != o.getClass()) {
/* 62 */       return false;
/*    */     }
/*    */     
/* 65 */     BsonDateTime that = (BsonDateTime)o;
/*    */     
/* 67 */     if (this.value != that.value) {
/* 68 */       return false;
/*    */     }
/*    */     
/* 71 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 76 */     return (int)(this.value ^ this.value >>> 32L);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 81 */     return "BsonDateTime{value=" + this.value + '}';
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonDateTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */